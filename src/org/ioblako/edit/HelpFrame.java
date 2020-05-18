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

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.prefs.Preferences;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.undo.UndoManager;
import org.ioblako.core.State;

/**
 *
 * @author sergey_nikitin
 */
public class HelpFrame  extends JFrame implements TextEdit {
    static final long serialVersionUID=1101;
   final TextEdit TEdit;
   final StringBuffer sb = new StringBuffer();
    private static String txt=null;
    private String lookingFor="";
   // private static State Memory;
    JTextArea area;
    
    private static final Image copIco=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/ioblako/edit/resources/images/edit-copy.png"));
    private static final Image findIco=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/ioblako/edit/resources/images/edit-find-5.png"));
    
    Action Copy = new Copy_Action((TextEdit)this,"Copy",new ImageIcon(copIco),"Copy");
    Action Find = new Find_Action((TextEdit)this,"Find",new ImageIcon(findIco),"Find");
    
    HelpFrame(TextEdit ed){
        TEdit=ed;
         Preferences Config = TEdit.getConfig();
       //  Memory = new State();
        if(txt == null)
                try{
                    BufferedReader br;
                     br = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("org/ioblako/edit/resources/Help.txt"),"UTF-8"));
                     for (int c = br.read(); c != -1; c = br.read()) sb.append((char)c);
                     txt=sb.toString();
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
    area = new JTextArea(txt,11,80);
    area.setEditable(true);
              String Font_Name =  Config.get("FONT_NAME","Monospaced");
              int Font_Size = Config.getInt("FONT_SIZE",12);
              int Font_Style = Config.getInt("FONT_STYLE",Font.PLAIN);
              area.setFont(new Font(Font_Name,Font_Style,Font_Size));
              	JScrollPane scroll = new JScrollPane(area,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll,BorderLayout.CENTER);
                            
                
                JToolBar tool = new JToolBar();
		add(tool,BorderLayout.NORTH);
                JButton  cop = tool.add(Copy), find = tool.add(Find);
                cop.setText(null); cop.setIcon(new ImageIcon(copIco));cop.setToolTipText("Copy");
                find.setText(null); find.setIcon(new ImageIcon(findIco));find.setToolTipText("Find");
                
                
                /*
                if(txt == null)
                try{
                    BufferedReader br;
                     br = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("org/ioblako/edit/resources/Help.txt"),"UTF-8"));
                     for (int c = br.read(); c != -1; c = br.read()) sb.append((char)c);
                     txt=sb.toString();
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
                area.setText(txt);
               */ 
                addFocusListener(new FocusListener() {
                                                  public void focusLost(FocusEvent e) {
                                                                  return;
                                                  }

                                                  public void focusGained(FocusEvent e) {
                                                           area.getCaret().setVisible(true); // show the caret anyway
                                                  }
                                        });
               
                //setTitle("Help");
                //pack();
                //setVisible(true);
    
    }
public void createAndShow() throws Exception{
    Preferences Config = TEdit.getConfig();
    JTextArea area = new JTextArea(10,40);
    area.setEditable(false);
              String Font_Name =  Config.get("FONT_NAME","Monospaced");
              int Font_Size = Config.getInt("FONT_SIZE",12);
              int Font_Style = Config.getInt("FONT_STYLE",Font.PLAIN);
              area.setFont(new Font(Font_Name,Font_Style,Font_Size));
              	JScrollPane scroll = new JScrollPane(area,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.add(scroll,BorderLayout.CENTER);
                if(txt == null){
                    BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("org/ioblako/edit/resources/Help.txt"), "UTF-8"));
                     for (int c = br.read(); c != -1; c = br.read()) sb.append((char)c);
                     txt=sb.toString();
                }
                
                area.setText(txt);
                this.setTitle("Help");
                this.pack();
                 this.setIconImage(TEdit.getAppImage());
                this.setVisible(true);
               
}

    @Override
    public void setChanged(boolean tf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setEnabled(String name, boolean tf) {
        return;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveOld() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveFileAs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveFile(String fileName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void readInFile(String fileName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateTextArea(String body, String title) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JFileChooser getFileChooser() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCurrentFileName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ActionMap getActionMap() {
        return area.getActionMap();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JTextArea getTextArea() {
        return area;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showDialog(String txt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void put(String txt, String value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String get(String txt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<String> keySet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsKey(String txt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(String txt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JFrame getFrame() {
        return this;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLookingFor(String txt) {
        lookingFor=txt;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getLookingFor() {
        return lookingFor;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UndoManager getUndoManager() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void putValue(String Redo, String Rd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Action getActionSave() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean writeFile(File fl) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Preferences getConfig() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public State getSwingPool(){
        return TEdit.getSwingPool();
    }
    @Override
    public ImageIcon getAppIcon(){
            return TEdit.getAppIcon();
      }    
    @Override
    public Image getAppImage(){
            return TEdit.getAppImage();
      }    
}
