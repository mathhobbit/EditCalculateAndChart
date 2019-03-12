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

import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.orsoncharts.Chart3DPanel;

/**
 * The base class for panels created by demo applications.  Some demos will 
 * subclass to add extra controls in addition to the main {@link Chart3DPanel}.
 */
@SuppressWarnings("serial")
public class DemoPanel extends JPanel {
    
    /** 
     * A list of the chart panels on the demo panel (usually just one, but 
     * there can be multiple panels).
     */
    private List<Chart3DPanel> chartPanels;
    
    /**
     * Creates a new instance.
     * 
     * @param layout  the layout manager. 
     */
    public DemoPanel(LayoutManager layout) {
        super(layout);
        this.chartPanels = new ArrayList<Chart3DPanel>();
    }
    
    /**
     * Returns the chart panel for this demo panel.  In the case where there
     * are multiple chart panels, this method will return the first one.
     * 
     * @return The chart panel (possibly {@code null}). 
     */
    public Chart3DPanel getChartPanel() {
        if (this.chartPanels.isEmpty()) {
            return null;
        }
        return this.chartPanels.get(0);    
    }
    
    /**
     * Returns the {@link Chart3DPanel} from the demo panel.
     * 
     * @return The {@link Chart3DPanel}. 
     */
    public List<Chart3DPanel> getChartPanels() {
        return this.chartPanels;
    }
    
    /**
     * Sets the chart panel that is displayed within this demo panel (for the
     * case where there is only one chart panel).
     * 
     * @param panel  the panel.
     */
    public void setChartPanel(Chart3DPanel panel) {
        this.chartPanels.clear();
        this.chartPanels.add(panel);
    }
    
    /**
     * Adds the {@link Chart3DPanel} for this demo panel.  This can be
     * accessed by code that wants to do something to the chart.
     * 
     * @param panel  the panel. 
     */
    public void addChartPanel(Chart3DPanel panel) {
        this.chartPanels.add(panel);
    }
}
