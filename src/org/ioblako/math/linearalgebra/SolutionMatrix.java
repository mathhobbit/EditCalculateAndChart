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
 * A specialized version of Matrix that represents a solution that
 * may be printed as a string. The reason this class is necessary is that an
 * implementation of a Matrix as a solutions list would be largely
 * interpretive. This class allows a solution matrix to be stored with
 * defined meaning attached to the contents of the matrix when the results of
 * any matrix operations are performed.
 * @author Robert Fruchtman
 */
public class SolutionMatrix extends Matrix implements java.io.Serializable
{
public static final long serialVersionUID = 30L;
    /**
     * This boolean is necessary if the rank of A != the rank A|B, which means
     * that no solutions exist.
     */
    private boolean isConsistent = true;

    /**
     * If the matrix is square, return an output that recognizes the
     * answer as such.
     */
    private boolean isSquare;
    
    /**
     * If there is only one answer to the matrix, this boolean will be true.
     */
    private boolean oneAnswer = true;

    /**
     * When there are infinite solutions to solve a system of linear equations,
     * the first row of this matrix represents which columns (variables) are
     * in the basis, and the second row represents which columns (variables)
     * are arbitrary. The second row's nonzero elements are numbered, to indicate
     * the discreteness of each arbitrary value.
     */
    private int[][] basisMap;

    /**
     * This matrix is only to be used when the number of solutions is infinite
     * and displaying the solution requires solution matrix B. This is where B
     * is to be stored.
     */
    private Fraction[][] B;

    /**
     * This is the constructor that should be used if there is only one
     * solution for given matrices A and B in BigDecimalLinearSystem.
     * @param matr The solutions matrix, generated as a result of creating
     * a BigDecimalLinearSystem object and solving it.
     */
    public SolutionMatrix(Fraction[] matrData)
    {
        super(matrData);
        if (matrData.length == matrData.length)
            isSquare = true;
        for (int nx = 0; nx < matrData.length; nx++)
        {
            //Make sure every variable in the matrix has a value--
            //otherwise, the matrix has more than one answer.
            if (matrData[nx] == null)
            {
                throw new NumberFormatException("Null element found in solution matrix");
            }//end if
        }//end for
    }//end constructor

    /**
     * This single-value constructor is used when a system of linear equations is
     * inconsisent. The only reason this constructor takes a parameter and calls
     * the superclass is to satisfy the structure of the program. The only necessary
     * action of this constructor is to mark its solution as inconsisent.
     * @param singleValue A fractional value that doesn't matter, since it won't
     * set any variable
     */
    public SolutionMatrix(Fraction singleValue)
    {
        super(1, 1);
        isConsistent = false;
    }

    /**
     * This constructor is used when the solution to a system of linear equations
     * contains infinitely many solutions.
     * @param matrix The reduced row echelon form of A
     * @param b The reduced row echelon form of B
     * @param basis The list of columns (variables) that must be non-arbitrary to
     * form the solution
     */
    public SolutionMatrix(Fraction[][] matrix, Fraction[][] b, int[][] basis)
    {
        super(matrix);
        basisMap = basis;
        B = b;
        for (int nx = 0; nx < n(); nx++)
            //If x is in the basis, find what its factors are
            if (basisMap[0][nx] == 1)
            {
                int lastNonZero = 0;
                for (int r = 0; r < m(); r++)
                {
                    if (get(r, nx).compareTo(Fraction.ZERO) != 0)
                        lastNonZero = r;
                }
                //Divide each item in the row by the coefficient of its x
                Fraction rowDivisor = get(lastNonZero,nx);
                b[lastNonZero][0] = b[lastNonZero][0].divide(rowDivisor);
                for (int cx = 0; cx < n(); cx++)
                    if (cx != nx && get(lastNonZero, cx).compareTo(Fraction.ZERO) != 0)
                        //Divide each row by the x value for that row
                        set(lastNonZero, cx, get(lastNonZero,cx).divide(rowDivisor));
            }

        oneAnswer = false;

        if (matrix.length != matrix[0].length)
            isSquare = false;
        else isSquare = true;
    }

    /**
     * Produces a string containing the solution for the matrix.
     * @return A series of equations describing the solution using fractional numbers
     */
    public String toString()
    {
        return toString(false);
    }

/**
 * A string that represents the solution in the calculator text edit field.
 */
String toJC="";
public String getSolutionForJC(){

if(toJC.contentEquals(""))
  this.toString(false);
  if(oneAnswer)
    return String.format("%s%s",toJC,"}"); 
   if(!isConsistent)
     return "";
if(!toJC.contentEquals(""))
     toJC=String.format("%s%s",toJC,"}+cspan({");
int ZeroDim=0;
for(int s=0;s<n();s++)
      if(basisMap[0][s]==0)
          ZeroDim++;

for(int i=0;i<n();i++){
     if(toJC.endsWith(","))
        toJC=toJC.substring(0,toJC.length()-1);
    if(i!=0)
      toJC=String.format("%s%s",toJC,";"); 


   if(basisMap[0][i]!=0){  
    for (int j =0;j<n();j++)
      if(basisMap[0][j]==0 ){
       if(i<m())
          toJC=String.format("%s%s%s",toJC,get(i,j).negate().toPlainString(),",");
        else
            toJC=String.format("%s%s%s",toJC,"0",",");
       }
   }
  else{
     //System.out.println("basisMap[1]["+i+"]="+basisMap[1][i]);
      for(int p=0;p<ZeroDim;p++)
          if(basisMap[1][i]==p+1) 
                toJC=String.format("%s%s%s",toJC,"1",",");
          else
                toJC=String.format("%s%s%s",toJC,"0",",");
               
    } 
         
}


     if(toJC.endsWith(","))
        toJC=toJC.substring(0,toJC.length()-1);
return String.format("%s%s",toJC,"})");
}
    /**
     * Allows the user to specify whether or not the displayed answer to a
     * sseries of linear equations should be formatted as fractional or
     * decimal numbers
     * @param useBigDecimal True if the user wants to see the data as decimals;
     * false otherwise
     * @return A String describing the solution(s) to a system of linear equations
     */
    public String toString(boolean useBigDecimal)
    {
        
        boolean bigDecimal = useBigDecimal;
        String str = "";
        if (isConsistent)
        {
            if (oneAnswer)
            {
                //If there is only one solution, list the solution for every
                //variable in the system
 
                for (int nx = 0; nx < this.n(); nx++)
                {
                    //If the user wants the data to appear as BigDecimal
                    //objects, they will be formatted here as such
                    str += "x" + (nx + 1) + " = " + (bigDecimal ? data[0][nx].toBigDecimal().toPlainString() : data[0][nx].toPlainString()) + "\n";
                    if(toJC.contentEquals(""))
                         toJC=String.format("%s%s%s",toJC,"{",(bigDecimal ? data[0][nx].toBigDecimal().toPlainString() : data[0][nx].toPlainString()));
                    else
                         toJC=String.format("%s%s%s",toJC,",",(bigDecimal ? data[0][nx].toBigDecimal().toPlainString() : data[0][nx].toPlainString()));
                         
                              
                }//end nested for
            }//end if oneAnswer
            else //Many solutions possible
            {
                //System.out.println("General solution:");
                for (int nx = 0;nx < n(); nx++)
                {
                    String equation = "";
                    //If x is in the basis, find what its factors are
                    if (basisMap[0][nx] == 1)
                    {
                        int lastNonZero = 0;
                        for (int r = 0; r < m(); r++)
                        {
                            if (get(r, nx).compareTo(Fraction.ZERO) != 0)
                                lastNonZero = r;
                        }
                        //prints get(ai, aj) + "x = b"
                        equation +=  "x" + (nx + 1) + " = " + B[lastNonZero][0].toPlainString();
                        if(toJC.contentEquals(""))
                         toJC=String.format("%s%s%s",toJC,"{",B[lastNonZero][0].toPlainString());
                        else
                         toJC=String.format("%s%s%s",toJC,";",B[lastNonZero][0].toPlainString());

                        for (int cx = 0; cx < n(); cx++)
                        {
                            if (cx != nx && get(lastNonZero, cx).compareTo(Fraction.ZERO) != 0)
                            {
                                //The other variables are moved to the other side as the x being calculated--
                                //the sign of each iterated variable must be reversed
                                if (get(lastNonZero, cx).compareTo(Fraction.ZERO) == -1)
                                    equation += " + ";
                                else equation += " - ";

                                //If the user wants to display the data as
                                //BigDecimal objects, they wil be formatted
                                //as such
                                equation += (bigDecimal ? get(lastNonZero, cx).abs().toBigDecimal().toPlainString() : get(lastNonZero, cx).abs().toPlainString()) + "*";
                                if (basisMap[0][cx] == 1)
                                    equation += "x" + (cx + 1);
                                else equation += "t" + basisMap[1][cx];
                            }
                        }

                    }
                    //If x is not in the basis, mark it as an unknown
                   else{
                      equation = "x" + (nx + 1) + " = t" + basisMap[1][nx];
                      toJC=String.format("%s%s%s",toJC,";","0");
                     }
                    str=String.format("%s%s%s",str,System.lineSeparator(),
                                    equation);
                    //System.out.println(equation);
                }
            }//end if there is more than one answer
        }//end if isConsistent
        //If the matrix is inconsistent, show a message saying it
        else str = "Inconsistent system. No solution possible.";
        str=String.format("%s%s",str,System.lineSeparator());
        return str;
    }//end toString
}
