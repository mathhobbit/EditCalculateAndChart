/* 
 * Copyright (C) 2019 Sergey Nikitin
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
package org.ioblako.math;


import java.util.ArrayList;
import java.util.Iterator;
import java.math.BigInteger;



public class DoubleLGA{

private int DEGREE = 0;
private ArrayList<Double> JUMPS=null;

public static void main(String[] argv) throws Exception{

DoubleLGA LGA = new DoubleLGA();
BigInteger C = LGA.BinomialCoefficient(new BigInteger(argv[0]), new BigInteger(argv[1]));

//System.out.println("Number of "+argv[1]+"-collection out of "+argv[0]+" is "+C.toString());
System.out.println(C.toString());

/*
double[] p = new double[argv.length];
for(int i=p.length;i>0;i--)
  p[i-1] =  Double.parseDouble(argv[p.length - i]);




DoubleLGA LGA = new DoubleLGA();
System.out.println(LGA.Poly2String(p));
System.out.println(LGA.Poly2String(LGA.ReflectAboutYAxis(p)));
double min = LGA.getMin(p,Double.parseDouble("-4"), Double.parseDouble("4"), Double.parseDouble("0.1"));
System.out.println("x = "+Double.toString(min));
System.out.println("p(x) = "+Double.toString(LGA.HornerEval(p,min)));
double[] rt = LGA.Horner(p,new double("1"));
for(int i=rt.length;i>0;i--)
 System.out.println(rt[i-1].toString());


double rt = new double(argv[0]);
double tr = new double(argv[1]);
 rt.reduce();

System.out.println("reduced: " +rt.toString());
System.out.println("flipped: " +rt.flip().toString());
System.out.println("sum: " +(rt.add(tr)).reduce().toString());
System.out.println("product: " +(rt.multiply(tr)).reduce().toString());
System.out.println("subtract: " +(rt.subtract(tr)).reduce().toString());
System.out.println("square: " +(rt.pow(2)).reduce().toString());
System.out.println("argv[0] > argv[1]" +rt.compareTo(tr));
*/
}

public BigInteger BinomialCoefficient(BigInteger N, BigInteger K) throws Exception{

if(K.compareTo(BigInteger.ZERO) == 0)
   return BigInteger.ONE;


if(K.compareTo(N) >0)
   throw new Exception("Cannot calculate binomial coefficient when K > N!!!");

if(K.compareTo(N) == 0)
  return BigInteger.ONE;



if(N.subtract(K).compareTo(BigInteger.ONE) == 0)
   return N;


return BinomialCoefficient(N.subtract(BigInteger.ONE),K).add(BinomialCoefficient(N.subtract(BigInteger.ONE),K.subtract(BigInteger.ONE)));

}



public double getMin(double[] polynom, double a, double b, double step){
 double rt;//=a;
 double[] pl; //= null;
 double rt_prev;//=a;
if(DEGREE == 0)
   DEGREE = polynom.length;
if(a >= b)
   return b;

if(b-a <= step)
   return a;

while(polynom[polynom.length - 1] == 0 && polynom.length > 1){
     pl= new double[polynom.length - 1];
     System.arraycopy(polynom, 0, pl, 0, polynom.length - 1);
    polynom=pl;
}
if(polynom.length == 1)
  return a;
if(polynom.length == 2){
  if(polynom[1]>= 0 )
        return a;
  else
        return b;
}
if(polynom.length == 3){
  if(polynom[2]> 0 ){
   double ans = -polynom[1]/(2*polynom[2]);
    if(ans >=b)
        return b;
    if(ans <=a)
        return a;
      return ans;  
  }
  if(polynom[2]< 0 ){
   double ans = -polynom[1]/(2*polynom[2]);
    if(ans >=b)
        return a;
    if(ans <=a)
        return b;
    ans = polynom[1] + polynom[2]*(a+b);
    if(ans>=0)
           return a;
    else 
           return b;
  }
    
}
if(polynom.length == 4){
   if(polynom[3]>=0&& polynom[2]*polynom[2]<=3*polynom[3]*polynom[1])
                  return a;
   if(polynom[3]<=0&& polynom[2]*polynom[2]<=3*polynom[3]*polynom[1])
                  return b;

    if(polynom[3]>0 && polynom[2]*polynom[2] > 3*polynom[3]*polynom[1]){
        double c=(-polynom[2]+Math.sqrt(polynom[2]*polynom[2] - 3*polynom[3]*polynom[1]))/(3*polynom[3]);
        if(c>=b)            
            if(polynom[3]*a*a*a+polynom[2]*a*a+polynom[1]*a+polynom[0]<=polynom[3]*b*b*b+polynom[2]*b*b+polynom[1]*b+polynom[0])
                     return a;
                   else 
                     return b;
        if(c<=a)
             return a; 
       if(c>a && c<b){
                        double fa=polynom[3]*a*a*a+polynom[2]*a*a+polynom[1]*a+polynom[0];
                        double fb=polynom[3]*b*b*b+polynom[2]*b*b+polynom[1]*b+polynom[0];
                        double fc=polynom[3]*c*c*c+polynom[2]*c*c+polynom[1]*c+polynom[0];

                        if(fc<=fa&&fc<=fb)
                              return c;
                        if(fa<=fc&&fa<=fb)
                             return a;
                        if(fb<=fc&&fb<=fa)
                              return b;
        }

    }
    if(polynom[3]<0 && polynom[2]*polynom[2] > 3*polynom[3]*polynom[1]){
        double c=(-polynom[2]+Math.sqrt(polynom[2]*polynom[2] - 3*polynom[3]*polynom[1]))/(3*polynom[3]);
       if(c>=b)
            return b;
       if(c <=a)
            if(polynom[3]*a*a*a+polynom[2]*a*a+polynom[1]*a+polynom[0]<=polynom[3]*b*b*b+polynom[2]*b*b+polynom[1]*b+polynom[0])
                     return a;
                   else 
                     return b;
       if(c>a && c<b){
                        double fa=polynom[3]*a*a*a+polynom[2]*a*a+polynom[1]*a+polynom[0];
                        double fb=polynom[3]*b*b*b+polynom[2]*b*b+polynom[1]*b+polynom[0];
                        double fc=polynom[3]*c*c*c+polynom[2]*c*c+polynom[1]*c+polynom[0];

                        if(fc<=fa&&fc<=fb) 
                              return c;
                        if(fa<=fc&&fa<=fb)
                             return a;
                        if(fb<=fc&&fb<=fa)
                              return b;
        }
    }

   if((polynom[3] >0 && 3*polynom[3]*a+polynom[2] >=0)||
     (polynom[3] <0 && 3*polynom[3]*b+polynom[2] >=0)){
             double mn = GradientDescent(polynom,a,b,step);
               return (HornerEval(polynom,b)<HornerEval(polynom,mn))?b:mn; 
   }
}

if(polynom.length == 5)
    if(3*polynom[3]*polynom[3]<8*polynom[4]*polynom[2]){
        double mn = GradientDescent(polynom,a,b,step);
             return (HornerEval(polynom,b)<HornerEval(polynom,mn))?b:mn;  
    }
rt=a;
rt_prev=rt;
int Njumps = 0;
    do{
          rt_prev = GradientDescent(polynom,rt,b,step);
            if(rt_prev >= b)
                  return b;
// recording ooriginal evolutionary jumps
           if(DEGREE == polynom.length){
              if(JUMPS == null)
                 JUMPS = new ArrayList<>();
              JUMPS.add(rt_prev);
             }
// done with jumps

           if(Njumps >= (polynom.length - 3))
                  return rt_prev;

          pl = Horner(polynom,rt_prev);
          rt = getMin(pl,rt_prev,b,step);
        if(rt - rt_prev<= step)
              return rt_prev;
        else{
        if(rt_prev == a) 
          Njumps++;
        else
          Njumps=Njumps + 2;
         }
        
          if(HornerEval(pl,rt)>=0)
                 return rt_prev;
          
        rt_prev=rt;
      }
  while(rt < b-step);
  
 return rt_prev; 
}

public double getMinimum(double[] polynom, double a, double b, double step) {
 double rt;//=a;
 double[] pl;//=null;
 double rt_prev;//=a;

if(a >= b)
   return b;

if(b-a <= step)
   return a;

while(polynom[polynom.length - 1] == 0 && polynom.length > 1){
     pl= new double[polynom.length - 1];
     System.arraycopy(polynom, 0, pl, 0, polynom.length - 1);
    polynom=pl;
}
if(polynom.length == 1)
  return a;
if(polynom.length == 2){
  if(polynom[1]>= 0 )
        return a;
  else
        return b;
}
if(polynom.length == 3){
  if(polynom[2]> 0 ){
   double ans = -polynom[1]/(2*polynom[2]);
    if(ans >=b)
        return b;
    if(ans <=a)
        return a;
      return ans;  
  }
  if(polynom[2]< 0 ){
   double ans = -polynom[1]/(2*polynom[2]);
    if(ans >=b)
        return a;
    if(ans <=a)
        return b;
    ans = polynom[1] + polynom[2]*(a+b);
    if(ans>=0)
           return a;
    else 
           return b;
  }
    
}
if(polynom.length == 4)
   if((polynom[3] >0 && 3*polynom[3]*a+polynom[2] >=0)||
     (polynom[3] <0 && 3*polynom[3]*b+polynom[2] >=0))
             return GradientDescent(polynom,a,b,step);   

if(polynom.length == 5)
    if(3*polynom[3]*polynom[3]<8*polynom[4]*polynom[2])
             return GradientDescent(polynom,a,b,step);   
rt=a;
rt_prev=rt;
int Njumps = 0;
    do{
          rt_prev = GradientDescent(polynom,rt,b,step);
            if(rt_prev >= b)
                  return b;


           if(Njumps == (polynom.length - 3))
                  return rt_prev;
          rt = getLeapG(polynom,rt_prev,b,step);
          //  rt=Jump(polynom,rt_prev,b,step);
        if(rt - rt_prev<= step)
              return rt_prev;
        else
          Njumps++;
          
          
        rt_prev=rt;
      }
  while(rt < b-step);
  return rt_prev;
}

public double Jump(double[] p,double a,double b, double step){
double[] pl = Horner(p,a);
double ret = a, LeapCandidat, lp=a;
double MIN=HornerEval(pl,ret);

while(ret < b){
  LeapCandidat = HornerEval(pl,ret); 
  if(LeapCandidat<MIN){
    MIN=LeapCandidat;
    lp=ret; 
   } 
       ret=ret+step;
}
return (MIN>=0)?a:lp;
}

public double getLeapG(double[] p,double a,double b, double step){
if(p.length<4)
  return a;
    
double[] pl = Horner(p,a);
double[] dp = Derivative(p);
double[] leapP; //= null;// new double[dp.length - 1];
double ret = a, LeapCandidat;//=0;
for(int i =0; i < dp.length; i++)
      dp[i] = pl[i]-dp[i];

leapP=Horner(dp,a);

double MIN=HornerEval(pl,ret);
double lp=ret;
double min = HornerEval(leapP,ret);
for(int i=0;i<dp.length-2&&ret<b;i++){
//do{
if(min == 0)
    ret=ret+step;

if(min > 0)
// while(HornerEval(leapP,ret)>HornerEval(Horner(leapP,ret),ret+step)*step&&ret<b){
 while(HornerEval(leapP,ret)>0&&ret<b){
  LeapCandidat = HornerEval(pl,ret); 
  if(LeapCandidat<MIN){
    MIN=LeapCandidat;
    lp=ret; 
   } 
       ret=ret+step;
  }

if(min < 0)
 while(HornerEval(leapP,ret)<0&&ret<b)
       ret=ret+step;
ret=ret-step/2;
if(min > 0 && ret < b){
  LeapCandidat = HornerEval(pl,ret); 
  if(LeapCandidat<MIN){
    MIN=LeapCandidat;
    lp=ret; 
   } 
}
if(min < 0 && ret < b){
  LeapCandidat = HornerEval(pl,ret); 
  if(LeapCandidat<MIN){
    MIN=LeapCandidat;
    lp=ret; 
   } 
}
if(ret < b)
min = HornerEval(leapP,ret);
}
//while(ret<b);
//ret = (ret>b)?b:ret;
if(ret>=b){
LeapCandidat = HornerEval(pl,b);
  if(LeapCandidat<MIN){
    MIN=LeapCandidat;
    lp=b; 
   } 
}
return (MIN>=0)?a:lp;
}


public double[] ReflectAboutYAxis(double[] p){
double[] ret=new double[p.length];

ret[0]=p[0];
double s=-1;

for(int i = 1;i<p.length;i++){
     ret[i] = s*p[i];
     s = s*(-1);
}
   
return ret;

}
public double EvalDerivative(double[] polynom,double x){
double ret = 0;

for(int i = polynom.length -1; i>0; i--)
          ret=ret*x + i*polynom[i];
return ret;
}


public double GradientDescent(double[] polynom,double a, double b, double step){
    //    double  rt_prev=a;
        double  rt=a;
           //  do{
          //          rt_prev=rt;
          //          rt=rt+step;
           //    }
          while(EvalDerivative(polynom,rt)<0 && rt < b )
                             rt=rt+step;
        //  while(HornerEval(Horner(polynom,rt_prev),rt)<0 && rt < b );
            
             return rt;   
}


public double[] Horner(double[] polynom, double root){
double[] rt = new double[polynom.length - 1];
 rt[polynom.length - 2] = polynom[polynom.length - 1];
for (int i = polynom.length - 2 ; i>0; i--){
       rt[i-1]= root*rt[i] + polynom[i];
}
return rt;
}

public double HornerEval(double[] polynom, double x){
double rt;// = 0.0;

rt = polynom[ polynom.length - 1 ];

for ( int i = polynom.length - 2; i >= 0; i--)
    rt = rt*x+polynom[i];

return rt;
}

protected String Poly2String(double[] p){
String pOut = "p(x) = "+ Double.toString(p[0]);

for (int j = 1; j< p.length; j++)
     pOut=pOut +" + "+Double.toString(p[j]) + "*X^"+j;

return pOut;


}
public String PrintPoly(double[] p){
String pOut = "p(x) = "+ Double.toString(p[0]);

for (int j = 1; j< p.length; j++)
     pOut=pOut +" + "+Double.toString(p[j]) + "*X^"+j;

 return pOut;
}

public double getMinBySearch(double[] Polinom, double left, double right, double step){
      double x = left;
      double minLocation=x;
      double bf;//=0.0;
      double min = HornerEval(Polinom,x);
       x = x+step;
      while(x<=right){
       bf = HornerEval(Polinom,x);
       if(bf<min){
           min=bf;
           minLocation=x;
       }
       x = x+step;

       }
     return minLocation;

}
public double getZero(double left, double right, double[] poly, double stepSize) throws Exception{
        if(HornerEval(poly,left) == 0)
                  return left;
        if(HornerEval(poly,right) == 0)
                  return right;
if(HornerEval(poly,left)*HornerEval(poly,right)>0)
 throw new Exception("Can not apply binary search for zeros!"); 
double mid=left;
    while(right-left >stepSize ){
           mid= (right+left)/2;
        if(HornerEval(poly,mid) == 0)
                  return mid;
          if(HornerEval(poly,left) * HornerEval(poly,mid)<0)
             right = mid;
          else
          if(HornerEval(poly,right) * HornerEval(poly,mid)<0)
             left = mid;
 
    }
return mid;
}

public double[] getRemainder(double[] dvdnd, double[] dvsr){
double[] rtn;//=null;
double bf=0.0;
int i;// = 0;
    while(dvsr.length > 1 &&
           dvsr[dvsr.length - 1] == 0){
         rtn = new double[dvsr.length - 1];
           for(i=0; i < rtn.length;i++)
               rtn[i] = dvsr[i];
           dvsr = rtn;
     }
if(dvsr.length == 1){
rtn = new double[1];
rtn[0]=0.0;
 return rtn;
}
 while( dvsr.length <= dvdnd.length ){
     while(dvdnd.length > 1 &&
           dvdnd[dvdnd.length - 1] == 0){
         rtn = new double[dvdnd.length - 1];
           for(i=0; i < rtn.length;i++)
               rtn[i] = dvdnd[i];
           dvdnd = rtn;
     }
   if(dvdnd.length == 1)
        break;
   if(dvsr.length > dvdnd.length)
       break;
  rtn = new double[dvdnd.length - 1];
   for (int j = 0; j < rtn.length; j++)
       rtn[j]=0.0;
   for ( int j = 0 ; j < dvsr.length - 1;j++)
       rtn[rtn.length - 1 - j] = dvsr[dvsr.length - 2 -j ]*dvdnd[dvdnd.length - 1]/dvsr[dvsr.length - 1];
   for (int j = 0; j < rtn.length; j++)
         rtn[j]=dvdnd[j]-rtn[j];
       dvdnd=rtn;
}
 
return dvdnd;
}
public double[] Integral(double[] Polinom){
    double[] IntP;
    IntP = new double[Polinom.length+1];
    IntP[0]=0;
    for(int i = 1;i<IntP.length;i++)
        IntP[i]=Polinom[i-1]/i;
    return IntP;
}
public double[] Derivative(double[] Polinom){
double[] DerP;// = null;
if(Polinom.length == 1){
DerP = new double[1];
 DerP[0]=0.0;
 return DerP;
}
DerP = new double[Polinom.length - 1];

for(int j = 1;j < Polinom.length;j++)
    DerP[j-1]=Polinom[j]*j;
return DerP;
}
public ArrayList<double[]> SturmianSequence(double[] poly){
ArrayList<double[]> SturmianSeq = new ArrayList<>();
if(poly.length == 1){
  SturmianSeq.add(poly);
  return SturmianSeq;
}
double[] f0 = poly;
double[] f1 = Derivative(f0);
double[] f2;// = null;
SturmianSeq.add(f0);
SturmianSeq.add(f1);
boolean flag;//=true;
String trr=null;
 while(f1.length >= 1){
   f2=getRemainder(f0,f1);
trr=Poly2String(f2);

  f0=f1;
  flag=true;
   for(double ff:f2) 
       flag = flag && (ff == 0);
      if(flag)
               break;
 for(int i = 0; i < f2.length;i++)
     f2[i] = -f2[i];
  f1=f2;
   SturmianSeq.add(f1);
}
return SturmianSeq;

}

public int NumberOfRoots (double[] Polynom, double left, double right, double stepSize){
if(Polynom.length == 1)
  return 0;
int rtn = 0;
if(HornerEval(Polynom,left)==0){
rtn++;
   left=left+((right-left)*stepSize);
   while(HornerEval(Polynom,left)==0){
          rtn++; 
          if(rtn >= Polynom.length)
            return -1; 
          if(right <= left)
              return rtn;
       left=left+(right-left)*stepSize;
     }
}
if(HornerEval(Polynom,right)==0){
rtn++;
   right=right-(right-left)*stepSize;
   while(HornerEval(Polynom,right)==0){
          rtn++; 
          if(rtn >= Polynom.length)
            return -1; 
          if(right<= left)
              return rtn;
   right=right-(right-left)*stepSize;
     }
}
return rtn + getSturmianNumber(Polynom,left) - getSturmianNumber(Polynom,right);

}
public int getSturmianNumber(double[] Polynom, double left){
int rtn = 0;
Iterator<double[]> it = (SturmianSequence(Polynom)).iterator();
double[] bfP;// = null;
double c0;//=0.0;
double c1;//=0.0;
if(it.hasNext()){
bfP = it.next();
c0=HornerEval(bfP,left);
}
else
 return rtn;

while(it.hasNext()){
bfP = it.next();
if(bfP.length == 1)
 c1=bfP[0];
else
c1=HornerEval(bfP,left);
  if(c0*c1<0){
      rtn++;
           c0=c1;
  }
  else{
       if(c1 != 0)
           c0=c1;
   }
}


return rtn;


}


public ArrayList<Double> getJumps(){
 return JUMPS;
}

public double[] negate(double[] poly){
    double[] ret = new double[poly.length];
    for(int i = 0; i < poly.length; i++)
        ret[i]=-poly[i];
    return ret;     
}

}
