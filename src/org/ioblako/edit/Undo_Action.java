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

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.undo.UndoManager;
import javax.swing.undo.CannotUndoException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;


public class Undo_Action extends AbstractAction {
public static final long serialVersionUID=1L;
    private TextEdit TEdit; 
    private UndoManager undo;

    public Undo_Action(TextEdit ed, String text, ImageIcon icon,
               String desc) {
        super(text, icon);
        TEdit = ed;
            undo=ed.getUndoManager();
            setEnabled(false);
        putValue(SHORT_DESCRIPTION, desc);
     //   putValue(MNEMONIC_KEY, KeyEvent.VK_CUT);
    }

        public Undo_Action(TextEdit ed) {
            super("Undo");
            TEdit = ed;
            undo=ed.getUndoManager();
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            try {
                undo.undo();
            } catch (CannotUndoException ex) {
                 TEdit.showDialog("Unable to undo: " + ex.getMessage());
                //ex.printStackTrace();
            }
            updateState();
           // TEdit.updateRedoState();
        }

        public void updateState() {
            if (undo.canUndo()) {
                setEnabled(true);
                putValue(Action.NAME, undo.getUndoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Undo");
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

