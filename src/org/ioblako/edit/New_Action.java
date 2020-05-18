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
import javax.swing.ImageIcon;;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class New_Action extends AbstractAction {
public static final long serialVersionUID=1L;
private TextEdit TEdit;
private ImageIcon  myIcon;
    public New_Action(TextEdit ed, String text, ImageIcon icon,
               String desc) {
            //   String desc, Integer mnemonic) { like mnemomnic=KeyEvent.VK_AT
            //   will be updated after "escape" command mode is introduced
            // (like in "vi") 
        super(text, icon);
        myIcon=icon;
        TEdit = ed; 
        putValue(SHORT_DESCRIPTION, desc);
//        putValue(MNEMONIC_KEY, mnemonic); //change it after "escape" is done
    }
    public void actionPerformed(ActionEvent e) {
        String currentFileName = TEdit.getCurrentFileName();
       
        Object[] options = {"Cancel",
                    "No",
                    "Yes"};
        int n = JOptionPane.showOptionDialog(TEdit.getFrame(),
                "Would you like to save "+ currentFileName +" ?",
                 "Save",
                 JOptionPane.YES_NO_CANCEL_OPTION,
                 JOptionPane.QUESTION_MESSAGE,
                 myIcon,
                 options,
                 options[0]);
        if(n == 0)
             return;
                   if(n==2){
                       if(currentFileName.contentEquals("Untitled"))
                           TEdit.saveFileAs();
                       else
                        TEdit.saveFile(currentFileName);
                   }
                   if(n==1){
                        TEdit.updateTextArea("","Untitled");
                        TEdit.setEnabled("SaveAs",true);
                   }
        //displayResult("Action for first button/menu item", e);
        
    }
}


