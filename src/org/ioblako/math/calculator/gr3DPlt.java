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
package org.ioblako.math.calculator;

import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Chart3DPanel;
import com.orsoncharts.Range;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.data.function.Function3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.legend.LegendAnchor;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.RainbowScale;
import com.orsoncharts.renderer.xyz.SurfaceRenderer;
import com.orsoncharts.util.Orientation;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author Evan
 */
public class gr3DPlt extends AbstractFramePlt3D implements FramePlt, Function3D{
     static final long  serialVersionUID=2203110; 
     private String function=null;
     private String title="";
     private final ArrayList<String> Vars = new ArrayList<>();
     private final ArrayList<String> rangeLeft = new ArrayList<>();
     private final ArrayList<String> rangeRight = new ArrayList<>();
     
     
     
    public gr3DPlt(){
        super("");
    }
    public gr3DPlt(String title) {
        super(title);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setInput(String txt) {
        toShow=txt;
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createAndShow() throws Exception {
               gr3DPlt frameToShow;
        
        
        //toShow = toShow.replace(" ","");
        if(!toShow.startsWith("gr3DPlt")||toShow.length()<8)
             return;
         String txt = jc.getInside(toShow.substring(8),'(',')'); 
          String bf;
          
          String ExceptionMessage="gr3DPlt is not defined correctly!";
      if(txt.indexOf('{')==-1||txt.indexOf('=')==-1)
          throw new Exception(ExceptionMessage);
     int splitIndex = txt.substring(0,txt.indexOf('{')).lastIndexOf(',');
         if(splitIndex == -1)
             throw new Exception(ExceptionMessage); 
         bf = txt.substring(splitIndex+1);
        function=txt.substring(0,splitIndex);
        title=function;
        frameToShow=new gr3DPlt(title);
       if(function.indexOf('=')!=-1)
           function=function.substring(function.indexOf('=')+1);
      String[] raw = bf.split("\\.\\.");
        for(String chunk:raw){
            if(chunk.indexOf('=')!=-1){
                if(chunk.indexOf('}')!=-1)
                  Vars.add(chunk.substring(chunk.indexOf(',')+1,chunk.indexOf('=')).trim());
                else
                    Vars.add(chunk.substring(0,chunk.indexOf('=')).trim());
                if(chunk.indexOf('{')==-1)
                    throw new Exception(ExceptionMessage);
                rangeLeft.add(jc.eval(chunk.substring(chunk.lastIndexOf('{')+1)));
                if(chunk.indexOf('}')!=-1)
                    rangeRight.add(jc.eval(chunk.substring(0,chunk.indexOf('}'))));
            }
            else{
                if(chunk.indexOf('}')==-1)
                    throw new Exception(ExceptionMessage);
                
                rangeRight.add(jc.eval(chunk.substring(0,chunk.indexOf('}'))));
            }
        }
        if(Vars.size()!=rangeLeft.size()||Vars.size()!=rangeRight.size())
            throw new Exception(ExceptionMessage);  
        if(Vars.size()<2)
            throw new Exception(ExceptionMessage);
             
              chart = Chart3DFactory.createSurfaceChart(
                "", 
                function, 
                this, Vars.get(0),"Z",Vars.get(1));
        XYZPlot plot = (XYZPlot) chart.getPlot();
       // plot.setDimensions(new Dimension3D(10, 5, 10));
        ValueAxis3D xAxis = plot.getXAxis();
        //System.out.println(rangeLeft.get(0));
        xAxis.setRange(new Double(rangeLeft.get(0)), new Double(rangeRight.get(0)));
        ValueAxis3D zAxis = plot.getZAxis();
        zAxis.setRange(new Double(rangeLeft.get(1)), new Double(rangeRight.get(1)));
        SurfaceRenderer renderer = (SurfaceRenderer) plot.getRenderer();
        renderer.setColorScale(new RainbowScale(new Range(-1.0, 1.0)));
        renderer.setDrawFaceOutlines(false);
        chart.setLegendPosition(LegendAnchor.BOTTOM_RIGHT, 
                Orientation.VERTICAL);
               
        DemoPanel content = new DemoPanel(new BorderLayout());
        content.setPreferredSize(DEFAULT_CONTENT_SIZE);
        //Chart3D chart = SurfaceRenderer2.createChart();
        Chart3DPanel chartPanel = new Chart3DPanel(chart);
        chartPanel.zoomToFit(DEFAULT_CONTENT_SIZE);
        content.setChartPanel(chartPanel);
        content.add(new DisplayPanel3D(chartPanel));
        frameToShow.getContentPane().add(content);
        //frameToShow.setJMenuBar(gr3DPlt.getMenu(frameToShow.getChart(),frameToShow));
        frameToShow.pack();
        RefineryUtilities.centerFrameOnScreen(frameToShow);
       frameToShow.setVisible(true);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getValue(double x, double y) {
        if(function==null)
            return 0.0;
        double ret;
        try{
        ret =new Double(jc.eval("2dbl("+
           SmartReplace.get(SmartReplace.get(function,Vars.get(0),'('+Double.toString(x)+')'),Vars.get(1),'('+ Double.toString(y)+')')+")"));
           // function.replace(Vars.get(0),'('+Double.toString(x)+')').replace(Vars.get(1),'('+ Double.toString(y)+')')+
          //      ")"));
        }
        catch(Exception e){
            return 0.0;
            //JOptionPane.showMessageDialog(this,e.getMessage());
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    return ret;
    }

    @Override
    public String getHelp() {
        return "Plotting a graph of a 3D function" +System.lineSeparator()+
                "Example"+System.lineSeparator()+
                "gr3DPlt(x^2+y^2,x={-1..1},y={-1..1})";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
