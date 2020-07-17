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
public class CalCtail implements CalcFunction{
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
        return "tail({a1;...aN;aN+1...aM+m},m) returns {aN+1;...;aN+m }";
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
        int numberOFitems = Integer. parseInt(argv.substring(argv.lastIndexOf(",")+1).trim());

  String[] dataArray = data.split(";");

  if(dataArray.length <= numberOFitems)
	  ret = data;
  else{
      for(int i= dataArray.length - numberOFitems;i<dataArray.length;i++)
		ret=(ret.compareTo("")!=0)?ret+";"+dataArray[i]:dataArray[i];
  }
     return "{"+ret+"}";
    }
}
