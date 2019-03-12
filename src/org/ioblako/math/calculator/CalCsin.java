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
import org.ioblako.math.linearalgebra.Fraction;
public class CalCsin implements CalcFunction {
@Override
public String getHelp(){
return "sin(s) is a y-coordinate of a point on the unit circle x^2+y^2=1 which you can reach after traveling (along the circle) distance \"s\" going coounter-clockwise and starting at (1,0).";
}

static BigDecimal PI = new BigDecimal("3.14159265358979323846264338327950288419716939937510582097494459230781640628620"+
"899862803482534211706798214808651328230664709384460955058223172535940812848111"+
"745028410270193852110555964462294895493038196442881097566593344612847564823378"+
"678316527120190914564856692346034861045432664821339360726024914127372458700660"+
"631558817488152092096282925409171536436789259036001133053054882046652138414695"+
"194151160943305727036575959195309218611738193261179310511854807446237996274956"+
"735188575272489122793818301194912983367336244065664308602139494639522473719070"+
"217986094370277053921717629317675238467481846766940513200056812714526356082778"+
"577134275778960917363717872146844090122495343014654958537105079227968925892354"+
"201995611212902196086403441815981362977477130996051870721134999999837297804995"+
"105973173281609631859502445945534690830264252230825334468503526193118817101000"+
"313783875288658753320838142061717766914730359825349042875546873115956286388235"+
"378759375195778185778053217122680661300192787661119590921642019893809525720106"+
"548586327886593615338182796823030195203530185296899577362259941389124972177528"+
"347913151557485724245415069595082953311686172785588907509838175463746493931925"+
"506040092770167113900984882401285836160356370766010471018194295559619894676783"+
"744944825537977472684710404753464620804668425906949129331367702898915210475216"+
"205696602405803815019351125338243003558764024749647326391419927260426992279678"+
"235478163600934172164121992458631503028618297455570674983850549458858692699569"+
"092721079750930295532116534498720275596023648066549911988183479775356636980742"+
"654252786255181841757467289097777279380008164706001614524919217321721477235014",jc.MC) ;

static final BigDecimal twoPI = BigDecimal.ONE.add(BigDecimal.ONE).multiply(PI,jc.MC);

BigDecimal error;

@Override
public String eval(String argv) throws Exception{

int precision = jc.MC.getPrecision();
String errorString="0.";
  for(int j = 1; j< precision ;j++)
      errorString=errorString+'0';
error = new BigDecimal(errorString+'1');

boolean negateIt=false;

argv=jc.eval(argv);
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
if(x.subtract(twoPI).abs().compareTo(error)<=0)
    return "0";
if(x.subtract(PI).abs().compareTo(error)<=0)
    return "0";

BigDecimal counter = BigDecimal.ONE;
/*
BigDecimal twoPImultiple = twoPI;

if(x.compareTo(twoPI)>0){
while(x.compareTo(twoPImultiple)>0){
     counter = counter.add(BigDecimal.ONE,jc.MC);
     twoPImultiple = twoPI.multiply(counter,jc.MC);
}

if(x.subtract(twoPImultiple).abs().compareTo(error)<=0)
    return "0";

x=x.subtract(twoPI.multiply(counter.subtract(BigDecimal.ONE,jc.MC),jc.MC),jc.MC);
}
*/

x = getUnderTwoPi(x);

BigDecimal PI4=PI.divide(new BigDecimal("4"),jc.MC);
if(x.compareTo(PI4)<=0 ||
   x.subtract(PI4,jc.MC).abs().compareTo(error)<=0)
    if(negateIt)
       return "-"+sin(x).stripTrailingZeros().toPlainString();
    else
      return sin(x).stripTrailingZeros().toPlainString();

BigDecimal PI2 = PI.divide(BigDecimal.ONE.add(BigDecimal.ONE),jc.MC);
if(x.subtract(PI2,jc.MC).abs().compareTo(error)<=0)
   if(negateIt)
      return "-1";
  else
    return "1";
BigDecimal threePI4=PI4.multiply(new BigDecimal("3"),jc.MC);
if(x.compareTo(PI4)>0&&
   x.compareTo(threePI4)<=0)
    if(negateIt)
       return "-"+cos(PI2.subtract(x,jc.MC)).stripTrailingZeros().toPlainString();
    else
      return cos(PI2.subtract(x,jc.MC)).stripTrailingZeros().toPlainString();
if(x.subtract(PI,jc.MC).abs().compareTo(error)<=0)
    return "0";
if(x.compareTo(threePI4)>0&&
   x.compareTo(PI)<=0)
    if(negateIt)
       return "-"+sin(PI.subtract(x,jc.MC)).stripTrailingZeros().toPlainString();
    else
      return sin(PI.subtract(x,jc.MC)).stripTrailingZeros().toPlainString();
if(x.compareTo(PI)>0&&
    x.compareTo(twoPI)<=0)
if(negateIt)
 return (new CalCsin()).eval(twoPI.subtract(x,jc.MC).stripTrailingZeros().toPlainString()); 
else
 return "-"+(new CalCsin()).eval(twoPI.subtract(x,jc.MC).stripTrailingZeros().toPlainString()); 
return "NaN";
}

BigDecimal sin(BigDecimal x){
BigDecimal sin=x;
BigDecimal step=BigDecimal.ONE;
BigDecimal mistake=x;
  while(mistake.abs().compareTo(error)>0){

    step=step.add(BigDecimal.ONE);

    mistake=mistake.multiply(x.pow(2,jc.MC)).divide(step.multiply(step.add(BigDecimal.ONE,jc.MC),jc.MC),jc.MC).negate();

 
    sin= sin.add(mistake,jc.MC);
    step=step.add(BigDecimal.ONE);
  }

return sin;
}

BigDecimal cos(BigDecimal x){
BigDecimal cos=BigDecimal.ONE;
BigDecimal step=BigDecimal.ZERO;
BigDecimal mistake=BigDecimal.ONE;

  while(mistake.abs().compareTo(error)>0){

    step=step.add(BigDecimal.ONE);

    mistake=mistake.multiply(x.pow(2,jc.MC)).divide(step.multiply(step.add(BigDecimal.ONE,jc.MC),jc.MC),jc.MC).negate();

 
    cos= cos.add(mistake,jc.MC);
    step=step.add(BigDecimal.ONE);
  }

return cos;
}
String Report="";
@Override
public void setReport(String str){
 Report=str;
}
@Override
public String getReport(){
return Report;
}

BigDecimal getUnderTwoPi(BigDecimal in){
  if(in.compareTo(twoPI)<=0)
     return in;

BigDecimal prev = twoPI;
BigDecimal next = BigDecimal.TEN.multiply(twoPI);

   while(in.compareTo(next)>0){
         prev = next;
         next = BigDecimal.TEN.multiply(next);
    }

    if(in.compareTo(next) == 0 )
          return BigDecimal.ZERO;

BigDecimal base = prev;
     next = prev.add(prev);

     while(in.compareTo(next)>0){
         prev = next;
         next=next.add(base);
     }

      if(in.compareTo(next)==0)
        return BigDecimal.ZERO;

     return getUnderTwoPi(in.subtract(prev));




}


}
