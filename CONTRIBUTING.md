How to contribute?
------------------

Thank you for your interest in EditCalculateAndChart.

There are many ways one can contribute to this project.  

 1. If you are using EditCalculateAndChart in your research, then you can submit examples of your usage and it can be posted to EditCalculateAndChart Wiki pages.

2. EditCalculateAndChart has two parts. One part is responsible for user graphical interface,  “org.ioblako.edit”. The most important action in this package is “Calc_Action.java”. If you commit some code to this package please start your git message with “WUI” which is the abbreviation for Windows User Interface.

3. The second part is located in “org.ioblako.math.calculator”. The engine that drives all the calculations and parses the  input is in “jc.java”. To write your own function (say with the name “MyRoot”) you need to create the class with the name CalCMyRoot in the package “org.ioblako.math.calculator” and this class should implement the interface CalcFunction. When you commit to “org.ioblako.math.calculator”  please start your git message with “JC” which stays for “Java Calculator”.   The package “org.ioblako.math.calculator” also contains the classes responsible for graphical interpretation of data. Those classes implement either the interface AbstractFramePlt (for 2D graphs) or AbstractFramePlt3D (for 3D graphs).   

Have fun with EditCalculateAndChart!
