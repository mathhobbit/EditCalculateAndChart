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
import org.ioblako.math.linearalgebra.Fraction.Fraction;

/**
 *
 * @author sergey_nikitin
 */
public class CalCSeq implements CalcFunction{
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
        return "Seq(f1(x),f2(x),..fm(x),x={step,start..end}) returns {f1(x1)..fm(x1);f1(x2),..fm(x2);..f1(xn),..fm(xn)}"+System.lineSeparator()+
                "where x{j+1} = xj + step and x1 = start, xn = end";
    }
 public final String ExceptionMessage="Format Error";
    @Override
    public String eval(String argv) throws Exception {
            String ret;
            if(argv.indexOf('=')==-1)
           throw new Exception(ExceptionMessage);
         int splitIndex = argv.substring(0,argv.indexOf('{')).lastIndexOf(',');
         if(splitIndex == -1)
             throw new Exception(ExceptionMessage);
         
        String functions=argv.substring(0,splitIndex);
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
                    throw new Exception(ExceptionMessage);
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
 
        String FunctionString = Seq(functions,Vars,steps,rangeLeft,rangeRight);
     
         String[] aboutToReturn = FunctionString.split(";");
         
         ret="";
         for(String line:aboutToReturn){
             //System.out.println(line);
             for(String anotherString:line.split(",")){
                 ret=ret+jc.eval(anotherString)+",";
             }
             ret=(ret.endsWith(","))?ret.substring(0,ret.length()-1):ret;
             ret=ret+";";
         }

        return (ret.endsWith(";"))?ret.substring(0,ret.length()-1):ret;

     //      return FunctionString;
    }
    
    public String Seq(String function,ArrayList<String> Vars,ArrayList<String> steps, 
                                        ArrayList<String> rangeLeft,ArrayList<String> rangeRight) throws Exception{
        
        int S = steps.size();
        if(S<1)
            throw new Exception("No variables for sequence!?");
        
        String tp = jc.eval(steps.get(S-1));
        
        BigDecimal step =(tp.indexOf('/')==-1)?new BigDecimal(tp,jc.MC):(new Fraction(tp)).toBigDecimal(jc.MC);
        tp = jc.eval(rangeRight.get(S-1));
        
         BigDecimal right = (tp.indexOf('/')==-1)?new BigDecimal(tp,jc.MC):(new Fraction(tp)).toBigDecimal(jc.MC);
        String start = jc.eval(rangeLeft.get(S-1));
        
        tp = start;
        BigDecimal left = (tp.indexOf('/')==-1)?new BigDecimal(tp,jc.MC):(new Fraction(tp)).toBigDecimal(jc.MC);
        String var = Vars.get(S-1);
        
         BigInteger N = right.subtract(left).divide(step,jc.MC).toBigInteger();
         //N=N.subtract(BigInteger.ONE).subtract(BigInteger.ONE);
         N=N.subtract(BigInteger.ONE);
         if(Vars.size()==1){
             String ret=SmartReplace.get(function,var,start);
                       
                      BigInteger it = BigInteger.ZERO;
                    while(it.compareTo(N)<=0){
                        it=it.add(BigInteger.ONE);
                        left=left.add(step);
                        start=left.toPlainString();
                        ret = ret + ";"+SmartReplace.get(function,var,start);
                        //integralV=integralV.add(BDCM(jc.eval(function.replace(var,start))),jc.MC);
                    }
               return ret;     
         }
        steps.remove(S-1);
        rangeRight.remove(S-1);
        rangeLeft.remove(S-1);
        Vars.remove(S-1);  
              ///String ret=SmartReplace.get(function,var,start);
                 String ret = Replace(var,start,function,Vars,steps,rangeLeft,rangeRight);      
                      BigInteger it = BigInteger.ZERO;
                    while(it.compareTo(N)<=0){
                        it=it.add(BigInteger.ONE);
                        left=left.add(step);
                        start=left.toPlainString();
                        ret = ret + ";"+ Replace(var,start,function,Vars,steps,rangeLeft,rangeRight);
                        //integralV=integralV.add(BDCM(jc.eval(function.replace(var,start))),jc.MC);
                    }
               return ret;            
    
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
        return Seq(SmartReplace.get(function,var,value),nVars,nsteps,nRangeLeft,nRangeRight);
    }  
}
