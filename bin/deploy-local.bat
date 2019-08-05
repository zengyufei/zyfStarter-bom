@echo off
cd %~dp0
cd..
cmd /k mvn clean deploy -Plocal -Dmaven.test.skip=true
pause