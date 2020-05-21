<img src="images/Icon128.png" align="right" />

# Edit Calculate and Chart


The calculator has the following functions/operations:

> "2bdcm", "2qt", "2dbl", "rref", "I"," ceil",
> "floor", "dgrs", "rdns", "cosh", "sinh", "tanh",
> "cbrt", "log10", "expm1", "ln1p", "asin", "sin",
> "acos", "cos", "atan", "tan", "ln", "exp", "sqrt",
> "abs", "cross", "dot", "powb", "atan2", "hypot", 
> "min", "max", "det", "rank", "inv", "lslv", "random",
> "date", "roots", "rroots", "polynomMin", "Int", "Seq",
> "sum", "memory", "clear", "<-", "->", "plot", "xyPlt",
> "xyzPlt", "gr3DPlt", "vfPlt", "xyLinePlt", "linePlt", "barPlt",
> "crvPlt","EF", "FE", "mod", "modInverse", "pow", "getNR"


  One can get a quick help by evaluating ?name.
For example,

"?sin"
 
will return some information about sin(x).
 The procedure "Int" can calculate iterated integrals.
 "lslv"  solves systems of linear equations which can be underdetermined.
 For example, "lslv({1,1;1,-2},{1;1})" will solve the following system of linear equations
x+y=1,
x-2y=1.
"rroots" finds all real roots for a polynomial with real coefficients, e.g., "rroots({1,0,0,1})" yields "-1". On the other hand, "roots" calculate all roots for a polynomial, e.g., 
"roots({1,0,0,1})" 
returns 
"{-1;0.5,0.8660254;0.5,-0.8660254}".
"polynomMin" finds the minimum of a real polynomial on a given interval, e.g., 
"polynomMin(0.01,-8..1,{1,2,1})" returns "-1", where "0.01" is the precision. "polynomMin" is based on the recently discovered algorithm (Leap Gradient Algorithm) presented in
"https://arxiv.org/abs/1405.5548".  

EF and FE implement Euler-Fermat algorithm introduced in "Euler-Fermat algorithm" posted at
"http://ResearchGate.net".

A<-{1,1;1,2} defines a 2x2 matrix A. "inv(A)" delivers the inverse of "A". "det(A)" yields the determinant of "A". "rank(A)"  calculates the rank of "A". "rref(A)" delivers the reduced row echelon form for "A".
"A<-eval(Seq(x^2-1,x^3-x,x={0.01,-2..2}))" evaluates and stores the sequence 
"Seq(x^2-1,x^3-x,x={0.01,-2..2})" into "A".   "xyPlt({A})" creates the plot for "A". "xyzPlt" is for 3D plots. For example,

C<-eval(Seq(t,cos(t),sin(t),t={0.1,-pi..pi}))<br/>
A<-eval(Seq(t,t,t,t={0.1,-pi..pi}))


xyzPlt(title=3D curves,subtitle=helix,line,helix,A,C)

"vfPlt" is a vector field plot. crvPlt({t^2-a,t^3-a*t},t={0.01,-2..2},a={0.1,-1..1}) will plot a cubic curve depending on a parameter "a".


## Compiling

One needs the following
 
- [openjdk](https://github.com/openjdk/jdk)
- [orson-charts](http://github.com/jfree/orson-charts)
- [jfreechart](http://github.com/jfree/jfreechart) 


First, you need to build/ java, it needs to be version higher than 11.

Second, you need to build orson-charts and jfreechart and  "jar -xvf" the respective jar files in  

EditCalculateAndChart/build/modules/org.jfree.chart3d
(for orson-charts)

and

EditCalculateAndChart/build/modules/org.jfree
(for jfreechart)


Finally, in the scripts from EditCalculateAndChart/build/bin

edit the line 

IOBLAKO_HOME=/Users/sergey_nikitin/work/EditCalculateAndChart

and put the location of EditCalculateAndChart in your system.

In the line  

JDK=jvm/openjdk-15-internal

put location of your JDKi, in my situation it is relative to /usr/local.

If you are building EditCalc... with MS Windows then you need to edit *.bat files
from EditCalculateAndChart/build/bin.


On Unix, you go to 

EditCalculateAndChart/build/bin

and run

<br/>imm
<br/>link

and it will build the application. The script "ecc" will run it.

On MS Windows you need to use the respective *.bat files.

Good luck! 








