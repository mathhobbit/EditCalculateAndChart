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
import java.math.MathContext;

public class CalCexp implements CalcFunction {
String Report="";
MathContext localMC;
static BigDecimal E = new BigDecimal("2.71828182845904523536028747135266249775724709369995957496696762772407663035354"+
"759457138217852516642742746639193200305992181741359662904357290033429526059563"+
"073813232862794349076323382988075319525101901157383418793070215408914993488416"+
"750924476146066808226480016847741185374234544243710753907774499206955170276183"+
"860626133138458300075204493382656029760673711320070932870912744374704723069697"+
"720931014169283681902551510865746377211125238978442505695369677078544996996794"+
"686445490598793163688923009879312773617821542499922957635148220826989519366803"+
"318252886939849646510582093923982948879332036250944311730123819706841614039701"+
"983767932068328237646480429531180232878250981945581530175671736133206981125099"+
"618188159304169035159888851934580727386673858942287922849989208680582574927961"+
"048419844436346324496848756023362482704197862320900216099023530436994184914631"+
"409343173814364054625315209618369088870701676839642437814059271456354906130310"+
"720851038375051011574770417189861068739696552126715468895703503540212340784981"+
"933432106817012100562788023519303322474501585390473041995777709350366041699732"+
"972508868769664035557071622684471625607988265178713419512466520103059212366771"+
"943252786753985589448969709640975459185695638023637016211204774272283648961342"+
"251644507818244235294863637214174023889344124796357437026375529444833799801612"+
"549227850925778256209262264832627793338656648162772516401910590049164499828931") ;

@Override
public String getHelp(){
return "exp(x) is the limit of the sequence {(1+x/n)^n} as n goes to infinity.";
}

@Override
public String eval(String argv) throws Exception{
  if(argv.indexOf('.')==-1  && argv.indexOf('/')==-1)
      return E.pow(Integer.parseInt(argv),jc.MC).toPlainString();
BigDecimal x;
if(argv.indexOf('/')!=-1)
 x = jc.getFraction(argv).toBigDecimal(jc.MC.getPrecision()); 
else
  x = new BigDecimal(argv,jc.MC); 

if(x.compareTo(BigDecimal.ZERO)==0)
     return "1";
int floor = 0;
double xDouble=x.doubleValue();
if(xDouble<0){
while(xDouble<floor)
     floor--;
}
else{
while(xDouble>floor)
     floor++;
if(xDouble<floor)
floor--;
}
BigDecimal eFloor = E.pow(floor,jc.MC);
if(xDouble == floor)
      return eFloor.toPlainString();

  int precision =jc.MC.getPrecision();  
   int precisionCorrection=(floor-floor%3)/3;
   precision=precision+precisionCorrection;
   localMC=new MathContext(precision,jc.MC.getRoundingMode());

String errorString="0.";
  for(int j = 1; j< precision ;j++)
      errorString=errorString+'0';
BigDecimal error = new BigDecimal(errorString+'0'+'1');

x=x.subtract(new BigDecimal(floor));
BigDecimal exp=BigDecimal.ONE.add(x,localMC); 
BigDecimal step=BigDecimal.ONE;
BigDecimal mistake=x;
BigDecimal abs_mistake=x.abs();
  while(abs_mistake.compareTo(error)>0){
    step=step.add(BigDecimal.ONE,localMC);
    mistake=mistake.multiply(x,localMC).divide(step,localMC);
    exp= exp.add(mistake,localMC); 
    abs_mistake=mistake.abs();
  } 
  return exp.multiply(eFloor,localMC).stripTrailingZeros().round(jc.MC).toPlainString();
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
