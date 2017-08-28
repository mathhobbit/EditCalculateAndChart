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

//import com.apple.eawt.QuitHandler;
//import com.apple.eawt.QuitResponse;  Mac OS X
//import com.apple.eawt.AppEvent;
import javax.swing.JOptionPane;
/**
 *
 * @author sergey_nikitin
 */
//public class QuitMacHandler implements QuitHandler {
    public class QuitMacHandler{
    TextEdit TEdit;
    public QuitMacHandler(TextEdit ed){
        TEdit=ed;
    }
    //public void handleQuitRequestWith(AppEvent.QuitEvent e, QuitResponse response){
    public void handleQuitRequestWith(){
                                //TEdit.saveOld();
                                String currentFile=TEdit.getCurrentFileName();
                               if(TEdit.getActionSave().isEnabled()) 
                                if(JOptionPane.showConfirmDialog(TEdit.getFrame(), "Would you like to save "+ currentFile +" ?","Save",JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION)
				        TEdit.saveFile(currentFile);
                                System.exit(0);
    }
    
    
}
