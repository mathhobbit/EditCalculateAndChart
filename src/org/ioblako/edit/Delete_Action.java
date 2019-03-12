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
import javax.swing.JTextArea;
import javax.swing.text.Highlighter;

/**
 *
 * @author sergey_nikitin
 */
public class Delete_Action extends AbstractAction{
static final long serialVersionUID=1001;
private final TextEdit TEdit;


public Delete_Action(TextEdit ed, String text, ImageIcon icon,
               String desc){
    super(text, icon);
         
        TEdit = ed;
        Init(desc,text,KeyEvent.VK_DELETE);
/*
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, KeyEvent.VK_DELETE); //change it after "escape" is done
        putValue(NAME,text);
*/ 
}
  private void Init(String desc, String text,int KeyEventValue){
       putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, KeyEventValue); //change it after "escape" is done
        putValue(NAME,text); 
  }  
    @Override
    public void actionPerformed(ActionEvent e) {
        
        
         JTextArea area = TEdit.getTextArea();
         Highlighter hilite = area.getHighlighter();
         Highlighter.Highlight[] hilites = hilite.getHighlights();
         int Shift=0;
         
         if(hilites != null){ 
             if(hilites.length == 0 && 
                     TEdit.getSwingPool().containsKey("area.hilites")){
                 hilites = (Highlighter.Highlight[])TEdit.getSwingPool().get("area.hilites");
                 TEdit.getSwingPool().remove("area.hilites");
             }
                 
             for (Highlighter.Highlight hilite1 : hilites) {
                   area.replaceRange("",hilite1.getStartOffset()-Shift, hilite1.getEndOffset()-Shift);
                   Shift = Shift -(hilite1.getEndOffset()-hilite1.getStartOffset());
             }
            
             if(hilites.length>0){
             area.setCaretPosition(hilites[0].getStartOffset());
             area.getCaret().setVisible(true);
             }
                        TEdit.setEnabled("Calc",false);
                        TEdit.setEnabled("Delete",false);
                        TEdit.setEnabled("Save",true); 
                        TEdit.setEnabled("SaveAs",true);
         }
    }
    
}
