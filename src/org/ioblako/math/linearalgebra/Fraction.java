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

import java.math.BigInteger;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
//import org.ioblako.math.calculator.jc;


/**
 * An immutable integer-integer pair (arbitrary-precision) datatype meant to replace
 * the use of decimal numbers for exact arithmetic operations
 * @author Robert Fruchtman
 */
public final class Fraction extends Number implements Cloneable, Comparable<Fraction>, java.io.Serializable
{
    MathContext MC=MathContext.DECIMAL128;
    private static final long serialVersionUID = 1L;
    private BigInteger numerator;
    private BigInteger denominator;

    /**
     * The Fraction constant zero
     */
    public final static Fraction ZERO = new Fraction(BigInteger.ZERO);



    /**
     * The Fraction constant one
     */
    public final static Fraction ONE = new Fraction(BigInteger.ONE);

    /**
     * The Fraction constant ten
     */
    public final static Fraction TEN = new Fraction(BigInteger.TEN);
    
public void setMathContext(MathContext newMC){
  MC=newMC;
}

    /**
     * Initializes the default fraction to be zero
     */
    public Fraction()
    {
        numerator = BigInteger.ZERO;
        denominator = BigInteger.ONE;
    }
    
    /**
     * Creates a fraction from an existing one
     * @param fraction A fraction object that already exists
     */
    public Fraction(Fraction fraction)
    {
        reduce(fraction.denominator, fraction.numerator);
    }
    
    /**
     * Takes an ordinary BigInteger object and makes it a fraction
     * @param numerator A BigInteger
     */
    public Fraction(BigInteger numerator)
    {
        this.numerator = numerator;
        denominator = BigInteger.ONE;
    }
    
    public Fraction(int numerator)
    {
        //int converted to a String to handle BigInteger's limitations
        String integer = Integer.toString(numerator);
        this.numerator = new BigInteger(integer);
        denominator = BigInteger.ONE;
    }
    
    public Fraction(double number)
    {
        denominator = BigInteger.ONE;
        String strValue = Double.toString(number);
        String integer = strValue.substring(0, strValue.indexOf('.'));
        String decimals = strValue.substring(strValue.indexOf('.') + 1);

        //If the number has decimal points, multiply until they go away
        while (!decimals.equals("") && !decimals.equals("0"))
        {
            //Multiply number by ten until there are no more decimal points (and the denominator is a power of ten)
            integer = integer.concat(Character.toString(decimals.charAt(0)));
            decimals = decimals.substring(1);
            denominator = denominator.multiply(BigInteger.TEN);
        }
        numerator = new BigInteger(integer);
    }
    
    public Fraction(BigDecimal number)
    {
        String strValue = number.toPlainString();
       if(strValue.indexOf('.')==-1){
           denominator=BigInteger.ONE;
           numerator=new BigInteger(strValue);  
          return;  
          }
        String integer = strValue.substring(0, strValue.indexOf('.'));
        String decimals = strValue.substring(strValue.indexOf('.') + 1);
            numerator = new BigInteger(integer.concat(decimals));
            integer="1";
            for(int i = 0 ; i<decimals.length();i++)
                 integer=integer.concat("0");    
            denominator = new BigInteger(integer);
          reduce(denominator,numerator);
    }

private static boolean gcdIt=true;
public static Fraction plainFraction(String number){
 Fraction.gcdIt=false;
  Fraction ret = new Fraction(number);
  Fraction.gcdIt=true;
  return ret;
}

    public Fraction(String number)
    {
        String strValue = number.trim();
        if (strValue.contains(".") && strValue.contains("/"))
            throw new NumberFormatException("A number cannot contain both a fraction and a decimal");

        denominator = BigInteger.ONE;
        if (strValue.contains("."))
        {
            String integer = strValue.substring(0, strValue.indexOf('.'));
            String decimals = strValue.substring(strValue.indexOf('.') + 1);
            numerator = new BigInteger(integer.concat(decimals));
            integer="1";
            for(int i = 0 ; i<decimals.length();i++)
                 integer=integer.concat("0");    
            denominator = new BigInteger(integer);
        }
        else if (strValue.contains("/"))
        {
            String[] values = strValue.split("/");
            numerator = new BigInteger(values[0]);
            denominator = new BigInteger(values[1]);
        }
        else
        {
            //If the Fraction doesn't contain a decimal point or a forward slash, it
            //must be an integer
            numerator = new BigInteger(number.trim());
            denominator = BigInteger.ONE;
        }
    if(numerator.compareTo(BigInteger.ZERO) == 0){
            denominator = BigInteger.ONE;
            return;
         }
     if(denominator.compareTo(BigInteger.ZERO) == 0){
            numerator = BigInteger.ONE;
            return;
        }
   if(numerator.compareTo(BigInteger.ZERO) != 0 &&
      denominator.compareTo(BigInteger.ZERO) != 0){
  if(gcdIt)
    reduce(denominator,numerator); 
  }

    }
    
    public Fraction(BigInteger denominator, BigInteger numerator)
    {
        if (denominator.compareTo(BigInteger.ZERO) == 0)
            throw new ArithmeticException("Cannot divide by zero--value undefined");
        
        reduce(denominator, numerator);
    }
    
    /**
     * Creates the fraction numerator/denominator using two integers
     * @param denominator A divisor for another integer
     * @param numerator A dividend for another integer
     */
    public Fraction(int denominator, int numerator)
    {
        if (denominator == 0)
            throw new ArithmeticException("Cannot divide by zero--value undefined");
        
        BigInteger tempNumerator = new BigInteger(Integer.toString(numerator));
        BigInteger tempDenominator = new BigInteger(Integer.toString(denominator));
        reduce(tempDenominator, tempNumerator);
    }

  /**
     * Creates the fraction numerator/denominator using two long integers
     * @param denominator A divisor for another integer
     * @param numerator A dividend for another integer
     */
    public Fraction(long denominator, long numerator)
    {
        if (denominator == 0)
            throw new ArithmeticException("Cannot divide by zero--value undefined");

        BigInteger tempNumerator = new BigInteger(Long.toString(numerator));
        BigInteger tempDenominator = new BigInteger(Long.toString(denominator));
        reduce(tempDenominator, tempNumerator);
    }


    
    public Fraction(String denominator, String numerator)
    {
        if ((new BigDecimal(denominator)).compareTo(BigDecimal.ZERO) == 0)
            throw new ArithmeticException("Cannot divide by zero--value undefined");
        if (numerator.contains(".") || numerator.contains("/"))
            throw new NumberFormatException("Inputted numerator is not an integer");
        if (denominator.contains(".") || denominator.contains("."))
            throw new NumberFormatException("Inputted denominator is not an integer");
        
        BigInteger tempNumerator = new BigInteger(numerator.trim());
        BigInteger tempDenominator = new BigInteger(denominator.trim());
        reduce(tempDenominator, tempNumerator);
    }

    /**
     * @return A nonnegative Fraction representation
     */
    public Fraction abs()
    {
        return new Fraction(denominator.abs(),numerator.abs());
    }
    
    /**
     * Returns <tt>this + val</tt>
     * @param val A Fraction to be added to the caller
     * @return A Fraction that adds the caller and <tt>val</tt>
     */
    public Fraction add(Fraction val)
    {
        //Thank you Michael Gilleland for this code
        return new Fraction(denominator.multiply(val.denominator), numerator.multiply(val.denominator).add(val.numerator.multiply(denominator)));
    }
    
    /**
     * Adds a BigInteger to the callng Fraction
     * @param val A BigInteger
     * @return <tt>this + val</tt>
     */
    public Fraction add(BigInteger val)
    {
        return add(new Fraction(BigInteger.ONE, val));
    }

    /**
     * Provides a representation of the integer value of this fraction as a
     * series of bytes. Note that a fraction as much more precision than an
     * integer if the denominator is not a multiple of the numerator.
     * Therefore, if the fraction is best represented as a decimal, the byte
     * value this method returns will be significantly less accurate than
     * the float, long, or double representations.
     * @return A byte representation of the calling fractional number
     */
    public byte byteValue()
    {
        return (byte)intValue();
    }

    /**
     * Clones the fraction by making copies of its constituents
     * @return
     */
    public Object clone()
    {
        return this.copy();
    }

    /**
     * Makes a deep copy of the calling fractional object
     * @return A fractional number with the same properties as its members
     */
    public Fraction copy()
    {
        return new Fraction(this.getDenominator(), this.getNumerator());
    }

    /**
     * I'd like to thank Michael Gilleland for providing an algorithm similar to this one--
     * one that makes comparing Fractions possible
     * @param val A different Fraction to be compared with the calling Fraction
     * @return 0 if equal, 1 if greater than the other one, -1 if less than the other one
     */
    public int compareTo(Fraction val)
    {
        //If both numerators are zero, ignore the denominators
        if (val.numerator.compareTo(BigInteger.ZERO) == 0&& numerator.compareTo(BigInteger.ZERO) == 0)
            return 0;
        BigInteger left = numerator.multiply(val.denominator);
        BigInteger right = val.numerator.multiply(denominator);
    return left.compareTo(right);
       /* 
        if (left.compareTo(right) == -1)
            return - 1;
        else if (left.compareTo(right) == 0)
            return 0;
        else return 1;
     */
    }

    /**
     * Gets the denominator
     * @return A BigInteger object representing the denomerator
     */
    public BigInteger denominator()
    {
        return denominator;
    }


    /**
     * @param val Another Fraction object
     * @return <tt>this/val</tt>
     */
    public Fraction divide(Fraction val)
    {
        return new Fraction(denominator.multiply(val.numerator), numerator.multiply(val.denominator));
    }

    /**
     * To achieve the best double translation, the numerator and denominator are
     * both converted to <tt>BigDecimal</tt> numbers, then they are divided,
     * and lastly the {@link BigDecimal#doubleValue()} method in the BigDecimal class
     * is used to return the <tt>Fraction</tt> as a double
     * @return A double representation of a <tt>Fraction</tt> using <tt>BigDecimal</tt>
     * methods
     */
    public double doubleValue()
    {
        return toBigDecimal().doubleValue();
    }

    /**
     * The <tt>BigDecimal</tt> class is the most precise class for calculating
     * floats. The <tt>Fraction</tt> is converted to a <tt>BigDecimal</tt>
     * object, and then the  <tt>Bigecimal</tt> class's {@link BigDecimal#floatValue()} 
     * method for float conversion is used
     * @return A float calculated using the <tt>BigDecimal</tt> class
     */
    public float floatValue()
    {
        return toBigDecimal().floatValue();
    }
    
    /**
     * @return A deep copy of the calling fraction's denominator
     */
    public BigInteger getDenominator()
    {
        BigInteger d = denominator;
        return d;
    }
    
    /**
     * @return A deep copy of the callng fraction's numerator
     */
    public BigInteger getNumerator()
    {
        BigInteger n = numerator;
        return n;
    }


    /**
     * @return The hashcode for this <tt>Fraction</tt>
     */
    public int hashCode()
    {
        //Code inspired by Michael Gilleland
        return numerator.hashCode() ^ denominator.hashCode();
    }
    
    /**
     * Reverses the {@link Fraction#numerator} and {@link Fraction#denominator}
     * of the calling fractional number
     * @return A fraction with the numerator and denominator switched
     */
    public Fraction invert()
    {
        return new Fraction(numerator, denominator);
    }

    /**
     * To get an integer truncation of the <tt>Fraction</tt>, the <tt>Fraction</tt>
     * is converted to a <tt>BigDecimal</tt> object, which itself has a method
     * for integer translation, {@link BigDecimal#intValue()}
     * @return An integer represented using the methods of the <tt>BigDecimal</tt>
     * class
     */
    public int intValue()
    {
        return toBigDecimal().intValue();
    }
    
    /**
     * Determines if the numerator is negative, since for a reduced fraction only 
     * the numerator may ever be negative.
     * @return true if the numerator is true; false otherwise
     */
    public boolean isNegative()
    {
        if (numerator.compareTo(BigInteger.ZERO) < 0&&
            denominator.compareTo(BigInteger.ZERO)>0)
            return true;
        if (numerator.compareTo(BigInteger.ZERO) > 0&&
            denominator.compareTo(BigInteger.ZERO)<0)
            return true;
        return false;
    }

    /**
     * Uses a conversion to <tt>BigDecimal</tt> and then the {@link BigDecimal#longValue()}
     * method of <tt>BigDecimal</tt> to return a long value
     * @return A long number created using a conversion to a <tt>BigDecimal</tt> object
     */
    public long longValue()
    {
        return toBigDecimal().longValue();
    }
    
    /**
     * Determines if the calling Fraction object is larger than another one. 
     * If this one is larger, it is returned. If the two values are equal or 
     * this one is smaller, <tt>val</tt> is returned.
     * @param val A Fraction object to be compared to the caller
     * @return The calling Fraction object if it is larger--otherwise, <tt>val</tt>
     */
    public Fraction max(Fraction val)
    {
        Fraction max = this;
        //If both values are equal, return the other one, since it's faster.
        if (this.compareTo(val) <= 0)
            return val;
        //If this value is larger, the above if statement will not return.
        //Return this.
        return this;
    }
    
    /**
     * Determines if the calling Fraction object is smaller than another one. 
     * If this one is smaller, it is returned. If the two values are equal or 
     * this one is larger, <tt>val</tt> is returned.
     * @param val A Fraction object to be compared to the caller
     * @return The calling Fraction object if it is smaller--otherwise, <tt>val</tt>
     */
    public Fraction min(Fraction val)
    {
        Fraction min = this;
        //If both values are equal, return the other one, since it's faster.
        if (this.compareTo(val) >= 0)
            return val;
        //If this value is smaller, the above if statement will not return.
        //Return this.
        return this;
    }

    /**
     * @param val A Fraction to be multiplied by the caller
     * @return  <tt>this * val</tt>
     */
    public Fraction multiply(Fraction val)
    {
        return new Fraction(denominator.multiply(val.denominator), numerator.multiply(val.numerator));
    }

    /**
     * This method multiplies the calling fraction by 10 to a chosen power. If 
     * the power is greater than 0, the fraction is multiplied by 10 a certain 
     * number of times. If the power is less than 0, the fraction is divided 
     * by 10 a certain number of times. The power is 0, the calling fraction is 
     * returned.
     * @param pow An integer value to which 10 will be raised
     * @return <tt>This</tt> * 10*<tt>power</tt>
     */
    public Fraction multiplyByPowerOfTen(int pow)
    {
        //If the caller is zero, doesn't do anything
        if (numerator.compareTo(BigInteger.ZERO) == 0)
            return this;
        BigInteger work = BigInteger.ONE;
        if (pow > 0)
        {
            for (int i = pow; i > 0; i--)
                work = numerator.multiply(BigInteger.TEN);
            return new Fraction(denominator, work);
        }
        else if (pow < 0)
        {
            for (int i = pow; i < 0; i++)
                work = denominator.multiply(BigInteger.TEN);
            return new Fraction(work, numerator);
        }
        return this;
    }
    
    /**
     * @return A fraction with the numerator of the calling fraction multiplied by -1
     */
    public Fraction negate()
    {
        return new Fraction(denominator, numerator.negate());
    }

    /**
     * Gets the numerator
     * @return A BigInteger object representing the numerator
     */
    public BigInteger numerator()
    {
        return numerator;
    }

    /**
     * This method reduces the calling fraction by dividing its numerator and
     * denominator by their greatest common denominator.
     * @param denominator The denominator of a fraction to be reduced
     * @param numerator The numerator of a fraction to be reduced
     */
    private void reduce(BigInteger denominator, BigInteger numerator)
    {
        BigInteger num;
        BigInteger den;

        //Reduces to lowest terms--thank you, Michael Gilleland
        int numCompare = numerator.compareTo(BigInteger.ZERO);
        boolean numNonnegative = numCompare >= 0? true : false;
        int denCompare = denominator.compareTo(BigInteger.ZERO);
        boolean denNonnegative = denCompare >= 0? true: false;

        BigInteger a = numNonnegative? numerator : numerator.negate();
        BigInteger b = denNonnegative? denominator : denominator.negate();

        BigInteger g = a.gcd(b);
        if (numNonnegative == denNonnegative)
            num = a.divide(g);
        else num = a.negate().divide(g);
        den = b.divide(g);

        if (numerator.compareTo(BigInteger.ZERO) == 0)
            den = BigInteger.ONE;

        setDenominator(den);
        setNumerator(num);
    }
    
    private void setDenominator(BigInteger denominator)
    {
        this.denominator = denominator;
    }
    
    private void setNumerator(BigInteger numerator)
    {
        this.numerator = numerator;
    }

    /**
     * Returns the fractional number as a short. Note that a good deal of
     * precision may be lost, given the discrepancy in the accuracy of
     * numbers that integers provide compared to fractions.
     * @return The calling fractional number as a short integer
     */
    public short shortValue()
    {
        return (short)intValue();
    }
    
    /**
     * Returns <tt>this - val</tt>
     * @param val A Fraction to be subtracted from the caller
     * @return A Fraction that suctracts <tt>val</tt> from the caller
     */
    public Fraction subtract(Fraction val)
    {
        //Thank you Michael Gilleland for this code
        return new Fraction(denominator.multiply(val.denominator), numerator.multiply(val.denominator).subtract(val.numerator.multiply(denominator)));
    }

    /**
     * Subtracts a BigInteger from the callng Fraction
     * @param val A BigInteger
     * @return <tt>this - val</tt>
     */
    public Fraction subtract(BigInteger val)
    {
        return subtract(new Fraction(BigInteger.ONE, val));
    }

    /**
     * Creates a {@link BigDecimal} from a fractional object by dividing the
     * numerator by the denominator. If that division would result in a non-terminating
     * decimal, the decimal number is rounded to have the precision of a decimal
     * formatted with IEEE standard 754R as defined in {@link MathContext#DECIMAL128}.
     * @return A BigDecimal object that may or may not be rounded
     */
    public BigDecimal toBigDecimal()
    {
        BigDecimal quotient = BigDecimal.ZERO;
        try
        {
            quotient = (new BigDecimal(numerator)).divide((new BigDecimal(denominator))).add(BigDecimal.ZERO,MC);
        }
        catch(ArithmeticException e)
        {
            quotient = (new BigDecimal(numerator)).divide((new BigDecimal(denominator)), MC);
        }
        return quotient;
    }

    /**
     * Creates a BigDecimal from a fractional number by dividing the numerator by
     * the denominator. If this operation results in a non-terminating decimal,
     * the number is truncated to a certain number of digits, as supplied as a
     * parameter.
     * @param precision The number of digits the returned BigDecimal object will
     * have if it would otherwise be unable to terminate its decimals
     * @return A BigDecimal number with either full precision or a precision level
     * specified by a calling method
     */
    /* It does not work 09/01/2016
    public BigDecimal toBigDecimal(int precision)
    {
        try
        {
            return (new BigDecimal(numerator)).divide((new BigDecimal(denominator))).add(BigDecimal.ZERO,MC);
        }
        catch(ArithmeticException e)
        {
            BigDecimal decimal = (new BigDecimal(numerator)).divide((new BigDecimal(denominator)), precision * 2 + 1, RoundingMode.HALF_EVEN);
            return CyclicDecimalRepeater.get(decimal, precision).add(BigDecimal.ZERO,MC);
        }
    }
*/
     public BigDecimal toBigDecimal(int precision)
    {
        MathContext myMC= new MathContext(precision,MC.getRoundingMode());
        
            return (new BigDecimal(numerator,myMC)).divide((new BigDecimal(denominator,myMC)),myMC).add(BigDecimal.ZERO,myMC);
       
    }
    public BigDecimal toBigDecimal(int scale, int RM)
    {
        return (new BigDecimal(numerator)).divide((new BigDecimal(denominator)), scale, RoundingMode.valueOf(RM));
    }

    public BigDecimal toBigDecimal(MathContext mc)
    {
        return (new BigDecimal(numerator)).divide((new BigDecimal(denominator)), mc);
    }
    
    public BigDecimal toBigDecimal(RoundingMode round)
    {
        return (new BigDecimal(numerator)).divide((new BigDecimal(denominator)), round);
    }

    /**
     * Converts a 2D array of doubles to an array of fractional numbers
     */
    public static Fraction[][] toFraction(double[][] matrix)
    {
        Fraction[][] result = new Fraction[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                result[i][j] = new Fraction(matrix[i][j]);
        return result;
    }

    public String toEngineeringString()
    {
        return this.toBigDecimal().toEngineeringString();
    }

    /**
     * Simplifies the representation of the fractional caller, as compared to
     * {@link Fraction#toString()}. Here, if the {@link Fraction#denominator} is
     * <tt>1</tt>, it is excluded. Otherwise, the aforementioned default disiplay
     * method is used.
     * @return Shows only the numerator if the denominator is <tt>1</tt>;
     * otherwise, both the numerator and the denominator
     */
    public String toPlainString()
    {
        if (denominator.compareTo(BigInteger.ONE) == 0)
            return numerator.toString();
        else return toString();
    }
   
   public static String toPlainString(Fraction fr){
        if (fr.getDenominator().compareTo(BigInteger.ONE) == 0)
            return fr.getNumerator().toString();
        else return fr.toString();
           
   }

    /**
     * The fraction is represented in its most simplest form,
     * {@link Fraction#numerator}/{@link Fraction#denominator}.
     * @return Shows the numerator, followed by a slash, which is followed by
     * a denominator
     */
    public String toString() 
    {
      if(numerator.compareTo(BigInteger.ZERO) == 0)
           return "0";
        
      if(denominator.compareTo(BigInteger.ZERO) == 0)
          return numerator.toString().concat("/").concat("0");
    
        reduce(denominator,numerator);
 
        return numerator.toString().concat("/").concat(denominator.toString());
    }
  public static String toString( Fraction fr){
       BigInteger nmr = fr.getNumerator();
       BigInteger dnmr = fr.getDenominator();
      if(nmr.compareTo(BigInteger.ZERO) == 0)
           return "0";
        
      if(dnmr.compareTo(BigInteger.ZERO) == 0)
          return nmr.toString().concat("/").concat("0");

       Fraction rFr = reduceFraction(dnmr,nmr);    
        nmr = rFr.getNumerator();
        dnmr= rFr.getDenominator();

        return nmr.toString().concat("/").concat(dnmr.toString());
          
  }
    /**
     * This method creates a fraction by dividing its numerator and
     * denominator by their greatest common denominator.
     * @param denominator The denominator of a fraction to be reduced
     * @param numerator The numerator of a fraction to be reduced
     */
    public static Fraction reduceFraction(BigInteger denominator, BigInteger numerator)
    {
        BigInteger num;
        BigInteger den;

        //Reduces to lowest terms--thank you, Michael Gilleland
        int numCompare = numerator.compareTo(BigInteger.ZERO);
        boolean numNonnegative = numCompare >= 0? true : false;
        int denCompare = denominator.compareTo(BigInteger.ZERO);
        boolean denNonnegative = denCompare >= 0? true: false;

        BigInteger a = numNonnegative? numerator : numerator.negate();
        BigInteger b = denNonnegative? denominator : denominator.negate();

        BigInteger g = a.gcd(b);
        if (numNonnegative == denNonnegative)
            num = a.divide(g);
        else num = a.negate().divide(g);
        den = b.divide(g);

        if (numerator.compareTo(BigInteger.ZERO) == 0)
            den = BigInteger.ONE;

        return new Fraction(den,num);
    }
public static void main(String[] argv) throws Exception {

      System.out.println("Testing abs()\n");
      System.out.println("========================\n");
      Fraction fr = new Fraction("-4/5");  
      System.out.println("abs(-4/5) = "+fr.abs().toString()+"\n");
      System.out.println("========================\n");

      System.out.println(" (new Fraction(\"-4/8\")).toString() = " +(new Fraction("-4/8")).toString()+"\n");
       
}


}
