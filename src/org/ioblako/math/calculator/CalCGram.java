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

import org.ioblako.math.linearalgebra.Matrix;
import org.ioblako.math.linearalgebra.SystemSolver;
import org.ioblako.math.linearalgebra.Fraction;

public class CalCGram implements CalcFunction{
String Report="";
public String getHelp(){
  return "Gram(A) returns the product: A times transpose of A."; 
}
public String eval(String argv) throws Exception{
argv=argv.trim();
if(argv.contentEquals("{}"))
    throw new Exception("Erroneous matrix \"{}\"!");
if(argv.startsWith("{"))
   argv=argv.substring(1);
if(argv.endsWith("}"))
   argv=argv.substring(0, argv.length()-1);
if(!argv.contains(",")&&!argv.contains(";"))
  return Double.toString(1/Double.valueOf(argv)); 

Matrix A = jc.StringToMatrix(argv);
                   
return jc.toStr(A.getGram());
}
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}

}
