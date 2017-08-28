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
import static org.ioblako.math.calculator.CalCsin.PI;
import static org.ioblako.math.calculator.CalCsin.twoPI;
import org.ioblako.math.linearalgebra.Fraction.Fraction;


public class CalCcos implements CalcFunction {
String Report="";
@Override
public String getHelp(){
return "cos(s) is an x-coordinate of a point on the unit circle x^2+y^2=1 which you can reach after traveling (along the circle) distance \"s\" going coounter-clockwise and starting at (1,0).";
}
BigDecimal error;
@Override
public String eval(String argv) throws Exception{
    int precision = jc.MC.getPrecision()+3;
String errorString="0.";
  for(int j = 1; j< precision ;j++)
      errorString=errorString+'0';
error = new BigDecimal(errorString+'1');

argv=jc.eval(argv);
if(argv.startsWith("-"))
  argv=argv.substring(1);

BigDecimal x;
if(argv.indexOf('/')==-1)
     x = new BigDecimal(argv,jc.MC);
else
  x = (new Fraction(argv)).toBigDecimal();
CalCsin Sin = new CalCsin();
x = Sin.getUnderTwoPi(x);

BigDecimal PI2 = PI.divide(new BigDecimal("2"));


if(x.abs().compareTo(error)<=0)
 return "1";
if(x.subtract(twoPI).abs(jc.MC).compareTo(error)<=0)
    return "1";
if(x.subtract(PI).abs(jc.MC).compareTo(error)<=0)
    return "-1";
if(x.subtract(PI2).abs(jc.MC).compareTo(error)<=0)
    return "0";
if(x.subtract(PI2.add(PI)).abs(jc.MC).compareTo(error)<=0)
    return "0";

BigDecimal PI3=PI.divide(new BigDecimal("3"),jc.MC);
if(x.subtract(PI3).abs(jc.MC).compareTo(error)<=0)
    return "1/2";
BigDecimal twoPI3=PI3.add(PI3);
if(x.subtract(twoPI3).abs(jc.MC).compareTo(error)<=0)
    return "-1/2";
if(x.subtract(twoPI3.add(twoPI3)).abs(jc.MC).compareTo(error)<=0)
    return "-1/2";
if(x.subtract(twoPI3.add(PI)).abs(jc.MC).compareTo(error)<=0)
    return "1/2";
  return (new CalCsin()).eval(x.stripTrailingZeros().toPlainString()+"+pi/2");
}
@Override
public void setReport(String str){
 Report=str;
}
@Override
public String getReport(){
return Report;
}

}
