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
import java.math.BigInteger;
import org.ioblako.math.linearalgebra.Fraction;

public class CalCpowb implements CalcFunction{
BigDecimal error;
BigInteger Ynumerator;
BigInteger Ydenominator;
BigInteger toDeno;
BigDecimal pTWO;
BigDecimal TWOp;
Fraction y;
public String getHelp(){
return "powb(x,y) calculates x^y, x raised to the power of y. It is implemeted with the binomial series ((1+z)^s = 1+ s*z+s*(s-1)z^2/2+...)" ;
}

public String eval(String argv) throws Exception{
if(argv.indexOf(',')==-1)
   throw new Exception("powb(x,y) requires two arguments.");
String[] args = argv.split(",");
if(args.length!=2)
   throw new Exception("powb(x,y) requires two arguments.");
args[0] = jc.eval(args[0]);
args[1] = jc.eval(args[1]);

if(jc.compare(args[0],"0")==0&&!args[1].startsWith("-"))
    return "0";

boolean negateIt=false;
if(args[0].startsWith("-")){
Fraction dec = Fraction.plainFraction(args[1]);
if(dec.getNumerator().intValue()%2==0)
     args[0]=args[0].substring(1);
else{
 if(dec.getDenominator().intValue()%2==0)
   throw new Exception("powb("+args[0]+","+args[1]+") is not defined.");
 else
     args[0]=args[0].substring(1);
     negateIt=true;
   }
}
 if(args[1].indexOf('/')==-1 &&
    args[1].indexOf('.')==-1){
  if(args[1].startsWith("-")) {
     if(negateIt)
     return "-"+(new Fraction(args[0])).invert().toBigDecimal().pow(Integer.parseInt(args[1].substring(1))).toPlainString();
    else
     return (new Fraction(args[0])).invert().toBigDecimal().pow(Integer.parseInt(args[1].substring(1))).toPlainString();
   }
   else
   if(negateIt)
   return "-"+(new BigDecimal(args[0])).pow(Integer.parseInt(args[1])).toPlainString();
  else
   return (new BigDecimal(args[0])).pow(Integer.parseInt(args[1])).toPlainString();
}
int precision = jc.MC.getPrecision();
String errorString="0.";
  for(int j = 1; j< precision ;j++)
      errorString=errorString+'0';
error = new BigDecimal(errorString+'1');


BigDecimal x = (new Fraction(args[0])).toBigDecimal(precision);

y = new Fraction(args[1]);
Ynumerator = y.getNumerator();
Ydenominator = y.getDenominator();

if(Ydenominator.compareTo(new BigInteger("20"))>0)
    throw new Exception("Performance of \"powb\" for fraction powers with denominators larger than 20 is poor use \"powl\" instead.");

if(x.compareTo(error)<=0 && args[1].startsWith("-"))
 throw new Exception("Connot divide by 0!");
if(x.compareTo(error)<=0)
   return "0";


if(args[1].startsWith("-")){
   y= new Fraction(args[1].substring(1));
Ynumerator = y.getNumerator();
Ydenominator = y.getDenominator();
  x= BigDecimal.ONE.divide(x,jc.MC);
}

pTWO = BigDecimal.ONE.add(BigDecimal.ONE,jc.MC).pow(Ydenominator.intValue());
toDeno = BigInteger.ONE.add(BigInteger.ONE).pow(Ydenominator.intValue());
TWOp = BigDecimal.ONE.add(BigDecimal.ONE,jc.MC).pow(Ynumerator.intValue());

if(x.compareTo(pTWO)<0)
   if(negateIt)
    return "-"+TWOp.multiply(bnm(x.divide(pTWO,jc.MC),error),jc.MC).toPlainString();
  else
    return TWOp.multiply(bnm(x.divide(pTWO,jc.MC),error),jc.MC).toPlainString();

if(x.compareTo(pTWO) == 0)
      if(negateIt)
      return "-"+TWOp.toPlainString();
      else
      return TWOp.toPlainString();

BigDecimal floor = pTWO;

while(x.compareTo(floor)>0)
      floor=floor.add(BigDecimal.ONE,jc.MC);

if(x.compareTo(floor)==0)
  if(negateIt)
   return "-"+lBnm(getBinary(floor)).toPlainString();
  else
   return lBnm(getBinary(floor)).toPlainString();

floor=floor.subtract(BigDecimal.ONE);
if(negateIt)
return "-"+lBnm(getBinary(floor)).multiply(bnm(x.divide(floor),error),jc.MC).toPlainString();
else
return lBnm(getBinary(floor)).multiply(bnm(x.divide(floor),error),jc.MC).toPlainString();

}

BigDecimal bnm(BigDecimal x, BigDecimal error){
BigDecimal ret=BigDecimal.ONE;
BigDecimal z = x.subtract(ret);
BigDecimal step=BigDecimal.ZERO;
BigDecimal mistake=BigDecimal.ONE;
BigDecimal Al=y.toBigDecimal();
while(mistake.abs().compareTo(error)>0){
  step=step.add(BigDecimal.ONE);
  mistake=mistake.multiply(z,jc.MC).multiply(Al.subtract(step.subtract(BigDecimal.ONE),jc.MC),jc.MC).divide(step,jc.MC);  
  ret=ret.add(mistake,jc.MC);
}
return ret;
}

String getBinary(BigDecimal fl) throws Exception{
BigInteger fi = fl.toBigInteger();
BigInteger rm;
String response="";
while(fi.compareTo(toDeno)>=0){
 rm=fi.remainder(toDeno);
 if(rm.compareTo(BigInteger.ZERO)==0)
    response='0'+","+response;
 else
    response=rm.toString()+","+response;
 fi=fi.subtract(rm).divide(toDeno);
}
response  = fi.toString()+","+response;
if(response.endsWith(","))
 response=response.substring(0,response.length()-1);

return response;
}

BigDecimal lBnm(String bin) throws Exception{
if(bin.contentEquals("1"))
  return BigDecimal.ONE;

if(bin.contentEquals("0"))
  return BigDecimal.ZERO;

if(bin.indexOf(',')==-1){
   return TWOp.multiply(bnm((new BigDecimal(bin)).divide(pTWO,jc.MC),error),jc.MC);
}

if(bin.startsWith("0"))
   throw new Exception("Erroneous digital string input!");
while(bin.endsWith(","))
   bin=bin.substring(0,bin.length()-1);
int mltp = 0;
if(bin.endsWith(",0")){
while(bin.endsWith(",0")){
   bin= bin.substring(0,bin.length()-2);
    mltp++;
}
return TWOp.pow(mltp).multiply(lBnm(bin),jc.MC);
}

BigDecimal ltp = BigDecimal.ZERO;

String[] inb = bin.split(","); 
BigDecimal z = new BigDecimal(inb[inb.length - 1]);

for(int i = 0;i<inb.length - 1; i++){
  ltp=ltp.multiply(pTWO,jc.MC);
  ltp = ltp.add(new BigDecimal(inb[i]),jc.MC);
}
  ltp = ltp.multiply(pTWO,jc.MC);
return  TWOp.multiply(lBnm(bin.substring(0,bin.lastIndexOf(','))),jc.MC).multiply(bnm(BigDecimal.ONE.add(z.divide(ltp,jc.MC)),error),jc.MC); 
}

String Report="";
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}

}
