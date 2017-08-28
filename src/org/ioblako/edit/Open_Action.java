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

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;

public class Open_Action extends AbstractAction {
public static final long serialVersionUID=1L;
private TextEdit TEdit;
    public Open_Action(TextEdit ed, String text, ImageIcon icon,
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
                        TEdit.saveOld();
          JFileChooser dialog = TEdit.getFileChooser();
        if(dialog.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
                                TEdit.readInFile(dialog.getSelectedFile().getAbsolutePath());
                        }
                       // TEdit.updateTextArea("","Untitled");
                        TEdit.setEnabled("SaveAs",true); 
        //displayResult("Action for first button/menu item", e);
    }
}


