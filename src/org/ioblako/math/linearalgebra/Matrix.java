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

import java.math.*;
import java.util.*;
/**
 * The <tt>Matrix</tt> class represents a two-dimensional array of {@link Fraction}
 * objects.
 * <p>
 * It was originally written by Team343, whose members include
 * Mohamed Abdullah, Mark Arvieux, John Burrell, Teran Chase, and Shravan
 * Sridhar. In fall of 2009, Professor Sergey Nikitin at Arizona State University
 * tasked undergraduate student Robert Fruchtman with creating a linear system
 * solver using this source and several other Java classes. In the process, Robert
 * rewrote most of Team343's algorithms, including a Gaussian elimination routine
 * and a rank finder. Even though much of their contribution was erased, they
 * are credited for being there first.
 * <p>
 * Several methods in this class were adapted from the JAMA package published by
 * the National Institute of Standards and Technology (released into the public
 * domain).
 * @author Robert Fruchtman
 * @author Mohamed Abdullah
 * @author Mark Arvieux
 * @author John Burrell
 * @author Teran Chase
 * @author Sharavan Sridhar
 */
public class Matrix implements Cloneable, java.io.Serializable
{
 public static final long serialVersionUID=10L;
    /**
     * Number of rows of the matrix
     */
    private final int M;

    /**
     * Number of columns of the matrix
     */
    private final int N;

   /**
     * Transporition of the matrix
     */

   private Matrix Transposed = null;

    /**
     * Transporition of the matrix
     */

   private Matrix GramMatrix = null;

    /**
     * Contains all the coefficients of the M-by-N array
     */
    public final Fraction[][] data;

    /**
     * Creates an M-by-N matrix of zeros
     * @param M Numbers of rows of the new matrix
     * @param N Number of columns of the new matrix
     */
    public Matrix(int M, int N)
    {
        this.M = M;
        this.N = N;
        data = new Fraction[M][N];
        for (int i = 0; i < M; i++)
        {
            for (int j = 0; j < N; j++)
            {
                this.data[i][j] = Fraction.ZERO;
            }
        }
    }
    
    /**
     * Creates a one-column matrix with variable rows. Useful for creating a 
     * solutions matrix to be augmented onto another one in A|B format
     * @param coefficients a size-variable list of Strings that contain numbers
     */
    public Matrix(ArrayList<String> coefficients)
    {
        M = coefficients.size();
        N = 1;
        data = new Fraction[M][N];
        for (int i = 0; i < coefficients.size(); i++)
        {
            data[i][0] = new Fraction(coefficients.get(i));
        }//end for
    }
/**
 *Creates a Fraction object from a number of ArrayList objects that
 * represent rows in an array
 * @param coefficients an ArrayList of String[] containing numbers in a row x column format
     */
boolean checkSanity = true;
    public Matrix(String[] coefficients, String delimiter)
    {
        M = coefficients.length;
        N = (coefficients[0].split(delimiter)).length;
        data = new Fraction[M][N];
        String[] tmp;
        for (int i  = 0; i < coefficients.length; i++)
        {
            tmp = coefficients[i].split(delimiter);
               if(tmp.length != N){
                   checkSanity=false;
                   break;
               } 
            for (int j = 0; j < tmp.length; j++)
                data[i][j] = new Fraction(tmp[j].trim());
        }//end for

    }
public boolean CheckSanity(){
     return checkSanity;
}

    /**
     * Creates a Fraction object from a number of ArrayList objects that
     * represent rows in an array
     * @param coefficients an ArrayList of Strings containing numbers in a row x column format
     */
    public Matrix(ArrayList<String>[] coefficients)
    {
        //The numer of rows is the number of indices of the array
        M = coefficients.length;

        //The number of columns is the number of elements in the first ArrayList,
        //since a properly generated ArrayList will contain the same number of elements
        //in each ArrayList index
        N = coefficients[0].size();
        data = new Fraction[M][N];
        for (int i  = 0; i < coefficients.length; i++)
        {
            for (int j = 0; j < coefficients[i].size(); j++)
            {
                data[i][j] = new Fraction(coefficients[i].get(j));
            }//end nested for
        }//end for

    }

    /**
     * Creates a one row matrix with N columns
     * @param data An array of fractional numbers to become a <tt>Matrix</tt> object
     */
    public Matrix(Fraction[] data)
    {
		M = 1;
		N = data.length;
		this.data = new Fraction[M][N];
        for (int nx = 0; nx < N; nx ++)
        {
            this.data[0][nx] = data[nx];
        }//end for
	}

    /**
     * Creates an M x N matrix from a 2D array
     * @param data An array of coefficients representing lines of a
     * multivariable equation
     */
    public Matrix(double[][] data)
    {
        M = data.length;
        N = data[0].length;
        this.data = new Fraction[M][N];
        for (int mx = 0; mx < M; mx++)
            for (int nx = 0; nx < N; nx++)
                    this.data[mx][nx] = new Fraction("" + data[mx][nx] * 1.0);
    }

    /** Construct a matrix quickly without checking arguments.
    @param A    Two-dimensional array of doubles.
    @param m    Number of rows.
    @param n    Number of colums.
    */
    public Matrix(Fraction[][] A, int m, int n)
    {
        data = new Fraction[m][n];
        for (int mx = 0; mx < m; mx++)
            for (int nx = 0; nx < n; n++)
                data[mx][nx] = A[mx][nx];
        M = m;
        N = n;
    }

     public Matrix(int[][] data)
     {
        M = data.length;
        N = data[0].length;
        this.data = new Fraction[M][N];
        for (int mx = 0; mx < M; mx++)
            for (int nx = 0; nx < N; nx++)
                    this.data[mx][nx] = new Fraction("" + data[mx][nx] * 1.0);
     }

     //Makes a matrix from Strings converted into Fraction objects
     public Matrix(String[][] data)
     {
        M = data.length;
        N = data[0].length;
        this.data = new Fraction[M][N];
        for (int mx = 0; mx < M; mx++)
            for (int nx = 0; nx < N; nx++)
                    this.data[mx][nx] = new Fraction(data[mx][nx]);
     }

     //Makes a matrix from a series of BigDecimal objects
     public Matrix(BigDecimal[][] data)
     {
         M = data.length;
         N = data[0].length;
         this.data = new Fraction[M][N];
         for (int mx = 0; mx < M; mx++)
            for (int nx = 0; nx < N; nx++)
                this.data[mx][nx] = new Fraction(data[mx][nx]);
     }

     /**
      * Takes an existing 2D array of fractional numbers and gives it all the
      * added methods and and routines of a datatype. There's no need to
      * turn a 2D array of fractions into an object unlesss the methods are
      * desired, since the array will simply be copied into the <tt>Matrix</tt>
      * object.
      * @param data A 2D array of {@link Fraction}s
      */
     public Matrix(Fraction[][] data)
     {
         this.data = data;
         M = data.length;
         N = data[0].length;
     }
    
    //Returns C = A + B
     /**
      * Adds the elements A<sub>ij</sub> and B<sub>ij</sub> to reach C<sub>ij</sub>.
      * @param B A matrix with the same dimensions as the calling matrix (matrix
      * A)
      * @return Returns a matrix that adds all the elements of the calling matrix
      * and matrix B together for each respective index
      */
    public Matrix add(Matrix B)
    {
        if (B.M != M || B.N != N) throw new RuntimeException("Illegal matrix dimensions for addition.");
        Matrix C = new Matrix(M, N);
        for (int mx = 0; mx < M; mx++)
            for (int nx = 0; nx < N; nx++)
                //Use Fraction method to add two Fractions
                C.data[mx][nx] = data[mx][nx].add(B.data[mx][nx]);
        return C;
    }

    /**
     * @author Team343
     * @author Robert Fruchtman
     * @param B An array of Fraction objects
     * @return The calling matrix augmented with b
     */
    public Matrix augmentColumn(Fraction[] b)
    {
        //Throw an exception if the row numbers mismatch
        if (M != b.length)
            throw new ArrayIndexOutOfBoundsException("Cannot augment a column with a mismatched size");
        Fraction[][] newData = new Fraction[M][N + 1];
        for (int mx = 0; mx < M; mx++)
        {
            //Set columns 0 through N - 1
            for (int nx = 0; nx < N; nx++)
                newData[mx][nx] = data[mx][nx];
            //Set column N
            newData[mx][N] = b[mx];
        }
        return new Matrix(newData);
    }

	/**
     * @author Team343
     * @author Robert Fruchtman
     * @param B A matrix with only one column
     * @return The calling matrix augmented with B
     */
	public Matrix augmentColumn(Matrix B)
    {
        //If there's more than one column, throw an exception to avoid getting
        //an unexpected result
        if (B.N > 1)
            throw new ArrayIndexOutOfBoundsException("Cannot augment more than one column");
		Fraction[] column = new Fraction[B.M];
		for(int mx = 0; mx < this.data.length; mx++)
        {
            column[mx] = B.data[mx][0];
		}
        return augmentColumn(column);
	}

    /**
     * @author Team343
     * @author Robert Fruchtman
     * @param B A matrix with as many rows as A and an arbitrary number of columns
     * @return Augmented matrix A|B
     */
	public Matrix augmentMatrix(Matrix B)
    {
		Matrix augmentedMatrix = null;

		//Throws an exception if A's M-number and B's M-number aren't equal
		if (M != B.M)
			throw new ArrayIndexOutOfBoundsException("Number of rows should be equal between A and B!");

		//Create an empty array of Fractions where the number of
        //rows = the number of rows in A = the number of rows in B
		Fraction[][] augData = new Fraction[this.data.length][this.data[0].length + B.data[0].length];

        Fraction[] column = new Fraction[B.M];
		//Fill the augmented matrix's remaining columns with B
        for (int mx = 0; mx < B.M; mx++)
        {
            for (int nx = 0; nx < B.N; nx++)
                column[mx] = B.data[mx][nx];
            augmentColumn(column);
        }

		//Instantiate the augmented matrix using the new array of Fractions
		augmentedMatrix = new Matrix(augData);
		return augmentedMatrix;
	}

    /**
     * Clones the <tt>Matrix</tt> object
     * @return A copy of the calling matrix without directly accessing its contents
     */
    public Object clone () 
    {
      return this.copy();
    }

    /**
     * Produces a deep copy of a matrix, using an array copier method
     * @return A copy of a matrix made by accessing its data using
     * {@link Matrix#getArrayCopy()}
     */
    public Matrix copy()
    {
        Fraction[][] C = getArray();
        return new Matrix(C);
    }

    /**
     * Uses the Laplace method to find the numerical determinant of a matrix.
     * @return For a given Matrix with elements a11...ann, the determinant
     * will be a<sub>11</sub>*det(Minor<sub>11</sub>) +
     * a<sub>12</sub>*det(Minor<sub>12</sub>) +...+ a<sub>1n</sub>*det(Minor<sub>1n</sub>).
     * The minor is the matrix produced by removing the row and column
     * holding element a<sub>1n</sub>.
     */
    public Fraction determinant()
    {
        Fraction determinant = Fraction.ZERO;
        if (M != N )
            throw new ArrayIndexOutOfBoundsException ("Number of rows is not equal to the number of columns. The determinant is undefined!");
        if(M==1)
            return data[0][0];
        for (int i =0; i < N ; i++ )
        {
            int g=0;
            while(2*g<i)
                g++;
            if(2*g==i)
                //determinant=determinant + data[0][i]*(new Matrix(this.getMinor(0,i))).getDeterminant();
                determinant = determinant.add(data[0][i].multiply((new Matrix(this.getMinor(0,i))).determinant()));
            else
                //determinant = determinant - data[0][i]*(new Matrix(this.getMinor(0,i))).getDeterminant();
                determinant = determinant.subtract(data[0][i].multiply((new Matrix(this.getMinor(0,i))).determinant()));
        }
        return determinant;
    }//end of the determinant method

    /**
     * @param B A matrix
     * @return A boolean that is true if A equals B exactly
     */
    public boolean equals(Matrix B)
    {
        if (B.M != M || B.N != N) return false;
        for (int mx = 0; mx < M; mx++)
            for (int nx = 0; nx < N; nx++)
                if (data[mx][nx].compareTo(B.data[mx][nx]) != 0) return false;
        return true;
    }
    
   /** Get a single element.
   @param i    Row index.
   @param j    Column index.
   @return     A(i,j)
   @exception  ArrayIndexOutOfBoundsException
   */
   public Fraction get(int i, int j)
   {
      return data[i][j];
   }
    
    /** 
     * Accesses the internal two-dimensional array without making a copy.
    @return A pointer to the two-dimensional array of matrix elements.
    */
    public Fraction[][] getArray()
    {
        return data;
    }

    /**
     * Copies the internal two-dimensional array of fractional numbers
    @return Gives a 2D array copy of matrix elements.
    */
    public Fraction[][] getArrayCopy()
    {
        Fraction[][] C = new Fraction[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++)
            {
                C[i][j] = data[i][j];
            }
        }
        return C;
    }

    /**
     * A utility to find the basis of a matrix
     * @author Robert Fruchtman
     * @param input The input should be a matrix in redcued row echelon form
     * @return The array of columns that contain leading entries
     */
    public static int[] getBasisColumns(Matrix A)
    {
        Fraction[][] matrix = A.getArrayCopy();
        //find out which columns/rows are nonrank by counting the leading entries
        boolean leadingEntryIsPresent = false;
        int[] basis = new int[A.n()];
        int lastRow = 0;
        for (int i = A.m() - 1; i >= 0; i--)
        {
            if (matrix[i][0].compareTo(Fraction.ZERO) != 0)
            {
                leadingEntryIsPresent = true;
                lastRow = i;
            }
        }
        if (leadingEntryIsPresent)
            basis[0] = 1;
        for (int j = 1; j < A.n(); j++)
        {
            for (int i = A.m() - 1; i >= lastRow; i--)
            {
                //for each column in the matrix, if there is a row that is nonzero
                //with a nonzero entry in the index directly left of the
                //nonzero index, that index is a leading entry if that row
                //is one row down from the last leading entry
                if (i > lastRow && matrix[i][j].compareTo(Fraction.ZERO) != 0 && matrix[i][j - 1].compareTo(Fraction.ZERO) == 0)
                {
                    basis[j] = 1;
                    lastRow = i;
                }
                else
                    basis[j] = 0;
            }
        }

        return basis;
    }
    
    /**
     * @author Team343
     * @param cix A column index for a matrix
     * @return An array containing all the indices of a
     * specified column of the calling matrix
     */
    public Fraction[] getCol(int cix)
    {
        Fraction[] ret = new Fraction[M];
        for (int mx = 0; mx < M; mx++)
            ret[mx] = this.data[mx][cix];
        return ret;
    }

    /**
     * Produces the minor of a matrix, which is the determinant of a submatrix
     * @return The minor of a calling matrix using the given row and column
     * specifications
     */
    public Fraction[][] getMinor(int m, int n)
    {
        if(m>=M|| n >= N || m < 0 || n < 0)
            return null;
        Fraction[][] minor = new Fraction[M - 1][N - 1];
        for(int k = 0; k < m ; k++)
        {
            for(int l = 0; l < n ; l++)
                minor[k][l]=data[k][l];
            for(int l = n; l < N-1 ; l++)
                minor[k][l]=data[k][l+1];
        }//for
        for(int k = m; k < M-1 ; k++)
        {
            for(int l = 0; l < n ; l++)
                minor[k][l]=data[k+1][l];
            for(int l = n; l < N-1 ; l++)
                minor[k][l]=data[k+1][l+1];
        }//for
        return minor;
    }//end of the minor getter
    
    /**
     * @author Robert Fruchtman
     * @param rix A row index for a matrix
     * @return An array containing all the indices of a 
     * specified row of the calling matrix
     */
    public Fraction[] getRow(int rix)
    {
        Fraction[] ret = new Fraction[M];
        for (int nx = 0; nx < N; nx++)
            ret[nx] = this.data[rix][nx];
		return ret;
    }

   /** Get a submatrix.
    * @param r    Array of row indices.
    * @param j0   Initial column index
    * @param j1   Final column index
    * @return     A(r(:),j0:j1)
    * @exception  ArrayIndexOutOfBoundsException Submatrix indices
   */
   public Matrix getSubmatrix (int[] r, int j0, int j1) {
      Matrix X = new Matrix(r.length,j1-j0+1);
      Fraction[][] B = X.getArrayCopy();
      try
      {
         for (int i = 0; i < r.length; i++)
            for (int j = j0; j <= j1; j++)
               B[i][j-j0] = get(r[i], j);
      }
      catch(ArrayIndexOutOfBoundsException e)
      {
        throw new ArrayIndexOutOfBoundsException("Submatrix indices");
      }
      return new Matrix(B);
    }

   /**
    * Gets the matrix inside a larger matrix with the paramters specified
    * @param mStart The inclusive starting row of the submatrix
    * @param nStart The inclusive starting column of the submatrix
    * @param mEnd The exclusive ending row of the submatrix
    * @param nEnd The exclusive ending column of the submatrix
    * @return A submatrix with either indices inside the matrix or encompassing
    * the entire matrix
    */
    public Matrix getSubmatrix(int mStart, int nStart, int mEnd, int nEnd)
    {
        String error = "";
        //If the inputs reach invalid indices, return null. Tests broken up for redability
        if (mStart >= m() || nStart >= n() || mEnd <= mStart || nEnd <= nStart || mEnd > m() || nEnd > n())
            throw new ArrayIndexOutOfBoundsException("Submatrix can't be found with inputted indices");
        if (mStart < 0 || nStart < 0 || mEnd < 0 || nEnd < 0)
            throw new ArrayIndexOutOfBoundsException("Submatrix can't be found with inputted indices");

        //Start indices are inclusive, end indices are exclusive
        Fraction[][] segment = new Fraction[mEnd - mStart][nEnd - nStart];

        //Create a new matrix filled with only the indices within the bounds
        for (int i = mStart; i < mEnd; i++)
        {
            for (int j = nStart; j < nEnd; j++)
            {
                segment[i - mStart][j - nStart] = get(i, j);
            }//end column transversal
        }//end row transversal

        return new Matrix(segment);
    }
    
    /**
     * Creates and returns an N by N identity array
     * @param N An integer greater than zero
     * @return A square identity matrix that is size N by N
     */
    public static Matrix identity(int N)
    {
        Matrix I = new Matrix(N, N);
        for (int nx = 0; nx < N; nx++)
            I.data[nx][nx] = Fraction.ONE;
        return I;
    }

    /**
     * Determines whether or not a matrix is nonsingular. If it is nonsingular,
     * then it is a square matrix that can be inverted to find an identy matrix
     * @return true if the matrix is nonsingular; false means the matrix is singular
     */
    public boolean isNonsingular()
    {
        if (M != N)
            return false;
        //If any elements on the pivot are zero, the matrix is singular
        for (int j = 0; j < n(); j++)
        {
            if (get(j, j).compareTo(Fraction.ZERO) == 0)
                return false;
        }
        return true;
    }//end isNonsingular

    /**
     * @author Team343
     * @return The number of rows in the matrix
     */
    public final int m()
    {
        return M;
    }

    /**
     * Performs matrix multiplcation on each of the elements in the calling
     * matrix and the paramter matrix
     * @param B A matrix with as many rows as the caller has columns
     * @return C = A * B
     */
    public Matrix multiply(Fraction d){
      
       Fraction[][] new_data=new Fraction[M][N];

         for(int i = 0; i < M; i++)
              for( int j = 0;j<N;j++)
                     new_data[i][j] = data[i][j].multiply(d);

       return new Matrix(new_data);  


    }
    public Matrix multiply(Matrix B)
    {
        if (N != B.M) throw new RuntimeException("Illegal matrix dimensions for multiplication.");

        Matrix C = new Matrix(M, B.N);
        for (int mx = 0; mx < C.M; mx++)
            for (int nx = 0; nx < C.N; nx++)
                for (int k = 0; k < N; k++)
                    C.data[mx][nx] =C.data[mx][nx].add(data[mx][k].multiply(B.data[k][nx]));
        return C;
    }

    /**
     * @author Team343
     * @return The number of columns in the matrix
     */
	public final int n()
    {
		return N;
	}

    /**
     * Produces a matrix with a random number of rows, and a random
     * number of columns that is filled with random fractions between 0 and 1
     * @param M Number of rows for the random matrix
     * @param N Number of columns for the random matrix
     * @return A random M-ny-N matrix with values between 0 and 1
     */
    public static Matrix random(int M, int N)
    {
        Matrix A = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[i][j] = new Fraction("" + Math.random() * 1.0);
        return A;
    }

    /**
     * For a matrix in reduced row echelon form, finding the rank is done by
     * examining the pivot of the matrix, checking for nonzero entries, and then
     * counting the rows with leading entries. If a row with no leading entries
     * exists, then if the following row has a zero pivot it may be assumed
     * that the rest of the rows of the matrix hold only zeros.
     * @author Robert Fruchtman
     * @param A A matrix of numbers in reduced row echelon form
     * @return The number of columns/rows of the matrix that define its rank
     */
    public static int rank(Matrix A)
    {
        int r = 0;
        int minRC = Math.min(A.M, A.N);
        for (int mx = 0; mx < minRC; mx++)
            //Searches the pivot of the matrix for nonzero entries. Nonzero
            //numbers will not appear to the left of the pivot, as per RREF
            if (A.get(mx, mx).compareTo(Fraction.ZERO) != 0)
                r++;
            //If the nonpivot of the previous row was zero and there were no
            //nonzero values in the row, stop searching--RREF doesn't put holes
            //in its rows. Otherwise, check all the columns in the row being
            //searched for nonzeros
            else 
                for (int nx = mx; nx < A.N; nx++)
                    if (A.get(mx, nx).compareTo(Fraction.ZERO) != 0)
                    {
                        r++;
                        break;
                    }
        return r;
    }

    /**
     * If the matrix is not reduced as it is given, it is reduced before its
     * rank is found. If the matrix is already reduced, the single-argument
     * version of this method should be used, since it will give the same
     * result as this one without first branching based on whether or not
     * it is reduced.
     * @author Robert Fruchtman
     * @param A A matrix that may or may not be reduced
     * @param isReduced A boolean that should indicate whether or not the
     * matrix is in reduced row echelon form
     * @return The number of columns/rows of the matrix that define its rank
     */
    public static int rank(Matrix A, boolean isReduced)
    {
      //For First build comment SystemSolver SERGEY
        if (!isReduced)
            return (Matrix.rank(A.toRREF()));
        return rank(A);
    }
   
   /**
    * Sets a specified member of the matrix to a desired value
    * @param i Matrix row number
    * @param j Matrix column number
    * @param x Number that will replace the number at i, j
    */
   public void set(int i, int j, BigDecimal x)
   {
       data[i][j] = new Fraction(x);
   }

   /**
    * Sets a specified member of the matrix to a desired value
    * @param i Matrix row number
    * @param j Matrix column number
    * @param x Number that will replace the number at i, j
    */
    public void set(int i, int j, Fraction x)
    {
        data[i][j] = x;
    }

    /**
     * Sets a submatrix of the matrix by the values of a smaller matrix
     * @author Robert Fruchtman, with the idea taken from JAMA
     * @param mStart The (inclusive) row index where the submatrix will be placed
     * @param nStart The (inclusive) column index where the submatrix will be placed
     * @param subMatrix A matrix smaller than the caller to be moved inside the larger one
     */
    public void setSubMatrix(int mStart, int nStart, Matrix subMatrix)
    {
        if (mStart < 0 || nStart < 0 || mStart > m() - 1 || nStart > n() - 1)
            throw new ArrayIndexOutOfBoundsException("Invalid matrix indices");
        else if (this.m() < subMatrix.m() || this.n() < subMatrix.n())
        {
            throw new ArrayIndexOutOfBoundsException("Invalid submatrix size");
        }
        else if (this.m() - subMatrix.m() - mStart < 0 || this.n() - subMatrix.n() - nStart < 0)
        {
            throw new ArrayIndexOutOfBoundsException("Invalid submatrix placement");
        }
        else for (int mx = mStart; mx < mStart + subMatrix.m(); mx++)
        {
            for (int nx = nStart; nx < nStart + subMatrix.n(); nx++)
            {
                set(mx, nx, subMatrix.get(mx - mStart, nx - nStart));
            }
        }
    }

    /**
     * @param B a matrix with the same dimensions as the calling matrix
     * @return C = A - B for every element A<sub>ij</sub> and B<sub>ij</sub>
     */
    public Matrix subtract(Matrix B)
    {
        if (B.M != M || B.N != N) throw new RuntimeException("Illegal matrix dimensions for subtraction.");
        Matrix C = new Matrix(M, N);
        for (int mx = 0; mx < M; mx++)
            for (int nx = 0; nx < N; nx++)
                C.data[mx][nx] = data[mx][nx].subtract(B.data[mx][nx]);
        return C;
    }
   
    /**
     * Swaps columns i and j
     * @param i A column index that will receive the contents of column j
     * @param j A column index that will receive the contents of column i
     */
    public void swapColumns(int i, int j)
    {
        Fraction[] temp = new Fraction[M];
        for (int mx = 0; mx < M; mx++)
        {
            temp[mx] = data[mx][i];
            data[mx][i] = data[mx][j];
            data[mx][j] = temp[mx];
        }
    }

    /**
     * Swaps rows i and j
     * @param i A row index that will receive the contents of row j
     * @param j A row index that will receive the contents of row i
     */
    public void swapRows(int i, int j)
    {
        Fraction[] temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    //Creates and returns the transpose of the invoking matrix
    /**
     * Creates the transpose of the calling matrix, where the transpose switches
     * the rows and columns of the matrix so that A<sub>ij</sub> becomes
     * A<sub>ji</sub>
     * @return The transpose of matrix A, A<sup>T</sup>
     */
    public Matrix transpose()
    {
      if (Transposed == null ) {
             Transposed = new Matrix(M,N);
        for (int mx = 0; mx < M; mx++)
            for (int nx = 0; nx < N; nx++)
                Transposed.data[nx][mx] = this.data[mx][nx];
         }//if
        return Transposed;
    }

    /**
     * @author Robert Fruchtman
     * @return A string showing all the elements in the matrix, numerator over 
     * denominator
     */
    public String toString()
    {
        int maxLength = 0;
        for (int mx = 0; mx < M; mx++)
        {
            for (int nx = 0; nx < N; nx++)
            {
                int numerLength = data[mx][nx].numerator().toString().length();
                int denomLength = data[mx][nx].denominator().toString().length();
                if (denomLength > maxLength)
                    maxLength = denomLength;
            }
        }
		String s= "";
        for (int mx = 0; mx < M; mx++)
        {
            for (int nx = 0; nx < N; nx++)
            {
                s += data[mx][nx].numerator();
                for (int i = data[mx][nx].numerator().toString().length(); i < maxLength; i++)
                    s += " ";
                s += "\t";  
            }
            s += "\n";
            for (int nx = 0; nx < N; nx++)
            {
                for (int i = 0; i < maxLength; i++)
                    s += "-";
                s += "\t";
            }
            s += "\n";
            for (int nx = 0; nx < N; nx++)
            {
                s += data[mx][nx].denominator();
                for (int i = data[mx][nx].denominator().toString().length(); i < maxLength; i++)
                    s += " ";
                s += "\t";
            }
            s += "\n";
            s += "\n";
		}
		return s;
	}

public Matrix inverse() throws Exception{
  if(N != M)
      throw new Exception("Inverse matrix does not exists!");

Fraction[][]  ident = new Fraction[N][N];

for(int i = 0;i<N;i++)
    for(int j = 0; j< N; j++)
        if(i==j)
            ident[i][j] = Fraction.ONE;
        else
            ident[i][j] = Fraction.ZERO;
Fraction[][] agm = new Fraction[N][2*N];

for(int i = 0;i<N;i++)
    for(int j = 0; j< N; j++)
          agm[i][j]=get(i,j);
for(int i = 0;i<N;i++)
    for(int j = N; j< 2*N; j++)
          agm[i][j]=ident[i][j-N];

 Matrix A = (new Matrix(agm)).toRREF();

for(int i = 0;i<N;i++)
    for(int j = 0; j< N; j++)
          ident[i][j]=A.get(i,N+j);
return new Matrix(ident);
}
 /**
     * Converts a matrix to reduced row echelon form by iterating over the
     * matrix, putting 1s in the pivots and minimizing al other matrix indices
     * (if they are not zero). Adapted from
     * <a href="http://en.wikipedia.org/w/index.php?title=Row_echelon_form&oldid=326271627">Row
     * echelon form</a> on Wikipedia. Code is made available through the
     * <a href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons
     * Attribution-ShareAlike License, version 3.0</a>
     * @author Robert Fruchtman
     * @param sys A system of Ax = B
     * @return A|B in reduced row echelon form
     */

public Matrix toRREF()
    {
	Matrix M = new Matrix(data);    
        Fraction lv;
        int lead = 0;
        int rowCount = M.m();
        int columnCount = M.n();
        int i;
        if (rowCount == 0)
            return M;
        for (int r = 0; r < rowCount; r++)
        {
            //stop if the number of leading entries is less than or equal to the
            //number of clumns
            if (columnCount <= lead)
                break;
            i = r;
            while (M.get(i, lead).compareTo(Fraction.ZERO) == 0)
            {
                i++;
                if (rowCount == i)
                {
                    i = r;
                    lead++;
                    //stop if the pivot is at the number of colums
                    if (columnCount == lead)
                        return M;
                }
            }
            M.swapRows(r, i);

            //Divide row r by its leading value
            lv = M.get(r, lead);
            for (int nx = 0; nx < columnCount; nx++)
            {
                M.set(r, nx, M.get(r, nx).divide(lv));
            }

            for (int mx = 0; mx < rowCount; mx++)
            {
                if (mx != r)
                {
                    lv = M.get(mx, lead);
                    for (int j = 0; j < columnCount; j++)
                        M.set(mx, j, M.get(mx, j).subtract(lv.multiply(M.get(r, j))));
                }
            }
            lead++;
        }
        return M;
    }//RREF method
 /**
     * Returns the Gram matrix, see
     * <a href="http://en.wikipedia.org/wiki/Gram_matrix">
     * @author Sergey Nikitin
     * @param A
     * @return A times A transposed
     */
public static  Matrix getGram ( Matrix A ){
  return A.multiply(A.transpose());
}//getGram
public final Matrix getGram(){
 if(GramMatrix == null )
   GramMatrix = this.multiply(this.transpose());
  return GramMatrix;
}

}//end Matrix class
