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
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.MNEMONIC_KEY;
import static javax.swing.Action.SHORT_DESCRIPTION;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author sergey_nikitin
 */
public class FindLeft_Action  extends AbstractAction{
    static final long serialVersionUID=1002;
    private final Find_Action TEdit;
ImageIcon  myIcon;
    public FindLeft_Action(Find_Action ed, String text, ImageIcon icon,
               String desc) {
            //   String desc, Integer mnemonic) { like mnemomnic=KeyEvent.VK_AT
            //   will be updated after "escape" command mode is introduced
            // (like in "vi") 
        super(text, icon);
        myIcon = icon;
        TEdit = ed; 
        Init(desc);
       // putValue(MNEMONIC_KEY, KeyEvent.VK_FIND); //change it after "escape" is done
    }
private void Init(String desc){
    putValue(SHORT_DESCRIPTION, desc);
}
    @Override
    public void actionPerformed(ActionEvent e) {
         JTextArea area = TEdit.getTextArea();
         String txt = area.getText();
         if(txt == null)
             return;
         String s = TEdit.getLookingFor();
         if(s.contentEquals(""))
             return;
         if(txt.contentEquals("") || txt.length()<1)
             return;
         if(area.getCaretPosition() < s.length()-1){
              JOptionPane.showMessageDialog(TEdit.getFrame(),"Reached the beginning of the text");
              return;
          }
               if(area.getCaretPosition()-s.length()<=0){
                   area.setCaretPosition(txt.length()-1);
               }
               int foundAt = txt.substring(0,area.getCaretPosition()-s.length()).lastIndexOf(s);
               
               if(foundAt == -1){
                   JOptionPane.showMessageDialog(TEdit.getFrame(),"Reached the beginning of the text");
                   return;
               }
                   area.setCaretPosition(foundAt);
                   area.select(foundAt,foundAt+s.length());
    }
    
}
