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
import org.ioblako.math.DoubleLGA;

/**
 *
 * @author sergey_nikitin
 */
public class CalCpolynomMin implements CalcFunction{
    
    public String Report="";

    @Override
    public String getReport() {
        return Report;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setReport(String str) {
        Report = str;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getHelp() {
        return "polynomMin(step,left..right,{a0,a1,..,an}) returns the minimum of a0+a1*x+...+an*x^n "+System.lineSeparator()+
               "on the interval [left,right] with the precision \"step\""+
                System.lineSeparator()+"It is implemented in double precision format.";
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String eval(String argv) throws Exception {
        String ErrorMessage="Format error. polynomMin(step,left..right,{a0,...,an}).";
        
         if(argv.indexOf('{')==-1 || argv.indexOf('}')==-1)
            throw new Exception(ErrorMessage);
        
        if(argv.endsWith("{"))
            throw new Exception(ErrorMessage);
        
        String bf = argv.substring(0,argv.indexOf('{'));
        String step = bf.substring(0,bf.indexOf(","));
        bf = bf.substring(bf.indexOf(',')+1,bf.lastIndexOf(','));
        String left = bf.substring(0,bf.indexOf(".."));
        String right = bf.substring(bf.indexOf("..")+2);
        
        String strPoly = jc.getInside(argv.substring(argv.indexOf('{')+1), '{', '}');
               String[] stArray = strPoly.split(",");
         double[] poly=new double[stArray.length];
         int i = 0;
         for(String next:stArray){
            if(next.indexOf('/')!=-1)
                 poly[i] = jc.getFraction(next).toBigDecimal(jc.MC).doubleValue();
            else
                 poly[i]=Double.valueOf(next);
             i++;
         }
         DoubleLGA LGA = new DoubleLGA();
         double min = LGA.getMin(poly,Double.parseDouble(left),Double.parseDouble(right), Double.parseDouble(step));
          return (new BigDecimal(Double.toString(min),jc.MC)).stripTrailingZeros().toPlainString();
    }
    
}
