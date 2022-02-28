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
import org.ioblako.math.linearalgebra.Fraction;

/**
 *
 * @author evan_nikitin
 */
public class CalCdffEq implements CalcFunction{
String Report="";
public final String ExceptionMessage="Format Error";
    @Override
    public String getReport() {
        return Report;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setReport(String str) {
        Report=str;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getHelp() {
        return "returns the approximation of the solution for the initital value problem"+ System.lineSeparator()+
               "dx1/dt = f1(t,x1,...,xm),  x1(0)=x10"+ System.lineSeparator()+
               "dx2/dt = f2(t,x1,...,xm),  x2(0)=x20"+ System.lineSeparator()+
               " ."+ System.lineSeparator()+
               " ."+ System.lineSeparator()+
               " ."+ System.lineSeparator()+
               "dxm/dt = fm(t,x1,...,xm),  xm(0)=xm0"+ System.lineSeparator()+
                " dffEq(f1(t,x1,...,xm),...,fm(t,x1,...,xm),t={method,step,Range_left..Range_right},{x1=x10,...,xm=xm0})"+System.lineSeparator()+
                "where \"method\" can take values: \"Euler\", \"ImprovedEuler\", \"Runge-Kutta\" "+System.lineSeparator()+
                "If \"method\" is not present (e.g. dffEq(x+y,2*x-y,t={0.1,0..1},{x=1,y=2})) then \"Runge-Kutta\" rule is used by default.";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String eval(String argv) throws Exception {
        String ret="";
    System.out.println(argv);
            if(!argv.contains("="))
           throw new Exception(ExceptionMessage);
         int splitIndex = argv.substring(0,argv.indexOf('{')).lastIndexOf(',');
         if(splitIndex == -1)
             throw new Exception(ExceptionMessage);
        String righHandSideDiffEq=argv.substring(0,splitIndex); 
        System.out.println("diff. eq. ="+righHandSideDiffEq);
        String bf = argv.substring(splitIndex+1);
        
        String[] raw = bf.split("\\.\\.");
  
        if(raw.length != 2)
             throw new Exception(ExceptionMessage);

        if(!raw[0].contains("="))
             throw new Exception(ExceptionMessage);
       System.out.println(raw[0]); 
       String time = raw[0].substring(0,raw[0].indexOf('=')).trim();

        System.out.println("time variable is "+time);

        if(!raw[0].contains("{"))
             throw new Exception(ExceptionMessage);

        String[] chunks = raw[0].substring(raw[0].indexOf('{')+1).split(",");
 
        String method="Euler", step="0.1", timeInitialValue="0", timeTerminalValue="1";

        if(chunks.length < 2)      
             throw new Exception(ExceptionMessage);

        if(chunks.length > 2){
            method = chunks[0];
            step = chunks[1];
            timeInitialValue = chunks[2];
           }
        if(chunks.length == 2){
            step = chunks[0];
            timeInitialValue = chunks[1];
           }

        if(!raw[1].contains("}"))
             throw new Exception(ExceptionMessage);

         timeTerminalValue=raw[1].substring(0,raw[1].indexOf("}")); 
 
        System.out.println("method = "+method);
        System.out.println("step = "+step);
        System.out.println("timeInitialValue = "+timeInitialValue);
        System.out.println("timeTerminalValue = "+timeTerminalValue);

        //Calculataing number of steps
   
        BigInteger NumberOfSteps = BigInteger.ZERO;
        BigDecimal h = (step.contains("/"))?(new Fraction(step)).toBigDecimal(jc.MC):new BigDecimal(step,jc.MC);           
        BigDecimal Start = (timeInitialValue.contains("/"))?(new Fraction(timeInitialValue)).toBigDecimal(jc.MC):new BigDecimal(timeInitialValue,jc.MC);               BigDecimal End = (timeTerminalValue.contains("/"))?(new Fraction(timeTerminalValue)).toBigDecimal(jc.MC):new BigDecimal(timeTerminalValue,jc.MC);           
         BigDecimal var = Start;

           while(var.compareTo(End)<0){
               var=var.add(h,jc.MC);
               NumberOfSteps=NumberOfSteps.add(BigInteger.ONE);
           } 
       System.out.println("NumberOfSteps = "+NumberOfSteps.toString());
       String InitialValues=raw[1].substring(raw[1].indexOf("{")+1);

          if(!InitialValues.contains("}"))     
             throw new Exception(ExceptionMessage+" "+InitialValues);

          InitialValues = InitialValues.substring(0,InitialValues.indexOf("}"));

       System.out.println(InitialValues); 
//Input parsing ends
//Building input for Rec

//Euler method
          if(method.compareToIgnoreCase("Euler") == 0){
            String[] diffEq = righHandSideDiffEq.split(",");
            String[] vars = InitialValues.split(",");
         if(diffEq.length != vars.length )
             throw new Exception(ExceptionMessage);
          
           String Euler = "", stepSize = h.toPlainString();
 
          for(int i = 0; i < diffEq.length ;i++){
           
             Euler=(Euler.compareTo("")==0)?Euler+
                             vars[i].substring(0,vars[i].indexOf("="))+"+"+stepSize+"*"+"("+diffEq[i]+")":Euler+","+
                              vars[i].substring(0,vars[i].indexOf("="))+"+"+stepSize+"*"+"("+diffEq[i]+")";

           }
             
             System.out.println(time+"+"+stepSize+","+Euler+",{"
                                                 +time+"="+Start.toPlainString()+","+InitialValues+";"+
                                                         NumberOfSteps.toString()+"}");
                 ret = (new CalCRec()).eval(time+"+"+stepSize+","+Euler+",{"
                                                 +time+"="+Start.toPlainString()+","+InitialValues+";"+
                                                         NumberOfSteps.toString()+"}"); 
          }

//Improved Euler method

        if(method.compareToIgnoreCase("ImprovedEuler") == 0){

        throw new UnsupportedOperationException("Improved Euler method is not yet implemented."); 
 
              } 
             
//Runge-Kutta method

        if(method.compareToIgnoreCase("Runge-Kutta") == 0){

        throw new UnsupportedOperationException("Runge-Kutta  method is not yet implemented."); 

              } 


             return ret; 

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
