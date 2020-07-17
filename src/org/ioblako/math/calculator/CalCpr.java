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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import org.ioblako.math.linearalgebra.Fraction;

/**
 *
 * @author sergey_nikitin
 */
public class CalCpr implements CalcFunction{
String Report="";
    @Override
    public String getReport() {
        return Report;
    }

    @Override
    public void setReport(String str) {
         Report=str;
    }

    @Override
    public String getHelp() {
        return "pr({a1,a2,a3,...;b1,b2,b3,...},i1,i2,..im) returns the sequence of projections for \";\" - separated vectors into the space with coordinates i1,i2,..im" ;
    }
 public final String ExceptionMessage="Format Error";
    @Override
    public String eval(String argv) throws Exception {
            String ret="";
        if(!argv.contains("{")) 
		throw new Exception(ExceptionMessage);
        if(!argv.substring(argv.indexOf("{")+1).contains("}"))
		throw new Exception(ExceptionMessage);
        if(!argv.substring(argv.lastIndexOf("}")+1).contains(","))
		throw new Exception(ExceptionMessage);

        String data=argv.substring(argv.indexOf("{")+1,argv.indexOf("}"));
String nbms = argv.substring(argv.lastIndexOf("}")+1).trim();
if(nbms.startsWith(","))
   nbms=nbms.substring(1);
String[] numbers=null;
    if(nbms.contains(","))
        numbers = nbms.split(",");
    else{
           numbers= new String[1];
           numbers[0] = nbms;  
        }
       
      int[] nm = new int[numbers.length];
for(int j =0; j<nm.length; j++){
      nm[j] = Integer. parseInt(numbers[j].trim());
}
 int  i=0; 
String part="";
	for(String str:data.split(";")){
               i=1;
               part="";
              for(String brr:str.split(",")){
                    for(int j =0; j<nm.length; j++)
                           if(i == nm[j])
		           part=(part.compareTo("")!=0)?part+","+brr:part+brr;
                  i++; 
               }
		ret=(ret.compareTo("")!=0)?ret+";"+part:ret+part;
	}
     return "{"+ret+"}";
    }
}
