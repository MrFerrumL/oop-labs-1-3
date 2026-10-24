@echo off
chcp 65001 >nul
powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%~dp0..\run.ps1" -Lab 1
set "result=%errorlevel%"
pause
exit /b %result%
