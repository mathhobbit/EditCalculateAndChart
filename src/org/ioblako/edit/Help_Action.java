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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.AbstractAction;
import static javax.swing.Action.SHORT_DESCRIPTION;
import javax.swing.ImageIcon;

/**
 *
 * @author sergey_nikitin
 */
public class Help_Action  extends AbstractAction {
    static final long serialVersionUID=1041;
    private final TextEdit TEdit;
    public Help_Action(TextEdit ed, String text, ImageIcon icon,
               String desc) {
        super(text, icon);
        TEdit = ed; 
        putValue(SHORT_DESCRIPTION, desc);
     //   putValue(MNEMONIC_KEY, KeyEvent.VK_CUT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(TEdit.getSwingPool().containsKey("org.ioblako.edit.HelpFrame")){
            ((HelpFrame)TEdit.getSwingPool().get("org.ioblako.edit.HelpFrame")).toFront();
            return;
        }
        
        HelpFrame HelpFrm = new HelpFrame(TEdit);
        HelpFrm.getSwingPool().put("org.ioblako.edit.HelpFrame",HelpFrm);
        
        javax.swing.SwingUtilities.invokeLater(() -> {
       
                //javax.swing.SwingUtilities.vokeLater(() -> {
                                       HelpFrm.addWindowListener(new HelpWindowEvents(HelpFrm));
                                       HelpFrm.setResizable(true);
                                       HelpFrm.setTitle("Help");
                                       HelpFrm.pack();
                                       HelpFrm.getTextArea().getCaret().setVisible(true);
                                       HelpFrm.setVisible(true);
                                       //HelpFrm.dispose();
                                       //HelpFrm.createAndShow();
                                       
                                   
                 //              });
        });
        //HelpFrm.dispose();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
