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
import org.ioblako.math.linearalgebra.Fraction.Fraction;


public class CalCasin implements CalcFunction {
String Report="";
Fraction error;
public String getHelp(){
return "inverse of sin(x). That means sin(asin(x))=x for x from [-1,1]";
}
public String eval(String argv) throws Exception{
argv=jc.eval(argv);

if(argv.contentEquals("0"))
  return "0";
if(argv.contentEquals("1"))
  return "pi/2";
if(argv.contentEquals("-1"))
  return "-pi/2";

int precision = jc.MC.getPrecision();

String errorString="0.";
  for(int j = 1; j< precision ;j++)
      errorString=errorString+'0';
error = new Fraction(errorString+'0'+'1');
boolean negateIt=false;

if(argv.startsWith("-")){
  negateIt=true;
  argv=argv.substring(1);
}

Fraction x = new Fraction(argv);

if(x.compareTo(error)<=0)
 return "0";

if(x.compareTo(Fraction.ONE)>0)
 throw new Exception("asin(x) is not defined for x outside of [-1,1]");

if(x.compareTo(Fraction.ONE.subtract(error))>=0){
      if(negateIt)
         return "-pi/2";
      else
        return "pi/2";
}
Fraction mid =  Fraction.ONE.divide(new Fraction( (new CalCsqrt()).eval("2")));
if(x.compareTo(mid)<0){         
  if(negateIt)
   return "-"+asin(x).toBigDecimal(precision).add(BigDecimal.ZERO,jc.MC).toPlainString();
  else
   return asin(x).toBigDecimal(precision).add(BigDecimal.ZERO,jc.MC).toPlainString();
}
if(x.subtract(mid).abs().compareTo(error)<0){         
  if(negateIt)
   return "-pi/4";
  else
   return "pi/4";
}
  x= new Fraction( (new CalCsqrt()).eval(BigDecimal.ONE.subtract(x.toBigDecimal(precision).pow(2)).toPlainString()));

  String rt=asin(x).toBigDecimal(precision).add(BigDecimal.ZERO,jc.MC).toPlainString();
  if(negateIt)
    return "-"+jc.eval("pi/2-"+rt); 
  else
    return jc.eval("pi/2-"+rt); 
}

Fraction asin(Fraction x){
Fraction asin=x;
long step=0;
Fraction mistake=x;

Fraction Al=new Fraction("1/2");
while(mistake.divide(new Fraction(1,2*step+1) ).abs().compareTo(error)>0){
  mistake=mistake.multiply(x).multiply(x);
  mistake=mistake.multiply(Al.add(new Fraction(1,step))).divide(new Fraction(1,step+1));
  step=step+1;
  asin=asin.add(mistake.divide(new Fraction(1,2*step+1)));
}
return asin;



}
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}

}
