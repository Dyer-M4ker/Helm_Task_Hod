param(
    [Parameter(Mandatory = $true)]
    [string]$Image
)

$result = (docker run --rm $Image add 2 3).Trim()
Write-Host "Container output:"
Write-Host $result

if ($result -notmatch 'Result:\s+5(\.0)?') {
    Write-Error "Unexpected calculator result"
    exit 1
}
