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
 * This class is used to create solution sets for linear algebra problems. Methods 
 * are available to convert a matrix or linear system into reduced row echelon form.
 * Once a matrix is reduced, another class can use logic to decide which solver 
 * method to run--one for matrices with a single solution and one for matrices 
 * with many. A linear system with a single solution produces a matrix with 1-by-N 
 * indices, while one with multiple solutions creates a system that uses a basis 
 * map to determine how to write the solution to a string.
 * @author Robert Fruchtman
 */
public class SystemSolver
{
    /**
     * Finds the basis map for a reduced linear system, determines which
     * columns can hold arbitrary values, and then creates a package that
     * {@link SolutionMatrix} can decode to create a
     * solutions string
     * @param reducedSystem A system where matrices Ax = b have been reduced
     * to reduced row echelon form
     * @return A package of variables used to print the solutions of a
     * linear equation to a string
     */
    public static SolutionMatrix solveForMultipleSolutions(LinearSystemInterface reducedSystem)
    {
        int[][] basis = new int[2][reducedSystem.getA().n()];
        basis[0] = Matrix.getBasisColumns(reducedSystem.getA());

        System.gc();

        //The following loop numbers the arbitrary variable columns: 1, 2, 3, ...
        int arbitraryCounter = 1;
        for (int i = 0; i < basis[0].length; i++)
        {
            if (basis[0][i] == 0)
            {
                basis[1][i] = arbitraryCounter;
                arbitraryCounter++;
            }
        }

        return new SolutionMatrix(reducedSystem.getA().getArrayCopy(), reducedSystem.getB().getArrayCopy(), basis);
    }

    /**
     * Creates a column of fractional numbers that represents variable assignments
     * for single-solution a system of equations
     * @param reducedSystem A linear system in reduced row echelon form
     * @return A specialized {@link Matrix} that holds a single row of coefficients
     */
    public static SolutionMatrix solveForSingleSolution(LinearSystemInterface reducedSystem)
    {
        System.gc();
        Fraction[] solutions = new Fraction[reducedSystem.getA().n()];
        for (int mx = 0; mx < solutions.length; mx++)
            solutions[mx] = reducedSystem.getB().get(mx, 0);
        

        return new SolutionMatrix(solutions);
    }

    /**
     * Converts a linear system to reduced row echelon form by augmenting its 
     * matrices together
     * @param sys A linear system of equations that may or may not be already 
     * reduced
     * @return Matrix A|B of the inputted linear system, now in RREF
     */
    public static Matrix toRREF(LinearSystemInterface sys)
    {
        return (sys.getA().augmentColumn(sys.getB())).toRREF();
    }
}//SystemSolver class
