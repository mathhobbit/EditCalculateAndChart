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
package org.ioblako.edit;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.text.Highlighter;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import org.ioblako.math.calculator.*;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Calc_Action extends AbstractAction {
public static final long serialVersionUID=1L;
//String mkey, mvalue;
private final TextEdit TEdit;
    public Calc_Action(TextEdit ed, String text, ImageIcon icon,
               String desc) {
        super(text, icon);
        TEdit = ed;
        Init(desc);
        //putValue(SHORT_DESCRIPTION, desc);
     //   putValue(MNEMONIC_KEY, KeyEvent.VK_CUT);
    }
    private void Init(String desc){
        putValue(SHORT_DESCRIPTION, desc);
    }
@Override
    public void actionPerformed(ActionEvent e) {
        String mkey, mvalue;
                        //TEdit.saveOld();
                        //TEdit.updateTextArea("","Untitled");
                       //ActionMap m = TEdit.getActionMap();
                       // m.get(DefaultEditorKit.cutAction).actionPerformed(e);
                   JTextArea area = TEdit.getTextArea();    
                        //System.out.println(area.getSelectedText());

                         Highlighter hilite = area.getHighlighter();
                         Highlighter.Highlight[] hilites = hilite.getHighlights();
                         //String input;
                         String insertIt; 
                         String txt=area.getText();
                         int Shift = 0;
                         if(hilites != null)
                         if(hilites.length == 0 && 
                                   TEdit.getSwingPool().containsKey("area.hilites")){
                                   hilites = (Highlighter.Highlight[])TEdit.getSwingPool().get("area.hilites");
                                   TEdit.getSwingPool().remove("area.hilites");
                         } 
                     if(hilites != null) 
                         for (Highlighter.Highlight hilite1 : hilites) {
                             Shift=Shift+hilite1.getStartOffset();
                     String exeString="";        
                     for(String input:(txt.substring(hilite1.getStartOffset(), hilite1.getEndOffset())).split("\\r?\\n"))        
                       try {
                           if(input.replace(" ", "").contentEquals("")){
                                  Shift = Shift+input.length()+1;
                                  continue;
                           }
                          if(input.endsWith("\\")){
                           exeString = exeString+input.substring(0,input.length()-1);
                                  Shift = Shift+input.length()+1;
                                  continue;
                          }
                          exeString = exeString+input;
                          //debug: System.out.println(exeString);
                           //input = txt.substring(hilite1.getStartOffset(), hilite1.getEndOffset());
                           //if (input.trim().startsWith("?")) {
                           if (exeString.trim().startsWith("?")) {
                               String name=exeString.trim().substring(1);
                               if(name.indexOf('(')!=-1)
                                   name= name.substring(0,name.indexOf('('));
                               try {
                                   CalcFunction fn=(CalcFunction) Class.forName("org.ioblako.math.calculator.CalC"+name).getDeclaredConstructor().newInstance();
                                   insertIt=System.lineSeparator()+fn.getHelp()+System.lineSeparator();
                                   Shift=Shift+input.length();
                                   area.insert(insertIt, Shift);
                                   Shift=Shift+insertIt.length()+1;
                                   //System.out.println(fn.getHelp());
                                   //TEdit.showDialog(fn.getHelp());
                               }catch(ClassNotFoundException | InstantiationException | IllegalAccessException exee){
                                  try{ 
                                   if(name.contentEquals("plot"))
                                       name="grPlt";
                                   FramePlt frPlt = (FramePlt) Class.forName("org.ioblako.edit."+name).getDeclaredConstructor().newInstance();
                                   Shift=Shift+input.length();
                                   insertIt=System.lineSeparator()+frPlt.getHelp()+System.lineSeparator();
                                   area.insert(insertIt, Shift);
                                   Shift=Shift+insertIt.length()+1;
                                  }
                                   catch(ClassNotFoundException | InstantiationException | IllegalAccessException exee1){
                                      TEdit.showDialog("There is no help for \""+exeString.substring(1)+"\"");
                                    }
                               }
                               exeString="";
                               continue;//return;
                           }
                           //if(input.contains("<-") && !input.endsWith("<-")){
                           if(exeString.contains("<-") && !exeString.endsWith("<-")){
                                Shift=Shift+input.length()+1;
                               mkey = exeString.trim().substring(0,exeString.trim().indexOf("<-")).trim();
                               mvalue = exeString.substring(exeString.indexOf("<-")+2).trim();
                               try{
                                if(mvalue.length()>5){
                                   switch(mvalue.trim().toLowerCase().substring(0,5)){
                                       case "eval(":
                                           TEdit.put(mkey,jc.eval(jc.getInside(substitute(mvalue.substring(5)),'(',')')));
                                           jc.Report="";
                                       break;
                                       case "file(":
                                               File fl = new File(jc.getInside(substitute(mvalue.trim().substring(5)),'(',')'));
                               
                                        if(!fl.exists()||fl.isDirectory()){
                                               JFileChooser dialog = TEdit.getFileChooser();
                                               if(fl.isDirectory())
                                                   dialog.setCurrentDirectory(fl);
                                                 if(dialog.showSaveDialog((Component)TEdit)==JFileChooser.APPROVE_OPTION) {
                                                         fl = new File(dialog.getSelectedFile().getAbsolutePath());
                                                      TEdit.put(mkey, returnFile(fl));
                                                         exeString="";
                                                        continue;//return;
                                                 }
                                         
                                                exeString="";
                                                continue; //return;
                                           } 
                                        else{
                                                     TEdit.put(mkey, returnFile(fl));
                                                    exeString="";
                                                    continue; //return;
                                        }
                                       default:
                                           TEdit.put(mkey,mvalue);
                                                    exeString="";
                                       break;
                                   }
                                }
                                else
                                    TEdit.put(mkey,mvalue);
                               }
                               catch(Exception evalEx){
                                   TEdit.showDialog(evalEx.getMessage());
                               }
                                                    exeString="";
                               continue;//return;
                           }
                           if (exeString.endsWith("->")) {
                               mkey = exeString.trim().substring(0,exeString.trim().indexOf("->"));
                               if (TEdit.containsKey(mkey)) {
                                   Shift=Shift+input.length();
                                   insertIt=System.lineSeparator()+mkey+" = "+TEdit.get(mkey)+System.lineSeparator();
                                   area.insert(insertIt, Shift);
                                //   Shift=Shift+insertIt.length()+1;
                                   Shift=Shift+insertIt.length();
                               } else {
                                   TEdit.showDialog("Memory does not contain \""+mkey+"\"");
                               }
                                                    exeString="";
                               continue;//return;
                           }
                           if(exeString.contains("->")){
                               mkey = exeString.trim().substring(0,exeString.trim().indexOf("->"));
                               mvalue = exeString.substring(exeString.indexOf("->")+2).trim();
                                Shift=Shift+input.length()+1;
                               if(TEdit.containsKey(mkey)&&
                                       mvalue.trim().length()>5&&
                                       mvalue.trim().toLowerCase().substring(0,5).contentEquals("file(")){
                                         File fl = new File(jc.getInside(substitute(mvalue.trim().substring(5)),'(',')'));
                               
                                        if(fl.isDirectory()){
                                               JFileChooser dialog = TEdit.getFileChooser();
                                               
                                               dialog.setCurrentDirectory(fl);
                                                 if(dialog.showSaveDialog((Component)TEdit)==JFileChooser.APPROVE_OPTION) {
                                                         fl = new File(dialog.getSelectedFile().getAbsolutePath());
                                                      writeFile(TEdit.get(mkey), fl);
                                                    exeString="";
                                                        continue;//return;
                                                 }
                                         
                                                    exeString="";
                                                 continue;//return;
                                           } 
                                        else{
                                            if(fl.getParentFile()==null||!fl.getParentFile().isDirectory()){
                                                JFileChooser dialog = TEdit.getFileChooser();
                                                 if(dialog.showSaveDialog((Component)TEdit)==JFileChooser.APPROVE_OPTION) {
                                                         fl = new File(dialog.getSelectedFile().getAbsolutePath());
                                                      writeFile(TEdit.get(mkey), fl);
                                                    exeString="";
                                                       continue; //return;
                                                 }
                                            }
                                            else         
                                                 writeFile(TEdit.get(mkey), fl);
                                                    exeString="";
                                                    continue; //return;
                                        }
                               }
                               if(TEdit.containsKey(mkey)){
                                   TEdit.put(mvalue,TEdit.get(mkey));
                               }
                               else{
                                   TEdit.showDialog("Memory does not contain \""+mkey+"\"");
                               }
                                                    exeString="";
                               continue;//return;
                           }
                           if(exeString.trim().contentEquals("clear")){
                               Shift=Shift+input.length()+1;
                               TEdit.clear();
                                                    exeString="";
                               continue;//return;
                           }
                           if(exeString.trim().startsWith("clear") && exeString.length()>5){
                               Shift=Shift+input.length()+1;
                               mkey = exeString.trim().substring(5).trim();
                               if(TEdit.containsKey(mkey)){
                                   TEdit.remove(mkey);
                               }
                               else{
                                   TEdit.showDialog("Memory does not contain \""+mkey+"\"");
                               }
                                                    exeString="";
                               continue;//return;
                           }
                           int input_length = input.length();
                           
                           exeString = substitute(exeString);
                          // if(input.startsWith("plot")||
                           //        input.startsWith("plrPlt")){
                             if(exeString.trim().startsWith("plot")){
                                 Shift=Shift+input_length+1;
                              // String inputV=input.trim();
                               FramePlt frm = (FramePlt)Class.forName("org.ioblako.edit.grPlt").getDeclaredConstructor().newInstance();
                               frm.setInput("grPlt"+exeString.trim().substring(4));
                               UIManager.put("swing.boldMetal", Boolean.FALSE);
                              // MathContext MathC= jc.MC;
                               javax.swing.SwingUtilities.invokeLater(() -> {
                                   try {
                                       frm.createAndShow();
                                   } catch (Exception e1) {
                                        TEdit.showDialog(e1.getMessage());
                                       //System.out.println(e1.getMessage());
                                   }
                               });
                              // jc.setMathContext(MathC);
                                                    exeString="";
                              continue;// return;
                           }
                           if(exeString.indexOf('(')!=-1&&
                                   exeString.substring(0,exeString.indexOf('(')).contains("Plt")){
                               Shift=Shift+input_length+1;
                               String inputV=exeString.trim();
                               FramePlt frm = (FramePlt)Class.forName("org.ioblako.edit."+inputV.substring(0,inputV.indexOf('('))).getDeclaredConstructor().newInstance();
                               frm.setInput(inputV);
                               UIManager.put("swing.boldMetal", Boolean.FALSE);
                              // MathContext MathC= jc.MC;
                               javax.swing.SwingUtilities.invokeLater(() -> {
                                   try {
                                       frm.createAndShow();
                                   } catch (Exception e1) {
                                       TEdit.showDialog(e1.getMessage());
                                      // System.out.println(e1.getMessage());
                                   }
                               });
                              // jc.setMathContext(MathC);
                               
                                                    exeString="";
                               continue;//return;
                           }
                           if(exeString.trim().toLowerCase().startsWith("file(")){
                               File fl = new File(jc.getInside(exeString.trim().substring(5),'(',')'));
                               
                               if(!fl.exists()||fl.isDirectory()){
                                    JFileChooser dialog = TEdit.getFileChooser();
                                      if(dialog.showOpenDialog((Component)TEdit)==JFileChooser.APPROVE_OPTION) {
                                          fl = new File(dialog.getSelectedFile().getAbsolutePath());
                                          Shift=Shift+input_length;
                                          insertIt=System.lineSeparator()+returnFile(fl)+System.lineSeparator();
                                            area.insert(insertIt,Shift);
                                            Shift=Shift+insertIt.length()+1;
                                                    exeString="";
                                            continue;//return;
                                      }
                               }
                               else{
                                   Shift=Shift+input_length;
                                   insertIt = System.lineSeparator()+returnFile(fl)+System.lineSeparator();
                                    area.insert(insertIt, Shift);
                                    Shift=Shift+insertIt.length()+1;
                                                    exeString="";
                                           continue; //return;
                               }
                               
                           }
                           if(exeString.indexOf('=')!=-1 &&
                                   !exeString.contains("Rec") &&
                                   !exeString.contains("Seq") &&
                                   !exeString.contains("Int") &&
                                   !exeString.contains("xySeq")&&
                                   !exeString.contains("plot") &&
                                   !exeString.contains("Plt")){
                               String prev=null;
                               insertIt="";
                               for(String current : exeString.split("=")){
                                   if(prev != null){
                                       current=jc.eval(current.trim());
                                       if(current.contentEquals(prev)){
                                           insertIt=System.lineSeparator()+prev+" = "+current;
                                       }
                                       else{
                                           insertIt=System.lineSeparator()+prev+" ?= "+current;
                                       }
                                       prev=current;
                                   }
                                   else
                                       prev=jc.eval(current.trim());
                               }
                               if (jc.Report.contentEquals("")) {
                                   Shift=Shift+input_length;
                                   insertIt=insertIt+System.lineSeparator();
                                 area.insert(insertIt, Shift);
                                 Shift=Shift+insertIt.length()+1;
                                } else {
                                   Shift=Shift+input_length;
                                   insertIt=insertIt+System.lineSeparator()+jc.Report+System.lineSeparator();
                                    area.insert(insertIt, Shift);
                                    Shift=Shift+insertIt.length()+1;
                                 }
                                jc.Report="";
                                                    exeString="";
                              continue; // return;
                           }
                           
                    /*  if(input.trim().startsWith("xySeq")){
                               insertIt = jc.hInside("xySeq(", input,'(',')')[1];
                               insertIt=(new CalCxySeq()).eval(insertIt);
                           }
                           else
                     */
                           
                           insertIt = jc.eval(substitute(exeString));
                           if (jc.Report.contentEquals("")) {
                               insertIt="="+insertIt;
                               Shift=Shift+input_length;
                               area.insert(insertIt, Shift);
                               Shift=Shift+insertIt.length()+1;
                           } else {
                                Shift=Shift+input_length;
                               insertIt="="+insertIt+System.lineSeparator()+jc.Report+System.lineSeparator();
                               area.insert(insertIt, Shift);
                               Shift=Shift+insertIt.length()+1;
                           }
                           jc.Report="";
                           //hilite.removeHighlight(hilites[i]);
                       }catch(Exception Ex){
                           TEdit.showDialog(Ex.toString());
                                                    exeString="";
                           return;
                       }
                   }
                        //TEdit.setEnabled("Delete",false);    
                        TEdit.setEnabled("Calc", false);
                        TEdit.setEnabled("Save",true); 
                        TEdit.setEnabled("SaveAs",true); 
        //displayResult("Action for first button/menu item", e);
    }
 private String substitute(String input){
     String mkey;
                         if(!TEdit.isEmpty()){
                                      Iterator<String> it = TEdit.keySet().iterator();
                                      while(it.hasNext()){
                                          mkey=it.next();
                                          if(input.contains(mkey))
                                            input = SmartReplace.get(input,mkey,TEdit.get(mkey));
                                       }
                                 }
            return input;
          }
 private String returnFile(File fl) throws Exception{
     BufferedReader rd = new BufferedReader(new FileReader(fl));
                                          String bf;
                                          String returnIt="";
                                          while((bf=rd.readLine())!=null)
                                               returnIt=returnIt+bf+System.lineSeparator();
                                            rd.close();
                    return returnIt;
 }
 private void writeFile(String txt, File fl) throws Exception{
     PrintWriter writeIt=null;
     if(fl.exists()&&fl.length()>0){
         if(JOptionPane.showConfirmDialog(TEdit.getFrame(), fl.getName() +" is not empty! Append to it?","Save",JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION)
				        writeIt=new PrintWriter(new FileWriter(fl,true));
         else
          if(JOptionPane.showConfirmDialog(TEdit.getFrame(), "Overwrite "+fl.getName()+"?","Save",JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION)
                                        writeIt=new PrintWriter(new FileWriter(fl));
     }
    else
              writeIt=new PrintWriter(new FileWriter(fl));
     
  if(writeIt!=null){
     writeIt.println(txt);
     writeIt.flush();
     writeIt.close();
  }
   
 }
}


