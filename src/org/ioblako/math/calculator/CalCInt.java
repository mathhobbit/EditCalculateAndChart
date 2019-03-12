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
import java.util.HashMap;
import java.util.Iterator;
import org.ioblako.math.linearalgebra.Fraction;

/**
 *
 * @author sergey_nikitin
 */
public class CalCInt implements CalcFunction{
String Report="";
HashMap<String,String> IntegrationMethods = new HashMap<>(); 
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
        return "returns value of an iterated integral of a given function:"+ System.lineSeparator()+
                " Int(f(x1,...xm),x1={method,step_1,Range_left1..Range_right1},...,xm={method,step_m,Range_left_m..Range_right_m})"+System.lineSeparator()+
                "where \"method\" can take values: \"simpson\", \"trapezoid\", \"leftsum\", \"rightsum\" "+System.lineSeparator()+
                "If \"methods\" is not present (e.g. Int(x^2,x={0.1,0..1})) then \"midpoint\" rule is used by default.";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String eval(String argv) throws Exception {
        String ret="";
            if(argv.indexOf('=')==-1)
           throw new Exception(ExceptionMessage);
         int splitIndex = argv.substring(0,argv.indexOf('{')).lastIndexOf(',');
         if(splitIndex == -1)
             throw new Exception(ExceptionMessage);
         
        String[] functions=argv.substring(0,splitIndex).split(",");
        String bf = argv.substring(splitIndex+1);
        ArrayList<String> Vars = new ArrayList<>();
        ArrayList<String> rangeLeft = new ArrayList<>();
        ArrayList<String> rangeRight = new ArrayList<>();
        ArrayList<String> steps = new ArrayList<>();
        
        String toBeVar, myVar;
        String[] raw = bf.split("\\.\\.");
        for(String chunk:raw){
            
            if(chunk.indexOf('=')!=-1){
                if(chunk.indexOf('}')!=-1){
                    myVar=chunk.substring(chunk.indexOf(',')+1,chunk.indexOf('=')).trim();
                    Vars.add(myVar);
                }
                else{
                    myVar=chunk.substring(0,chunk.indexOf('=')).trim();
                    Vars.add(myVar);
                }
                if(chunk.indexOf('{')==-1)
                    throw new Exception(ExceptionMessage);
                toBeVar=chunk.substring(chunk.indexOf('{')+1,chunk.lastIndexOf(','));
                if(toBeVar.indexOf(',')!=-1){
                    IntegrationMethods.put(myVar,toBeVar.substring(0,toBeVar.indexOf(',')));
                    toBeVar=toBeVar.substring(toBeVar.indexOf(',')+1);
                }
                    
                steps.add(jc.eval(toBeVar));
                //rangeLeft.add(jc.eval(chunk.substring(chunk.lastIndexOf(',')+1)));
                rangeLeft.add(chunk.substring(chunk.lastIndexOf(',')+1));
                if(chunk.indexOf('}')!=-1)
                    rangeRight.add(chunk.substring(0,chunk.indexOf('}')));
                    //rangeRight.add(jc.eval(chunk.substring(0,chunk.indexOf('}'))));
            }
            else{
                if(chunk.indexOf('}')==-1)
                    throw new Exception(ExceptionMessage);
                
                //rangeRight.add(jc.eval(chunk.substring(0,chunk.indexOf('}'))));
                rangeRight.add(chunk.substring(0,chunk.indexOf('}')));
            }
        }
        if(Vars.size()!=rangeLeft.size()||Vars.size()!=rangeRight.size())
            throw new Exception(ExceptionMessage);
        int Size = Vars.size();
         for(String fn:functions)
              ret=ret+IInt(fn,Vars,steps,rangeLeft,rangeRight)+",";
         
         
         ret = ret.endsWith(",")?ret.substring(0,ret.length()-1):ret;
        
        return ret;
                
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        final BigDecimal four = new BigDecimal("4");
        final BigDecimal three = new BigDecimal("3");
        final BigDecimal two = new BigDecimal("2");
    private String IInt(String function,ArrayList<String> Vars,ArrayList<String> steps, 
                                        ArrayList<String> rangeLeft,ArrayList<String> rangeRight) throws Exception{
        int S = steps.size();
        if(S<1)
            throw new Exception("No variables in integral!?");
        String tp = jc.eval(steps.get(S-1));
        
        BigDecimal step =(tp.indexOf('/')==-1)?new BigDecimal(tp,jc.MC):(new Fraction(tp)).toBigDecimal(jc.MC);
        tp = jc.eval(rangeRight.get(S-1));
        
         BigDecimal right = (tp.indexOf('/')==-1)?new BigDecimal(tp,jc.MC):(new Fraction(tp)).toBigDecimal(jc.MC);
        String start = jc.eval(rangeLeft.get(S-1));
        
        tp = start;
        BigDecimal left = (tp.indexOf('/')==-1)?new BigDecimal(tp,jc.MC):(new Fraction(tp)).toBigDecimal(jc.MC);
        String var = Vars.get(S-1);
        
        String integrationRule = "MIDPOINT";
        if(IntegrationMethods.containsKey(var))
            integrationRule=IntegrationMethods.get(var).toUpperCase();
       
        //BigDecimal oneHalf = new BigDecimal("0.5");
        BigInteger N = right.subtract(left).divide(step,jc.MC).toBigInteger();
        N=N.subtract(BigInteger.ONE).subtract(BigInteger.ONE);
        if(Vars.size()==1){
            switch(integrationRule){
              
                case "SIMPSON":
                    BigDecimal integralV = BDCM(jc.eval(SmartReplace.get(function,var,start)));
                    //BigDecimal integralV = BDCM(jc.eval(function.replace(var,start)));
                    BigInteger it = BigInteger.ZERO;
                    step=step.divide(two,jc.MC);
                  while(it.compareTo(N)<=0){
                    it=it.add(BigInteger.ONE);
                    left = left.add(step);
                   start=left.toPlainString();
                   integralV=integralV.add(four.multiply(BDCM(jc.eval(SmartReplace.get(function,var,start)))),jc.MC);
                   //integralV=integralV.add(four.multiply(BDCM(jc.eval(function.replace(var,start)))),jc.MC);
                   left = left.add(step);
                   start=left.toPlainString();
                   integralV=integralV.add(two.multiply(BDCM(jc.eval(SmartReplace.get(function,var,start)))),jc.MC);
                   //integralV=integralV.add(two.multiply(BDCM(jc.eval(function.replace(var,start)))),jc.MC);
                   //left=right;
                 }
                  left = left.add(step);
                   start=left.toPlainString();
                   integralV=integralV.add(four.multiply(BDCM(jc.eval(SmartReplace.get(function,var,start)))),jc.MC);
                   //integralV=integralV.add(four.multiply(BDCM(jc.eval(function.replace(var,start)))),jc.MC);
                   left = left.add(step);
                   start=left.toPlainString();
                   integralV=integralV.add(BDCM(jc.eval(SmartReplace.get(function,var,start))),jc.MC);
                   //integralV=integralV.add(BDCM(jc.eval(function.replace(var,start))),jc.MC);
                   integralV=integralV.divide(three,jc.MC).multiply(step,jc.MC);  
                   return integralV.stripTrailingZeros().toPlainString();
                
                case "TRAPEZOID":
                    integralV = BDCM(jc.eval(SmartReplace.get(function,var,start)));
                    //integralV = BDCM(jc.eval(function.replace(var,start)));
                    it = BigInteger.ZERO;
                while(it.compareTo(N)<=0){
                    it=it.add(BigInteger.ONE);
                    left=left.add(step);
                   start=left.toPlainString();
                   integralV=integralV.add(two.multiply((BDCM(jc.eval(SmartReplace.get(function,var,start))))),jc.MC);
                   //integralV=integralV.add(two.multiply((BDCM(jc.eval(function.replace(var,start))))),jc.MC);
                }
                 left=left.add(step);
                   start=left.toPlainString();
                   integralV=integralV.add(BDCM(jc.eval(SmartReplace.get(function,var,start))),jc.MC);
                   //integralV=integralV.add(BDCM(jc.eval(function.replace(var,start))),jc.MC);
                   integralV=(integralV.divide(two,jc.MC)).multiply(step,jc.MC);
                   return integralV.stripTrailingZeros().toPlainString();
                case "LEFTSUM":
                    integralV = BDCM(jc.eval(SmartReplace.get(function,var,start)));
                    //integralV = BDCM(jc.eval(function.replace(var,start)));
                    it = BigInteger.ZERO;
                    while(it.compareTo(N)<=0){
                        it=it.add(BigInteger.ONE);
                        left=left.add(step);
                        start=left.toPlainString();
                        integralV=integralV.add(BDCM(jc.eval(SmartReplace.get(function,var,start))),jc.MC);
                        //integralV=integralV.add(BDCM(jc.eval(function.replace(var,start))),jc.MC);
                    }
                    integralV=integralV.multiply(step,jc.MC);
                    return integralV.stripTrailingZeros().toPlainString();
                 case "RIGHTSUM":
                     left=left.add(step);
                     start=left.toPlainString();
                     integralV = BDCM(jc.eval(SmartReplace.get(function,var,start)));
                    //integralV = BDCM(jc.eval(function.replace(var,start)));
                    it = BigInteger.ZERO;
                    while(it.compareTo(N)<=0){
                        it=it.add(BigInteger.ONE);
                        left=left.add(step);
                        start=left.toPlainString();
                        integralV=integralV.add(BDCM(jc.eval(SmartReplace.get(function,var,start))),jc.MC);
                        //integralV=integralV.add(BDCM(jc.eval(function.replace(var,start))),jc.MC);
                    }
                    integralV=integralV.multiply(step,jc.MC);
                    return integralV.stripTrailingZeros().toPlainString();   
                
                default:
                    BigDecimal halfStep = step.divide(two,jc.MC);
                    start=left.add(halfStep).toPlainString();
                    integralV = BDCM(jc.eval(SmartReplace.get(function,var,start)));
                    //integralV = BDCM(jc.eval(function.replace(var,start)));
                    it = BigInteger.ZERO;
                    while(it.compareTo(N)<0){
                        it=it.add(BigInteger.ONE);
                        left=left.add(step);
                        start=left.add(halfStep).toPlainString();
                        integralV=integralV.add(BDCM(jc.eval(SmartReplace.get(function,var,start))),jc.MC);
                        //integralV=integralV.add(BDCM(jc.eval(function.replace(var,start))),jc.MC);
                    }
                    left=left.add(step);
                        start=left.add(halfStep).toPlainString();
                        integralV=integralV.add(BDCM(jc.eval(SmartReplace.get(function,var,start))),jc.MC);
                        //integralV=integralV.add(BDCM(jc.eval(function.replace(var,start))),jc.MC);
                        integralV=integralV.multiply(step,jc.MC);
                        return integralV.stripTrailingZeros().toPlainString();
            }
        }
        steps.remove(S-1);
        rangeRight.remove(S-1);
        rangeLeft.remove(S-1);
        Vars.remove(S-1);
        switch(integrationRule){
              
                case "SIMPSON":
                    //BigDecimal integralV = new BigDecimal(jc.eval(function.replace(var,start)));
                    BigDecimal integralV = BDCM(jc.eval(Replace(var,start,function,Vars,steps,rangeLeft,rangeRight)));
                    BigInteger it = BigInteger.ZERO;
                    step=step.divide(two,jc.MC);
                  while(it.compareTo(N)<=0){
                    it=it.add(BigInteger.ONE);
                    left = left.add(step);
                   start=left.toPlainString();
                   integralV=integralV.add(four.multiply(BDCM(jc.eval(Replace(var,start,function,Vars,steps,rangeLeft,rangeRight)))),jc.MC);
                   left = left.add(step);
                   start=left.toPlainString();
                   integralV=integralV.add(two.multiply(BDCM(jc.eval(Replace(var,start,function,Vars,steps,rangeLeft,rangeRight)))),jc.MC);
                   //left=right;
                 }
                  left = left.add(step);
                   start=left.toPlainString();
                   integralV=integralV.add(four.multiply(BDCM(jc.eval(Replace(var,start,function,Vars,steps,rangeLeft,rangeRight)))));
                   left = left.add(step);
                   start=left.toPlainString();
                   integralV=integralV.add(BDCM(jc.eval(Replace(var,start,function,Vars,steps,rangeLeft,rangeRight))),jc.MC);
                   integralV=integralV.divide(three,jc.MC).multiply(step,jc.MC);  
                   return integralV.stripTrailingZeros().toPlainString();
                
                case "TRAPEZOID":
                    integralV = BDCM(jc.eval(Replace(var,start,function,Vars,steps,rangeLeft,rangeRight)));
                    it = BigInteger.ZERO;
                while(it.compareTo(N)<=0){
                    it=it.add(BigInteger.ONE);
                    left=left.add(step);
                   start=left.toPlainString();
                   integralV=integralV.add(two.multiply(BDCM(jc.eval(Replace(var,start,function,Vars,steps,rangeLeft,rangeRight)))),jc.MC);
                }
                 left=left.add(step);
                   start=left.toPlainString();
                   integralV=integralV.add(BDCM(jc.eval(Replace(var,start,function,Vars,steps,rangeLeft,rangeRight))),jc.MC);
                   integralV=(integralV.divide(two,jc.MC)).multiply(step,jc.MC);
                   return integralV.stripTrailingZeros().toPlainString();
                case "LEFTSUM":
                    integralV = BDCM(jc.eval(Replace(var,start,function,Vars,steps,rangeLeft,rangeRight)));
                    it = BigInteger.ZERO;
                    while(it.compareTo(N)<=0){
                        it=it.add(BigInteger.ONE);
                        left=left.add(step);
                        start=left.toPlainString();
                        integralV=integralV.add(BDCM(jc.eval(Replace(var,start,function,Vars,steps,rangeLeft,rangeRight))),jc.MC);
                    }
                    integralV=integralV.multiply(step,jc.MC);
                    return integralV.stripTrailingZeros().toPlainString();
                 case "RIGHTSUM":
                     left=left.add(step);
                     start=left.toPlainString();
                    integralV = BDCM(jc.eval(Replace(var,start,function,Vars,steps,rangeLeft,rangeRight)));
                    it = BigInteger.ZERO;
                    while(it.compareTo(N)<=0){
                        it=it.add(BigInteger.ONE);
                        left=left.add(step);
                        start=left.toPlainString();
                        integralV=integralV.add(BDCM(jc.eval(Replace(var,start,function,Vars,steps,rangeLeft,rangeRight))),jc.MC);
                    }
                    integralV=integralV.multiply(step,jc.MC);
                    return integralV.stripTrailingZeros().toPlainString();   
                
                default:
                    BigDecimal halfStep = step.divide(two,jc.MC);
                    start=left.add(halfStep).toPlainString();
                    integralV = BDCM(jc.eval(Replace(var,start,function,Vars,steps,rangeLeft,rangeRight)));
                    it = BigInteger.ZERO;
                    while(it.compareTo(N)<0){
                        it=it.add(BigInteger.ONE);
                        left=left.add(step);
                        start=left.add(halfStep).toPlainString();
                        integralV=integralV.add(BDCM(jc.eval(Replace(var,start,function,Vars,steps,rangeLeft,rangeRight))),jc.MC);
                    }
                    left=left.add(step);
                        start=left.add(halfStep).toPlainString();
                        integralV=integralV.add(BDCM(jc.eval(Replace(var,start,function,Vars,steps,rangeLeft,rangeRight))),jc.MC);
                        integralV=integralV.multiply(step,jc.MC);
                        return integralV.stripTrailingZeros().toPlainString();
            }
    }
    
    private String Replace(String var, String value, String function,ArrayList<String> Vars,ArrayList<String> steps, 
                                        ArrayList<String> rangeLeft,ArrayList<String> rangeRight) throws Exception{
        ArrayList<String> nRangeLeft = new ArrayList<>();
        Iterator<String> it = rangeLeft.iterator();
        while(it.hasNext())
            nRangeLeft.add(SmartReplace.get(it.next(),var,value));
            //nRangeLeft.add(it.next().replace(var,value));
        
        ArrayList<String> nRangeRight = new ArrayList<>();
                    it = rangeRight.iterator();
        while(it.hasNext())
            nRangeRight.add(SmartReplace.get(it.next(),var,value));
            //nRangeRight.add(it.next().replace(var,value));
        
        ArrayList<String> nVars = new ArrayList<>();
           it = Vars.iterator();
           while(it.hasNext())
               nVars.add(it.next());
         ArrayList<String> nsteps = new ArrayList<>();
           it = steps.iterator();
           while(it.hasNext())
               nsteps.add(it.next());
             
           
        
        //return IInt(function.replace(var,value),nVars,nsteps,nRangeLeft,nRangeRight);
        return IInt(SmartReplace.get(function,var,value),nVars,nsteps,nRangeLeft,nRangeRight);
    }
    
    private BigDecimal BDCM(String str) throws Exception{
        return (str.indexOf('/')!=-1)?jc.getFraction(str).toBigDecimal(jc.MC):new BigDecimal(str,jc.MC);
    }
    
}
