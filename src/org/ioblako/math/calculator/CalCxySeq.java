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
package org.ioblako.math.calculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.ioblako.math.linearalgebra.Fraction;

/**
 *
 * @author sergey_nikitin
 */
public class CalCxySeq implements CalcFunction{
    
    String Report="";
public String getHelp(){
return "xySeq(f(x),x={x1,x2,..xn}) returns {x1,f(x1);x2,f(x2);..xn,f(xn)}";
}
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}


    @Override
    public String eval(String argv) throws Exception{
       String function=argv.substring(0,argv.indexOf(','));
       String bf = argv.substring(argv.indexOf(',')+1);
       String Var="x";
       if(bf.indexOf('=')!=-1)
           Var=bf.substring(0,bf.indexOf('='));
       if(bf.indexOf('{')==-1||bf.indexOf('}')==-1)
            throw new Exception("Format Error for xySeq(f(x),x={x1,x2,..xn}) or xySeq(f(x),x={step,rangeLeft..rangeRight})");
       
       String[] xValues=new String[0];
       if(bf.contains("..")){
           bf = bf.substring(bf.indexOf('{')+1,bf.indexOf('}'));
           String[] pts = bf.split(",");
           if(pts.length != 2)
                throw new Exception("Format Error for xySeq(f(x),x={x1,x2,..xn}) or xySeq(f(x),x={step,rangeLeft..rangeRight})");
           String step=null, rangeLeft=null,rangeRight=null;
           for(String el:pts){
                  if(el.contains("..")){
                      rangeLeft = el.substring(0,el.indexOf(".."));
                      rangeRight=el.substring(el.indexOf("..")+2);
                  }
                  else{
                      if(el.contains("="))
                          step=el.substring(el.indexOf('=')+1);
                      else
                          step=el;
                  }
             }
           if(step == null||rangeLeft==null||rangeRight==null)
                throw new Exception("Format Error for xySeq(f(x),x={x1,x2,..xn}) or xySeq(f(x),x={step,rangeLeft..rangeRight})");
           
           ArrayList<String> preX = new ArrayList<>();
           
             
              BigDecimal st=(step.indexOf('/')!=-1)?(new Fraction(step)).toBigDecimal(jc.MC):(new BigDecimal(step,jc.MC));
              BigDecimal Left=(rangeLeft.indexOf('/')!=-1)?(new Fraction(rangeLeft)).toBigDecimal(jc.MC):(new BigDecimal(rangeLeft,jc.MC));
              BigDecimal Right=(rangeRight.indexOf('/')!=-1)?(new Fraction(rangeRight)).toBigDecimal(jc.MC):(new BigDecimal(rangeRight,jc.MC));
              BigDecimal addIt=Left;
             while(addIt.compareTo(Right)<0){
                 preX.add(addIt.stripTrailingZeros().toPlainString());
                 addIt=addIt.add(st);
             }
             xValues=preX.toArray(xValues);
       }
       else
        xValues = bf.substring(bf.indexOf('{')+1,bf.indexOf('}')).split(",");
       
       
       
       String ret="";
       
       for(String dt:xValues)
           ret=ret+dt+","+jc.eval(function.replace(Var,jc.eval(dt.trim())))+";";
       
       
       if(ret.length()==0)
           return ret;
       
       setReport(ret.replace(";",System.lineSeparator()));
       
       return ret.substring(0,ret.length()-1);
       
}
    
}
