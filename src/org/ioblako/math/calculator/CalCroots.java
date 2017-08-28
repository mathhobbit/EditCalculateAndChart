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

import org.ioblako.math.Complex;
import org.ioblako.math.DoubleLGA;
import org.ioblako.math.PolynomialRootFinderJenkinsTraub;
import org.ioblako.math.PolynomialRootFinderLaguerre;

/**
 *
 * @author sergey_nikitin
 */
public class CalCroots implements CalcFunction{
     public String Report="";
     
     final DoubleLGA LGA = new DoubleLGA();
    double[] Polynom=null;
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
        return "roots({a0,a1,..,an}) returns all roots of "+System.lineSeparator()+
                " a0+a1*x+...+an*x^n"+System.lineSeparator()+
               // " with precision \"step\"."+System.lineSeparator()+
                "roots({a0,a1,..,an},Laguerre) will employ Laguerre method"+
                System.lineSeparator()+" to find roots. Jenkins-Traub algorithm is used as default for roots.";//+System.lineSeparator()+
                //"Real roots are computed with \"Leap Gradient\" approach." ; 
    }
String ErrorMessage="Format error. roots(step,{a0,...,an}).";
    @Override
    public String eval(String argv) throws Exception {
        String ret="";
        if(argv.indexOf('{')==-1 || argv.indexOf('}')==-1)
            throw new Exception(ErrorMessage);
        
        if(argv.endsWith("{"))
            throw new Exception(ErrorMessage);
        
       
      //  String step = argv.substring(0,argv.indexOf(","));
        
        String Method = argv.substring(argv.lastIndexOf("}")+1);
        Method = Method.startsWith(",")?Method.substring(1):Method;
        
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
         //ret=getRroots(poly, Double.parseDouble(step));
         Polynom = poly;
         
             double[] polynomC = new double[Polynom.length];
             for(int j = 0; j < polynomC.length ; j++)
                 polynomC[j] = Polynom[Polynom.length-j-1];
             
             Complex[] roots;
             if(Method.equalsIgnoreCase("LAGUERRE"))
             try{    
              //roots = PolynomialRootFinderLaguerre.findRoots(polynomC,true);
              roots = PolynomialRootFinderLaguerre.findRoots(polynomC);
             }
             catch(Exception ex){
               roots = PolynomialRootFinderJenkinsTraub.findRoots(polynomC);  
             }
             else
              try{   
                roots = PolynomialRootFinderJenkinsTraub.findRoots(polynomC);
              }
             catch(Exception e){
                 roots = PolynomialRootFinderLaguerre.findRoots(polynomC);
             }
             String complexRoots="";
             String rpt = getReport();
             String Re, Im, bf;
             for(Complex rt:roots){
                 if(rt.im() == 0){
                        bf = rt.toPlainString(jc.MC);
                        Re = bf.substring(bf.indexOf('(')+1,bf.indexOf(','));
                        ret = ret + ((ret.contentEquals(""))?Re:","+Re);
                 }
                
                 bf = rt.toPlainString(jc.MC);
                 Re = bf.substring(bf.indexOf('(')+1,bf.indexOf(','));
                 Im = bf.substring(bf.indexOf(',')+1,bf.indexOf(')'));
                 rpt = rpt+System.lineSeparator()+"{"+Re+","+Im+"}";
                 complexRoots = complexRoots+
                         ((complexRoots.contentEquals(""))?Re+","+Im:";"+
                           Re+","+Im);
                 
             }
             
             if(!ret.contentEquals(""))
                 rpt=ret.contains(",")?rpt+System.lineSeparator()+"Real roots: "+ret:rpt+System.lineSeparator()+"Real root: "+ret;
             
                 ret="{"+complexRoots+"}";
             
             
             setReport(rpt);
        
        return ret;
    }
}
