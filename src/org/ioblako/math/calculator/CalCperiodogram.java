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
 *=====================================================================
 * The development of this code is done as Honors Enrichment Contract
 * of Evan Nikitin
 * in the framework of Barrett, The Honors College
 * (under the guidance of Dr. S. Nikitin, nikitin@asu.edu).
 *
 *
 * Given a sequence {S_j, j={0..N-1}}
 * the program calculates its periodogram,
 *
 * { (f,P_f), f={0..[N/2]},
 *
 *where 
 *
 * P_f =(2/N)*sum{k=0,N-1}((sum{t=0,N-1-k} S_t * S_{t+k})*cos((f/N)*2*pi*k))
 * 
 * 
 */

package org.ioblako.math.calculator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import org.ioblako.math.linearalgebra.Fraction;

/**
 *
 * @author evan_nikitin
 */
public class CalCperiodogram implements CalcFunction{
String Report="";
    @Override
    public String getReport() {
        return Report;
    }

    @Override
    public void setReport(String str) {
         Report=str;
    }

    @Override
    public String getHelp() {
        return "periodogram({y1;...yN}) returns  {f1,A1;f2,A2;...;fm, Am },"+System.lineSeparator()+
          "where fj and Aj are respectively the values of the frequency"+System.lineSeparator()+
          "and"+System.lineSeparator()+
          "Aj=(2/N)*sum{k=0,N-1}((sum{t=0,N-1-k} S_t * S_{t+k})*cos((fj/N)*2*pi*k)) ";
    }
 public final String ExceptionMessage="Format Error";
    @Override
    public String eval(String argv) throws Exception {
            String ret="";
        if(!argv.contains("{")) 
		throw new Exception(ExceptionMessage);
        if(!argv.substring(argv.indexOf("{")+1).contains("}"))
		throw new Exception(ExceptionMessage);

        String data=argv.substring(argv.indexOf("{")+1,argv.indexOf("}"));
     //System.out.println(data);
    String[] dataArray = data.split(";");
  int count=dataArray.length;
  BigDecimal[][] dec=new BigDecimal[count][count];
  int i;
  String cmd="";
  for(i=0;i<count;i++){
   int i2;
   for(i2=0;i2<count;i2++){
    cmd = "cos(("+i+"*2*PI*"+i2+")/"+count+")";
    cmd=jc.eval(cmd);

   if(cmd.indexOf('/')!=-1)
       cmd=jc.getFraction(cmd).toBigDecimal(jc.MC).toPlainString();
      dec[i][i2]= new BigDecimal(cmd,jc.MC); 
   //dec[i][i2]= new BigDecimal(Math.cos(Double.parseDouble((((BigDecimal.valueOf(2*Math.PI*i2)).divide(BigDecimal.valueOf(count),jc.MC.getRoundingMode())).multiply(BigDecimal.valueOf(i))).toString())));
   }
  }
int result_size = 0;
while(2*result_size < count)
   result_size++;

  BigDecimal[] result=new BigDecimal[result_size];
   int f,s,ag,k;//s12;
 for(f=0;2*f<count;f++){
  BigDecimal sum=new BigDecimal(0);
  for(k=0;k<count;k++){
   BigDecimal d_prod=new BigDecimal(0);
   for(s=0;s<count-k;s++){
    ag =   s+k;
//    if(ag >count - 1)
//       ag=ag%count;
    //d_prod=d_prod.add((new BigDecimal(dataArray[s])).multiply((new BigDecimal(dataArray[s+k]))));
    d_prod=d_prod.add((new BigDecimal(dataArray[s],jc.MC)).multiply(new BigDecimal(dataArray[ag],jc.MC),jc.MC),jc.MC);
   }
   sum=sum.add(d_prod.multiply(dec[k][f],jc.MC),jc.MC);
  }
  //result[f]=(sum.multiply(new BigDecimal((count*count)/4)));
  //s12=(f>0)?2:1;
  result[f]=(sum.multiply(new BigDecimal(2))).divide(new BigDecimal(count),jc.MC);

 }
 for(i=0;2*i<count;i++){
  String datao="";
  String fs=";";
  if(i==0){fs="";}
  datao=fs+Integer.toString(i)+","+result[i].toPlainString();
  ret=ret+datao;
 }

     return "{"+ret+"}";
    }
}
