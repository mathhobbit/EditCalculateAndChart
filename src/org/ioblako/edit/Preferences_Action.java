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

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import static javax.swing.Action.SHORT_DESCRIPTION;
import javax.swing.ImageIcon;
import org.ioblako.math.calculator.jc;

/**
 *
 * @author Sergey Nikitin
 */
public class Preferences_Action extends AbstractAction{
    public static final long serialVersionUID=1L;
private TextEdit TEdit;
    public Preferences_Action(TextEdit ed, String text, ImageIcon icon,
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
          javax.swing.SwingUtilities.invokeLater(() -> {
                                   try {
                                      // final JFrame f = new JFrame("Preferences");
                                       final FontChooser2 fc = new FontChooser2(TEdit);
                                            fc.setResizable(false);
                                            fc.setVisible(true);
                                            Font myNewFont = fc.getSelectedFont();
                                                // System.out.println("You chose " + myNewFont);
                                           if(myNewFont!=null){     
                                             TEdit.getTextArea().setFont(myNewFont);
                                             Preferences Config = TEdit.getConfig();
                                             Config.put("FONT_NAME",myNewFont.getName());
                                             Config.putInt("FONT_SIZE",myNewFont.getSize());
                                             Config.putInt("FONT_STYLE", myNewFont.getStyle());
                                             Config.putInt("PRECISION",jc.MC.getPrecision());
                                             Config.flush();
                                           }
                                             //f.pack(); // adjust for new size
                                              fc.dispose();
                                   } catch (Exception e1) {
                                       System.out.println(e1.getMessage());
                                   }
                               });
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
