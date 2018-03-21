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
import org.ioblako.math.linearalgebra.Matrix;
import org.ioblako.math.linearalgebra.Fraction.Fraction;

//date
//8/12/16 2:08:16 PM





public class jc{
public static MathContext MC=MathContext.DECIMAL128;
public static boolean writeReport=false;
public static void setMathContext(MathContext newMC){
  MC=newMC;
}
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
"654252786255181841757467289097777279380008164706001614524919217321721477235014") ;





public static String Report="";

public static void main(String[] argv) throws Exception{
if(argv.length != 1){
System.out.println("jc String");
return;
}
String input = argv[0];
//System.out.println(processFunctions(input));

//System.out.println("parsing "+input);
if(input.startsWith("?")){
String name=input.substring(1);
if(name.indexOf('(')!=-1)
  name= name.substring(0,name.indexOf('('));
try{
CalcFunction fn=(CalcFunction) Class.forName("org.ioblako.math.calculator.CalC"+name).newInstance();
System.out.println(fn.getHelp());
}
catch(ClassNotFoundException | IllegalAccessException | InstantiationException e){
System.out.println("There is no help for \""+input.substring(1)+"\"");
}
return;
}
//System.out.println(getInside(argv[0],'{','}'));
System.out.println(eval(input)); 
}
public static String eval(String st) throws Exception{
CalcFunction fn;
String[] hndl;
String vntr, part1, part2, bf, inside;
//double db;
//BigDecimal Bdb;
Fraction fr;
st=st.trim();
st=st.replace(" ","");


//inserting random,date
if(st.contains("random")||
   st.contains("date")){
String[] insertThem = {"random","date"};
for(String op:insertThem){
fn=null;
if(st.contentEquals(op)){
   fn = (CalcFunction) Class.forName("org.ioblako.math.calculator.CalC"+op).newInstance();
   return fn.eval("");
}
if(st.startsWith(op)){
if(fn == null)
fn = (CalcFunction) Class.forName("org.ioblako.math.calculator.CalC"+op).newInstance();
  st=String.format("%s%s",fn.eval(""),st.substring(op.length()));
}


if(st.endsWith(op)){
if(fn == null)
fn = (CalcFunction) Class.forName("org.ioblako.math.calculator.CalC"+op).newInstance();
   st=String.format("%s%s",st.substring(0,st.lastIndexOf(op)),fn.eval(""));
}


while(st.contains(op)){
if(fn == null)
  fn = (CalcFunction) Class.forName("org.ioblako.math.calculator.CalC"+op).newInstance();
       st=String.format("%s%s%s",st.substring(0,st.indexOf(op)),fn.eval(""),st.substring(st.indexOf(op)+op.length()));
}
}
}

//check for special functions
if(st.trim().startsWith("lslv(")){
    fn = new CalClslv();
    st=fn.eval(jc.hInside("lslv(", st,'(',')')[1]);
    Report=fn.getReport();
    return st;
}
if(st.trim().startsWith("Seq(")){
    fn = new CalCSeq();
    st=fn.eval(jc.hInside("Seq(", st,'(',')')[1]);
    Report=fn.getReport();
    return st;
}
while(st.trim().contains("Int(")){
    fn = new CalCInt();
    hndl = jc.hInside("Int(", st,'(',')');
    st=hndl[0]+fn.eval(hndl[1])+hndl[2];
    Report=fn.getReport();
    //return st;
}
//done with special functions

while(st.contains("}^")){
  if(st.contains("}^{"))
    throw new Exception("\"}^{\" is not supported!");

 part1= st.substring(0,st.indexOf("}^"));
 String tmp = part1.substring(part1.lastIndexOf('{')+1);
  Matrix A = buildMatrix(tmp);
if(A.m() != A.n())
  throw new Exception("Cannot calculate power. Matrix is not square!");



 part1=part1.substring(0,part1.lastIndexOf('{'));

 part2 = st.substring(st.indexOf("}^")+2);

 tmp ="";
  if(part2.charAt(0) == '-'){
     tmp="-";
     part2=part2.substring(1);
  } 
  if(part2.charAt(0)=='('){
      tmp=tmp+'('+getInside(part2.substring(1),'(',')')+')';
  }
  else
 for(int i = 0; i<part2.length()&&
            part2.charAt(i)!='*'&&
            part2.charAt(i)!='-'&&
            part2.charAt(i)!='+'&&
   //         part2.charAt(i)!='/'&&
            part2.charAt(i)!='^'&&
            part2.charAt(i)!=','&&
            part2.charAt(i)!=';'&&
            part2.charAt(i)!='}'&&
            part2.charAt(i)!='{'&&
            part2.charAt(i)!='('&&
            part2.charAt(i)!=')' ;i++)
     tmp = tmp+part2.charAt(i);
     String old_tmp = tmp;
     tmp = eval(tmp);
  if(tmp.indexOf('/')!=-1 || tmp.indexOf('.')!=-1)
      throw new Exception("Cannot calculate a non-integer power of a matrix!");

   if(tmp.startsWith("-")){
      A = A.inverse();
      tmp=tmp.substring(1);
    }
  int Pw = Integer.valueOf(tmp);


   Matrix M=A;
  for(int i = 1; i< Pw;i++){
      M=M.multiply(A);
   }

st = eval(part1+toStr(M)+part2.substring(part2.indexOf(old_tmp)+old_tmp.length()));
}

while(st.indexOf('^')!=-1){
    part1 = st.substring(0,st.indexOf('^'));
    part2=st.substring(st.indexOf('^')+1);
    vntr="";
    if(part1.endsWith(")")){
        part1=part1.substring(0,part1.length()-1);
       int bracketIndicator=1;
       for(int i = part1.length()-1;i>=0;i--){
        char cc = part1.charAt(i);
           if(cc==')')
               bracketIndicator++;
           if(cc=='(')
               bracketIndicator--;
           if(bracketIndicator == 0)
               break;
           vntr=cc+vntr;
       }
       if(part1.length()<=vntr.length())
           part1="";
       else{
           part1=part1.substring(0,part1.length()-vntr.length());
           part1=(part1.endsWith("("))?part1.substring(0,part1.length()-1):part1;
       }
    }
    else{
    for(int i = part1.length()-1;i>=0;i--){
        char cc = part1.charAt(i);
        if(cc!='+'&&cc!='-'&&cc!='*'&&cc!='/'&&cc!='(')
            vntr=cc+vntr;
        else{
            if(cc=='-'&& i==0)
                vntr='-'+vntr;
            if(cc=='-'&& i>0)
                if(part1.charAt(i-1)=='+'||
                   part1.charAt(i-1)=='-'|| 
                   part1.charAt(i-1)=='*'||
                   part1.charAt(i-1)=='/'||
                   part1.charAt(i-1)=='(' 
                        )
                    vntr='-'+vntr;
            break;
        }
    }
       if(part1.length()<=vntr.length())
           part1="";
       else
           part1=part1.substring(0,part1.length()-vntr.length());
    }
   
    inside="";
    if(part2.startsWith("(")){
                     bf=part2.substring(1);
                     inside = getInside(bf);
                     part2=bf.substring(inside.length()+1);
     }
    else{
                           
                     if(part2.startsWith("+"))
                         part2=part2.substring(1);
                     
                   
                     if(part2.startsWith("-")){
                         part2=part2.substring(1);
                         inside="-";
                     }
                   char cc;  
                     for(int i = 0;i <part2.length();i++){
                         cc=part2.charAt(i);
                         if(cc!='+' && cc!='-'&& cc!='*'&&cc!=')'&& cc!='/')
                             inside=inside+cc;
                         else
                            i=part2.length();
                     }
                     if(part2.length()<=inside.length())
                         part2="";
                     else
                         part2=part2.substring(inside.length());
        
    }
    
    st = part1+    
           (new CalCpowl()).eval(eval(vntr)+","+eval(inside))+
          part2;
    
}



        while( st.contains("+-"))
            st=st.replace("+-","-");
        while( st.contains("-+"))
            st=st.replace("-+","-");
        while( st.contains("--"))
            st=st.replace("--","");
        while( st.contains("++"))
            st=st.replace("++","");
        while( st.contains("+*"))
            st=st.replace("+*","*");
        while( st.contains("*+"))
            st=st.replace("*+","*");
        while( st.contains("**"))
            st=st.replace("**","*");
        while( st.contains("/+"))
            st=st.replace("/+","/");

// bracket test
if(st.equalsIgnoreCase("pi")|| st.equalsIgnoreCase("+pi"))
    return PI.add(BigDecimal.ZERO,MC).toPlainString(); 

if(st.equalsIgnoreCase("e")||st.equalsIgnoreCase("+e"))
   return E.add(BigDecimal.ZERO,MC).toPlainString();


if(st.equalsIgnoreCase("-pi"))
    return PI.add(BigDecimal.ZERO,MC).negate().toPlainString(); 

if(st.equalsIgnoreCase("-e"))
   return E.negate().add(BigDecimal.ZERO,MC).toPlainString();

checkDelimiter(st,'(',')');
checkDelimiter(st,'{','}');


//functions start
st=processFunctions(st);
//functions end


while(st.indexOf('(')!=-1){

   part1= st.substring(0,st.indexOf('('));
   bf = st.substring(st.indexOf('(')+1);
   inside = getInside(bf);
   part2=bf.substring(inside.length()+1);
    bf = eval(inside);
    if(part1.endsWith("/")&&bf.contains("/"))
           st = part1.substring(0,part1.length()-1)+"*"
            + getFraction(bf).invert().toPlainString()+part2;
    else            
           st = part1+ bf + part2;
}

if(st.indexOf('{')!=-1){
if(st.contains("{}"))
   throw new Exception("Erroneous matrix \"{}\"!");
hndl=hInside("{",st,'{','}');
//String rt=st.substring(0,st.indexOf('{')+1);
String rt="";
//String tmp=st.substring(st.indexOf('{')+1);
String tmp=hndl[1];
String[] els = tmp.split("[ ,;{}]");
//System.out.println(hndl[1]);
for(String bb:els){
       rt=String.format("%s%s%s",rt,tmp.substring(0,tmp.indexOf(bb)),eval(bb));
       tmp=tmp.substring(tmp.indexOf(bb)+bb.length());
}
       rt=String.format("%s%s",rt,tmp);
//System.out.println(rt);
st=hndl[0]+
   "{"+rt+"}"+
   hndl[2];
}

if(st.startsWith("{") && st.endsWith("}")){
    if(st.length() ==2)
       throw new Exception("Empty matrix \"{}\" is not supported!");
  if(st.substring(1).indexOf('{') <0)
     return st;
}



String mA;
while(st.contains("}+{")){
 mA=st.substring(0,st.indexOf("}+{"));
 part1 = mA.substring(0,mA.lastIndexOf('{'));
 mA = mA.substring(mA.lastIndexOf('{')+1);

 Matrix A = buildMatrix(mA);
 mA=st.substring(st.indexOf("}+{")+3);
 part2 = mA.substring(mA.indexOf('}')+1);
 mA=mA.substring(0,mA.indexOf('}'));

 Matrix B=buildMatrix(mA);
 st=part1+toStr(A.add(B))+part2;
}
while(st.contains("}-{")){
 mA=st.substring(0,st.indexOf("}-{"));
 part1 = mA.substring(0,mA.lastIndexOf('{'));
 mA = mA.substring(mA.lastIndexOf('{')+1);

 Matrix A = buildMatrix(mA);
 mA=st.substring(st.indexOf("}-{")+3);
 part2 = mA.substring(mA.indexOf('}')+1);
 mA=mA.substring(0,mA.indexOf('}'));

 Matrix B=buildMatrix(mA);
 st=part1+toStr(A.subtract(B))+part2;
}
while(st.contains("}*{")){
 mA=st.substring(0,st.indexOf("}*{"));
 part1 = mA.substring(0,mA.lastIndexOf('{'));
 mA = mA.substring(mA.lastIndexOf('{')+1);

 Matrix A = buildMatrix(mA);
 mA=st.substring(st.indexOf("}*{")+3);
 part2 = mA.substring(mA.indexOf('}')+1);
 mA=mA.substring(0,mA.indexOf('}'));

 Matrix B=buildMatrix(mA);
 st=part1+toStr(A.multiply(B))+part2;
}

//while(st.indexOf('*')== st.indexOf("}*")+1){
while(st.contains("}*")){
  part2=st.substring(st.indexOf("}*")+2);
  if(part2.startsWith("(")){
   hndl = hInside("}*(",st);
     fr=new Fraction(hndl[1]);
     part2=hndl[2];
   }
else{
  
 String tmp ="";
  if(part2.charAt(0) == '-'){
     tmp="-";
     part2=part2.substring(1);
  } 
 for(int i = 0; i<part2.length()&&
            part2.charAt(i)!='*'&&
            part2.charAt(i)!='-'&&
            part2.charAt(i)!='+'&&
   //         part2.charAt(i)!='/'&&
            part2.charAt(i)!='^'&&
            part2.charAt(i)!=','&&
            part2.charAt(i)!=';'&&
            part2.charAt(i)!='}'&&
            part2.charAt(i)!='{'&&
            part2.charAt(i)!='('&&
            part2.charAt(i)!=')' ;i++)
     tmp = tmp+part2.charAt(i);

  if(tmp.contentEquals("")||tmp.contentEquals("-"))
     throw new Exception("Incomplete formula!");
  fr = new Fraction(tmp);
  part2=part2.substring(part2.indexOf(tmp)+tmp.length());
}
     

  part1=st.substring(0,st.indexOf("}*"));
  String tmp=part1.substring(part1.lastIndexOf('{')+1);
  String rt = ""; 
   while(tmp.indexOf('}')!= -1){
         rt='{'+tmp+rt;  
         part1=part1.substring(0,part1.lastIndexOf('{')); 
         tmp=part1.substring(part1.lastIndexOf('{')+1);
    }
   
   part1=part1.substring(0,part1.lastIndexOf('{')); 
   rt = tmp+rt;
String[] els = rt.split("[ ,;{}]");
tmp=rt;
rt="";
//System.out.println(hndl[1]);
for(String bb:els){
       if(!bb.contentEquals(""))
                   rt=String.format("%s%s%s",rt,tmp.substring(0,tmp.indexOf(bb)),
                         (new Fraction(bb)).multiply(fr).toPlainString());
                         tmp=tmp.substring(tmp.indexOf(bb)+bb.length());
}
       rt=String.format("%s%s",rt,tmp);

//System.out.println(part1+'{'+rt+'}'+part2);
      st = eval(part1+'{'+rt+'}'+part2);
}

while(st.lastIndexOf("*{")!=-1){ 
   //   st.lastIndexOf('*')== st.lastIndexOf("*{")){
   hndl=hInside("*{",st,'{','}');
String tmp;
if(hndl[0].endsWith(")")){
   tmp=hndl[0].substring(hndl[0].lastIndexOf("(")+1,hndl[0].length()-1);
fr = new Fraction(tmp);
part1=hndl[0].substring(0,hndl[0].lastIndexOf("(")+1);
}
else{
tmp="";
int i;
for(i = hndl[0].length()-1;i>=0 &&
            hndl[0].charAt(i)!='*'&&
            hndl[0].charAt(i)!='-'&&
            hndl[0].charAt(i)!='+'&&
    //        hndl[0].charAt(i)!='/'&&
            hndl[0].charAt(i)!='^'&&
            hndl[0].charAt(i)!=','&&
            hndl[0].charAt(i)!=';'&&
            hndl[0].charAt(i)!='}'&&
            hndl[0].charAt(i)!='{'&&
            hndl[0].charAt(i)!='('&&
            hndl[0].charAt(i)!=')' ;i--)
     tmp=hndl[0].charAt(i)+tmp;
fr = new Fraction(tmp);
if(i==0&&hndl[0].charAt(i)=='-'){
 fr=fr.negate();
 part1="";
}
else
if(i>=0)
part1=hndl[0].substring(0,i+1);
else
 part1="";
}
String rt="";
tmp=hndl[1];
String[] els = tmp.split("[ ,;{}]");
for(String bb:els){
  if(!bb.contentEquals(""))
       rt=String.format("%s%s%s",rt,tmp.substring(0,tmp.indexOf(bb)),
                       (new Fraction(bb)).multiply(fr).toPlainString());
                       tmp=tmp.substring(tmp.indexOf(bb)+bb.length());
}
       rt=String.format("%s%s",rt,tmp);
st=eval(part1+
   "{"+rt+"}"+
   hndl[2]);
//System.out.println(st);
}

if(st.lastIndexOf('+') > 0)
  if(st.lastIndexOf('+') != st.lastIndexOf("E+")+1 &&
     st.lastIndexOf('+') != st.lastIndexOf("^+")+1 &&
     st.lastIndexOf('+') != st.lastIndexOf(",+")+1 &&
     st.lastIndexOf('+') != st.lastIndexOf(";+")+1 &&
     st.lastIndexOf('+') != st.lastIndexOf("{+")+1 &&
     st.lastIndexOf('+') != st.lastIndexOf("}+")+1 &&
     st.lastIndexOf('+') != st.lastIndexOf("+{")){
    bf = eval(st.substring(0,st.lastIndexOf('+')));
    vntr = eval(st.substring(st.lastIndexOf('+')+1));
  // if(bf.indexOf('/')==-1 && vntr.indexOf('/')==-1)
 //   Bdb=(new BigDecimal(bf,MC)).add(new BigDecimal(vntr,MC),MC);
//   else
   if(bf.indexOf('/')==-1 && vntr.indexOf('/')==-1)
     return (new BigDecimal(bf,jc.MC)).add(new BigDecimal(vntr,jc.MC)).stripTrailingZeros().toPlainString();
   else
     return getFraction(bf).add(getFraction(vntr)).toPlainString();
//return Bdb.toPlainString();
}

while(st.indexOf("*-")>0 ||st.indexOf("/-")>0 ){
  if(st.indexOf("*-")>0){ 
    hndl=resolve(st,"*-");
//System.out.println("hndl[0] = "+hndl[0]);
//System.out.println("hndl[1] = "+hndl[1]);
//System.out.println("hndl[2] = "+hndl[2]);
//System.out.println("hndl[3] = "+hndl[3]);
      bf=eval(hndl[1]);
      vntr=eval(hndl[2]);
    //bf = eval(st.substring(0,st.indexOf("*-")));
   //vntr = eval(st.substring(st.indexOf("*-")+2));
 // if(bf.indexOf('/')==-1 && vntr.indexOf('/')==-1)
 //   Bdb=(new BigDecimal(bf,jc.MC)).multiply(new BigDecimal(vntr,jc.MC),jc.MC).negate();
  if(bf.indexOf('/')==-1 && vntr.indexOf('/')==-1)
    st=hndl[0]+(new BigDecimal(bf,jc.MC)).multiply(new BigDecimal(vntr,jc.MC)).negate().stripTrailingZeros().toPlainString()+hndl[3]; 
  else
    st=hndl[0]+getFraction(bf).multiply(getFraction(vntr)).negate().toPlainString()+hndl[3]; 
//        return Bdb.toPlainString();
}

if(st.indexOf("/-")>0){
    hndl=resolve(st,"/-");
      bf=eval(hndl[1]);
      vntr=eval(hndl[2]);
    //bf = eval(st.substring(0,st.indexOf("/-")));
    //vntr = eval(st.substring(st.indexOf("/-")+2));
if(bf.indexOf('/')==-1 && vntr.indexOf('/') == -1){
    BigDecimal denominator = new BigDecimal(vntr,jc.MC);
if(bf.indexOf('.') == -1 && vntr.indexOf('.')==-1)
    st= hndl[0]+(new Fraction(new BigInteger(vntr),new BigInteger(bf))).negate().toPlainString()+hndl[3];
else
  st=hndl[0]+getFraction(bf).divide(getFraction(vntr)).negate().toBigDecimal(MC.getPrecision()).stripTrailingZeros().toPlainString()+hndl[3];
}
else
  st=hndl[0]+getFraction(bf).divide(getFraction(vntr)).negate().toPlainString()+hndl[3];
}
}


    while(st.contains("--"))
      st=eval(st.replace("--","+"));
if(st.lastIndexOf('-')>0)
  if(st.lastIndexOf('-') != st.lastIndexOf("E-")+1 &&
     st.lastIndexOf('-') != st.lastIndexOf("^-")+1 &&
     st.lastIndexOf('-') != st.lastIndexOf(",-")+1 &&
     st.lastIndexOf('-') != st.lastIndexOf(";-")+1 &&
     st.lastIndexOf('-') != st.lastIndexOf("{-")+1){
    bf = eval(st.substring(0,st.lastIndexOf('-')).trim());
   vntr =eval(st.substring(st.lastIndexOf('-')+1));
//System.out.println("bf = "+bf);
//System.out.println("vntr = "+vntr);
//    bf = eval(bf);
//   vntr = eval(vntr);
   if(vntr.trim().contentEquals(""))
        vntr="0";
 if(bf.trim().contentEquals(""))
        bf="0";
//  if(bf.indexOf('/')==-1&& vntr.indexOf('/')==-1)
//    Bdb=(new BigDecimal(bf,jc.MC)).subtract(new BigDecimal(vntr,jc.MC),jc.MC);
//  else
   
  if(bf.indexOf('/')==-1&& vntr.indexOf('/')==-1)
    return getFraction(bf).subtract(getFraction(vntr)).toBigDecimal(MC.getPrecision()).stripTrailingZeros().toPlainString(); 
  else
    return getFraction(bf).subtract(getFraction(vntr)).toPlainString(); 
  //  return Bdb.toPlainString();
}

if(st.indexOf('*')!= -1 &&
   st.indexOf('*')!= st.indexOf("}*")+1 &&
   st.indexOf('*')!= st.indexOf("*{")){
      bf = eval(st.substring(0,st.indexOf('*')));
      vntr = eval(st.substring(st.indexOf('*')+1));

//  if(bf.indexOf('/')==-1&& vntr.indexOf('/')==-1)
//    Bdb=(new BigDecimal(bf,MC)).multiply(new BigDecimal(vntr,MC),MC);
//  else

  if(bf.indexOf('/')==-1&& vntr.indexOf('/')==-1)
    return getFraction(bf).multiply(getFraction(vntr)).toBigDecimal(MC.getPrecision()).stripTrailingZeros().toPlainString();
     else
    return getFraction(bf).multiply(getFraction(vntr)).toPlainString();

//return Bdb.toPlainString();
}
/*
if(st.indexOf('^')!= -1 &&
   st.indexOf('^')!= st.indexOf("}^")+1 &&
   st.indexOf('^')!= st.indexOf("^{")){
    bf = eval(st.substring(0,st.indexOf('^')));
    vntr = eval(st.substring(st.indexOf('^')+1));
return (new CalCpowl()).eval(bf+","+vntr);
}
*/


if(st.indexOf('/')!= -1 && st.indexOf('{')== -1 && st.indexOf('}')==-1 ){
    
    
      bf= eval(st.substring(0,st.lastIndexOf('/')));
      vntr = eval(st.substring(st.lastIndexOf('/')+1));
      
      
if(bf.indexOf('/') == -1 && vntr.indexOf('/')==-1){

BigDecimal denominator=new BigDecimal(vntr,jc.MC);
if(denominator.compareTo(BigDecimal.ZERO)==0 )
   throw new Exception("Cannot devide by\"0\"!!!");
if(bf.indexOf('.') == -1 &&
   vntr.indexOf('.') == -1)
    return(new Fraction(new BigInteger(vntr),new BigInteger(bf))).toPlainString();
//return (new BigDecimal(bf,jc.MC)).divide(denominator,jc.MC).toPlainString();
 return getFraction(bf).divide(getFraction(vntr)).toBigDecimal(jc.MC).stripTrailingZeros().toPlainString();
}
else
 return getFraction(bf).divide(getFraction(vntr)).toPlainString();
}

while(st.startsWith("+")&& st.length()>1)
  st=st.substring(1);
    return st;
}
/**
* getInside takes as an argument something like "1+sqrt(1+sqrt(2)))" and uses the unbalanced delimiter bracket ")" to return 1+sqrt(1+sqrt(2))
*/
private static String getInside(String st) throws Exception{
int delimitterBalance=0;
String ret="";
char A;
for(int i = 0;i<st.length();i++){
  A=st.charAt(i);
  if(A == '(')
       delimitterBalance++;
  if(A == ')')
       delimitterBalance--;
   if(delimitterBalance < 0)
     break;
   ret=ret+A;
}
if(delimitterBalance >=0)
throw new Exception("Unacceptable format: "+st);

       return ret;
}
/**
* getInside takes as an argument something like "1+sqrt d1 1+sqrt d1 2 d2 d2 d2" and uses the unbalanced delimiter "d2"  to return 1+sqrt d1 1+sqrt d1 2 d2 d2
     * @param st
     * @param d1
     * @param d2
     * @return 
     * @throws java.lang.Exception
*/
public static String getInside(String st, char d1, char d2)throws Exception{
int delimitterBalance=0;
String ret="";
char A;
for(int i = 0;i<st.length();i++){
  A=st.charAt(i);
  if(A == d1)
       delimitterBalance++;
  if(A == d2)
       delimitterBalance--;
   if(delimitterBalance < 0)
     break;
   ret=ret+A;
}
if(delimitterBalance >=0)
throw new Exception("Unacceptable format: "+st);

     return ret;
}
/**
* hInside return the part of string bfore "func(" the inside "argv"  for "finc(argv)" and the part of the string after "func(argv)" 
* as String[] ret, ret[0], ret[1] and ret[2] respectively.
     * @param fnct
     * @param st
     * @return 
     * @throws java.lang.Exception
*/
public static String[] hInside(String fnct,String st)throws Exception{
String[] ret = new String[3];
ret[0]= st.substring(0,st.indexOf(fnct));
String bf = st.substring(st.indexOf(fnct)+fnct.length());
ret[1] = getInside(bf);
if(bf.length()>ret[1].length()+1)
ret[2]=bf.substring(ret[1].length()+1);
else
ret[2]="";
  return ret;
}

/**
* hInside return the part of string bfore "func d1" the inside "argv"  for "finc d1 argv d2" and the part of the string after "func d1 argv d2" 
* as String[] ret, ret[0], ret[1] and ret[2] respectively.
     * @param fnct
     * @param st
     * @param d1
     * @param d2
     * @return 
     * @throws java.lang.Exception 
*/
public static String[] hInside(String fnct,String st,char d1, char d2) throws Exception{
String[] ret = new String[3];
ret[0]= st.substring(0,st.indexOf(fnct));
String bf = st.substring(st.indexOf(fnct)+fnct.length());
ret[1] = getInside(bf,d1,d2);
if(bf.length()>ret[1].length()+1)
ret[2]=bf.substring(ret[1].length()+1);
else
ret[2]="";
     return ret;
}


public static void checkDelimiter(String st, char d1, char d2) throws Exception{
if(st.indexOf(d1) != -1 || st.indexOf(d2) != -1){

int brackets_test=0;

for(int i = 0; i<st.length();i++){
if(st.charAt(i) == d1)
   brackets_test++;
else
if(st.charAt(i) == d2)
   brackets_test--;
}
if(brackets_test != 0)
throw new Exception(String.format("%s%c %c%s","Please close prentices ",d1,d2," and try again!"));
}


}

public static Matrix buildMatrix(String str) throws Exception{
if(str.indexOf('{') != -1  || str.indexOf('}') != -1)
 throw new Exception("Nested matrices are not supported!");
String[] Rows = str.split(";");
String[] Clm = Rows[0].split(",");
int N = Clm.length;
int M=Rows.length;

Fraction[][] data = new Fraction[M][N];
for(int j =0; j< N;j++)
   data[0][j] = new Fraction(Clm[j]);
    
for(int i = 1;i<M;i++){
     Clm = Rows[i].split(",");
    if(Clm.length!= N)
       throw new Exception("Erroneous matrix?!");
    for(int j =0; j< N;j++)
        data[i][j] = new Fraction(Clm[j]);
}

return new Matrix(data);    

}
public static String toStr(Matrix A){
 String rt="";

for(int i =0; i<A.m();i++){
    for(int j =0; j<A.n();j++){
        if(j==0)
        rt=rt+A.get(i,j).toPlainString();
       else
        rt=rt+","+A.get(i,j).toPlainString();
    }
   if(i != A.m()-1)
     rt=rt+";";
}
return "{"+rt+"}";
}
public static Matrix StringToMatrix(String argv) throws Exception{
Fraction data[][];
String[] Rows =argv.split(";");

int M = Rows.length;
String[] tmp = Rows[0].split(",");
int N =tmp.length;


data = new Fraction[M][N];

for (int j = 0; j < tmp.length; j++)
                data[0][j] = new Fraction(jc.eval(tmp[j].trim()));


for(int i  = 1; i < Rows.length; i++)
        {
            tmp = Rows[i].split(",");
               if(tmp.length != N)
                   throw new Exception("Erroneous matrix!?");
            for (int j = 0; j < tmp.length; j++)
                data[i][j] = new Fraction(jc.eval(tmp[j].trim()));
        }


  return new Matrix(data);

}
public static Fraction getFraction(String st) throws Exception{
//double db;
  if(st.indexOf('/') == -1){
     return new Fraction(st);
  }
String  bf = eval(st.substring(0,st.indexOf('/')));
String vntr= eval(st.substring(st.indexOf('/')+1));
   if(bf.indexOf('/')==-1 && vntr.indexOf('/')==-1){
BigDecimal denominator=new BigDecimal(vntr);
if(denominator.compareTo(BigDecimal.ZERO)==0)
   throw new Exception("Cannot devide by\"0\"!!!");
if(bf.indexOf('.')==-1 && vntr.indexOf('.')==-1)
    return new Fraction(new BigInteger(vntr),new BigInteger(bf) );
else
 return getFraction(bf).divide(getFraction(vntr));
}
else
  return getFraction(bf).divide(getFraction(vntr)); 
}
public static String[] resolve(String st, String op) throws Exception{
String[] ret = new String[4];
if(st.startsWith(op) || st.endsWith(op))
   throw new Exception("Incomplete formula");

ret[0] = st.substring(0,st.indexOf(op));
ret[1]="";
ret[2]="";
ret[3] = st.substring(st.indexOf(op)+op.length());
int i;
for(i = ret[0].length()-1;i>=0 && isInteger(""+ret[0].charAt(i));i--)
     ret[1]=ret[0].charAt(i)+ret[1];
if(i<0)
   ret[0]="";
else
   ret[0]=ret[0].substring(0,i+1);

for(i = 0; i < ret[3].length()&&isInteger(""+ret[3].charAt(i));i++)
      ret[2]=ret[2]+ret[3].charAt(i);

if(i == ret[3].length())
   ret[3]="";
else
   ret[3]=ret[3].substring(i);

return ret;
}

public static boolean isInteger(String st){
if(st.contentEquals("."))
    return true;
try{
Integer.parseInt(st);
}
catch(NumberFormatException e){
return false;
}
return true;
}

public static String processFunctions(String input)throws Exception{

String a = "";
String function="", bf,ins, reportIt="",ret;
int i = 0;
CalcFunction fn;
for(char c: input.toCharArray()){
      i++;
      if(c=='+'||c=='-'||c=='*'||c=='/'||c=='('||c==')'||c=='{'||c=='}'||
         c==','||c==';'||c=='^'||c=='='){
          if(!function.contentEquals("")){
               //System.out.println("function ="+function);
              //constants
                  //PI
              if(function.equalsIgnoreCase("pi")|| function.equalsIgnoreCase("+pi")){
                     bf = input.substring(i-1);
                   
               if(!bf.trim().contentEquals(""))
                     return a+PI.add(BigDecimal.ZERO,MC).toPlainString()+processFunctions(bf); 
               else
                     return a+PI.add(BigDecimal.ZERO,MC).toPlainString(); 
                 }
                 //-PI
              if(function.equalsIgnoreCase("-pi")){
                     bf = input.substring(i-1);
                   
               if(!bf.trim().contentEquals(""))
                     return a+PI.add(BigDecimal.ZERO,MC).negate().toPlainString()+processFunctions(bf); 
               else
                     return a+PI.add(BigDecimal.ZERO,MC).negate().toPlainString(); 
                 }
                 //E
              if(function.equalsIgnoreCase("e")||function.equalsIgnoreCase("+e")){
                     bf = input.substring(i-1);
               if(!bf.trim().contentEquals(""))
                            return a+E.add(BigDecimal.ZERO,MC).toPlainString()+processFunctions(bf);
                else
                            return a+E.add(BigDecimal.ZERO,MC).toPlainString();
                 }
                 //-E
              if(function.equalsIgnoreCase("-e")){
                     bf = input.substring(i-1);
               if(!bf.trim().contentEquals(""))
                            return a+E.add(BigDecimal.ZERO,MC).negate().toPlainString()+processFunctions(bf);
                else
                            return a+E.add(BigDecimal.ZERO,MC).negate().toPlainString();
                 }
              //done with constants
               fn = (CalcFunction) Class.forName("org.ioblako.math.calculator.CalC"+function).newInstance();
               
               //System.out.println("a ="+a);
               bf = input.substring(i);
               ins = getInside(bf,'(',')');               
               if(bf.length()>ins.length()+1)
               bf=bf.substring(ins.length()+1);
               else
               bf="";
                if(!bf.contentEquals("")){
                    ret=fn.eval(jc.eval(ins));
                    ReportIt(fn,function,jc.eval(ins),fn.getReport());
                      return a+ret+processFunctions(bf);
                }
               else{
                    ret=fn.eval(jc.eval(ins));
                    ReportIt(fn,function,jc.eval(ins),fn.getReport());
                    return a+ ret;
                }
               //System.out.println("rest = "+ input.substring(i));
               //a+fn.eval(getIsude(input.substring(i)))
               //function="";
           }
          else
            a=a+c;
          continue;
      }
      if(isInteger(""+c)){
          if(!function.contentEquals("")){
                  function=function+c;
                  continue;
             }
           else 
           a=a+c;
          continue;
      }
     if(function.contentEquals("")){
            function=""+c;
            while(a.length()>0 && isInteger(""+a.charAt(a.length()-1))){
              function=a.charAt(a.length()-1)+function;
              a=a.substring(0,a.length()-1);
            }
       }
     else
       function=function+c;
}
return input;
}
public static void ReportIt(CalcFunction fn,String function, String ins,String reportIt){
        if(!reportIt.contentEquals("")){
             Report=String.format("%s%s%s%s%s%s%s%s",Report,System.lineSeparator(),function,"(",ins,")",System.lineSeparator(),reportIt);
             fn.setReport("");
           }
}
public static int compare(String one,String theOther)throws Exception{
     one=jc.eval(one.trim());
     theOther = jc.eval(theOther.trim());
     
     BigDecimal theOne=(one.indexOf('/')!=-1)?(new Fraction(one)).toBigDecimal(jc.MC):(new BigDecimal(one,jc.MC));
     BigDecimal OneMore=(theOther.indexOf('/')!=-1)?(new Fraction(theOther)).toBigDecimal(jc.MC):(new BigDecimal(theOther,jc.MC));
     
     return theOne.compareTo(OneMore);
     
 }
}
