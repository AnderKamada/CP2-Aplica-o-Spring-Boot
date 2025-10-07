# scripts/smoke-tests.ps1  (ASCII only)
$ErrorActionPreference = 'Stop'
$base = "http://localhost:8080"

function Assert-Status([object]$resp, [int]$expected, [string]$name) {
    $code = [int]$resp.StatusCode
    if ($code -ne $expected) {
        throw "$name -> expected $expected got $code. Body: $($resp.Content)"
    }
    Write-Host ("OK: {0} ({1})" -f $name,$code) -ForegroundColor Green
}

# 1) create author (201)
$createAuthorBody = @'
{
  "name": "Ada Lovelace",
  "email": "ada@lovelace.dev"
}
'@
$resp = Invoke-WebRequest -Uri "$base/authors" -Method Post -ContentType "application/json" -Body $createAuthorBody
Assert-Status $resp 201 "create author"
$authorId = (ConvertFrom-Json $resp.Content).id

# 2) list authors (200)
$resp = Invoke-WebRequest -Uri "$base/authors" -Method Get
Assert-Status $resp 200 "list authors"

# 3) get author by id (200)
$resp = Invoke-WebRequest -Uri "$base/authors/$authorId" -Method Get
Assert-Status $resp 200 "get author by id"

# 4) update author (200)
$updateAuthorBody = @{
    id    = $authorId
    name  = "Ada L."
    email = "ada@lovelace.dev"
} | ConvertTo-Json
$resp = Invoke-WebRequest -Uri "$base/authors/$authorId" -Method Put -ContentType "application/json" -Body $updateAuthorBody
Assert-Status $resp 200 "update author"

# 5) create book (201)
$createBookBody = @{
    title    = "Notes on the Analytical Engine"
    isbn     = "ISBN-001"
    authorId = $authorId
} | ConvertTo-Json
$resp = Invoke-WebRequest -Uri "$base/books" -Method Post -ContentType "application/json" -Body $createBookBody
Assert-Status $resp 201 "create book"
$bookId = (ConvertFrom-Json $resp.Content).id

# 6) list books (200)
$resp = Invoke-WebRequest -Uri "$base/books" -Method Get
Assert-Status $resp 200 "list books"

# 7) books by author (200)
$resp = Invoke-WebRequest -Uri "$base/authors/$authorId/books" -Method Get
Assert-Status $resp 200 "books by author"

# 8) not found author (404)
try {
    $ok = Invoke-WebRequest -Uri "$base/authors/999999" -Method Get -ErrorAction Stop
    throw "author not found -> expected 404 but got $($ok.StatusCode)"
}
catch {
    $code = [int]($_.Exception.Response.StatusCode.value__)
    if ($code -ne 404) { throw "author not found -> expected 404 got $code" }
    Write-Host "OK: author not found (404)" -ForegroundColor Green
}

# 9) validation error (400)
try {
    $badBody = @{
        title    = ""
        isbn     = "ERR"
        authorId = $authorId
    } | ConvertTo-Json
    $bad = Invoke-WebRequest -Uri "$base/books" -Method Post -ContentType "application/json" -Body $badBody -ErrorAction Stop
    throw "validation error -> expected 400 but got $($bad.StatusCode)"
}
catch {
    $code = [int]($_.Exception.Response.StatusCode.value__)
    if ($code -ne 400) { throw "validation error -> expected 400 got $code" }
    Write-Host "OK: validation error (400)" -ForegroundColor Green
}

# 10) delete book (204)
$resp = Invoke-WebRequest -Uri "$base/books/$bookId" -Method Delete -ErrorAction SilentlyContinue
Assert-Status $resp 204 "delete book"

# 11) delete author (204)
$resp = Invoke-WebRequest -Uri "$base/authors/$authorId" -Method Delete -ErrorAction SilentlyContinue
Assert-Status $resp 204 "delete author"

Write-Host "All smoke tests passed!" -ForegroundColor Cyan
