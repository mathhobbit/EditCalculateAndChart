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


public class CalCln1p implements CalcFunction {
public String getHelp(){
return "Returns ln(1+x).";
}
public String eval(String argv) throws Exception{
  argv=jc.eval(argv);
  BigDecimal Arg;
  if(argv.indexOf('/')==-1)
  Arg=new BigDecimal(argv);
  else
   Arg=(new Fraction(argv)).toBigDecimal();

   Arg=Arg.add(BigDecimal.ONE,jc.MC);
  return (new CalCln()).eval(Arg.toPlainString());
}
String Report="";
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}

}
