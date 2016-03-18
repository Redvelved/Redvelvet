@echo on
rem
cd /d %~dp0
call java -jar mybatis-generator-core-1.3.2.4.jar -configfile generatorConfig.xml -overwrite

pause