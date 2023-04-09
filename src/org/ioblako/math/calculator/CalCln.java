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
import java.math.MathContext;
import org.ioblako.math.linearalgebra.Fraction;

public class CalCln implements CalcFunction {
String Report="";
public String getHelp(){
return "ln(x) is the inverse function for exp(x)."; 
}
BigInteger tWO=BigInteger.ONE.add(BigInteger.ONE);
BigDecimal TWO=BigDecimal.ONE.add(BigDecimal.ONE,jc.MC);
MathContext localMC=jc.MC;
static final BigDecimal LN2=new BigDecimal("0.693147180559945309417232121458176568075"+
"50013436025525412068000949339362196969471560586332699641868754200148102057068573"+
"368552023575813055703267075163507596193072757082837143519030703862389167347112335"+
"011536449795523912047517268157493206515552473413952588295045300709532636664265410"+
"423915781495204374043038550080194417064167151864471283996817178454695702627163106"+
"454615025720740248163777338963855069526066834113727387372292895649354702576265209"+
"885969320196505855476470330679365443254763274495125040606943814710468994650622016"+
"772042452452961268794654619316517468139267250410380254625965686914419287160829380"+
"317271436778265487756648508567407764845146443994046142260319309673540257444607030"+
"809608504748663852313818167675143866747664789088143714198549423151997354880375165"+
"861275352916610007105355824987941472950929311389715599820565439287170007218085761"+
"025236889213244971389320378439353088774825970171559107088236836275898425891853530"+
"243634214367061189236789192372314672321720534016492568727477823445353476481149418"+
"642386776774406069562657379600867076257199184734022651462837904883062033061144630"+
"073719489002743643965002580936519443041191150608094879306786515887090060520346842"+
"973619384128965255653968602219412292420757432175748909770675268711581705113700915"+
"894266547859596489065305846025866838294002283300538207400567705304678700184162404"+
"418833232798386349001563121889560650553151272199398332030751408426091479001265168"+
"243443893572472788205486271552741877243002489794540196187233980860831664811490930"+
"667519339312890431641370681397776498176974868903887789991296503619270710889264105"+
"230924783917373501229842420499568935992206602204654941510613");

BigDecimal error;
public String eval(String argv) throws Exception{
argv=jc.eval(argv);
if(argv.startsWith("-"))
 throw new Exception("ln(x) is not defined for x<0!");
BigDecimal x;
if(argv.indexOf('/')==-1)
 x = new BigDecimal(argv);
else{
Fraction fr = new Fraction(argv);
 if(fr.isNegative())
 throw new Exception("ln(x) is not defined for x<0!");
 x = fr.toBigDecimal(jc.MC.getPrecision());
}
int shift=0;
argv=x.toPlainString();
if(argv.startsWith("0.0")){
  argv=argv.substring(2);
  for(shift=0;shift < argv.length()&&argv.startsWith("0");shift++)
    argv=argv.substring(1);
       
if(argv.length()==0)
 throw new Exception("ln(x) is not defined for x=0!");
argv="0."+argv; 
x=new BigDecimal(argv);
}


int precision = jc.MC.getPrecision();
String errorString="0.";
  for(int j = 1; j< precision ;j++)
      errorString=errorString+'0';
error = new BigDecimal(errorString+'0'+'1');


if(x.compareTo(error)<=0)
 throw new Exception("ln(x) is not defined for x=0!");

if(x.subtract(TWO).abs().compareTo(error)<=0){   
 if(shift!=0)
  return (new BigDecimal("-"+Integer.toString(shift))).multiply(new BigDecimal((new CalCln()).eval("10"))).add(LN2,jc.MC).toPlainString(); 
  else
  return LN2.add(BigDecimal.ZERO,jc.MC).toPlainString();
}

if(x.subtract(jc.E).abs().compareTo(error)<=0)   
  return "1";


if(x.compareTo(TWO.subtract(new BigDecimal(0.2)))<0){
 if(shift!=0)
  return (new BigDecimal("-"+Integer.toString(shift))).multiply(new BigDecimal((new CalCln()).eval("10"))).add(ln(x.subtract(BigDecimal.ONE),error),jc.MC).toPlainString();
  else
  return ln(x.subtract(BigDecimal.ONE),error).add(BigDecimal.ZERO,jc.MC).toPlainString();
}
if(x.compareTo(jc.E)<0)
  return BigDecimal.ONE.add(ln(x.divide(jc.E,jc.MC).subtract(BigDecimal.ONE),error),jc.MC).toPlainString();


BigDecimal floor = jc.E;

int i=1;
while(x.compareTo(floor)>0){
      i++;
      floor=floor.multiply(jc.E,jc.MC);
}

if(x.subtract(floor).abs().compareTo(error)<=0)
   return Integer.toString(i);
//floor=floor.subtract(BigDecimal.ONE);
return (new BigDecimal(i)).add(ln(x.divide(floor,jc.MC).subtract(BigDecimal.ONE,jc.MC),error)).toPlainString(); 

}
BigDecimal lBin(String bin) throws Exception{
if(bin.contentEquals("1"))
  return BigDecimal.ZERO;
if(bin.startsWith("0"))
   throw new Exception("Erroneous binary string input!");
if(bin.length()>1&& bin.substring(1).indexOf('1')==-1)
  return LN2.multiply(new BigDecimal(bin.length()-1));
BigDecimal mltp = BigDecimal.ZERO;
if(bin.endsWith("0")){
while(bin.endsWith("0")){
   bin= bin.substring(0,bin.length()-1);
   mltp=mltp.add(BigDecimal.ONE);
}
return LN2.multiply(mltp,localMC).add(lBin(bin),localMC);
}

mltp = BigDecimal.ZERO;
bin=bin.substring(0,bin.length()-1);

for(int i = 0;i<bin.length();i++){
   mltp=mltp.multiply(TWO);
  if(bin.charAt(i)=='1')
    mltp=mltp.add(BigDecimal.ONE,localMC);
}
mltp=mltp.multiply(TWO,localMC);  
return LN2.add(lBin(bin),localMC).add(ln(BigDecimal.ONE.divide(mltp,localMC),error),localMC);
}

String getBinary(BigDecimal fl) throws Exception{
BigInteger fi = fl.toBigInteger();
BigInteger rm;
String response="";
while(fi.compareTo(tWO)>=0){
 rm=fi.remainder(tWO);
 if(rm.compareTo(BigInteger.ZERO)==0)
    response='0'+response;
 else
    response='1'+response;
 fi=fi.subtract(rm).divide(tWO);
} 
int precisionCorrection=(response.length()-response.length()%10)/10;
localMC=new MathContext(jc.MC.getPrecision()+precisionCorrection,jc.MC.getRoundingMode());
return fi.toString()+response;
}



BigDecimal ln(BigDecimal z, BigDecimal error){
BigDecimal logz=z;
BigDecimal step=BigDecimal.ONE;
BigDecimal mistake=z;
BigDecimal abs_mistake=z.abs();
  while(abs_mistake.compareTo(error)>0){
    step=step.add(BigDecimal.ONE);
    mistake=mistake.multiply(z).negate();
    logz= logz.add(mistake.divide(step,localMC),localMC);
    abs_mistake=mistake.divide(step,localMC).abs();
  }
return logz;
}
public void setReport(String str){
 Report=str;
}
public String getReport(){
return Report;
}

}
