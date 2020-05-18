REM @ECHO OFF
SET IOBLAKO_HOME=I:\work\ECalcAndChart
SET JAVA_HOME=I:\work\Java\jdk-13.0.2
SET PATH=%JAVA_HOME%\bin;%PATH%
SET MOD_DIR=%IOBLAKO_HOME%\build\modules

rmdir /s /q %IOBLAKO_HOME%\build\jre

jlink --module-path %JAVA_HOME%\jmods;%IOBLAKO_HOME%\build\jmods --add-modules org.ioblako.math,org.ioblako.core,org.jfree,org.jfree.chart3d,org.ioblako.edit --launcher ecc=org.ioblako.edit/org.ioblako.edit.EditCalculateAndChart  --output %IOBLAKO_HOME%\build\jre


