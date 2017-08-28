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

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.ActionMap;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import java.util.Set;
import java.util.prefs.Preferences;
import javax.swing.undo.UndoManager;
import javax.swing.Action;
import org.ioblako.core.State;

public interface TextEdit{

public void setChanged(boolean tf);
public void setEnabled(String name, boolean tf);
public void saveOld();
public void saveFileAs();
public void saveFile(String fileName);
public void readInFile(String fileName);
public void updateTextArea(String body, String title);
public JFileChooser getFileChooser();
public String getCurrentFileName();
public ActionMap getActionMap();
public JTextArea getTextArea();
public void showDialog(String txt);
public void put(String txt,String value);
public String get(String txt);
public Set<String> keySet();
public boolean isEmpty();
public boolean containsKey(String txt);
public void clear();
public void remove(String txt);
public JFrame getFrame();
public void setLookingFor(String txt);
public String getLookingFor();
public UndoManager getUndoManager();
public void putValue(String Redo,String Rd);
public Action getActionSave();
public boolean writeFile(File fl) throws Exception;
public Preferences getConfig();
public State getSwingPool();
}