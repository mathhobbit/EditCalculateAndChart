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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import org.ioblako.math.linearalgebra.Fraction;
public class CalCpowl implements CalcFunction{

@Override
public String getHelp(){
return "powl(x,y) calculates x^y, x raised to the powler of y. It is implemented as exp(y*ln(x))." ;
}

@Override
public String eval(String argv) throws Exception{
if(argv.indexOf(',')==-1)
   throw new Exception("powl(x,y) requires two arguments.");
String[] args = argv.split(",");
if(args.length!=2)
   throw new Exception("powl(x,y) requires two arguments.");

args[0] = jc.eval(args[0]);
args[1] = jc.eval(args[1]);

if(jc.compare(args[0],"0")==0&&!args[1].startsWith("-"))
    return "0";

 if(args[1].indexOf('/')==-1 &&
    args[1].indexOf('.')==-1){
  if(args[1].startsWith("-")) {
     return localPow((new Fraction(args[0])).invert().toBigDecimal(),new BigInteger(args[1].substring(1)),jc.MC).toPlainString();
   }
   else
   return localPow((new Fraction(args[0])).toBigDecimal(),new BigInteger(args[1]),jc.MC).toPlainString(); 
}


boolean negateIt=false; 
if(args[0].startsWith("-")){
Fraction dec = Fraction.plainFraction(args[1]);
if(dec.getNumerator().intValue()%2==0)
     args[0]=args[0].substring(1);  
else{
 if(dec.getDenominator().intValue()%2==0)
   throw new Exception("powl("+args[0]+","+args[1]+") is not defined.");
 else
     args[0]=args[0].substring(1);  
     negateIt=true;
   }
}
CalCexp exp = new CalCexp();
CalCln ln = new CalCln();

if(args[1].indexOf('/')==-1){
if(negateIt)
return "-"+exp.eval((new BigDecimal(ln.eval(args[0]),jc.MC)).multiply(new BigDecimal(args[1],jc.MC),jc.MC).toPlainString()); 
return exp.eval((new BigDecimal(ln.eval(args[0]),jc.MC)).multiply(new BigDecimal(args[1],jc.MC),jc.MC).toPlainString()); 
}
else{
if(negateIt)
return "-"+exp.eval((new BigDecimal(ln.eval(args[0]),jc.MC)).multiply((new Fraction(args[1])).toBigDecimal(jc.MC.getPrecision()),jc.MC).toPlainString()); 

return exp.eval((new BigDecimal(ln.eval(args[0]),jc.MC)).multiply((new Fraction(args[1])).toBigDecimal(jc.MC.getPrecision()),jc.MC).toPlainString()); 
} 
}
String Report="";
@Override
public void setReport(String str){
 Report=str;
}
@Override
public String getReport(){
return Report;
}
public BigDecimal localPow(BigDecimal b, BigInteger pw, MathContext mc){
   
    BigInteger Threshold = new BigInteger("100000000");
    BigInteger Counter = Threshold;
    BigInteger Divider = BigInteger.ONE;
    if(pw.compareTo(Threshold)<=0)
         return b.pow(pw.intValueExact(), mc);
    
     
    while(Counter.compareTo(pw)<=0){
        Divider=Divider.add(BigInteger.ONE);
         Counter =Counter.add(Threshold);
    }
     Divider = Divider.subtract(BigInteger.ONE);
     Counter = Counter.subtract(Threshold);
     
     BigInteger Remainder = pw.subtract(Counter);
      
     return b.pow(Remainder.intValueExact(),jc.MC).multiply(localPow(b.pow(100000000,jc.MC),Divider,jc.MC),jc.MC);
     
     
}
}
