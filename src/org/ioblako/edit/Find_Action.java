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

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.AbstractAction;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Find_Action extends AbstractAction {
    JTextField lookingFor=new JTextField();;
public static final long serialVersionUID=1L;
private static final Image leftIco=Toolkit.getDefaultToolkit().getImage(Find_Action.class.getResource("resources/images/arrow-left.png"));
private static final Image rightIco=Toolkit.getDefaultToolkit().getImage(Find_Action.class.getResource("resources/images/arrow-right.png"));
private TextEdit TEdit;
ImageIcon  myIcon;
    Action FindLeft; 
    Action FindRight;
    public Find_Action(TextEdit ed, String text, ImageIcon icon,
               String desc) {
            //   String desc, Integer mnemonic) { like mnemomnic=KeyEvent.VK_AT
            //   will be updated after "escape" command mode is introduced
            // (like in "vi") 
        super(text, icon);
        myIcon = icon;
        TEdit = ed;
    FindLeft = new FindLeft_Action(TEdit,lookingFor,"Backward",new ImageIcon(leftIco),"Backward");
    FindRight = new FindRight_Action(TEdit,lookingFor,"Forward",new ImageIcon(rightIco),"Forward");
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, KeyEvent.VK_FIND); //change it after "escape" is done
    }
    public void actionPerformed(ActionEvent e) {
                JTextArea area = TEdit.getTextArea();
            if(!area.requestFocusInWindow()){
                        JOptionPane.showMessageDialog(TEdit.getFrame(),"Mouse click where to start search");
                        return;
              }
 

               JButton left = new JButton(FindLeft), right = new JButton(FindRight);
                       left.setBorderPainted(false); right.setBorderPainted(false);
                left.setText(null); right.setText(null);
                JPanel myPanel = new JPanel();
                    myPanel.add(left);
                    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                    myPanel.add(right);
                    
                    lookingFor.setText(TEdit.getLookingFor());
                    Object[] message = {
                     "", lookingFor,
                     "", myPanel
                    };
        
                 int option = JOptionPane.showConfirmDialog(TEdit.getFrame(), message, "Find Dialog", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,myIcon);
                       if (option != JOptionPane.OK_OPTION){
                                TEdit.setLookingFor("");
                                return;
                       }
                   TEdit.setLookingFor(lookingFor.getText());
    }
}


