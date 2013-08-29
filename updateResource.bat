@echo off
xcopy "src\main\resources\*.*" "target\classes\" /S /E /C /R /Y /U /F
pause>nul