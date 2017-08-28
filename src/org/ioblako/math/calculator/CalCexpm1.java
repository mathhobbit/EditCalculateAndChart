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

public class CalCexpm1 implements CalcFunction {
String Report="";
public String getHelp(){
return "Returns exp(x) - 1.";
}
public String eval(String argv) throws Exception{
String bf = (new CalCexp()).eval(argv);
if(bf.indexOf('/')==-1)
  return (new BigDecimal(bf)).subtract(BigDecimal.ONE,jc.MC).toPlainString(); 
else
  return (new Fraction(bf)).toBigDecimal().subtract(BigDecimal.ONE,jc.MC).toPlainString(); 
}
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}

}
