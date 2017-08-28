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
//import java.math.BigDecimal;
import org.ioblako.math.linearalgebra.Fraction.Fraction;

public class CalCsqrt implements CalcFunction {
public String getHelp(){
return "sqrt(x)=x^1/2";
}
public String eval(String argv) throws Exception{

int precision = jc.MC.getPrecision();
String errorString="0.";
  for(int j = 1; j< precision ;j++)
      errorString=errorString+'0';
Fraction error = new Fraction(errorString+'1');
argv=jc.eval(argv);


if(argv.startsWith("-"))
throw new Exception("sqrt(x) is not defined for x<0");

    if( (new Fraction(argv)).compareTo(error)<=0)
          return "0"; 

int shift=0; 
if(argv.indexOf('/')!=-1)
  argv=(new Fraction(argv)).toBigDecimal(jc.MC.getPrecision()).toPlainString();
String x=jc.eval(argv);

if(argv.startsWith("0.0")){
    x=x.substring(2);
   for(int i=2;i<argv.length();i++)
       if(argv.charAt(i) == '0'){
          x=x.substring(1);
          shift++;
        }
      else
        break;
   if(shift%2 == 0){
      x="0."+x;
    }
  else{
     String y="";
      for(int i = 0;i<x.length();i++){
         if(i==1)
         y=y+'.';
          y=y+x.charAt(i);
       } 
      x=y;
      shift=shift+1;
    }
   
shift=shift/2;
  x=(new CalCpowl()).eval(x+",1/2"); 
  if(x.startsWith("0.")){
    x=x.substring(2);
    for(int i=0;i<shift;i++)
      x='0'+x;
     x="0."+x;
  }
 else{
     if(x.indexOf('.')!=-1){
         if(!x.endsWith(".")) {
           String wh=x.substring(0,x.indexOf('.'));
           String fr=x.substring(x.indexOf('.')+1);
                 x=wh+fr;
            for(int i =0; i<shift-wh.length();i++)
                x='0'+x; 
            x="0."+x;
          }
         else
           throw new Exception("Wrong number format!");
       } 
  }
}
else
  x=(new CalCpowl()).eval(x+",1/2"); 
  
 return x; 
}
String Report="";
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}

}
