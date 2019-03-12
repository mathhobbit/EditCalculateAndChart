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
public class CalCcross implements CalcFunction{
        String Report="";
public String getHelp(){
return "cross({x1,x2,x3},{y1,y2,y3}) returns the cross product {x2*y3-y2*x3,y1*x3-y3*x1,x1*y2-x2*y1}";
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
   throw new Exception("cross{matrix_A},{matrix_B}");
if(!argv.startsWith("{") || !argv.endsWith("}"))
   throw new Exception("cross({matrix_A},{matrix_B}");

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
   throw new Exception("cross((matrix_A),(matrix_B)");
}

A=(new CalClslv()).StringToMatrix(mA);
B=(new CalClslv()).StringToMatrix(mB);

if(A.m() != B.m() || A.n()!=B.n())
   throw new Exception("cross({A},{B}) is not defined for these {A} and {B}!");
if((A.m() != 1 || A.n()!=3) && (A.m()!=3 || A.n()!=1))
    throw new Exception("cross({A},{B}) is not defined for these {A} and {B}!");

try {
                    for (int i = 0; i < A.m(); i++)
                        for (int j = 0; j < A.n(); j++)
                        {
                           A.data[i][j] = A.data[i][j].multiply(Fraction.ONE);
                         if (j == 0)
                            B.data[i][j] = B.data[i][j].multiply(Fraction.ONE);
                        }
                   if(A.m()==1){
                         Fraction X = A.data[0][1].multiply(B.data[0][2]).subtract(B.data[0][1].multiply(A.data[0][2]));
                         Fraction Y = A.data[0][2].multiply(B.data[0][0]).subtract(A.data[0][0].multiply(B.data[0][2]));
                         Fraction Z = A.data[0][0].multiply(B.data[0][1]).subtract(A.data[0][1].multiply(B.data[0][0]));
                         return "{"+X.toBigDecimal(jc.MC).stripTrailingZeros().toPlainString()+","+
                                 Y.toBigDecimal(jc.MC).stripTrailingZeros().toPlainString()+","+
                                 Z.toBigDecimal(jc.MC).stripTrailingZeros().toPlainString()+"}";
                    }
                   if(A.m()==3){
                         Fraction X = A.data[1][0].multiply(B.data[2][0]).subtract(B.data[1][0].multiply(A.data[2][0]));
                         Fraction Y = A.data[2][0].multiply(B.data[0][0]).subtract(A.data[0][0].multiply(B.data[2][0]));
                         Fraction Z = A.data[0][0].multiply(B.data[1][0]).subtract(A.data[1][0].multiply(B.data[0][0]));
                         return "{"+X.toBigDecimal(jc.MC).stripTrailingZeros().toPlainString()+";"+
                                 Y.toBigDecimal(jc.MC).stripTrailingZeros().toPlainString()+";"+
                                 Z.toBigDecimal(jc.MC).stripTrailingZeros().toPlainString()+"}";
                    }
                }
                catch(ArrayIndexOutOfBoundsException e) 
                {
                    throw new Exception("cross({A},{B}) is not defined for these {A} and {B}!");
                }
    return "";
}

    
}
