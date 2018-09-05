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
public class CalCmodInverse implements CalcFunction {
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
        return "modInverse(n,m) returns n^(-1) mod( m ) ";
    }

    @Override
    public String eval(String argv) throws Exception {
        String[] values = argv.split(",");
        if(values.length!=2)
            throw new Exception("modInverse(m,n) requires two arguments.");  
        return (new BigInteger(values[0])).modInverse(new BigInteger(values[1])).toString();
    }
    
    
}
