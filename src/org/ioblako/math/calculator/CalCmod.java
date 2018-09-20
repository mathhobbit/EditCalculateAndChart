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

import java.math.BigInteger;
/**
 *
 * @author Sergey Nikitin
 */
public class CalCmod implements CalcFunction {
    String Report = "";
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
        return "mod(n,m) returns n mod( m ) ";
    }

    @Override
    public String eval(String argv) throws Exception {
        if(!argv.contains(","))
               throw new Exception("mod(n,m) requires two arguments.");
        
       BigInteger IntArg, Module;  
        
       String[] values = argv.split(",");
       
       if(values.length >2)
             throw new Exception("mod(n,m) requires two arguments.");
       
        IntArg = new BigInteger(jc.eval(values[0]));
        Module = new BigInteger(jc.eval(values[1]));
        
        return IntArg.mod(Module).toString();
    }
    
    
    
}
