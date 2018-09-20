/*
 * Copyright (C) 2017 Sergey Nikitin
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
package org.ioblako.math.calculator;

import java.math.BigInteger;
/**
 *
 * @author Sergey Nikitin
 */
public class CalCFE implements CalcFunction{
     String Report="";
    @Override
    public String getReport() {
        return Report;
    }

    @Override
    public void setReport(String str) {
        Report=(Report.compareTo("")==0)?str:Report+System.lineSeparator()+str;
    }

    @Override
    public String getHelp() {
        return "FE(n) returns the integer p. FE calculates integers s, p and q such that 2^s * n * q = 2^s*(2^p - 1). "+System.lineSeparator()
                + " FE stays for Ferma, Euler statement c^E(a)=1 mod(a) for coprime a and c, "+System.lineSeparator()
                + "where E(a) is the Euler function:"+System.lineSeparator()
                + " the count of numbers coprime with and smaller than a."+System.lineSeparator()
                +"FE returns the length of the orbit of \"2\" in the multiplicative group of the ring Z/n*Z";
    }

    @Override
    public String eval(String argv) throws Exception {
        
        BigInteger m = new BigInteger(jc.eval(argv));
        
        if(m.compareTo(BigInteger.ZERO)<=0)
               throw new Exception("FA algorithm is defined only for natural numbers");
                    
         BigInteger n=BigInteger.ONE, Mersenne = m, original_m=BigInteger.ONE;


int shift = 0;
if(!Mersenne.testBit(0)){
    shift = getUnitI(Mersenne);
    Mersenne=Mersenne.shiftRight(shift);
    original_m = m;
    m=m.shiftRight(shift);
}

  if(getZeroI(Mersenne) == -1){
     setReport(Mersenne.toString() +" is a Mersenne number.");
       
if(shift == 0){
     setReport(Mersenne.toString()+" = "+"2^"+
      BigInteger.valueOf(Mersenne.bitLength()).toString()+
        " - 1 ");
     return BigInteger.valueOf(Mersenne.bitLength()).toString();
}
else{
     setReport(original_m.toString()+" = "+"(2^"+
      BigInteger.valueOf(Mersenne.bitLength()).toString()+
        " - 1)*2^"+shift); 
     return BigInteger.valueOf(Mersenne.bitLength()).toString();
}
  }
int k, M_Power = 0;
BigInteger Mersenne_Power;
 while((k = getZeroI(Mersenne))!=-1){
      //Mersenne_Power = Mersenne_Power.add(BigInteger.valueOf(k));
        M_Power = M_Power + k;
        //n = n.add(BigInteger.ONE.shiftLeft(Mersenne_Power.intValue()));
      n = n.add(BigInteger.ONE.shiftLeft(M_Power));
      Mersenne = Mersenne.shiftRight(k).add(m);
 }
 
Mersenne_Power = BigInteger.valueOf(M_Power);
Mersenne_Power = Mersenne_Power.add(BigInteger.valueOf(Mersenne.bitLength()));
if(shift == 0){
     setReport(m.toString()+"*"+n.toString()+" = 2^"+Mersenne_Power.toString()+"-1");
     return Mersenne_Power.toString();
}
else{
     setReport(original_m.toString()+"*"+n.toString()+" = (2^"+Mersenne_Power.toString()+"-1)*2^"+shift);
     return Mersenne_Power.toString();
}    
    }
    
public static int getZeroI(BigInteger m){
for (int i = 0 ;i<m.bitLength();i++)
  if(!m.testBit(i))
     return i;
return -1;
}

public static int getUnitI(BigInteger m){
for (int i = 0 ;i<m.bitLength();i++)
  if(m.testBit(i))
     return i;
return -1;
}
    
    
}
