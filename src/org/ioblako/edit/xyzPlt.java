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

import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Chart3DPanel;
import com.orsoncharts.Colors;
import com.orsoncharts.data.xyz.XYZSeries;
import com.orsoncharts.data.xyz.XYZSeriesCollection;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.label.StandardXYZLabelGenerator;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.xyz.ScatterXYZRenderer;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.event.ChangeEvent;
import org.jfree.ui.RefineryUtilities;

import org.ioblako.math.calculator.jc;


/**
 *
 * @author sergey_nikitin
 */
public class xyzPlt extends AbstractFramePlt3D implements FramePlt {
static final long serialVersionUID=100001;
    public xyzPlt() {
        super("");
    }
    public xyzPlt(ArrayList<String> titles, ArrayList<String> XYZdata) throws Exception{
                  super("");
       XYZSeries series;
       XYZSeriesCollection dataset = new XYZSeriesCollection();
        
        Iterator<String>it = titles.iterator();
        String it_next,title=null,subtitle=null,x_title=null,y_title=null,z_title=null;
        while(it.hasNext()){
            it_next=it.next();
            if(it_next.contains("=")){
                switch(it_next.substring(0,it_next.indexOf('=')).trim().toLowerCase()){
                    case "title":
                        title=it_next.substring(it_next.indexOf('=')+1);
                        titles.remove(it_next);
                        it = titles.iterator();
                    break;
                    case "subtitle":
                        subtitle=it_next.substring(it_next.indexOf('=')+1);
                        titles.remove(it_next);
                        it = titles.iterator();
                    break;
                    case "x_title":
                        x_title=it_next.substring(it_next.indexOf('=')+1);
                        titles.remove(it_next);
                        it = titles.iterator();
                    break;
                    case "y_title":
                        y_title=it_next.substring(it_next.indexOf('=')+1);
                        titles.remove(it_next);
                        it = titles.iterator();
                    break;
                    case "z_title":
                        z_title=it_next.substring(it_next.indexOf('=')+1);
                        titles.remove(it_next);
                        it = titles.iterator();
                    break;
                    default:
                    break;
                }
            }
            
        }
       if(title==null)
           title="XYZ Set";
       if(subtitle==null)
           subtitle="XYZ Set";
       
       if(x_title==null)
           x_title="x";
       
       if(y_title==null)
           y_title="y";
       
       if(z_title==null)
           z_title="z";
       
       it=XYZdata.iterator();
       int Title_index=0;
       while(it.hasNext()){
          if(!titles.isEmpty()){
              series=new XYZSeries(titles.get(0));
              titles.remove(0);
          }else{
              series=new XYZSeries(Integer.toString(Title_index));
              Title_index++;
          }
          
           it_next=it.next();
           String[] ct = it_next.split(";");
           //float[][] data = new float[2][ct.length];
          // int i = 0;
           for(String bf: ct){
           // if(bf.startsWith("("))
              //  bf=bf.substring(1);
           // if(bf.endsWith(")"))
              //  bf=bf.substring(0,bf.length()-1);
                 series.add(Double.valueOf(jc.eval("2dbl("+
                                      jc.eval(bf.substring(0,bf.indexOf(',')))+
                                                  ")")),
                        Double.valueOf(jc.eval("2dbl("+jc.eval(bf.substring(bf.indexOf(',')+1,bf.lastIndexOf(',')))+")")),
                        Double.valueOf(jc.eval("2dbl("+jc.eval(bf.substring(bf.lastIndexOf(',')+1))+")")) );
                //data[0][i]=Float.valueOf(jc.eval("2dbl("+jc.eval(bf.substring(0,bf.indexOf(',')))+")"));
                ///data[1][i]=Float.valueOf(jc.eval("2dbl("+jc.eval(bf.substring(bf.indexOf(',')+1))+")"));
                //i++;
        }
           
           
               dataset.add(series);
               
           
       }
       
       
       chart = Chart3DFactory.createScatterChart(title,
               subtitle, dataset, x_title, y_title, z_title);
        XYZPlot plot = (XYZPlot) chart.getPlot();
        //plot.setDimensions(new Dimension3D(10.0, 4.0, 4.0));
        plot.setLegendLabelGenerator(new StandardXYZLabelGenerator(
                StandardXYZLabelGenerator.COUNT_TEMPLATE));
        ScatterXYZRenderer renderer = (ScatterXYZRenderer) plot.getRenderer();
        renderer.setSize(0.15);
        renderer.setColors(Colors.createIntenseColors());
        chart.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(40));
        
        
        /*
          chart = ChartFactory.createScatterPlot(
                               title,
                               x_title,
                               y_title,
                               dataset,
                               PlotOrientation.VERTICAL,
                               true,                     // include legend
                               true,                     // tooltips?
                               false                     // URLs?
                        );
          */
    }
    @Override
    public void stateChanged(ChangeEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setInput(String txt) {
        toShow=txt;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createAndShow() throws Exception {
        ArrayList<String> XYZdata = new ArrayList<>();
        ArrayList<String> titles=new ArrayList<>();
        xyzPlt frameToShow;
        //toShow = toShow.replace(" ","");
        if(!toShow.startsWith("xyzPlt")||toShow.length()<7)
             return;
      String txt = jc.getInside(toShow.substring(7),'(',')'); 
      String data="",bf="";
      boolean addToData=false;
      if(txt.indexOf('{')==-1)
          throw new Exception("xyzPlt is not defined correctly!");
       for(int i = 0;i<txt.length();i++){
            switch(txt.charAt(i)){
                case '{':
                    addToData=true;
                    break;
                case '}':
                    if(!addToData)
                        throw new Exception("xyzPlt is not defined correctly!");
                    data=data+","+bf;
                    XYZdata.add(data);
                    data="";
                    bf="";
                    addToData=false;
                    break;
                case ',':
                    if(addToData)
                        if(data.contentEquals(""))
                          data=data+bf;
                        else
                            data=data+","+bf;
                    else
                        if(!bf.contentEquals(""))
                          titles.add(bf);
                    bf="";
                    break;
                default:
                    bf=bf+txt.charAt(i);
                    break;
            }
       }
       if(txt.endsWith("{"))
          throw new Exception("xyzPlt is not defined correctly!");
      
       
       frameToShow = new xyzPlt(titles,XYZdata);
       //ChartPanel chartP = new ChartPanel(frameToShow.getChart());
       
        DemoPanel content = new DemoPanel(new BorderLayout());
        content.setPreferredSize(DEFAULT_CONTENT_SIZE);
        //Chart3D chart = SurfaceRenderer2.createChart();
        Chart3DPanel chartPanel = new Chart3DPanel(frameToShow.getChart());
        chartPanel.zoomToFit(DEFAULT_CONTENT_SIZE);
        content.setChartPanel(chartPanel);
        content.add(new DisplayPanel3D(chartPanel));
        frameToShow.getContentPane().add(content);
        frameToShow.setJMenuBar(xyzPlt.getMenu(frameToShow.getChart(),frameToShow));
        frameToShow.pack();
        RefineryUtilities.centerFrameOnScreen(frameToShow);
        frameToShow.setVisible(true);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getHelp() {
        return "Example"+System.lineSeparator()+
                "C<-eval(Seq(t,cos(t),sin(t),t={0.1,-pi..pi}))" +System.lineSeparator()+
"A<-eval(Seq(t,t,t,t={0.1,-pi..pi}))" +System.lineSeparator()+
"" +System.lineSeparator()+
"" +System.lineSeparator()+
"xyzPlt(title=3D curves,subtitle=helix,line,helix,{A},{C})";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
