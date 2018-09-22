/*
 * Copyright (C) 2018 Sergey Nikitin
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
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Sergey Nikitin
 */
public class CalCEF implements CalcFunction{
    String Report = "";
    @Override
    public String getReport() {
       return Report;
    }

    @Override
    public void setReport(String str) {
         Report=(Report.compareTo("")==0)?str:Report+System.lineSeparator()+str;
    }

    @Override
    public String getHelp() {
        return "EF(r,n) returns the integer s = s(r,n). For r < n EF calculates integers k, s and q such that  n * q = r^k*(r^s - 1). "+System.lineSeparator()
                + " If r = n then s(r,n)=1. For r > n EF returns s(r mod n, n). EF stays for Euler-Fermat algorithm related"+System.lineSeparator()
                +"to statement c^E(a)=1 mod(a) for coprime a and c, "+System.lineSeparator()
                + "where E(a) is the Euler function:"+System.lineSeparator()
                + " the count of numbers coprime with and smaller than a."+System.lineSeparator()
                +"If r and n are coprime then EF returns the length of the orbit of \"r\" in the multiplicative group of the ring Z/(n*Z)."+System.lineSeparator()
                +" See also function FE which is equal to EF(2,n).";
    }

    @Override
    public String eval(String argv) throws Exception {
        if(argv.indexOf(',')==-1)
             throw new Exception("EF(r,n) requires two natural number arguments.");
        String[] args = argv.split(",");
        if(args.length!=2)
               throw new Exception("EF(r,n) requires two natural number arguments.");
        
        String radix = jc.eval(args[0]);
        String input_number = jc.eval(args[1]); 
        
        BigInteger m = new BigInteger(input_number), r = new BigInteger(radix);
        
        
        if(m.compareTo(BigInteger.ZERO)<=0 ||
             r.compareTo(BigInteger.ONE)<=0)
               throw new Exception("Euler-Fermat algorithm is defined only for natural numbers. Moreover, radix is to be larger than one");
        if(r.compareTo(BigInteger.ONE.add(BigInteger.ONE)) == 0){
                     CalCFE fe = new CalCFE();
                     String rt = jc.eval(fe.eval(input_number));
                     setReport(fe.getReport());
                     return rt;
        }

BigInteger n = BigInteger.ONE, k = BigInteger.ZERO, s = BigInteger.ONE;
BigInteger d = m.gcd(r);
while(d.compareTo(BigInteger.ONE)>0 && m.compareTo(BigInteger.ONE)>0){
m = m.divide(d);
n = n.multiply(r.divide(d));
k = k.add(BigInteger.ONE);
d = m.gcd(r);
}

if(m.compareTo(BigInteger.ONE)==0){
    if(n.compareTo(BigInteger.ONE)==0)
        setReport("r^"+k.toString()+" = "+input_number);
    else
     setReport("r^"+k.toString()+" = "+input_number+"*"+n.toString());
    
     setReport("s("+r.toString()+","+input_number+") = 1");
      setReport("where r = "+r.toString());
                                return "1";
}

int c;
BigInteger radixMinusOne = r.subtract(BigInteger.ONE),bf;
String output;

if(getTheFirstIndexForValueLessThan(m,radixMinusOne,r)==-1){
if(k.compareTo(BigInteger.ZERO)>0)
     output = input_number+"*"+n.toString()+" = "+r.toString()+"^"+k.toString()+"*(r^"+getLength(m,r)+"-1)";
else
     output = input_number+"*"+n.toString()+" = r^"+getLength(m,r)+"-1";

     setReport(output);
     setReport("where r = "+r.toString());
                     
                     return Integer.toString(getLength(m,r));
}


ArrayList<BigInteger> rPresentation = getNumeralRepresentation(m,r);
Iterator<BigInteger> it = rPresentation.iterator();

BigInteger atNull = rPresentation.get(0);
boolean NotAllEqual = false;

while(it.hasNext() && !NotAllEqual){
    if(it.next().compareTo(atNull)!=0)
        NotAllEqual = true;
}
 
if(!NotAllEqual){
    BigInteger[] ht = radixMinusOne.divideAndRemainder(atNull);
    if(ht[1].compareTo(BigInteger.ZERO)==0){
        n=n.multiply(ht[0]);
     if(k.compareTo(BigInteger.ZERO)>0)
         output = input_number+"*"+n.toString()+" = "+r.toString()+"^"+k.toString()+"*(r^"+getLength(m,r)+"-1)";
     else
         output = input_number+"*"+n.toString()+" = r^"+getLength(m,r)+"-1";

     setReport(output);
     setReport("where r = "+r.toString());  
     
        return Integer.toString(getLength(m,r));
    }
}

boolean special = false;
BigInteger M=m.modInverse(r);
n = n.multiply(M);
m = m.multiply(M);
BigInteger h = m.multiply(radixMinusOne);
BigInteger q = radixMinusOne;


   //rPresentation = getNumeralRepresentation(m,r);
//Iterator<BigInteger> it = rPresentation.iterator();


int MPower = 0;

while((c=getTheFirstIndexForValueLessThan(h,radixMinusOne,r))!=-1 && ValueLess != null){
MPower = MPower + c;
h=RightShift(c, h, r);
bf = radixMinusOne.subtract(ValueLess);
h = h.add(m.multiply(bf));
q=q.add(bf.multiply(r.pow(MPower)));
//System.out.println(q.toString());
}//while
MPower=MPower+getLength(h,r);
n = n.multiply(q);

if(k.compareTo(BigInteger.ZERO)>0)
     output = input_number+"*"+n.toString()+" = "+r.toString()+"^"+k.toString()+"*(r^"+MPower+"-1)";
else
     output = input_number+"*"+n.toString()+" = r^"+MPower+"-1";

     setReport(output);
     setReport("where r = "+r.toString());
     setReport("s("+r.toString()+","+input_number+") = "+MPower);
     
     return Integer.toString(MPower);
    }
    public BigInteger ValueLess=null;
public int getTheFirstIndexForValueLessThan(BigInteger im,BigInteger Given, BigInteger radix){

ArrayList<BigInteger> nm = getNumeralRepresentation(im,radix);
for(int i = 0; i< nm.size();i++){
   ValueLess= nm.get(i);
  if(ValueLess.compareTo(Given)<0)
       return i;
   }
 ValueLess= null;
 return -1;
}
public BigInteger getNumberAt(int i,BigInteger im, BigInteger radix){
ArrayList<BigInteger> nms = getNumeralRepresentation(im,radix);

if(i < nms.size())
  return nms.get(i);

return null;
}

public int getLength(BigInteger im, BigInteger radix){
return getNumeralRepresentation(im,radix).size();
}

public BigInteger RightShift(int i, BigInteger im, BigInteger radix){
BigInteger rt=BigInteger.ZERO,p=BigInteger.ONE;
ArrayList<BigInteger> nms = getNumeralRepresentation(im,radix);
for(int j = i;j<nms.size();j++){
    rt = rt.add(nms.get(j).multiply(p));
    p = p.multiply(radix);
}
return rt;
}

public BigInteger RepresentationToNumber(ArrayList<BigInteger> nms, BigInteger radix){
BigInteger rt=BigInteger.ZERO,p=BigInteger.ONE;

for(int j = 0;j<nms.size();j++){
    rt = rt.add(nms.get(j).multiply(p));
    p = p.multiply(radix);
}
return rt;
}

 public ArrayList<BigInteger> getNumeralRepresentation(BigInteger im, BigInteger radix){
ArrayList<BigInteger> rPresentation = new ArrayList<>();
BigInteger m = im, r = radix, rem;
while(m.compareTo(r)>=0){
    rem=m.remainder(r);
   rPresentation.add(rem);
   m=m.subtract(rem).divide(r);
}
rPresentation.add(m);
 return rPresentation;
}
   
    
}
