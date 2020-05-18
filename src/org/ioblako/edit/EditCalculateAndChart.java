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

//import com.apple.eawt.Application; for Mac OS X only
//import java.util.Properties;
import java.util.Set;
import java.io.IOException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Component;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.MathContext;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.KeyStroke;
import javax.swing.undo.UndoManager;

import org.ioblako.core.State;
import org.ioblako.math.calculator.jc;

class EditCalculateAndChart extends JFrame implements TextEdit {
//private static final Properties parameters = new Properties();
public static final long serialVersionUID=10L;
private String lookingFor="";
private static final Preferences Config = Preferences.userNodeForPackage(EditCalculateAndChart.class);
//private static final Image ProcessStop=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/ioblako/edit/resources/images/process-stop-5.png"));
private static final Image appIco=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/ioblako/edit/resources/images/spinning-calc.png"));
private static final Image newIco=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/ioblako/edit/resources/images/document-new.png"));
private static final Image openIco=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/ioblako/edit/resources/images/document-open.png"));
private static final Image saveIco=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/ioblako/edit/resources/images/document-save.png"));
private static final Image saveasIco=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/ioblako/edit/resources/images/document-save-as.png"));
private static final Image cutIco=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/ioblako/edit/resources/images/edit-cut.png"));
private static final Image copIco=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/ioblako/edit/resources/images/edit-copy.png"));
private static final Image pasIco=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/ioblako/edit/resources/images/edit-paste.png"));
private static final Image calcIco=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/ioblako/edit/resources/images/calc.png"));
private static final Image findIco=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/ioblako/edit/resources/images/edit-find-5.png"));
private static final Image undoIco=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/ioblako/edit/resources/images/edit-undo-5.png"));
private static final Image redoIco=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/ioblako/edit/resources/images/edit-redo-5.png"));
private static final Image helpIco=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/ioblako/edit/resources/images/help.png"));
private static final Image shutdownIco=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/ioblako/edit/resources/images/system-shutdown-2.png"));
private static final Image deleteIco=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/ioblako/edit/resources/images/edit-delete-6.png"));
private static final Image replaceIco=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/ioblako/edit/resources/images/edit-find-and-replace.png"));
//private static final Image stopIco=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/ioblako/edit/resources/images/process-stop-5.png"));

private static ImageIcon myAppIcon = new ImageIcon(appIco.getScaledInstance(32,32,Image.SCALE_DEFAULT));
private final JTextArea area = new JTextArea(10,40);
	private final JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
	private String currentFile = "Untitled";
        public static String startFile = "Untitled";
	private boolean changed = false;
        private static State Memory;
        private static State swingPool;
        protected UndoManager undoMan = new UndoManager();
      //  private static Application myApp=null; Mac OS X
/*        
static{
   if(System.getProperty("os.name").toLowerCase().startsWith("mac")){ 
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Edit Calculate & Chart");
 //   myApp = Application.getApplication(); Mac OS X
    try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        Logger.getLogger(EditCalculateAndChart.class.getName()).log(Level.SEVERE, null, ex);
    }
   }
}\*/
public EditCalculateAndChart() {
    /* Mac OS X
            if(myApp != null){
                myApp.setAboutHandler(new AboutMacHandler(this));
                myApp.setQuitHandler(new QuitMacHandler(this));
                myApp.setDockIconImage(appIco);
                myApp.setEnabledPreferencesMenu(false);
                //myApp.setDockIconBadge("Edit, Calculate And Chart");
            }
*/
            String Font_Name =  Config.get("FONT_NAME","Monospaced");
                int Font_Size = Config.getInt("FONT_SIZE",12);
                int Font_Style = Config.getInt("FONT_STYLE",Font.PLAIN);
                int Precision = Config.getInt("PRECISION",7);
                jc.setMathContext(new MathContext(Precision));
                dialog.setMultiSelectionEnabled(false);
                Memory = new State();
                swingPool = new State();
		//area.setFont(new Font("Monospaced",Font.PLAIN,12));
                area.setFont(new Font(Font_Name,Font_Style,Font_Size));
		JScrollPane scroll = new JScrollPane(area,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	//	add(scroll,BorderLayout.CENTER);
		
		JMenuBar JMB = new JMenuBar();
	//	setJMenuBar(JMB);
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
                JMenu about = new JMenu("About");
		JMB.add(file); JMB.add(edit); JMB.add(about);
                file.add(New);file.add(Open);file.add(Save);
		file.add(SaveAs);file.add(Quit);
		file.addSeparator();file.add(ChangePref);
                
                file.getItem(0).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, 
                                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
                file.getItem(1).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, 
                                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
		file.getItem(2).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 
                                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
                file.getItem(4).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 
                                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
                
		file.getItem(0).setIcon(new ImageIcon(newIco)); 
                file.getItem(1).setIcon(new ImageIcon(openIco)); 
                file.getItem(2).setIcon(new ImageIcon(saveIco)); 
                file.getItem(3).setIcon(new ImageIcon(saveasIco));
                file.getItem(4).setIcon(new ImageIcon(shutdownIco));
	         
		edit.add(Delete);edit.add(Cut);edit.add(Copy);edit.add(Paste);
               edit.add(Find);edit.add(Replace);edit.add(Undo);edit.add(Redo);edit.add(Calc);	
                	

		 edit.getItem(0).setText("Delete");
		edit.getItem(1).setText("Cut");
		edit.getItem(2).setText("Copy");
		edit.getItem(3).setText("Paste");
		edit.getItem(4).setText("Find");
                edit.getItem(5).setText("Replace");
		edit.getItem(6).setText("Undo");
		edit.getItem(7).setText("Redo");
		edit.getItem(8).setText("Calc");
                edit.getItem(8).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, 
                                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
               about.add(About);
               about.getItem(0).setText("About");
                JToolBar tool = new JToolBar();
	//	add(tool,BorderLayout.NORTH);
                
		JButton help=tool.add(Help), nw=tool.add(New), open=tool.add(Open), save=tool.add(Save);
               
		nw.setText(null); nw.setIcon(new ImageIcon(newIco));nw.setToolTipText("New");
		open.setText(null); open.setIcon(new ImageIcon(openIco));open.setToolTipText("Open");
		save.setText(null); save.setIcon(new ImageIcon(saveIco));save.setToolTipText("Save");
		tool.addSeparator();
		
		JButton  cut = tool.add(Cut), cop = tool.add(Copy),pas = tool.add(Paste),del=tool.add(Delete); 
		tool.addSeparator();
                JButton find = tool.add(Find), replace=tool.add(Replace),unBdo=tool.add(Undo), reBdo=tool.add(Redo),calc=tool.add(Calc);
		
                help.setText(null); help.setIcon(new ImageIcon(helpIco));help.setToolTipText("Help");
		cut.setText(null); cut.setIcon(new ImageIcon(cutIco));cut.setToolTipText("Cut");
		cop.setText(null); cop.setIcon(new ImageIcon(copIco));cop.setToolTipText("Copy");
		pas.setText(null); pas.setIcon(new ImageIcon(pasIco));pas.setToolTipText("Paste");
                del.setText(null); del.setIcon(new ImageIcon(deleteIco));del.setToolTipText("Delete");
		find.setText(null); find.setIcon(new ImageIcon(findIco));find.setToolTipText("Find");
                replace.setText(null); replace.setIcon(new ImageIcon(replaceIco));replace.setToolTipText("Reaplce");
		unBdo.setText(null); unBdo.setIcon(new ImageIcon(undoIco));unBdo.setToolTipText("Undo");
		reBdo.setText(null); reBdo.setIcon(new ImageIcon(redoIco));reBdo.setToolTipText("Redo");
		calc.setText(null); calc.setIcon(new ImageIcon(calcIco));calc.setToolTipText("Calculator");

                Delete.setEnabled(false);
                Redo.setEnabled(false);
	        Undo.setEnabled(false);	
		Save.setEnabled(false);
		SaveAs.setEnabled(false);
                Calc.setEnabled(false);
		
		Init(scroll,JMB,tool);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
            /*    
                addWindowListener(new WindowEvents(this));
		pack();
		area.addKeyListener(k1);
               if(startFile.contentEquals(currentFile))
		setTitle(currentFile);
               else{
                    currentFile=startFile;
                    readInFile(currentFile);
		   setTitle(currentFile);
                }
                area.getDocument().addUndoableEditListener(new TEditUndoableEditListener(this));
                
                this.setIconImage(appIco);
		setVisible(true);
*/
	}

private void Init(JScrollPane scroll,JMenuBar JMB, JToolBar tool ){
              add(tool,BorderLayout.NORTH);
              add(scroll,BorderLayout.CENTER);
              setJMenuBar(JMB);
              area.addMouseListener(new TextAreaMouseListener((TextEdit)this));
              area.addKeyListener(new TextAreaKeyListener((TextEdit)this));
              addWindowListener(new WindowEvents(this));
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		area.addKeyListener(k1);
               if(startFile.contentEquals(currentFile))
		setTitle(currentFile);
               else{
                    currentFile=startFile;
                    readInFile(currentFile);
		   setTitle(currentFile);
                }
                area.getDocument().addUndoableEditListener(new TEditUndoableEditListener(this));
                this.setIconImage(appIco);
		setVisible(true);
    
}


private final KeyListener k1 = new KAdaptor((TextEdit)this);
Action New = new New_Action((TextEdit)this,"New",new ImageIcon(newIco),"New");
Action Open = new Open_Action((TextEdit)this,"Open",new ImageIcon(openIco),"Open"); 
Action Save = new Save_Action((TextEdit)this,"Save",new ImageIcon(saveIco),"Save"); 
Action SaveAs = new SaveAs_Action((TextEdit)this,"Save as...",new ImageIcon(saveIco),"Save as..."); 
Action Quit = new Quit_Action((TextEdit)this,"Quit",null,"Quit"); 
Action About = new About_Action((TextEdit)this,"About",null,"About"); 
Action Paste = new Paste_Action((TextEdit)this,"Paste",new ImageIcon(pasIco),"Paste");
Action Copy = new Copy_Action((TextEdit)this,"Copy",new ImageIcon(copIco),"Copy");
Action Cut = new Cut_Action((TextEdit)this,"Cut",new ImageIcon(cutIco),"Cut");
Action Calc = new Calc_Action((TextEdit)this,"Calc",new ImageIcon(calcIco),"Calculator");
Action Find = new Find_Action((TextEdit)this,"Find",new ImageIcon(findIco),"Find");
Action Undo = new Undo_Action((TextEdit)this,"Undo",new ImageIcon(undoIco),"Undo");
Action Redo = new Redo_Action((TextEdit)this,"Redo",new ImageIcon(redoIco),"Redo");
Action ChangePref = new Preferences_Action((TextEdit)this,"Preferences",null,"Preferences"); 
Action Help = new Help_Action((TextEdit)this,"Help",new ImageIcon(helpIco),"Help");
Action Delete = new Delete_Action((TextEdit)this,"Delete",new ImageIcon(deleteIco),"Delete");
Action Replace = new Replace_Action((TextEdit)this,"Replace",new ImageIcon(replaceIco),"Reaplce");

@Override
public void saveFileAs() {
    		if(dialog.showSaveDialog((Component)this)==JFileChooser.APPROVE_OPTION)
                     try{
                         String fileName=dialog.getSelectedFile().getAbsolutePath();
				        writeFile(new File(fileName));
                                        currentFile=fileName;
                                        setTitle(currentFile);
                                        Save.setEnabled(false);
                                }
                                catch(Exception writeE){
                                    JOptionPane.showMessageDialog(this,writeE.getMessage(),"Save As",JOptionPane.INFORMATION_MESSAGE,myAppIcon);
                                }
		//if(dialog.showSaveDialog((Component)this)==JFileChooser.APPROVE_OPTION)
		//	saveFile(dialog.getSelectedFile().getAbsolutePath());
	}

@Override
public void saveOld() {
		if(changed) {
			if(JOptionPane.showConfirmDialog(this, "Would you like to save "+ currentFile +" ?","Save",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,myAppIcon)== JOptionPane.YES_OPTION)
				saveFile(currentFile);
		}
	}

@Override
public void readInFile(String fileName) {
		try {
                    try (FileReader r = new FileReader(fileName)) {
                        area.read(r,null);
                    }
                area.getDocument().addUndoableEditListener(new TEditUndoableEditListener(this));
                   undoMan.discardAllEdits();
                   Undo.setEnabled(false);
                   Redo.setEnabled(false);
			currentFile = fileName;
			setTitle(currentFile);
			changed = false;
		}
		catch(IOException e) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this,"Editor can't find the file called "+fileName,"Not found",JOptionPane.INFORMATION_MESSAGE,myAppIcon);
		}
	}

@Override
public void saveFile(String fileName) {
		try {
                    try (FileWriter w = new FileWriter(fileName)) {
                        area.write(w);
                    }
			currentFile = fileName;
			setTitle(currentFile);
			changed = false;
			Save.setEnabled(false);
		}
		catch(IOException e) {
                    JOptionPane.showMessageDialog(this,e.getMessage(),"Save",JOptionPane.INFORMATION_MESSAGE,myAppIcon);
		}
	}
@Override
public void setEnabled(String ActionName, boolean tf){
 if(ActionName.contentEquals("Save"))
          Save.setEnabled(tf);
   if(ActionName.contentEquals("SaveAs"))
          SaveAs.setEnabled(tf);
   if(ActionName.contentEquals("Undo"))
          Undo.setEnabled(tf);
   if(ActionName.contentEquals("Redo"))
          Redo.setEnabled(tf);
   if(ActionName.contentEquals("Delete"))
       Delete.setEnabled(tf);
     if(ActionName.contentEquals("Calc"))
       Calc.setEnabled(tf);
}

@Override
public void putValue(String ActionName ,String Rd){

   if(ActionName.contentEquals("Undo"))
        Undo.putValue(Action.NAME,Rd);  
   if(ActionName.contentEquals("Redo"))
          Redo.putValue(Action.NAME,Rd);

}
@Override
public void setChanged(boolean st){
changed=st;
}
@Override
public JFileChooser getFileChooser(){
return dialog;
}
@Override
public void updateTextArea(String body, String title){
                        area.setText(body);
                        currentFile=title;
		        setTitle(title);
                //area.getDocument().addUndoableEditListener(new TEditUndoableEditListener(this));
                   undoMan.discardAllEdits();
                   Undo.setEnabled(false);
                   Redo.setEnabled(false);
		       setVisible(true);
}
@Override
public String getCurrentFileName(){
   return currentFile;
}
@Override
public ActionMap getActionMap(){
   return area.getActionMap();
}
@Override
        public Action getActionSave(){
             return Save;
        }
@Override
public JTextArea getTextArea(){
   return area;
}
@Override
public void showDialog(String txt){
JOptionPane.showMessageDialog(this,txt,"Message",JOptionPane.INFORMATION_MESSAGE,myAppIcon);
}
@Override
public void put(String key, String value){
       Memory.put(key,value);
}
@Override
public String get(String key){
   return (String)Memory.get(key);
}
@Override
public void remove(String key){
    Memory.remove(key);
}
@Override
public void clear(){
    Memory.clear();
}
@Override
public boolean containsKey(String key){
    return Memory.containsKey(key);
}
@Override
public boolean isEmpty(){
   return Memory.isEmpty();
}
@Override
public Set<String> keySet(){
     return Memory.keySet();
}
@Override
public JFrame getFrame(){
 return this;
}
@Override
public String getLookingFor(){
return lookingFor;
}
@Override
public void setLookingFor(String lookFor){
       lookingFor = lookFor;
}
@Override
public UndoManager getUndoManager(){
  return undoMan;
}
@Override
public Preferences getConfig(){
    return Config;
} 
@Override
public boolean writeFile(File fl) throws Exception{
     String txt=area.getText();
     PrintWriter writeIt=null;
     if(fl.exists()&&fl.length()>0){
         if(JOptionPane.showConfirmDialog(getFrame(), fl.getName() +" is not empty! Append to it?","Save",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,myAppIcon)== JOptionPane.YES_OPTION)
				        writeIt=new PrintWriter(new FileWriter(fl,true));
         else
          if(JOptionPane.showConfirmDialog(getFrame(), "Overwrite "+fl.getName()+"?","Save",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,myAppIcon)== JOptionPane.YES_OPTION)
                                        writeIt=new PrintWriter(new FileWriter(fl));
     }
    else
              writeIt=new PrintWriter(new FileWriter(fl));
     
  if(writeIt!=null){
     writeIt.println(txt);
     writeIt.flush();
     writeIt.close();
  }
   return writeIt != null;
 }
@Override
public State getSwingPool(){
    return swingPool;
}
@Override
public ImageIcon getAppIcon(){
  return myAppIcon;
}
@Override
public Image getAppImage(){
 return appIco;
}

public  static void main(String[] arg) {
        if(arg.length > 0)
          startFile = arg[0];
       // if((new File(startFile)).getParent() == null)
      //            startFile = System.getProperty("user.dir")+File.separator + startFile; 
        ( new Thread(){
              @Override
              public void run(){
		EditCalculateAndChart editCalculateAndChart = new EditCalculateAndChart();
              }
          }).start();
	}

}


