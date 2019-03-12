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
import org.ioblako.math.linearalgebra.Fraction;


public class CalCatan2 implements CalcFunction {
String Report="";
public String getHelp(){
return "atan(y/x)";
}
public String eval(String st) throws Exception{
if(!st.contains(","))
  throw new Exception("atan2 requires two arguments!");

String[] arg = st.split(",");

if(arg.length != 2)
  throw new Exception("atan2 requires two arguments!");

Fraction y=new Fraction(jc.eval(arg[0]));
Fraction x=new Fraction(jc.eval(arg[1]));
if(y.compareTo(Fraction.ZERO)==0&&
   x.compareTo(Fraction.ZERO)==0)
  throw new Exception("atan2 is not defined for (0,0)!");

if(y.compareTo(Fraction.ZERO)==0){
    if(x.compareTo(Fraction.ZERO)>0)
        return "0";
    else
        return "pi";
}
if(x.compareTo(Fraction.ZERO)==0){
    if(y.compareTo(Fraction.ZERO)>0)
        return "pi/2";
    else
        return "3*pi/2";
}

if(y.compareTo(Fraction.ZERO)<0&&
   x.compareTo(Fraction.ZERO)<0)
  return jc.eval("pi+"+(new CalCatan()).eval(y.divide(x).toPlainString()));

if(y.compareTo(Fraction.ZERO)<0&&
   x.compareTo(Fraction.ZERO)>0)
  return jc.eval("2*pi+"+(new CalCatan()).eval(y.divide(x).toPlainString()));
 
if(y.compareTo(Fraction.ZERO)>0&&
   x.compareTo(Fraction.ZERO)<0)
  return jc.eval("pi+"+(new CalCatan()).eval(y.divide(x).toPlainString()));

  return jc.eval((new CalCatan()).eval(y.divide(x).toPlainString()));
}
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}

}
