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
import java.awt.event.ActionEvent;
/**
 *
 * @author Evan
 */
public class About_Action extends AbstractAction{
    public static final long serialVersionUID=1L;
    private TextEdit TEdit;
      public About_Action(TextEdit ed, String text, ImageIcon icon,
               String desc) {
            //   String desc, Integer mnemonic) { like mnemomnic=KeyEvent.VK_AT
            //   will be updated after "escape" command mode is introduced
            // (like in "vi") 
        super(text, icon);
        TEdit = ed; 
        putValue(SHORT_DESCRIPTION, desc);
//        putValue(MNEMONIC_KEY, mnemonic); //change it after "escape" is done
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TEdit.showDialog(String.format("%s","Application is a text editor that can"+System.lineSeparator()
                                             + "evaluate highlighted text with "+System.lineSeparator()
                                             + "the built in calculator and"+System.lineSeparator()
                                             + "create charts of functions"+System.lineSeparator()+
                                               "Build 01162017100920"+System.lineSeparator()+
                                                "Copyright (c) Sergey Nikitin, 2017.  All rights reserved."
 +System.lineSeparator()+"   This Software is written by Sergey Nikitin and others." 
 +System.lineSeparator()+"   This program is free software: you can redistribute it and/or modify"
 +System.lineSeparator()+"   it under the terms of the GNU General Public License as published by"
 +System.lineSeparator()+"   the Free Software Foundation, either version 3 of the License, or"
 +System.lineSeparator()+"   (at your option) any later version."
 +System.lineSeparator()+"    "            
 +System.lineSeparator()+"   This program is distributed in the hope that it will be useful,"
 +System.lineSeparator()+"   but WITHOUT ANY WARRANTY; without even the implied warranty of"
 +System.lineSeparator()+"   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the"
 +System.lineSeparator()+"   GNU General Public License for more details."
 +System.lineSeparator()+"    " 
 +System.lineSeparator()+"   You should have received a copy of the GNU General Public License"
 +System.lineSeparator()+"   along with this program.  If not, see <http://www.gnu.org/licenses/>."
                +System.lineSeparator()+"   "
));
    }
    
}
