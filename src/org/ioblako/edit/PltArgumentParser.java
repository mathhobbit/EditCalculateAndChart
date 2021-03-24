/*
 * Copyright (C) 2019 Sergey Nikitin
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
package org.ioblako.edit;

import java.util.ArrayList;
import java.util.Iterator;
import java.math.BigDecimal;
import org.ioblako.math.linearalgebra.Fraction;
import org.ioblako.math.calculator.jc;

public class PltArgumentParser{

private String Exception_String=" Parsing Error:";
private ArrayList<String> functions = new ArrayList<String>();
private ArrayList<String> Vars = new ArrayList<String>();
private ArrayList<BigDecimal> Steps = new ArrayList<BigDecimal>();
private ArrayList<BigDecimal> Left = new ArrayList<BigDecimal>();
private ArrayList<BigDecimal> Right = new ArrayList<BigDecimal>();

private String input="";

public PltArgumentParser(String input) throws Exception {
input=input.trim();

 if(input.startsWith("{")){
 for(String bf:input.substring(1,input.indexOf("}")).split(",")){
     bf = (bf.contains("="))?bf.substring(bf.indexOf("=")+1):bf;
         functions.add(bf);
  }
   input=input.replace(" ","");
   input=input.substring(input.indexOf("},")+2);
   process(input);
 }
 else{

   check(input,",");

   functions.add(input.substring(0,input.indexOf(",")));
   input=input.substring(input.indexOf(",")+1);
   process(input);
 }
}
public void process(String input) throws Exception {
   input= input.replace(" ","");
   String anend=null;
     for(String bf:input.split("},")){

       check(bf,"={");

              Vars.add(bf.substring(0,bf.indexOf("={")));
         bf = bf.substring(bf.indexOf("={")+2);

           check(bf,",");
              anend=bf.substring(0,bf.indexOf(",")); 
            
              if(anend.indexOf('/')!=-1)
                Steps.add((new Fraction(anend)).toBigDecimal());
              else     
                Steps.add(new BigDecimal(anend));

              bf = bf.substring(bf.indexOf(",")+1);

           check(bf,"..");
              anend=jc.eval(bf.substring(0,bf.indexOf("..")));   
              if(anend.indexOf('/')!=-1)
                Left.add((new Fraction(anend)).toBigDecimal());
              else     
                Left.add(new BigDecimal(anend));
              bf=bf.substring(bf.indexOf("..")+2);
             if(bf.endsWith("})")){
               anend=jc.eval(bf.substring(0,bf.length() - 2));
               if(anend.indexOf('/')!=-1)
                 Right.add((new Fraction(anend)).toBigDecimal());
               else     
                 Right.add(new BigDecimal(anend));
                }
             else{
               anend=jc.eval(bf);
               if(anend.indexOf('/')!=-1)
                  Right.add((new Fraction(anend)).toBigDecimal());
               else     
                  Right.add(new BigDecimal(anend));
             }
   }//for

}

private void check(String input,String delimiter)  throws Exception{
        if(!input.contains(delimiter))
              throw(new Exception(Exception_String+": \""+input+"\" should contain \""+delimiter+"\""));
}

public ArrayList<String> getFunctions(){
    return functions;
}

public ArrayList<String> getVariables(){
    return Vars;
}

public ArrayList<BigDecimal> getSteps(){
    return Steps;
}

public ArrayList<BigDecimal> getLeft(){
    return Left;
}

public ArrayList<BigDecimal> getRight(){
    return Right;
}
}//class
