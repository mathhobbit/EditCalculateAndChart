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
import org.ioblako.math.linearalgebra.Fraction.Fraction;

public class CalCinv implements CalcFunction{
String Report="";
public String getHelp(){
  return "inv(A) returns the inverse of A if it exists."; 
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

if(A.n() != A.m())
  throw new Exception("Inverse matrix does not exists!");

int N = A.n();

Fraction[][]  ident = new Fraction[N][N];

for(int i = 0;i<N;i++)
    for(int j = 0; j< N; j++)
        if(i==j)
            ident[i][j] = Fraction.ONE;
        else
            ident[i][j] = Fraction.ZERO;
Fraction[][] agm = new Fraction[N][2*N];

for(int i = 0;i<N;i++)
    for(int j = 0; j< N; j++)
          agm[i][j]=A.get(i,j);
for(int i = 0;i<N;i++)
    for(int j = N; j< 2*N; j++)
          agm[i][j]=ident[i][j-N];
          
//return jc.toStr(new Matrix(agm));
                   
A = SystemSolver.toRREF(new Matrix(agm));

for(int i = 0;i<N;i++)
    for(int j = 0; j< N; j++)
          ident[i][j]=A.get(i,N+j);

return jc.toStr(new Matrix(ident));
}
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}

}
