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

/**
 *
 * @author sergey_nikitin
 */
public class CalCsum implements CalcFunction{
   String Report="";
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
        return "returns the sum of a sequence, sum({1,2;-1,3})={0,5} or sum({1;2})=3"; 
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private final String ErrorMessage="Format error for sum({A})";
    @Override
    public String eval(String argv) throws Exception {
        
        if(argv.indexOf('{')==-1 ||argv.indexOf('}')==-1)
            throw new Exception(ErrorMessage);
        if(argv.endsWith("{"))
            throw new Exception(ErrorMessage);
        
        
        String bf = argv.substring(argv.indexOf('{')+1);
        
        bf=jc.getInside(bf, '{', '}');
        
        if(bf.indexOf(';')==-1)
            return argv;
        
        String[] vectors = bf.split(";");
        
        
        BigDecimal[] answer=null;
        for(String next:vectors){
            if(answer == null){
                if(next.indexOf(',')==-1){
                    answer = new BigDecimal[1];
                    if(next.indexOf('/')==-1)
                    answer[0]=new BigDecimal(next,jc.MC);
                    else
                      answer[0]=jc.getFraction(next).toBigDecimal(jc.MC);
                }
                else{
                    answer=new BigDecimal[next.split(",").length];
                    int i = 0;
                    for(String it:next.split(",")){
                        if(it.indexOf('/')==-1)
                        answer[i] = new  BigDecimal(it,jc.MC);
                        else
                            answer[i] = jc.getFraction(it).toBigDecimal(jc.MC);
                        i++;
                    }
                }
            }
            else{
                   int i = 0;
                  for(String it:next.split(",")){
                      if(it.indexOf("/")==-1)
                        answer[i] = answer[i].add(new  BigDecimal(it,jc.MC),jc.MC);
                      else
                        answer[i] = answer[i].add(jc.getFraction(it).toBigDecimal(jc.MC),jc.MC);  
                        i++;
                    }
            }
        }
        if(answer == null)
              throw new Exception(ErrorMessage);
        
        if(answer.length == 1)
            return answer[0].toPlainString();
        
     String ret="";
     for(BigDecimal nm:answer){
      ret=ret+","+nm.toPlainString();
     }
     return ret.substring(1);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
