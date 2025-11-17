This directory contains original algorithms presented in the form of articles and implemented in EditCalculateAndChart.
MP({matrix}) implements Moore-Penrose inverse algorithm from
"Algorithm for calculating and updating Moore-Penrose inverse." by S. Nikitin (09/25)
see
myMoorePenroseSolution.pdf

Examples.
MP({1,2;0,1})={1,-2;0,1}
MP({1,2;0,1;1,1})={0,-1,1;1/3,2/3,-1/3}
========================
FE(n) returns the integer p. FE calculates integers s, p and q such that 2^s * n * q = 2^s*(2^p - 1). 
 FE stays for Ferma, Euler statement c^E(a)=1 mod(a) for coprime a and c, 
where E(a) is the Euler function:
 the count of numbers coprime with and smaller than a.
FE returns the length of the orbit of "2" in the multiplicative group of the ring Z/n*Z

and


EF(r,n) returns the integer s = s(r,n). EF calculates smallest integers k, s and q such that  n * q = r^k*(r^s - 1). 
 EF stays for Euler-Fermat algorithm related
to statement c^E(a)=1 mod(a) for coprime a and c, 
where E(a) is the Euler function:
 the count of numbers coprime with and smaller than a.
If r and n are coprime then EF returns the length of the orbit of "r" in the multiplicative group of the ring Z/(n*Z).
 See also function FE which is equal to EF(2,n).

Functions FE and EF are described in
"Euler-Fermat algorithm." by S. Nikitin (09/18)
see
Euler_Fermat_algorithm.pdf

polynomMin(step,left..right,{a0,a1,..,an}) returns the minimum of a0+a1*x+...+an*x^n 
on the interval [left,right] with the precision "step"
It is implemented in double precision format.
polynomMin is based on the algorithm developed in

"Leap Gradient Algorithm. The Fastest Method for Univariate Polynomial Extrema." by S. Nikitin (04/2014)

see
Leap_Gradient.pdf

The directory "Examples" containes numerous files created by EditCalculateAndChart and used in calculus classes (lectures) conducted by S. Nikitin at ASU.

Have fun!!!




