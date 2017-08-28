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

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author sergey_nikitin
 */
public class WindowEvents implements WindowListener {
    TextEdit TEdit;
    
    WindowEvents(TextEdit ed){
        TEdit=ed;
    }
    @Override
    public void windowOpened(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
         String currentFile=TEdit.getCurrentFileName();
         if(TEdit.getActionSave().isEnabled()) 
                                if(JOptionPane.showConfirmDialog(TEdit.getFrame(), "Would you like to save "+ currentFile +" ?","Save",JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION){
                                         if(currentFile.contentEquals("Untitled")){
                                       JFileChooser dialog =  TEdit.getFileChooser();
                                         if(dialog.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
                                             currentFile=dialog.getSelectedFile().getAbsolutePath();
                                         else
                                         currentFile=System.getProperty("user.home")+File.separator+"Untitled.txt";
                                         }
                                try{
				        TEdit.writeFile(new File(currentFile));
                                }
                                catch(Exception writeE){
                                    JOptionPane.showMessageDialog(TEdit.getFrame(),writeE.getMessage());
                                }
                                }
                                System.exit(0);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
       
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
