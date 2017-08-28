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

public class CalCcosh implements CalcFunction {
String Report="";
public String getHelp(){
return "cosh(x), cosine hyperbolic. It is a x-coordinate of a point on the hyperbola x^2-y^2=1, cosh(x)=(e^x+e^{-x})/2.";
}
public String eval(String argv) throws Exception{
   
   String argvM = jc.eval('-'+argv);
   Fraction expP = new Fraction((new CalCexp()).eval(argv));   
   Fraction expM = new Fraction((new CalCexp()).eval(argvM));   

   Fraction TWO = Fraction.ONE.add(Fraction.ONE);
  return expP.add(expM).divide(TWO).toBigDecimal().toPlainString(); 
}
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}

}
