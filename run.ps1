param([ValidateSet(0,1,2,3)][int]$Lab = 0, [switch]$Test)
$ErrorActionPreference = 'Stop'
[Console]::OutputEncoding = New-Object System.Text.UTF8Encoding($false)
$OutputEncoding = [Console]::OutputEncoding
$projects = @('lab01-first-class', 'lab02-constructors-static', 'lab03-inheritance')
$candidates = @()
if ($env:JAVA_HOME) { $candidates += Join-Path $env:JAVA_HOME 'bin/javac.exe' }
$command = Get-Command javac.exe -ErrorAction SilentlyContinue
if ($command) { $candidates += $command.Source }
$candidates += 'C:/Program Files/JetBrains/CLion 2025.3/jbr/bin/javac.exe'
$candidates += 'C:/Program Files/JetBrains/PyCharm 2025.1/jbr/bin/javac.exe'
foreach ($base in @('C:/Program Files/Java','C:/Program Files/Eclipse Adoptium',"$env:USERPROFILE/.jdks")) {
    if (Test-Path -LiteralPath $base) {
        $candidates += Get-ChildItem -LiteralPath $base -Filter javac.exe -Recurse -ErrorAction SilentlyContinue |
            Select-Object -ExpandProperty FullName
    }
}
$compiler = $null
foreach ($candidate in $candidates) {
    if (!(Test-Path -LiteralPath $candidate)) { continue }
    $versionText = (& $candidate -version 2>&1 | Out-String)
    if ($versionText -match 'javac ([0-9]+)' -and [int]$Matches[1] -ge 17) {
        $compiler = $candidate
        break
    }
}
if (!$compiler) { throw 'JDK 17+ not found. Set JAVA_HOME to a JDK installation.' }
$java = Join-Path (Split-Path $compiler) 'java.exe'
Write-Host "JDK: $compiler"
$selected = if ($Lab -eq 0) { @(1,2,3) } else { @($Lab) }
foreach ($number in $selected) {
    $project = Join-Path $PSScriptRoot $projects[$number - 1]
    $classes = Join-Path $project 'target/classes'
    New-Item -ItemType Directory -Force -Path $classes | Out-Null
    $sources = @(Get-ChildItem -LiteralPath (Join-Path $project 'src/main/java') -Filter '*.java' -Recurse |
        Select-Object -ExpandProperty FullName)
    if ($Test) {
        $sources += Get-ChildItem -LiteralPath (Join-Path $project 'src/test/java') -Filter '*.java' -Recurse |
            Select-Object -ExpandProperty FullName
    }
    & $compiler -encoding UTF-8 --release 17 -Xlint:all -d $classes @sources
    if ($LASTEXITCODE -ne 0) { throw "Compilation failed: lab $number" }
    $main = 'com.university.lab{0:d2}.{1}' -f $number, $(if ($Test) {'LabTest'} else {'Main'})
    Write-Host "`n=== Lab $number ==="
    & $java '-Dfile.encoding=UTF-8' '-Dstdout.encoding=UTF-8' '-Dstderr.encoding=UTF-8' '-Dsun.stdout.encoding=UTF-8' '-Dsun.stderr.encoding=UTF-8' -cp $classes $main
    if ($LASTEXITCODE -ne 0) { throw "Execution failed: lab $number" }
}
