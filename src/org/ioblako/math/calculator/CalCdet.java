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
import org.ioblako.math.linearalgebra.Fraction;
import java.util.ArrayList;
public class CalCdet implements CalcFunction {
String Report="";
public String getHelp(){
return "det(A) is the determinant of A.";
}
public String eval(String argv) throws Exception{

if(argv.startsWith("{"))
    argv=argv.substring(1);
if(argv.endsWith("}"))
    argv=argv.substring(0,argv.length()-1);

if(!argv.contains(";")&&!argv.contains(","))
      return jc.eval(argv);

if(!argv.contains(";"))
      throw new Exception("Matrix is a set of rows delimited by \";\"!");


Fraction data[][];
String[] Rows =argv.split(";");

int M = Rows.length;
String[] tmp = Rows[0].split(",");
int N =tmp.length;

             if( N != M)
                   throw new Exception("Non-square matrix!?");

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


  return (new Matrix(data)).determinant().toString(); 
}
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}

}
