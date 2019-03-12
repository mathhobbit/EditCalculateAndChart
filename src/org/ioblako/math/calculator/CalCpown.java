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
import org.ioblako.math.linearalgebra.Fraction;

public class CalCpown implements CalcFunction{
Fraction error;

public String getHelp(){
return "powb(x,y) calculates x^y, x raised to the power of y. It is implemeted with Newton Method" ;
}

public String eval(String argv) throws Exception{
if(argv.indexOf(',')==-1)
   throw new Exception("powb(x,y) requires two arguments.");
String[] args = argv.split(",");
if(args.length!=2)
   throw new Exception("powb(x,y) requires two arguments.");
args[0] = jc.eval(args[0]);
args[1] = jc.eval(args[1]);

if(jc.compare(args[0],"0")==0&&!args[1].startsWith("-"))
    return "0";

boolean negateIt=false;
if(args[0].startsWith("-")){
Fraction dec = Fraction.plainFraction(args[1]);
if(dec.getNumerator().intValue()%2==0)
     args[0]=args[0].substring(1);
else{
 if(dec.getDenominator().intValue()%2==0)
   throw new Exception("powb("+args[0]+","+args[1]+") is not defined.");
 else
     args[0]=args[0].substring(1);
     negateIt=true;
   }
}
 if(args[1].indexOf('/')==-1 &&
    args[1].indexOf('.')==-1){
  if(args[1].startsWith("-")) {
     if(negateIt)
     return "-"+(new Fraction(args[0])).invert().toBigDecimal().pow(Integer.parseInt(args[1].substring(1))).toPlainString();
    else
     return (new Fraction(args[0])).invert().toBigDecimal().pow(Integer.parseInt(args[1].substring(1))).toPlainString();
   }
   else
   if(negateIt)
   return "-"+(new BigDecimal(args[0])).pow(Integer.parseInt(args[1])).toPlainString();
  else
   return (new BigDecimal(args[0])).pow(Integer.parseInt(args[1])).toPlainString();
}
int precision = jc.MC.getPrecision();
String errorString="0.";
  for(int j = 1; j< precision ;j++)
      errorString=errorString+'0';
error = new Fraction(errorString+'1');

return "Not Working Yet";
}

String Report="";
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}


}
