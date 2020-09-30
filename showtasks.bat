call runcrud.bat
goto runbrowser
echo.
echo RUNCRUD.BAT has errors - breaking work
goto fail

:runbrowser
echo.
echo run browser
start chrome http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end
echo.
echo RUN CHROME has errors - breaking work
goto fail

:fail
echo.
echo showtasks.bat - There were errors

:end
echo.
echo showtasks.bat - Work is finished.