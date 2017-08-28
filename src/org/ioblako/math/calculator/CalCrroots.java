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

import org.ioblako.math.DoubleLGA;
import org.ioblako.math.Complex;
import org.ioblako.math.PolynomialRootFinderJenkinsTraub;

/**
 *
 * @author sergey_nikitin
 */
public class CalCrroots implements CalcFunction{

     public String Report="";
     final DoubleLGA LGA = new DoubleLGA();
    
    @Override
    public String getReport() {
        return Report;
    }

    @Override
    public void setReport(String str) {
         Report = str;
    }

    @Override
    public String getHelp() {
        return "rroots({a0,a1,..,an}) returns real roots of a0+a1*x+...+an*x^n"; 
    }
String ErrorMessage="Format error. rroots(step,{a0,...,an}).";
    @Override
    public String eval(String argv) throws Exception {
        
        String ret="";
                 if(argv.indexOf('{')==-1 || argv.indexOf('}')==-1)
            throw new Exception(ErrorMessage);
        
        if(argv.endsWith("{"))
            throw new Exception(ErrorMessage);
        
       
        String step = argv.substring(0,argv.indexOf(","));
        
        
        String strPoly = jc.getInside(argv.substring(argv.indexOf('{')+1), '{', '}');
               String[] stArray = strPoly.split(",");
         double[] poly=new double[stArray.length];
         int i = 0;
         for(String next:stArray){
            if(next.indexOf('/')!=-1)
                 poly[i] = jc.getFraction(next).toBigDecimal(jc.MC).doubleValue();
            else
                 poly[i]=new Double(next);
             i++;
         }
         setReport(LGA.PrintPoly(poly));
         
           double[] polynomC = new double[poly.length];
             for(int j = 0; j < polynomC.length ; j++)
                 polynomC[j] = poly[poly.length-j-1];
        Complex[] roots = PolynomialRootFinderJenkinsTraub.findRoots(polynomC);
        String Re,bf;
        ret="";
        for(Complex rt:roots){
             if(rt.im() == 0){
                bf = rt.toPlainString(jc.MC);
                Re = bf.substring(bf.indexOf('(')+1,bf.indexOf(','));
                ret = ret + ((ret.contentEquals(""))?Re:","+Re);
              }
        }
        return ret;
         
         
       // return getRroots(poly, Double.parseDouble(step));
    }
}
