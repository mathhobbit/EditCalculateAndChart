/* 
 * Copyright (C) 2022 Evan Nikitin, 87.7fmradiopushka@gmail.com
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
 *=====================================================================
 * The development of this code is done as Honors Enrichment Contruct  
 * in the framework of Barrett, The Honors College
 * (under the guidance of Dr. S. Nikitin, nikitin@asu.edu). 
 *
 * Euler: yn+1=yn+hf(tn,yn)
 * Improved Euler: yn+1=yn+((f(xn,yn)+f(xn+1,pn+1))*h)/2
 *                 pn+1=yn+hf(tn,yn)
 * Runge Kutta:
 * Yn+1=Yn+(1/6)(K1+2K2+2K3+K4)
 * K1=h*f(t,Yn)
 * K2=h*f(t+(h/2),Yn+(1/2)K1)
 * K3=h*f(t+(h/2),Yn+(1/2)K2)
 * K4=h*f(t+h,Yn+K3)
 * 
 * returns the approximation of the solution for the initital value problem:
dx1/dt = f1(t,x1,...,xm), x1(t0)=x10,
dx2/dt = f2(t,x1,...,xm), x2(t0)=x20,
 .                        .
 .                        .
 .                        .
dxm/dt = fm(t,x1,...,xm), xm(t0)=xm0
 dffEq(f1(t,x1,...,xm),...,fm(t,x1,...,xm),t={method,step,Range_left..Range_right},{x1=x10,...,xm=xm0})
where "method" can take values: "Euler", "ImprovedEuler", "Runge-Kutta" 
If "method" is not present (e.g. dffEq(x+y,2*x-y,t={0.1,0..1},{x=1,y=2})) then "Runge-Kutta" rule is used by default.
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
               "dx1/dt = f1(t,x1,...,xm), x1(Range_left)=x10"+ System.lineSeparator()+
               "dx2/dt = f2(t,x1,...,xm), x2(Range_left)=x20"+ System.lineSeparator()+
               " ."+ System.lineSeparator()+
               " ."+ System.lineSeparator()+
               " ."+ System.lineSeparator()+
               "dxm/dt = fm(t,x1,...,xm), xm(Range_left)=xm0"+ System.lineSeparator()+
                " dffEq(f1(t,x1,...,xm),...,fm(t,x1,...,xm),t={method,step,Range_left..Range_right},{x1=x10,...,xm=xm0})"+System.lineSeparator()+
                "where \"method\" can take values: \"Euler\", \"ImprovedEuler\", \"Runge-Kutta\" "+System.lineSeparator()+
                "If \"method\" is not present (e.g. dffEq(x+y,2*x-y,t={0.1,0..1},{x=1,y=2})) then \"Runge-Kutta\" rule is used by default.";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public String ReplaceAll(String input,String[] vars,int st,String repwith){
	//repwith has x which gets replaced by vars
	    String out=input;
	    for(int i=st;i<vars.length;i++){
		      String var=vars[i];
		      var=var.substring(0,var.indexOf("="));
		      String with=repwith.replace("x#",var);
		      out=SmartReplace.get(out,var,"("+with+")");
		      
    	}	
    	
    	return out;
	}
    @Override
    public String eval(String argv) throws Exception {
        String ret="";
    //System.out.println(argv);
            if(!argv.contains("="))
           throw new Exception(ExceptionMessage);
         int splitIndex = argv.substring(0,argv.indexOf('{')).lastIndexOf(',');
         if(splitIndex == -1)
             throw new Exception(ExceptionMessage);
        String righHandSideDiffEq=argv.substring(0,splitIndex); 
        //System.out.println("diff. eq. ="+righHandSideDiffEq);
        String bf = argv.substring(splitIndex+1);
        
        String[] raw = bf.split("\\.\\.");
  
        if(raw.length != 2)
             throw new Exception(ExceptionMessage);

        if(!raw[0].contains("="))
             throw new Exception(ExceptionMessage);
       //System.out.println(raw[0]); 
       String time = raw[0].substring(0,raw[0].indexOf('=')).trim();

        //System.out.println("time variable is "+time);

        if(!raw[0].contains("{"))
             throw new Exception(ExceptionMessage);

        String[] chunks = raw[0].substring(raw[0].indexOf('{')+1).split(",");
 
        String method="Runge-Kutta", step="0.1", timeInitialValue="0", timeTerminalValue="1";

        if(chunks.length < 2)      
             throw new Exception(ExceptionMessage);

        if(chunks.length > 2){
            method = chunks[0].replace("\"","");
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
/* 
        System.out.println("method = "+method);
        System.out.println("step = "+step);
        System.out.println("timeInitialValue = "+timeInitialValue);
        System.out.println("timeTerminalValue = "+timeTerminalValue);
*/
        //Calculataing number of steps
        step=jc.eval(step);
        timeInitialValue = jc.eval(timeInitialValue);
        timeTerminalValue=jc.eval(timeTerminalValue);
   
        BigInteger NumberOfSteps = BigInteger.ZERO;
        BigDecimal h = (step.contains("/"))?(new Fraction(step)).toBigDecimal(jc.MC):new BigDecimal(step,jc.MC);           
        BigDecimal Start = (timeInitialValue.contains("/"))?(new Fraction(timeInitialValue)).toBigDecimal(jc.MC):new BigDecimal(timeInitialValue,jc.MC);               BigDecimal End = (timeTerminalValue.contains("/"))?(new Fraction(timeTerminalValue)).toBigDecimal(jc.MC):new BigDecimal(timeTerminalValue,jc.MC);           
         BigDecimal var = Start;

           while(var.compareTo(End)<0){
               var=var.add(h,jc.MC);
               NumberOfSteps=NumberOfSteps.add(BigInteger.ONE);
           } 
       //System.out.println("NumberOfSteps = "+NumberOfSteps.toString());
       String InitialValues=raw[1].substring(raw[1].indexOf("{")+1);

          if(!InitialValues.contains("}"))     
             throw new Exception(ExceptionMessage+" "+InitialValues);

          InitialValues = InitialValues.substring(0,InitialValues.indexOf("}"));

       //System.out.println(InitialValues); 
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
             
             /*System.out.println(time+"+"+stepSize+","+Euler+",{"
                                                 +time+"="+Start.toPlainString()+","+InitialValues+";"+
                                                         NumberOfSteps.toString()+"}");
             */
                 ret = (new CalCRec()).eval(time+"+"+stepSize+","+Euler+",{"
                                                 +time+"="+Start.toPlainString()+","+InitialValues+";"+
                                                         NumberOfSteps.toString()+"}"); 
          }

//Improved Euler method
 
        if(method.compareToIgnoreCase("ImprovedEuler") == 0){
			 String[] diffEq = righHandSideDiffEq.split(",");


            String[] vars = InitialValues.split(",");
         if(diffEq.length != vars.length )
             throw new Exception(ExceptionMessage);
          
         String[] euler = new String[vars.length];
          
           String Euler = "",ImprovedEuler="", stepSize = h.toPlainString();
          String it="";
          for(int i = 0; i < diffEq.length ;i++)
             euler[i]=vars[i].substring(0,vars[i].indexOf("="))+"+"+stepSize+"*"+"("+diffEq[i]+")";
            


          for(int i = 0; i < diffEq.length ;i++){
             it = diffEq[i]; 
             it =  SmartReplace.get(it,time,time+"+"+stepSize);    
             //System.out.println(it);
               for(int j = 0 ; j < vars.length; j++){
                         it = SmartReplace.get(it,vars[j].substring(0,vars[j].indexOf("=")),euler[j]);
                          //System.out.println(it);
                  }
              //System.out.println(vars[i]); 
             String impE=vars[i].substring(0,vars[i].indexOf("="))+"+((("+diffEq[i]+")+"+"("+it+"))/2)*"+stepSize;
                  
             ImprovedEuler=(ImprovedEuler.compareTo("")==0)?ImprovedEuler+impE:ImprovedEuler+","+impE;
           }
             
             /*System.out.println(time+"+"+stepSize+","+ImprovedEuler+",{"
                                                 +time+"="+Start.toPlainString()+","+InitialValues+";"+
                                                         NumberOfSteps.toString()+"}");
              */
                 ret = (new CalCRec()).eval(time+"+"+stepSize+","+ImprovedEuler+",{"
                                                 +time+"="+Start.toPlainString()+","+InitialValues.replace(";",",")+";"+
                                                         NumberOfSteps.toString()+"}"); 
          }

       
 
               
             
//Runge-Kutta method

        if(method.compareToIgnoreCase("Runge-Kutta") == 0){
          String it="";
         String[] diffEq = righHandSideDiffEq.split(",");
            String[] vars = InitialValues.split(",");
         if(diffEq.length != vars.length )
             throw new Exception(ExceptionMessage);
          
           String Rung = "", stepSize = h.toPlainString();
          String[] k1=new String[vars.length];
          for(int i=0;i<vars.length;i++){
			  
			  k1[i]=stepSize+"*("+diffEq[i]+")";
		  }
		  String[] varsnv=new String[vars.length];
		  for(int i=0;i<vars.length;i++){
			varsnv[i]=vars[i].substring(0,vars[i].indexOf("="));  
		  }
		  String[] k2=new String[vars.length];
		  String nv, val;
		  for(int i=0;i<diffEq.length;i++){
			  
			  val=SmartReplace.get(diffEq[i],time,"("+time+"+("+stepSize+")*0.5)");
			  for(int j=0;j<k1.length;j++){
			    nv=varsnv[j];
				val=SmartReplace.get(val,nv,"("+nv+"+"+"("+k1[j]+")*0.5)");
			  }
			  k2[i]=stepSize+"*("+val+")";
		  }
		  String[] k3=new String[vars.length];
		  for(int i=0;i<diffEq.length;i++){
			  
			  val=SmartReplace.get(diffEq[i],time,"("+time+"+("+stepSize+")*0.5)");
			  for(int j=0;j<k2.length;j++){
				nv=varsnv[j];
				val=SmartReplace.get(val,nv,"("+nv+"+"+"("+k2[j]+")*0.5)");
			  }
			  k3[i]=stepSize+"*("+val+")";
		  }
		  String[] k4=new String[vars.length];
		  for(int i=0;i<diffEq.length;i++){
			  
			  val=SmartReplace.get(diffEq[i],time,"("+time+"+("+stepSize+"))");
			  for(int j=0;j<k3.length;j++){
				nv=varsnv[j];
			    val=SmartReplace.get(val,nv,"("+nv+"+"+"("+k3[j]+"))");
		      }
			  k4[i]=stepSize+"*("+val+")";
		  }
		  String methodr;
		  String collect="";
          for(int i = 0; i < diffEq.length ;i++){
			 nv=varsnv[i];
			 collect="";
			
             collect=k1[i]+"+2*"+k2[i]+"+2*"+k3[i]+"+"+k4[i];
             
             methodr=nv+"+(1/6)*("+collect.substring(1)+")";
             Rung=(Rung.compareTo("")==0)?Rung+methodr:Rung+","+methodr;
           }
             
             /*System.out.println(time+"+"+stepSize+","+Rung+",{"
                                                 +time+"="+Start.toPlainString()+","+InitialValues+";"+
                                                         NumberOfSteps.toString()+"}");
              */
                 ret = (new CalCRec()).eval(time+"+"+stepSize+","+Rung+",{"
                                                 +time+"="+Start.toPlainString()+","+InitialValues.replace(";",",")+";"+
                                                         NumberOfSteps.toString()+"}"); 
/*
 * Yn+1=Yn+(H/6)(K1+2K2+2K3+K4)
 * K1=f(Xn,Yn)
 * K2=f(Xn+(H/2),Yn+(H/2)K1)
 * K3=f(Xn+(H/2),Yn+(H/2)K2)
 * K4=f(Xn+h,Yn+HK3)
 */
              } 


             return ret; 

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
