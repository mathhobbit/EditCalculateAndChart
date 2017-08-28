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

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.event.ChangeEvent;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.VectorRenderer;
import org.jfree.data.xy.VectorSeries;
import org.jfree.data.xy.VectorSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author sergey_nikitin
 */
public class vfPlt extends AbstractFramePlt implements FramePlt{
       static final long serialVersionUID=100001;
    public vfPlt(){
        super("");
    }
    @Override
    public void stateChanged(ChangeEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setInput(String txt) {
        toShow=txt.trim();
    }
    
         @SuppressWarnings("SuspiciousIndentAfterControlStatement")
    public vfPlt(ArrayList<String> titles, ArrayList<String> XYdata) throws Exception{
        super("");
       VectorSeries series;
       VectorSeriesCollection dataset = new VectorSeriesCollection();
        
        Iterator<String>it = titles.iterator();
        String it_next,title=null,x_title=null,y_title=null;
        while(it.hasNext()){
            it_next=it.next();
            if(it_next.contains("=")){
                switch(it_next.substring(0,it_next.indexOf('=')).trim().toLowerCase()){
                    case "title":
                        title=it_next.substring(it_next.indexOf('=')+1);
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
                    default:
                    break;
                }
            }
            
        }
       if(title==null)
           title="Vector Chart";
       
       if(x_title==null)
           x_title="x";
       
       if(y_title==null)
           y_title="y";
       it=XYdata.iterator();
       int Title_index=0;
       while(it.hasNext()){
          if(!titles.isEmpty()){
              series=new VectorSeries(titles.get(0));
              titles.remove(0);
          }else{
              series=new VectorSeries(Integer.toString(Title_index));
              Title_index++;
          }
          
           it_next=it.next();
           String[] ct = it_next.split(";");
           //float[][] data = new float[2][ct.length];
          // int i = 0;
          String[] bfSplit;
           for(String bf: ct){
            //if(bf.startsWith("("))
              //  bf=bf.substring(1);
            //if(bf.endsWith(")"))
               // bf=bf.substring(0,bf.length()-1);
            bfSplit=bf.split(",");
                series.add(new Double(jc.eval("2dbl("+
                                      jc.eval(bfSplit[0])+
                                                  ")")),
                        new Double(jc.eval("2dbl("+jc.eval(bfSplit[1])+")")),
                       new Double(jc.eval("2dbl("+jc.eval(bfSplit[2])+")")),
                       new Double(jc.eval("2dbl("+jc.eval(bfSplit[3])+")")));
                //data[0][i]=Float.valueOf(jc.eval("2dbl("+jc.eval(bf.substring(0,bf.indexOf(',')))+")"));
                ///data[1][i]=Float.valueOf(jc.eval("2dbl("+jc.eval(bf.substring(bf.indexOf(',')+1))+")"));
                //i++;
        }
           
           
               dataset.addSeries(series);
               
           
       }
        NumberAxis xAxis = new NumberAxis(x_title);
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        xAxis.setLowerMargin(0.01);
        xAxis.setUpperMargin(0.01);
        xAxis.setAutoRangeIncludesZero(false);
        
         NumberAxis yAxis = new NumberAxis(y_title);
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        yAxis.setLowerMargin(0.01);
        yAxis.setUpperMargin(0.01);
        yAxis.setAutoRangeIncludesZero(false);
        VectorRenderer renderer = new VectorRenderer();
        renderer.setSeriesPaint(0, Color.blue);
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
          plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5, 5, 5, 5));
        plot.setOutlinePaint(Color.black);

        chart = new JFreeChart(title, plot);
        chart.setBackgroundPaint(Color.white);
      
          
    }

    

    @Override
    public void createAndShow() throws Exception {
        ArrayList<String> XYdata = new ArrayList<>();
        ArrayList<String> titles=new ArrayList<>();
        vfPlt frameToShow;
        
        
        //toShow = toShow.replace(" ","");
        if(!toShow.startsWith("vfPlt")||toShow.length()<6)
             return;
      String txt = jc.getInside(toShow.substring(6),'(',')'); 
      String data="",bf="";
      boolean addToData=false;
      if(txt.indexOf('{')==-1)
          throw new Exception("vfPlt is not defined correctly!");
       for(int i = 0;i<txt.length();i++){
            switch(txt.charAt(i)){
                case '{':
                    addToData=true;
                    break;
                case '}':
                    if(!addToData)
                        throw new Exception("vfPlt is not defined correctly!");
                    data=data+","+bf;
                    XYdata.add(data);
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
          throw new Exception("vfPlt is not defined correctly!");
      
       
       frameToShow = new vfPlt(titles,XYdata);
       ChartPanel chartP = new ChartPanel(frameToShow.getChart());
       chartP.setPreferredSize(new java.awt.Dimension(500, 500));
       chartP.setEnforceFileExtensions(false);
      
       frameToShow.setContentPane(chartP);
       frameToShow.setJMenuBar(barPlt.getMenu(frameToShow.getChart(),frameToShow));
       frameToShow.pack();
        RefineryUtilities.centerFrameOnScreen(frameToShow);
       frameToShow.setVisible(true);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getHelp() {
        return "VECTOR FIELD PLOT" + System.lineSeparator()+
"" +System.lineSeparator()+
"To create a vector field plot use \"vfPlt\"" +System.lineSeparator()+
"" +System.lineSeparator()+
"A<-eval(Seq(x,y,y/(1+x^2+y^2),-x/(1+x^2+y^2),x={0.5,-2..2},y={0.5,-2..2}))" +System.lineSeparator()+
"vfPlt({A})" +System.lineSeparator()+
"" +System.lineSeparator()+
"or" +System.lineSeparator()+
"" +System.lineSeparator()+
"B<-eval(Seq(x,y,cos(x+y)/(2+x^2+y^2),x/(2+x^2+y^2),x={0.5,-3..3},y={0.5,-3..3}))" +System.lineSeparator()+
"vfPlt({B})";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
