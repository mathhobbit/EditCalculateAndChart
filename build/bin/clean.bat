@ECHO OFF

SET IOBLAKO_HOME=C:\cygwin64\home\serge\EditCalculateAndChart
SET MOD_DIR=%IOBLAKO_HOME%\build\modules
FOR %%A IN (core math) DO IF EXIST "%MOD_DIR%\org.ioblako.%%A\org"   RMDIR /s /q "%MOD_DIR%\org.ioblako.%%A\org" 
IF EXIST "%MOD_DIR%\org.ioblako.edit\org\ioblako\edit"   DEL  "%MOD_DIR%\org.ioblako.edit\org\ioblako\edit\*.class"  
FOR %%A IN (core math edit) DO IF EXIST "%MOD_DIR%\org.ioblako.%%A\module-info.class"   DEL "%MOD_DIR%\org.ioblako.%%A\module-info.class"  
FOR %%A IN (core math edit) DO IF EXIST "%IOBLAKO_HOME%\build\lib\org.ioblako.%%A.jar"   DEL "%IOBLAKO_HOME%\build\lib\org.ioblako.%%A.jar"  
FOR %%A IN (core math edit) DO IF EXIST "%IOBLAKO_HOME%\build\jmods\org.ioblako.%%A.jmod"   DEL "%IOBLAKO_HOME%\build\jmods\org.ioblako.%%A.jmod"  
IF EXIST "%IOBLAKO_HOME%\build\jmods\org.jfree.jmod" DEL  "%IOBLAKO_HOME%\build\jmods\org.jfree.jmod"
IF EXIST "%IOBLAKO_HOME%\build\jmods\org.jfree.chart3d.jmod" DEL  "%IOBLAKO_HOME%\build\jmods\org.jfree.chart3d.jmod"



