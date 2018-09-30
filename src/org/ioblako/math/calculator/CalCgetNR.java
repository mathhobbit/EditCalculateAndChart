/*
 * Copyright (C) 2018 Sergey Nikitin
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


/**
 *
 * @author Sergey Nikitin
 */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

public class CalCgetNR implements CalcFunction{
String Report="";
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
        return "getNR(r,n) returns am, ... , a1, a0 the numeral representation base \"r\" for \"n\","+System.lineSeparator()+
               "n = am * r^m + ... + a1 * r + a0"; 
    }

    @Override
    public String eval(String argv) throws Exception {
            String[] sp = argv.split(",");
            
            if(sp.length != 2)
                throw new Exception("getNR(r,n) needs two arguments: r is a radix, n is a number.");
        
            ArrayList<BigInteger> rt = (new CalCEF()).getNumeralRepresentation(new BigInteger(sp[1]),new BigInteger(sp[0]));
            
            String retS="",bf;
            Iterator<BigInteger> it = rt.iterator();
            while(it.hasNext()){
                bf=it.next().toString();
                retS= (retS.compareTo("")==0)?bf+retS:bf+","+retS;
            }
            return retS;  
    }
    
}
