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
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.jfree.chart.swing.ApplicationFrame;

public class Quit_Action extends AbstractAction {
public static final long serialVersionUID=1L;
private TextEdit TEdit;
    public Quit_Action(TextEdit ed, String text, ImageIcon icon,
               String desc) {
            //   String desc, Integer mnemonic) { like mnemomnic=KeyEvent.VK_AT
            //   will be updated after "escape" command mode is introduced
            // (like in "vi") 
        super(text, icon);
        TEdit = ed; 
        putValue(SHORT_DESCRIPTION, desc);
//        putValue(MNEMONIC_KEY, mnemonic); //change it after "escape" is done
    }
    public void actionPerformed(ActionEvent e) {
                                //TEdit.saveOld();
                   String currentFile=TEdit.getCurrentFileName();
                        if(TEdit.getActionSave().isEnabled()) 
                                if(JOptionPane.showConfirmDialog(TEdit.getFrame(), "Would you like to save "+ currentFile +" ?","Save",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,TEdit.getAppIcon())== JOptionPane.YES_OPTION){
				                                if(currentFile.contentEquals("Untitled")){
                                       JFileChooser dialog =  TEdit.getFileChooser();
                                         if(dialog.showSaveDialog((Component)TEdit)==JFileChooser.APPROVE_OPTION)
                                             currentFile=dialog.getSelectedFile().getAbsolutePath();
                                         else
                                         currentFile=System.getProperty("user.home")+File.separator+"Untitled.txt";
                                         }
                                try{
				       TEdit.writeFile(new File(currentFile));
                                }
                                catch(Exception writeE){
                                    JOptionPane.showMessageDialog(TEdit.getFrame(),writeE.getMessage(),"Exception",JOptionPane.INFORMATION_MESSAGE,TEdit.getAppIcon());
                                }  
                                    // TEdit.saveFile(currentFile);
                                }
                                System.exit(0);
        //displayResult("Action for first button/menu item", e);
    }
}


