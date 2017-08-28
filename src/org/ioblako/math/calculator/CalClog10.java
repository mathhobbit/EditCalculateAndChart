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
public class CalClog10 implements CalcFunction {
public String getHelp(){
return "The base 10 logarithm.";
}
public String eval(String argv) throws Exception{
if(argv.indexOf('/')==-1 && argv.indexOf('.')==-1&&argv.length()>1){
 String bf=argv.substring(1);
 if(argv.startsWith("1")){
    int i;
    for(i = 0;i<bf.length();i++)
          if(bf.charAt(i) !='0')
             break; 
     if(i == bf.length())
        return Integer.toString(i);
  } 
}
return (new Fraction((new CalCln()).eval(argv))).divide(new Fraction((new CalCln()).eval("10"))).toBigDecimal(jc.MC).toPlainString();
}
String Report="";
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}

}
