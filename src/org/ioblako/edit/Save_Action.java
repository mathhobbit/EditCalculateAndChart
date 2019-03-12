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

import java.awt.Toolkit;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Save_Action extends AbstractAction {
public static final long serialVersionUID=1L;
private TextEdit TEdit;
    public Save_Action(TextEdit ed, String text, ImageIcon icon,
               String desc) {
        super(text, icon);
        TEdit = ed; 
        putValue(SHORT_DESCRIPTION, desc);
        //putValue(MNEMONIC_KEY,KeyEvent.VK_S); //change it after "escape" is don
    }
    public void actionPerformed(ActionEvent e) {
                  String currentFile=TEdit.getCurrentFileName();
                        if(!currentFile.equals("Untitled"))
                                TEdit.saveFile(currentFile);
                        else
                                TEdit.saveFileAs();
        //displayResult("Action for first button/menu item", e);
    }
}


