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

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JTextField;
import static javax.swing.Action.SHORT_DESCRIPTION;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author sergey_nikitin
 */
public class FindRight_Action  extends AbstractAction{
    static final long serialVersionUID=1003;
        private JTextField jTxt;
        private TextEdit TEdit;
ImageIcon  myIcon;
    public FindRight_Action(TextEdit ed,JTextField txt, String text, ImageIcon icon,
               String desc) {
            //   String desc, Integer mnemonic) { like mnemomnic=KeyEvent.VK_AT
            //   will be updated after "escape" command mode is introduced
            // (like in "vi") 
        super(text, icon);
        myIcon = icon;
        TEdit = ed; 
	jTxt = txt; 
        Init(desc);
       // putValue(MNEMONIC_KEY, KeyEvent.VK_FIND); //change it after "escape" is done
    }
private void Init(String desc){
    putValue(SHORT_DESCRIPTION, desc);
}
public void setTextEdit(TextEdit ed){
           TEdit = ed;
}
    @Override
    public void actionPerformed(ActionEvent e) {
         JTextArea area = TEdit.getTextArea();
         String txt = area.getText();
         if(txt == null)
             return;
         //String s = TEdit.getLookingFor();
         String s = jTxt.getText();
         if(s.contentEquals(""))
             return;
         if(txt.contentEquals("") || txt.length()<1)
             return;
          if(area.getCaretPosition() >=txt.length()-1){
              area.setCaretPosition(0);
              //JOptionPane.showMessageDialog(TEdit.getFrame(),"Reached the end of the text");
              //return;
          }
               int foundAt = txt.indexOf(s,area.getCaretPosition()+s.length());
               if(foundAt == -1){
                   JOptionPane.showMessageDialog(TEdit.getFrame(),"Reached the end of the text");
                   return;
               }
                   area.setCaretPosition(foundAt);
                   area.select(foundAt,foundAt+s.length());
    }
    
    
}
