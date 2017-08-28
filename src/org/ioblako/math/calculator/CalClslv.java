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
import org.ioblako.math.linearalgebra.LinearSystem;
import org.ioblako.math.linearalgebra.Fraction.Fraction;

public class CalClslv implements CalcFunction{

public String getHelp(){
return "lslv({A},{b}) delivers the solution for Ax=b. For example, lslv({1,1;1,-1},{1;1}) returns {1,0}.";
}

String Report="";
public String eval(String argv) throws Exception{
argv=argv.trim();
Matrix A, B;

if(!argv.contains(","))
   throw new Exception("lslv({matrix_A},{matrix_b}");
if(!argv.startsWith("{") || !argv.endsWith("}"))
   throw new Exception("lslv({matrix_A},{matrix_b}");

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
   throw new Exception("lslv((matrix_A),(matrix_b)");
}

A=StringToMatrix(mA);
B=StringToMatrix(mB);
LinearSystem newSys;
if(A.m() != B.m())
   throw new Exception("inconsistent system!!!");
 try {
                    for (int i = 0; i < A.m(); i++)
                        for (int j = 0; j < A.n(); j++)
                        {
                           A.data[i][j] = A.data[i][j].multiply(Fraction.ONE);
                         if (j == 0)
                            B.data[i][j] = B.data[i][j].multiply(Fraction.ONE);
                        }
                    newSys = new LinearSystem(A, B);
                }
                catch(ArrayIndexOutOfBoundsException e) 
                {
                    throw new Exception("Erroneous matrix?!");
                }
  newSys.determineSolutions();
  setReport(String.format("%s%s%s",newSys.getReport(),System.lineSeparator(),newSys.showSolutions()));
  //return newSys.showSolutions(); 
//   System.out.println(Report);
  return newSys.getSolutionForJC();
}
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}
public Matrix StringToMatrix(String argv) throws Exception{
Fraction data[][];
String[] Rows =argv.split(";");

int M = Rows.length;
String[] tmp = Rows[0].split(",");
int N =tmp.length;


data = new Fraction[M][N];

for (int j = 0; j < tmp.length; j++)
                data[0][j] = new Fraction(jc.eval(tmp[j].trim()));


for(int i  = 1; i < Rows.length; i++)
        {
            tmp = Rows[i].split(",");
               if(tmp.length != N)
                   throw new Exception("Erroneous matrix!?");
            for (int j = 0; j < tmp.length; j++)
                data[i][j] = new Fraction(jc.eval(tmp[j].trim()));
        }


  return new Matrix(data);

}

}
