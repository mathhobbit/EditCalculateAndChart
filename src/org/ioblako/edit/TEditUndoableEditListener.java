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
import javax.swing.undo.UndoManager;
import javax.swing.event.UndoableEditListener;
import javax.swing.event.UndoableEditEvent;

public class TEditUndoableEditListener
          implements UndoableEditListener {
private TextEdit TEdit;
UndoManager undo;
public TEditUndoableEditListener(TextEdit ed){
      TEdit=ed;
      undo = ed.getUndoManager();
}
    public void undoableEditHappened(UndoableEditEvent e) {
        //Remember the edit and update the menus
        undo.addEdit(e.getEdit());
        //undoAction.updateUndoState();
        //redoAction.updateRedoState();
         if (undo.canUndo()) {
                TEdit.setEnabled("Undo",true);
                TEdit.putValue("Undo", undo.getUndoPresentationName());
            } else {
                TEdit.setEnabled("Undo",false);
                TEdit.putValue("Undo", "Undo");
            }
            if (undo.canRedo()) {
                TEdit.setEnabled("Redo",true);
                TEdit.putValue("Redo",undo.getRedoPresentationName());
            } else {
                TEdit.setEnabled("Redo",false);
                TEdit.putValue("Redo","Redo");
            }
 
    }
}  
