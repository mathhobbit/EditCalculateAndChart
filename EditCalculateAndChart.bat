REM @ECHO OFF
REM SET CLASSPATH=build\lib\org.ioblako.core.jar;build\lib\org.ioblako.edit.jar;build\lib\org.ioblako.math.jar;build\lib\org.jfree.jar;build\lib\org.jfree.chart3d.jar
SET CLASSPATH=build\lib\org.ioblako.edit.resources.jar

build\jre\bin\java -cp %CLASSPATH%  -m org.ioblako.edit/org.ioblako.edit.EditCalculateAndChart 
