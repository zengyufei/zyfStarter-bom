@echo off
cd %~dp0
cd..
cmd /k mvn clean deploy -Pdev -Dmaven.test.skip=true
pause