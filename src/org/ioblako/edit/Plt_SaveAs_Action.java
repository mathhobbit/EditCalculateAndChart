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

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.io.File;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;

public class Plt_SaveAs_Action extends AbstractAction {
public static final long serialVersionUID=1L;
private JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
private JFreeChart TEdit;
    public Plt_SaveAs_Action(JFreeChart ed, String text, ImageIcon icon,
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
    public void actionPerformed(ActionEvent e) {
             try{
                if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
                       String SelectedFileName = fileChooser.getSelectedFile().getAbsolutePath();
                 if(SelectedFileName.toUpperCase().endsWith(".JPG")||
                    SelectedFileName.toUpperCase().endsWith(".JPEG")){                     
                       ChartUtilities.saveChartAsJPEG(new File(SelectedFileName),TEdit,500,500);
                                                            return;
                      }

                 if(SelectedFileName.toUpperCase().endsWith(".PNG")){
                       ChartUtilities.saveChartAsPNG(new File(SelectedFileName),TEdit,500,500);
                                                            return; 
                          }
                       ChartUtilities.saveChartAsPNG(new File(SelectedFileName+".png"),TEdit,500,500);
                        
                  }
              }
            catch(Exception Ex){
                   System.out.println(Ex.toString());   
              }
    }
}


