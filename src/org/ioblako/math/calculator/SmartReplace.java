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



public class SmartReplace{


public static void  main(String[] argv){


if(argv.length < 3)
    return;

String st =  argv[0];
String Var = argv[1];
String Val = argv[2];

System.out.println(st);
System.out.println(Var);
System.out.println(Val);
if(Var.contentEquals(""))
      return;

System.out.println(get(st,Var,Val));


}


public static String get(String st, String Var, String Val){
st=st.replace(" ","");
       if(Var.contentEquals(""))
            return st;

       if(st.contentEquals(Var))
           return Val;
    String ret="";

 int i = 0, j, caseNumber=100;
   while(i < st.length()){
          for( j = 0 ; j < Var.length() ; j++ )
                 if(st.charAt(i+j)!= Var.charAt(j))
                      break;
           if(j < Var.length()){
              ret = ret + st.charAt(i);
              i++;
           }
           else{
            if(i == 0)
                 caseNumber = 0;

            if(i == st.length()-Var.length())
                caseNumber = 1; 
            switch (caseNumber){
            case  0:
               if(i+Var.length() < st.length()){
                  if(checkCharAt(i+Var.length(),st,false))
                      {    
                        ret = (st.charAt(i+Var.length()) =='^')? ret + '('+Val+')':ret + Val; 
                        
                          //ret = ret + Val;
                          i = i+Var.length();
                          
                      }
                    else{
                       ret = ret + st.charAt(i);
                       i++;
                      }
                   }
                  else{
                     while(i < st.length()){
                       ret = ret + st.charAt(i);
                       i++;
                     }
                  }
                 break;
              case 1:
                 if(checkCharAt(i-1,st,true))
                     {
                            ret = ret + Val;
                            i = i+Var.length();
                     }
                   else{
                       ret = ret + st.charAt(i);
                       i++;

                     }
                  break;
              default: 
                  if(i > st.length()-Var.length()){
                     while(i < st.length()){
                       ret = ret + st.charAt(i);
                       i++;
                     }
                  }
                else{
                   
                   if(checkCharAt(i-1,st,true))
                    {
                             if(checkCharAt(i+Var.length(),st,false))
                                {     
                                    ret = (st.charAt(i+Var.length()) =='^')? ret + '('+Val+')':ret + Val; 
                                     //ret = ret + Val;
                                     i = i+Var.length();
                                    
                                }
                              else
                                {
                                 ret = ret + st.charAt(i);
                                 i++;
                                }
                     }
                    else
                       {
                          ret = ret + st.charAt(i);
                          i++;
                       }

                 }
                  break;
            }
            caseNumber = 100;
            }
            
   }//while
    return ret;
}

 private static boolean checkCharAt(int i, String st, boolean rightORleft){
              if(rightORleft){
                   if(st.charAt(i) == '*'||
                    st.charAt(i) == '.'|| 
                    st.charAt(i) == '-'||
                    st.charAt(i) == '+'||
                    st.charAt(i) == '/'||
                    st.charAt(i) == '^'||
                    st.charAt(i) == ','||       
                    st.charAt(i) == '('||
                    st.charAt(i) == '{'){
                         return true;
                    }
               }
               else{
                   if(st.charAt(i) == '*'||
                    st.charAt(i) == '.'|| 
                    st.charAt(i) == '-'||
                    st.charAt(i) == '+'||
                    st.charAt(i) == '/'||
                    st.charAt(i) == '^'||
                    st.charAt(i) == ','||
                    st.charAt(i) == ')'||
                    st.charAt(i) == '}'){
                         return true;
                    }
                }
               return false; 
    }

}
