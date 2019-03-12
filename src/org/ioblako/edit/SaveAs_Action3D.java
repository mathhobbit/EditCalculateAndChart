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

import com.orsoncharts.graphics3d.Drawable3D;
import com.orsoncharts.graphics3d.ExportUtils;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import static javax.swing.Action.SHORT_DESCRIPTION;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author sergey_nikitin
 */
public class SaveAs_Action3D extends AbstractAction{
    public static final long serialVersionUID=1L;
private final JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
private final Drawable3D TEdit;
    public SaveAs_Action3D(Drawable3D ed, String text, ImageIcon icon,
               String desc) {
            //   String desc, Integer mnemonic) { like mnemomnic=KeyEvent.VK_AT
            //   will be updated after "escape" command mode is introduced
            // (like in "vi") 
        super(text, icon);
        TEdit = ed; 
        putValue(SHORT_DESCRIPTION, desc);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("..jpg, ..jpeg, ..png", "jpg", "jpeg","png"));
        //fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("supported image formats", "jpg", "jpeg","png"));
        //fileChooser.setAcceptAllFileFilterUsed(true);
//        putValue(MNEMONIC_KEY, mnemonic); //change it after "escape" is done
    }
    @Override
    public void actionPerformed(ActionEvent e) {
             try{
                if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
                       String SelectedFileName = fileChooser.getSelectedFile().getAbsolutePath();
                 if(SelectedFileName.toUpperCase().endsWith(".JPG")||
                    SelectedFileName.toUpperCase().endsWith(".JPEG")){                     
                       ExportUtils.writeAsJPEG(TEdit,500,500,new File(SelectedFileName));
                                                            return;
                      }

                 if(SelectedFileName.toUpperCase().endsWith(".PNG")){
                       ExportUtils.writeAsPNG(TEdit,500,500,new File(SelectedFileName));
                                                            return; 
                          }
                       ExportUtils.writeAsPNG(TEdit,500,500,new File(SelectedFileName+".png"));
                        
                  }
              }
            catch(Exception Ex){
                   System.out.println(Ex.toString());   
              }
    }
    
}
