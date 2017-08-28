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
import org.ioblako.math.linearalgebra.Fraction.Fraction;

/**
 *
 * @author sergey_nikitin
 */
public class CalCdot  implements CalcFunction{
 public String getHelp(){
return "dot({A},{B}) is the dot product between A and B.";
}

String Report=""; 
    public String eval(String argv) throws Exception{
argv=argv.trim();
Matrix A, B;

if(!argv.contains(","))
   throw new Exception("dot{matrix_A},{matrix_B}");
if(!argv.startsWith("{") || !argv.endsWith("}"))
   throw new Exception("dot({matrix_A},{matrix_B}");

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
   throw new Exception("dot((matrix_A),(matrix_B)");
}

A=(new CalClslv()).StringToMatrix(mA);
B=(new CalClslv()).StringToMatrix(mB);

if(A.m() != B.m() || A.n()!=B.n())
   throw new Exception("dot({A},{B}) is not defined for these {A} and {B}!");

try {
                    for (int i = 0; i < A.m(); i++)
                        for (int j = 0; j < A.n(); j++)
                        {
                           A.data[i][j] = A.data[i][j].multiply(Fraction.ONE);
                         if (j == 0)
                            B.data[i][j] = B.data[i][j].multiply(Fraction.ONE);
                        }
                    Matrix AtrB = A.multiply(B.transpose());
                    Fraction dotPr = Fraction.ZERO;
                    for(int k = 0;k < AtrB.n(); k++)
                          dotPr = dotPr.add(AtrB.data[k][k]);
                    return dotPr.toBigDecimal(jc.MC).stripTrailingZeros().toPlainString();
                }
                catch(ArrayIndexOutOfBoundsException e) 
                {
                    throw new Exception("dot({A},{B}) is not defined for these {A} and {B}!");
                }
    }
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}


}
