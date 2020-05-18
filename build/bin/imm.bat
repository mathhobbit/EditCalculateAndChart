REM @ECHO OFF
SET IOBLAKO_HOME=I:\work\ECalcAndChart
SET JAVA_HOME=I:\work\Java\jdk-13.0.2
SET PATH=%JAVA_HOME%\bin;%PATH%

SET MOD_DIR=%IOBLAKO_HOME%\build\modules
SET CLASSPATH=%MOD_DIR%\org.ioblako.core;%MOD_DIR%\org.ioblako.math;%MOD_DIR%\org.ioblako.edit;%MOD_DIR%\org.jfree;%MOD_DIR%\org.jfree.chart3d


javac -classpath %CLASSPATH% -d %MOD_DIR%\org.ioblako.core %IOBLAKO_HOME%\src\org\ioblako\core\*.java

FOR %%A IN (Fraction Matrix SolutionMatrix LinearSystemInterface SystemSolver LinearSystem) DO javac -classpath %CLASSPATH% -d %MOD_DIR%\org.ioblako.math %IOBLAKO_HOME%\src\org\ioblako\math\linearalgebra\%%A.java
FOR %%A IN (Complex PolynomialUtils) DO javac -classpath %CLASSPATH% -d %MOD_DIR%\org.ioblako.math %IOBLAKO_HOME%\src\org\ioblako\math\%%A.java
FOR %%A IN (ArrayUtils DoubleLGA PolynomialRootFinderJenkinsTraub PolynomialRootFinderLaguerre) DO javac -classpath %CLASSPATH% -d %MOD_DIR%\org.ioblako.math %IOBLAKO_HOME%\src\org\ioblako\math\%%A.java
FOR %%A IN (CalcFunction jc CalCexp CalCln CalCpowl CalCsqrt CalCsin CalCcos CalCtan CalCsinh CalCcosh CalCFE CalCEF SmartReplace CalClslv CalCasin CalCatan) DO javac -classpath %CLASSPATH% -d %MOD_DIR%\org.ioblako.math %IOBLAKO_HOME%\src\org\ioblako\math\calculator\%%A.java

javac -classpath %CLASSPATH% -d %MOD_DIR%\org.ioblako.math %IOBLAKO_HOME%\src\org\ioblako\math\calculator\*.java


FOR %%A IN (Plt_Quit_Action AbstractFramePlt AbstractFramePlt3D TextEdit FramePlt FindRight_Action FindLeft_Action Find_Action Copy_Action Open_Action Save_Action) DO javac -classpath %CLASSPATH% -d %MOD_DIR%\org.ioblako.edit %IOBLAKO_HOME%\src\org\ioblako\edit\%%A.java
FOR %%A IN (Quit_Action Paste_Action Cut_Action Calc_Action Undo_Action New_Action SaveAs_Action Redo_Action Delete_Action PltArgumentParser) DO javac -classpath %CLASSPATH% -d %MOD_DIR%\org.ioblako.edit %IOBLAKO_HOME%\src\org\ioblako\edit\%%A.java
FOR %%A IN (barPlt HelpFrame HelpWindowEvents Help_Action KAdaptor TEditUndoableEditListener WindowEvents TextAreaMouseListener TextAreaKeyListener DemoPanel) DO javac -classpath %CLASSPATH% -d %MOD_DIR%\org.ioblako.edit %IOBLAKO_HOME%\src\org\ioblako\edit\%%A.java

javac -classpath %CLASSPATH% -d %MOD_DIR%\org.ioblako.edit %IOBLAKO_HOME%\src\org\ioblako\edit\*.java


FOR %%A IN (org.ioblako.core org.ioblako.math org.jfree org.jfree.chart3d) DO javac -classpath %CLASSPATH% -p %MOD_DIR%  -d %MOD_DIR%\%%A %IOBLAKO_HOME%\src\%%A\module-info.java 

FOR %%A IN (org.ioblako.core org.ioblako.math org.jfree org.jfree.chart3d) DO jar --create --file %IOBLAKO_HOME%\build\lib\%%A.jar -C %MOD_DIR%\%%A .
FOR %%A IN (org.ioblako.core org.ioblako.math org.jfree org.jfree.chart3d) DO jmod  create -p %MOD_DIR%\%%A --module-path  %MOD_DIR% --class-path %IOBLAKO_HOME%\build\lib\%%A.jar   %IOBLAKO_HOME%\build\jmods\%%A.jmod 


javac -classpath %CLASSPATH% -p %MOD_DIR%  -d %MOD_DIR%\org.ioblako.edit %IOBLAKO_HOME%\src\org.ioblako.edit\module-info.java 
xcopy /S/E %IOBLAKO_HOME%\build\Images %MOD_DIR%\org.ioblako.edit\org\ioblako\edit
jar --create --file %IOBLAKO_HOME%\build\lib\org.ioblako.edit.jar -C %MOD_DIR%\org.ioblako.edit .
jmod  create -p %MOD_DIR%\org.ioblako.edit --module-path  %MOD_DIR%  --class-path %IOBLAKO_HOME%\build\lib\org.ioblako.edit.jar --main-class org.ioblako.edit.EditCalculateAndChart  %IOBLAKO_HOME%\build\jmods\org.ioblako.edit.jmod


FOR %%A IN (org.ioblako.core org.ioblako.math org.jfree org.jfree.chart3d) DO jmod  create -p %MOD_DIR%\%%A --module-path  %IOBLAKO_HOME%\build\jmods  --class-path %IOBLAKO_HOME%\build\lib\%%A.jar  --hash-modules org.ioblako.edit  %IOBLAKO_HOME%\build\%%A.jmod 

FOR %%A IN (org.ioblako.core org.ioblako.math org.jfree org.jfree.chart3d) DO DEL %IOBLAKO_HOME%\build\jmods\%%A.jmod
FOR %%A IN (org.ioblako.core org.ioblako.math org.jfree org.jfree.chart3d) DO MOVE %IOBLAKO_HOME%\build\%%A.jmod %IOBLAKO_HOME%\build\jmods\%%A.jmod

