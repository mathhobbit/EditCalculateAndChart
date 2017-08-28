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
package org.ioblako.edit;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.SHORT_DESCRIPTION;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.Highlighter;

/**
 *
 * @author sergey_nikitin
 */
public class Replace_Action extends AbstractAction{
    static final long serialVersionUID=1901;
private final TextEdit TEdit;
ImageIcon  myIcon;

public Replace_Action(TextEdit ed, String text, ImageIcon icon,
               String desc) {
            //   String desc, Integer mnemonic) { like mnemomnic=KeyEvent.VK_AT
            //   will be updated after "escape" command mode is introduced
            // (like in "vi") 
        super(text, icon);
        myIcon = icon;
        TEdit = ed; 
       Init(desc);
        //putValue(MNEMONIC_KEY, KeyEvent.VK_FIND); //change it after "escape" is done
    }
private void Init(String desc){
     putValue(SHORT_DESCRIPTION, desc);
}

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextArea area = TEdit.getTextArea();
        if(area.getText().length()==0){
            JOptionPane.showMessageDialog(TEdit.getFrame(),"There is nothing to replace!");
            return;
        }
        JTextField source = new JTextField();
        JTextField target = new JTextField();
        Object[] message = {
                              "Source:", source,
                              "Target:", target
                            };
        int option = JOptionPane.showConfirmDialog(TEdit.getFrame(), message, "Replace", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,myIcon);
        if (option != JOptionPane.OK_OPTION)
            return;
        
        String src = source.getText();
        String trgt= target.getText();
        
        if(src.contentEquals(""))
                return;
        
                 Highlighter hilite = area.getHighlighter();
         Highlighter.Highlight[] hilites = hilite.getHighlights();
         int Shift=0;
         String txt=area.getText();
         if(hilites != null && hilites.length >0){ 
             for (Highlighter.Highlight hilite1 : hilites) {
                    //txt.substring(hilite1.getStartOffset(), hilite1.getEndOffset()))
                   area.replaceRange(txt.substring(hilite1.getStartOffset(), hilite1.getEndOffset()).replace(src,trgt),
                                  hilite1.getStartOffset()-Shift, hilite1.getEndOffset()-Shift);
                   Shift = Shift -(src.length()-trgt.length());
             }
            
         }
         else{
             txt=txt.replace(src,trgt);
             area.setText(txt);
         }
                
                
        
         
    }
    
}
