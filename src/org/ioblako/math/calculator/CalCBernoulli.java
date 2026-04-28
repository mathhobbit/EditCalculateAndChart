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
import org.ioblako.math.linearalgebra.Fraction;

public class CalCBernoulli implements CalcFunction {
public String getHelp(){
return "Bernoulli(n)  returns the nth member of the Benoulli sequence. "+System.lineSeparator()+
	"For example, Bernoulli(0)=1, Bernoulli(1)=-1/2, Bernoulli(2*k +1) =0 for all k=1, 2, 3, ...";
}
public String eval(String argv) throws Exception{
int n = Integer.parseInt(argv);
if(n == 0)
  return "1";
if(n == 1)
  return "-1/2";
if(n%2 == 1)
  return "0";
int m = n/2+1;
System.out.println(m);
Fraction[] Bernoulli = new Fraction[m];
BigInteger[] Factorials = new BigInteger[m+2]; 
Factorials[0] = BigInteger.ONE;
Factorials[1] = new BigInteger("2");
Bernoulli[0] = Fraction.ONE;
for(int i=2; i<m+1;i++){
    Factorials[i]= Factorials[i-1].multiply(new BigInteger((Integer.valueOf((2*i-1)*2*i)).toString()));
//    System.out.println(Factorials[i].toString());
}
Fraction sum=Fraction.ZERO;
Fraction fact=Fraction.ZERO;
for(int i=1; i<m;i++){
	sum=Fraction.ZERO;
	for(int k=0;k<i;k++){
                 fact= new Fraction( Factorials[k].multiply(Factorials[i-k+1]),BigInteger.ONE);
		sum=sum.add(Bernoulli[k].multiply(fact));
	}
    sum = sum.multiply(new Fraction(1,2)).multiply(new Fraction(BigInteger.ONE,Factorials[i]));
   Bernoulli[i] =  (new Fraction(2*i+1,1)).subtract(sum);
 //  System.out.println(Bernoulli[i].toString());
}


  return Bernoulli[m-1].toString(); 
}
String Report="";
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}

}
