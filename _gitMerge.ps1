#@echo off

$branchName = "origin/JIRAPM-26438_logutil"
$result = git merge $branchName 2>&1

if ($LASTEXITCODE -eq 0) {

    Write-Host "Success"
    # Продолжайте выполнение вашего кода здесь

} elseif ($result -match "not something we can merge") {

    Write-Host "No branch found"
    # Продолжайте выполнение вашего кода здесь

} else {

    Write-Host "Error happened:"
    Write-Host $result
    Write-Host "See above for details"
    exit 13
}
pause
$host.UI.RawUI.ReadKey()
