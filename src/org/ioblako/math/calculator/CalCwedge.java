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

import org.ioblako.math.linearalgebra.Fraction;
import org.ioblako.math.linearalgebra.Matrix;

/**
 *
 * @author sergey_nikitin
 */
public class CalCwedge implements CalcFunction{
        String Report="";
public String getHelp(){
return "wedge({x1,x2,...,xn},{y1,y2,...,yn}) returns the wedge product";
}
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}
public String eval(String argv) throws Exception{
    argv=argv.trim();
Matrix A, B;

if(!argv.contains(","))
   throw new Exception("wedge{matrix_A},{matrix_B}");
if(!argv.startsWith("{") || !argv.endsWith("}"))
   throw new Exception("wedge({matrix_A},{matrix_B}");

String mA="", mB="", tmp;

int index=0;
 
tmp= argv.substring(1);
try{
 while(tmp.substring(0,tmp.indexOf("}")).contains("{")&& 
       tmp.indexOf("}") < tmp.length() -1){
     mA=String.format("%s%s",mA,tmp.substring(0,tmp.indexOf("}")+1));
     tmp=tmp.substring(tmp.indexOf("}")+1);
}
     mA=String.format("%s%s",mA,tmp.substring(0,tmp.indexOf("}")));
     tmp=tmp.substring(tmp.indexOf("}")+1);
     mB=tmp.substring(tmp.indexOf("{")+1,tmp.lastIndexOf("}"));
}
catch(Exception e){
   throw new Exception("wedge((matrix_A),(matrix_B)");
}

A=(new CalClslv()).StringToMatrix(mA);
B=(new CalClslv()).StringToMatrix(mB);

if(A.m() != B.m() || A.n()!=B.n())
   throw new Exception("wedge({A},{B}) is not defined for these {A} and {B}!");

try{
                    for (int i = 0; i < A.m(); i++)
                        for (int j = 0; j < A.n(); j++)
                        {
                           A.data[i][j] = A.data[i][j].multiply(Fraction.ONE);
                         if (j == 0)
                            B.data[i][j] = B.data[i][j].multiply(Fraction.ONE);
                        }
if(A.n()==1)
{
return "{"+(A.data[0][0].multiply(B.data[0][0])).toBigDecimal(jc.MC).stripTrailingZeros().toPlainString()+"}";
}
String rt="{";
                
                    for (int i = 0; i < A.n(); i++)
                        for (int j = i+1; j < A.n(); j++)
                        {
                               
                         Fraction X = A.data[0][i].multiply(B.data[0][j]).subtract(B.data[0][i].multiply(A.data[0][j]));
                          if(rt.compareTo("{")==0)
                            rt=rt+X.toBigDecimal(jc.MC).stripTrailingZeros().toPlainString();
                          else 
                            rt=rt+","+X.toBigDecimal(jc.MC).stripTrailingZeros().toPlainString();

                        }

                         return rt+"}";
                }
                catch(ArrayIndexOutOfBoundsException e) 
                {
                    throw new Exception("wedge({A},{B}) is not defined for these {A} and {B}!");
                }
}

    
}
