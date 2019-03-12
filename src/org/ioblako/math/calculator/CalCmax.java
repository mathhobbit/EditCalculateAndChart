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
import org.ioblako.math.linearalgebra.Fraction;

public class CalCmax implements CalcFunction {

public String getHelp(){
return "max(X1,..,Xn) is the largest among X1,..Xn";
}

public String eval(String st) throws Exception{
Fraction mx = null;

if(!st.contains(","))
  return jc.eval(st);

String[] arg = st.split(",");
Fraction tm;
for (String bf : arg){
   if(mx == null)
       mx = new Fraction(jc.eval(bf));
   else{
     tm = new Fraction(jc.eval(bf));
     if(mx.compareTo(tm)<0)
         mx=tm ;
  }
}
if(st.indexOf('/')==-1)
     return mx.toBigDecimal().toPlainString();
else
    return mx.toPlainString();
}
String Report="";
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}

}
