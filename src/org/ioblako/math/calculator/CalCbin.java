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
import java.math.BigInteger;
/**
 *
 * @author Sergey Nikitin
 */
public class CalCbin implements CalcFunction{
  String Report="";
    @Override
    public String getReport() {
        return Report;
    }

    @Override
    public void setReport(String str) {
        Report = (Report.compareTo("")==0)?str:Report+System.lineSeparator()+str;
    }

    @Override
    public String getHelp() {
        return "bin(s) returns the string representation of the integer s in binary form (radix 2)";
    }

    @Override
    public String eval(String argv) throws Exception {
        BigInteger s = new BigInteger(argv);
        String rt="";
        for(int i = s.bitLength()-1; i >= 0;i--)
             if(s.testBit(i))
                 rt=(rt.compareTo("")==0)?"1":rt+"1";
             else
                  rt=(rt.compareTo("")==0)?"0":rt+"0";
                 
        return (rt.compareTo("")==0)?"0":rt;         
    }
    
}
