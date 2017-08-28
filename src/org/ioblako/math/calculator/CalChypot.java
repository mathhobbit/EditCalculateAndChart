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

public class CalChypot implements CalcFunction {
String Report="";
public String getHelp(){
return "sqrt(x2 +y2), where x and y are the arguments given.";
}

public String eval(String st) throws Exception{

if(!st.contains(","))
  throw new Exception("hypot requires two arguments!");

String[] arg = st.split(",");

if(arg.length != 2)
  throw new Exception("hypot requires two arguments!");

return (new CalCsqrt()).eval(jc.eval("powb("+arg[0]+",2)+powb("+arg[1]+",2)"));
}
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}

}
