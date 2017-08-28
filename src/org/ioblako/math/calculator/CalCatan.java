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
import org.ioblako.math.linearalgebra.Fraction.Fraction;

public class CalCatan implements CalcFunction {
String Report="";
BigDecimal error;
Fraction frERROR;
public String getHelp(){
return "inverse of tan(x), tan(atan(x))=x";
}
public String eval(String argv) throws Exception{

argv=jc.eval(argv);

if(argv.contentEquals("0"))
  return "0";
if(argv.contentEquals("1"))
  return "pi/4";
if(argv.contentEquals("-1"))
  return "-pi/4";

int precision = jc.MC.getPrecision();
String errorString="0.";
  for(int j = 1; j< precision ;j++)
      errorString=errorString+'0';
error = new BigDecimal(errorString+'1');
frERROR = new Fraction(errorString+'1');
boolean negateIt=false;

if(argv.startsWith("-")){
  negateIt=true;
  argv=argv.substring(1);
}

BigDecimal x;
if(argv.indexOf('/')==-1)
     x = new BigDecimal(argv,jc.MC);
else
  x = (new Fraction(argv)).toBigDecimal();

if(x.compareTo(error)<=0)
 return "0";

if(x.subtract(BigDecimal.ONE,jc.MC).abs().compareTo(error)<=0){
  if(negateIt)
    return "-pi/4";
  else
    return "pi/4";
}
BigDecimal oneQt = new BigDecimal("1.20");
BigDecimal threeQt = new BigDecimal("0.80");
if(x.compareTo(threeQt)<=0){
 if(negateIt)
  return atan(x).negate().toPlainString();
 else
  return atan(x).toPlainString();
}
if(x.compareTo(oneQt)>0){
 x= (new Fraction(x)).invert().toBigDecimal(jc.MC); 
 if(negateIt)
  return  "-"+jc.eval("pi/2-"+atan(x).toPlainString());
 else
  return jc.eval("pi/2-"+atan(x).toPlainString());
}

 if(negateIt)
  return  atn(x).negate().toPlainString();
 else
  return atn(x).toPlainString();

}

public BigDecimal atan(BigDecimal x){
BigDecimal atan=x;
BigDecimal step=BigDecimal.ONE;
BigDecimal xPow=x;
BigDecimal mistake=x;
  while(mistake.abs().compareTo(error)>0){

    step=step.add(BigDecimal.ONE).add(BigDecimal.ONE);
    xPow=xPow.multiply(x.pow(2,jc.MC)).negate();
    mistake=xPow.divide(step,jc.MC);


    atan= atan.add(mistake,jc.MC);
  }

return atan;

}
//for x --> 1
public BigDecimal atn(BigDecimal x) throws Exception{
Fraction PI4 = new Fraction(jc.eval("pi"));
PI4 = PI4.divide(new Fraction("4"));
//BigDecimal PI4=fPI4.toBigDecimal(jc.MC.getPrecision());
//BigDecimal T1=(new Fraction("1/2")).toBigDecimal(jc.MC.getPrecision());
Fraction T1=new Fraction("1/2");
//BigDecimal T2=(new Fraction("-1/4")).toBigDecimal(jc.MC.getPrecision());
Fraction T2=new Fraction("-1/4");
//BigDecimal dX = (new Fraction(x)).subtract(Fraction.ONE).toBigDecimal(jc.MC.getPrecision());
Fraction dX = (new Fraction(x)).subtract(Fraction.ONE);
Fraction mistake = T2.multiply(dX.multiply(dX));
Fraction atan=PI4.add(T1.multiply(dX)).add(mistake);
Fraction dXpow=dX.multiply(dX);
Fraction buffer;
long step=1;
  while(mistake.abs().compareTo(frERROR)>0){
    dXpow=dXpow.multiply(dX);
    step=step+1;

    buffer = T2;

   T2=(new Fraction(step+1,step)).multiply(T2).add((new Fraction(2*(step+1),step-1)).multiply(T1)).negate();
    T1=buffer;
  if(T2.compareTo(Fraction.ZERO)==0)
       continue; 
    mistake=T2.multiply(dXpow);
    
    atan= atan.add(mistake);
   
  }


return atan.toBigDecimal(jc.MC.getPrecision());
}
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}

}
