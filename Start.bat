@echo off
set JAR_PATH=%~dp0target\RecipeBook-0.0.1-SNAPSHOT.jar
set VBS_FILE=%TEMP%\invisible_launcher.vbs

echo Set WshShell = CreateObject("WScript.Shell") > "%VBS_FILE%"
echo WshShell.Run "java -jar ""%JAR_PATH%""", 0, False >> "%VBS_FILE%"

cscript //nologo "%VBS_FILE%"
del "%VBS_FILE%"
