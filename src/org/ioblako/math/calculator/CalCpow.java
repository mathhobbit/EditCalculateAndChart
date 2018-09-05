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
public class CalCpow implements CalcFunction{
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
        return "pow(n,p) returns n^p for integers n and p ";
    }

    @Override
    public String eval(String argv) throws Exception {
       String[] values=argv.split(",");
       if(values.length != 2)
            throw new Exception("pow(n,p) requires two integer arguments");
       return (new BigInteger(values[0])).pow(Integer.parseInt(values[1])).toString();
    }
    
}
