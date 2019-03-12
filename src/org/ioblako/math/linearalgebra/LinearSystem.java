/*
 * Copyright (C) 2019 Sergey Nikitin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package org.ioblako.math.linearalgebra;

/**
 * A datatype that holds a number of coefficient-fronted variables  of degree 1 that are
 * paired with constant coefficients in the form Ax = B. This class also
 * contains methods used to solve linear systems, i.e. discover whether or not
 * a linear system has one singular solution with specifics values for variables
 * so that A = B, or whether or not there are multiple ways to specify variable
 * values to form a solution.
 * @author Robert
 */
public class LinearSystem implements Cloneable, java.io.Serializable
{
   public static final long serialVersionUID=100L;
   public String Report="";
    /**
     * In <tt>Ax = b</tt>, this matrix represents <tt>Ax</t>
     */
    private Matrix sysA = null;

    /**
     * In <tt>Ax = b</tt>, sysA represents <tt>b</tt>
     */
    private Matrix sysB = null;
    
    /**
     * The system of solutions, represented as a Matrix
     */
    public SolutionMatrix sysX = null;

    /**
     * Creates a linear system of equations, where Ax = b
     * @param A A matrix of size at least 1-by-1
     * @param B A column of coefficients with at least 1 row
     */
    public LinearSystem(Matrix A, Matrix B)
    {
        sysA=new Matrix(A.getArrayCopy());
        sysB=new Matrix(B.getArrayCopy());
        Report="";
    }

    /**
     * Creates two matrices from two 2D fractional arrays
     * @param aDat An array of fractions forming the Ax  portion for Ax =b
     * @param bDat An array of fractions forming the b  portion for Ax = b
     */
    public LinearSystem(Fraction[][] aDat, Fraction[] bDat)
    {
        //mA = aDat;
        //mB = bDat;
        sysA = new Matrix(aDat);
        sysB = new Matrix(bDat);
        Report="";
    }

    /**
     * Clones the calling LinearSystem object
     * @return A copy of the calling LinearSystem without directly accessing its
     * contents
     */
    public Object clone()
    {
        return this.copy();
    }

    /**
     * Makes a deep copy of the LinearSystem by creating new objects from its
     * essential properties
     * @return A LinearSystem that copies the calling object's members
     */
    public LinearSystem copy()
    {
        return new LinearSystem(new Matrix(this.sysA.getArrayCopy()), new Matrix(this.sysB.getArrayCopy()));
    }

    /**
     * This method determines the solution(s) to the linear system described
     * in AX = B and puts it into internal storage
     */
    public void determineSolutions()
    {
        sysX = solve();
    }
public String getSolutionForJC(){
        return sysX.getSolutionForJC();
}

    public Matrix getA()
    {
        return new Matrix(sysA.getArrayCopy());
    }

    public Matrix getB()
    {
        return new Matrix(sysB.getArrayCopy());
    }

    /**
     * Retrieves the solution(s) of this linear system, if it has been determined
     * @return An object representing the solution(s) to this linear system; null
     * if it hasn't been determined
     */
    public Matrix getSolutions()
    {
        return sysX;
    }

    boolean isConsistent(Matrix reducedMatrix)
    {
        /*
        if the number of rows in B is not the same as
        the number of rows in A, the system is not consistent
        */
        if(sysB.m() != sysA.m())
        {
            return false;
            // System.out.println("rows A != rows B");
        }
        /*if the rank of matrix A is not equal to the rank
         *of matrix A|B, the system is inconsistent.
        */
        else if(Matrix.rank(reducedMatrix) != Matrix.rank(sysA, false))
        {
            return false;
        }
        return true;
    }

    /**
     * Uses the <tt>SolutionMatrix</tt> class's {@link SolutionMatrix#toString()}
     * method to show a representation of the solution
     * @return A String representation of the solution(s) to the linear system
     */
    public String showSolutions()
    {
        return sysX.toString();
    }

    /**
     *
     * @return
     */
    public SolutionMatrix solve()
    {
           Report="";
//SERGEY for first run comment out the stuff bellow and replace with return null

        Matrix reduced = SystemSolver.toRREF(this);
        Matrix reducedA = reduced.getSubmatrix(0, 0, sysA.m(), sysA.n());
        Matrix reducedB = reduced.getSubmatrix(0, sysA.n(), sysA.m(), sysA.n() + 1);
        LinearSystem reducedSystem = new LinearSystem(reducedA, reducedB);

        int rank = Matrix.rank(reduced);

        //Finds solutions only if the system is consistent
        if(isConsistent(reduced))
        {
            //System.out.println("Consistent system found.");
            //Report=String.format("%s%s%s",Report,System.lineSeparator(),"Consistent system found.");
            if (sysA.m() == sysA.n())
            {
                //System.out.println("Analyzing square system of equations...");
                //Report=String.format("%s%s%s",Report,System.lineSeparator(),"Analyzing square system of equations..");
                if (rank < sysA.m())
                {
                    //System.out.println("Infinitely many solutions");
                Report=String.format("%s%s%s%s",Report,System.lineSeparator(),"Infinitely many solutions",System.lineSeparator());
                   
                    return SystemSolver.solveForMultipleSolutions(reducedSystem);
                }
                //System.out.println("Unique solution");
                Report=String.format("%s%s%s%s",Report,System.lineSeparator(),"Unique solution",System.lineSeparator());
              
                return SystemSolver.solveForSingleSolution(reducedSystem);
            }
            //getA().show();
            else if (sysA.m() < sysA.n())
            {
               // System.out.println("System of equations is undeterdetermined.\n"
                //        + "Infinite solutions are possible.");
                Report=String.format("%s%s%s%s%s",Report,System.lineSeparator(),"System of equations is underdetermined.",System.lineSeparator(),"Infinite solutions are possible.");
                return SystemSolver.solveForMultipleSolutions(reducedSystem);
            }
            //System.out.println("Anylzing overdetermined system of equations...");
                Report=String.format("%s%s%s",Report,System.lineSeparator(),"Overdetermined system of equations...");
            if (rank < sysA.n())
            {
             //   System.out.println("Infinite solutions are possible.");
                Report=String.format("%s%s%s",Report,System.lineSeparator(),"Infinite solutions are possible.");
                return SystemSolver.solveForMultipleSolutions(reducedSystem);
            }
          //  System.out.println("Unique solution found.");
                Report=String.format("%s%s%s%s",Report,System.lineSeparator(),"Unique solution",System.lineSeparator());
            return SystemSolver.solveForSingleSolution(reducedSystem);
        }
        //Inconsistent matrix returns a matrix holding a zero, just because this
        //method has to return something
        return new SolutionMatrix(Fraction.ZERO);
    }

    /**
     * Shows both systems as numerators over denominator Ax = b
     * @return A string that shows all the elements of the system
     */
    public String toString()
    {
    	int maxLengthA = 0; int maxLengthB = 0;
        int denomLengthA = 0; int numerLengthA = 0; int denomLengthB = 0; int numerLengthB = 0;
        for (int mx = 0; mx < sysA.m(); mx++)
        {
            for (int nx = 0; nx < sysA.n(); nx++)
            {
                numerLengthA = sysA.get(mx, nx).numerator().toString().length();
                denomLengthA = sysA.get(mx, nx).denominator().toString().length();
                if (Math.max(numerLengthA, denomLengthA) > maxLengthA)
                    maxLengthA = Math.max(numerLengthA, denomLengthA);
            }
            numerLengthB = sysB.get(mx, 0).numerator().toString().length();
            denomLengthB = sysB.get(mx, 0).denominator().toString().length();
            if (Math.max(numerLengthB, denomLengthB) > maxLengthB)
                maxLengthB = Math.max(numerLengthB, denomLengthB);
        }
		String s= "";
        for (int mx = 0; mx < sysA.m(); mx++)
        {
            //Print numerator of Ax
            for (int nx = 0; nx < sysA.n(); nx++)
            {
                s += sysA.get(mx, nx).numerator();
                for (int i = sysA.get(mx, nx).numerator().toString().length(); i < maxLengthA; i++)
                    s += " ";
                s += "\t";  
            }

            //Print numerator of B
            s += " \t" + sysB.get(mx, 0).numerator();
            s += "\n";

            //Print dividing line between numerator and denominator for Ax
            for (int nx = 0; nx < sysA.n(); nx++)
            {
                for (int i = 0; i < maxLengthA; i++)
                    s += "-";
                s += "\t";
            }
            s += "=\t";

            //Print dividing line between numerator and denomiantor for B
            for (int nx = 0; nx < maxLengthB; nx++)
                s += "-";
            s += "\n";

            //Print denominator for Ax
            for (int nx = 0; nx < sysA.n(); nx++)
            {
                s += sysA.get(mx, nx).denominator();
                for (int i = sysA.get(mx, nx).denominator().toString().length(); i < maxLengthA; i++)
                    s += " ";
                s += "\t";
            }

            //Print denominator for B
            s += " \t" + sysB.get(mx, 0).denominator();
            s += "\n";
            s += "\n";
		}
		return s;
    }//end toString

public String getReport(){
     return Report;
}
public void cleanReport(){
  Report="";
}
}//end LinearSystem class
