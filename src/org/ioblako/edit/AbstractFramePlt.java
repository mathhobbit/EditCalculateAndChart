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

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.event.ChangeListener;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;

/**
 *
 * @author sergey_nikitin
 */
public abstract class AbstractFramePlt extends ApplicationFrame implements  ActionListener,
                                                                            WindowListener,
                                                                            ChangeListener{
    static final long  serialVersionUID=1103110;
    /**
     *
     */
    public String toShow="";
    JFreeChart chart;
    public AbstractFramePlt(String title) {
        super(title);
    }
    public JFreeChart getChart(){
    return chart;
    }
    public static JMenuBar getMenu( JFreeChart chart,ApplicationFrame jg ){
Action SaveAs = new Plt_SaveAs_Action(chart,"Save as...",null,"Save as...");
Action Quit = new Plt_Quit_Action(jg,"Quit",null,"Quit");

    JMenuBar JMB = new JMenuBar();
                
    JMenu file = new JMenu("File");
    JMB.add(file);
    file.add(SaveAs);file.add(Quit);
    return JMB;
}
    @Override
    public void actionPerformed(ActionEvent e) {

}
  //React to window events.
    @Override
    public void windowIconified(WindowEvent e) {
//        stopAnimation();
    }
    @Override
    public void windowDeiconified(WindowEvent e) {
 //       startAnimation();
    }

    @Override
    public void windowOpened(WindowEvent e) {}
   // public void windowClosing(WindowEvent e) {System.exit(0);}
    @Override
     public void windowClosing(WindowEvent e) {}
   // public void windowClosed(WindowEvent e){ System.exit(0);}
    @Override
    public void windowClosed(WindowEvent e){}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
 void addWindowListener(Window w) {
        w.addWindowListener(this);
    }
}
