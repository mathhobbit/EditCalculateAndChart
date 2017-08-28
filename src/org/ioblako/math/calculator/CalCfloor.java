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

public class CalCfloor implements CalcFunction {
String Report="";
public String getHelp(){
return "floor(x) is the largest integer smaller than x.";
}
public String eval(String argv) throws Exception{

if(argv.indexOf('/')==-1&&
   argv.indexOf('.')==-1)
   return argv;
BigDecimal inp;
if(argv.indexOf('/')==-1)
  inp = new BigDecimal(argv,jc.MC);
else
  inp=(new Fraction(argv)).toBigDecimal();
  BigDecimal ret =BigDecimal.ZERO;
if(inp.compareTo(ret)==0)
   return "0";
if(inp.compareTo(ret)<0){
while(inp.compareTo(ret)<0)
    ret=ret.subtract(BigDecimal.ONE);
}
else{
while(inp.compareTo(ret)>0)
    ret=ret.add(BigDecimal.ONE);
    ret=ret.subtract(BigDecimal.ONE);
}

  return ret.toPlainString();
}
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}

}
