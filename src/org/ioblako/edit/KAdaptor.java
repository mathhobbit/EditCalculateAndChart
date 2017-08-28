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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class KAdaptor extends KeyAdapter{
private TextEdit TEdt;
public KAdaptor(TextEdit ed){
          TEdt=ed;
}
                public void keyPressed(KeyEvent e) {
                        TEdt.setChanged(true);
                        //Save.setEnabled(true);
                        //SaveAs.setEnabled(true);
                        TEdt.setEnabled("Save",true);
                        TEdt.setEnabled("SaveAs",true);
                   /*   switch (e.getKeyCode())
                                   {
                                      case KeyEvent.VK_M :
                                         if(e.isControlDown())
                                            (new Calc_Action(TEdt,null,null,null)).actionPerformed(null);
                                       break;
                                   }
                    */
                }


}
