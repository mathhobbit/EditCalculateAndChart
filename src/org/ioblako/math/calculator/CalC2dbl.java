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

public class CalC2dbl implements CalcFunction {
String Report="";
public String getHelp(){
return "convert each number in your formula into double format.";
}
public String eval(String argv) throws Exception{
argv=jc.eval(argv);
if(argv.indexOf('^')!=-1)
 throw new Exception("2dbl does nor support \"^\"!");
 String rt="";
 if(argv.startsWith("(")){
   rt="(";
   argv=argv.substring(1);
  }
 if(argv.startsWith("{")){
   rt="{";
   argv=argv.substring(1);
  }
   String tmp = argv;
  String[] els = tmp.split("[ ,;{}()]");
for(String bb:els){
  if(!bb.contentEquals("")){
       if(bb.indexOf('/')==-1)
       rt=String.format("%s%s%s",rt,tmp.substring(0,tmp.indexOf(bb)),
                      (new BigDecimal(Double.toString(Double.valueOf(bb)),jc.MC)).toPlainString());
       else
       rt=String.format("%s%s%s",rt,tmp.substring(0,tmp.indexOf(bb)),
                     (new BigDecimal(Double.toString(jc.getFraction(bb).doubleValue()),jc.MC)).toPlainString());
   }
       tmp=tmp.substring(tmp.indexOf(bb)+bb.length());
}
       return String.format("%s%s",rt,tmp);
}
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}

}
