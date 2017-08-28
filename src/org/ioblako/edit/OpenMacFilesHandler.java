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

//import com.apple.eawt.AppEvent;
//import com.apple.eawt.OpenFilesHandler;
import javax.swing.JFileChooser;

/**
 *
 * @author sergey_nikitin
 */
//public class OpenMacFilesHandler implements OpenFilesHandler{ Mac OS X
public class OpenMacFilesHandler{
    TextEdit TEdit;
public OpenMacFilesHandler(TextEdit ed){
    TEdit=ed;
}

   
  //  @Override
    //public void openFiles(AppEvent.OpenFilesEvent ofe) {
                    public void openFiles(){
                                  TEdit.saveOld();
        JFileChooser dialog = TEdit.getFileChooser();
        if(dialog.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
                                TEdit.readInFile(dialog.getSelectedFile().getAbsolutePath());
                        }
                       // TEdit.updateTextArea("","Untitled");
                        TEdit.setEnabled("SaveAs",true); 
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}  

