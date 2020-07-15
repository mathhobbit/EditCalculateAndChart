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
public class CalCRec implements CalcFunction{
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
        return "Rec(f1(n,x),f2(n,x),..fm(n,x),{x1=a1,x2=a2,..xm=am;N}) returns {x1(0)..xm(0);x1(2),..xm(2);..x1(N),..xm(N)}"+System.lineSeparator()+
                "where xj(n+1) =  fj(n,x(n)), xj(0) = aj (j=1,..m) and n=0,..N   ";
    }
 public final String ExceptionMessage="Format Error";
    @Override
    public String eval(String argv) throws Exception {
            String ret="";
//	    System.out.println(argv);
            if(argv.indexOf('=')==-1)
           throw new Exception(ExceptionMessage);
         int splitIndex = argv.substring(0,argv.indexOf('{')).lastIndexOf(',');
         if(splitIndex == -1)
             throw new Exception(ExceptionMessage);
         
        String functions=argv.substring(0,splitIndex);
        String bf = argv.substring(argv.indexOf('{')+1,argv.indexOf('}'));
        int secondSplitIndex = bf.lastIndexOf(';');
        int numberOFsteps = Integer. parseInt(bf.substring(secondSplitIndex+1).trim());
        String initialConditions = bf.substring(0,secondSplitIndex);
	ArrayList<String>  Vars = new ArrayList<String>();
	ArrayList<String>  Values = new ArrayList<String>();
	ArrayList<String>  Functions = new ArrayList<String>();
    
	for(String str:initialConditions.split(",")){
		str=str.trim();
		if(!str.contains("="))
                    throw new Exception(ExceptionMessage);

                Vars.add(str.substring(0,str.indexOf('=')));
				str=str.substring(str.indexOf('=')+1);
                Values.add(str);
                ret=(ret.compareTo("")==0)?ret+jc.eval(str):ret+","+jc.eval(str);
	}
	for(String str:functions.split(",")){
		Functions.add(str.trim());
	}
	if(Functions.size()!=Vars.size())
                    throw new Exception(ExceptionMessage);
	Iterator<String> it_Vars = null, it_Values=null;
	String functions_with_new_values="", preret="";
	for(int i = 0; i < numberOFsteps; i++){
                it_Vars = Vars.iterator();
                it_Values = Values.iterator();
               functions_with_new_values=functions;
		while(it_Vars.hasNext() && it_Values.hasNext())
                    functions_with_new_values=SmartReplace.get(functions_with_new_values,it_Vars.next(),it_Values.next());

		  Values.clear(); 	
		  preret="";
		  for(String str:functions_with_new_values.split(",")){
			     str = jc.eval(str);
			     Values.add(str);
                             preret=(preret.compareTo("")==0)?preret+str:preret+","+str;
		  }
		  ret=ret+";"+preret;
	}
/*
     System.out.println( "Initial Condotions: "+initialConditions+System.lineSeparator()+
	     "numberOFsteps ="+numberOFsteps+System.lineSeparator()+
	     "functions ="+functions+System.lineSeparator());
*/
	Values.clear();
	Vars.clear();
	Functions.clear();
	Values=null;
	Vars=null;
	Functions=null;
     return "{"+ret+"}";
    }
}
