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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTextArea;
import javax.swing.text.Highlighter;

/**
 *
 * @author sergey_nikitin
 */
public class TextAreaMouseListener implements MouseListener {
private final TextEdit TEdit;
    public TextAreaMouseListener(TextEdit ed){
        super();
        TEdit = ed;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        JTextArea area = TEdit.getTextArea();
         Highlighter hilite = area.getHighlighter();
         Highlighter.Highlight[] hilites = hilite.getHighlights();
         if(hilites != null&&hilites.length>0){
             TEdit.setEnabled("Delete", true);
             TEdit.setEnabled("Calc",true);
         }
         else{
             TEdit.setEnabled("Delete", false);
             TEdit.setEnabled("Calc",false);
         }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
              JTextArea area = TEdit.getTextArea();
         Highlighter hilite = area.getHighlighter();
         Highlighter.Highlight[] hilites = hilite.getHighlights();
         if(hilites != null&&hilites.length>0)
             TEdit.getSwingPool().put("area.hilites", hilites);
        /*
     if(hilites != null&&hilites.length>0){
             TEdit.setEnabled("Delete", true);
             TEdit.setEnabled("Calc",true);
         }
         else{
             TEdit.setEnabled("Delete", false);
             TEdit.setEnabled("Calc",false);
         }
*/
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
