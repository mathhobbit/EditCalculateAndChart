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
import javax.swing.event.ChangeEvent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author sergey_nikitin
 */
public class linePlt extends AbstractFramePlt implements FramePlt {
    static final long serialVersionUID=100001;
    public linePlt(){
        super("");
    }
    @Override
    public void setInput(String txt){
        toShow=txt;
    }
   public linePlt(String title, String x_title, String y_title, String Categories) throws Exception{
        super("");
       DefaultCategoryDataset dataset = new DefaultCategoryDataset();
       String[] ct = Categories.split(";");
        for(String bf: ct){
          //  if(bf.startsWith("("))
              //  bf=bf.substring(1);
           // if(bf.endsWith(")"))
              //  bf=bf.substring(0,bf.length()-1);
         dataset.addValue(new Double(jc.eval("2dbl("+jc.eval(bf.substring(0,bf.indexOf(',')))+")")),
                                  bf.substring(bf.indexOf(',')+1,bf.lastIndexOf(',')),
                                  bf.substring(bf.lastIndexOf(',')+1));
        }
        if(title==null)
          title = "Line Chart";
        if(y_title == null)
           y_title = "y";
        if(x_title == null)
            x_title="x";
        
          chart = ChartFactory.createLineChart(
                               title,
                               x_title,
                               y_title,
                               dataset,
                               PlotOrientation.VERTICAL, // orientation
                               false,                     // include legend
                               true,                     // tooltips?
                               false                     // URLs?
                        );
         chart.setBackgroundPaint(Color.white);
         LineAndShapeRenderer renderer = (LineAndShapeRenderer)( (CategoryPlot)chart.getPlot()).getRenderer(); 
         //renderer.setShapesVisible(true);
         renderer.setDrawOutlines(true);
         renderer.setUseFillPaint(true);
         //renderer.setFillPaint(Color.white);

         
         /*
         if(space!=null){
             BarRenderer renderer = (BarRenderer) ((CategoryPlot)chart.getPlot()).getRenderer();
              renderer.setItemMargin(new Double(space));
         }
         */
    
        
    }

    
    
    /**
     *
     * @throws Exception
     */
    @Override
    public void createAndShow() throws Exception{
        linePlt frameToShow;
        //toShow = toShow.replace(" ","");
        if(!toShow.startsWith("linePlt")||toShow.length()<8)
             return;
      String tt = jc.getInside(toShow.substring(8),'(',')'); 
      String title=null,y_title=null,x_title=null,space=null,bf="";
      if(tt.indexOf('{')==-1)
          throw new Exception("linePlot is not defined correctly!");
       String txt=tt.substring(0,tt.indexOf('{'));
       for(int i = 0;i<txt.length();i++){
            if(txt.charAt(i) == ','){
                if(bf.contentEquals(""))
                       throw new Exception("linePlot is not defined correctly!");
                 if(bf.indexOf('=')!= -1){
                    switch (bf.substring(0,bf.indexOf('=')).toLowerCase().trim()) {
                        case "title":
                            if(title != null)
                                throw new Exception("linePlot is not defined correctly!");
                            title=bf.substring(bf.indexOf('=')+1);
                            bf="";
                            break;
                        case "y_title":
                            if(y_title != null)
                                throw new Exception("linePlot is not defined correctly!");
                            y_title=bf.substring(bf.indexOf('=')+1);
                            bf="";
                            break;
                    /*else
                    if(bf.substring(0,bf.indexOf('=')).toLowerCase().contentEquals("space")){
                    if(space != null)
                    throw new Exception("linePlot is not defined correctly!");
                    space=bf.substring(bf.indexOf('=')+1);
                    bf="";
                    } */
                        case "x_title":
                            if(x_title != null)
                                throw new Exception("linePlot is not defined correctly!");
                            x_title=bf.substring(bf.indexOf('=')+1);
                            bf="";
                            break;
                        default:
                            break;
                    }
                 }
                 else{
                     if(title == null){
                         title=bf;
                         bf="";
                     }else
                     if(x_title == null){
                         x_title=bf;
                         bf="";
                     }else
                     if(y_title == null){
                         y_title=bf;
                         bf="";
                     }/* else
                     if(space == null){
                         space=bf;
                         bf="";
                         continue;
                     }*/else
                     throw new Exception("linePlot is not defined correctly!");
                 }
            }
            else
                bf=bf+txt.charAt(i);
       }
       if(tt.endsWith("{"))
          throw new Exception("barPlot is not defined correctly!");
       txt = tt.substring(tt.indexOf('{')+1);
       
       frameToShow = new linePlt(title,x_title,y_title,jc.getInside(txt,'{','}'));
       ChartPanel chartPanel = new ChartPanel(frameToShow.getChart());
       chartPanel.setPreferredSize(new java.awt.Dimension(500, 500));
       chartPanel.setEnforceFileExtensions(false);
       frameToShow.setContentPane(chartPanel);
       frameToShow.setJMenuBar(linePlt.getMenu(frameToShow.getChart(),frameToShow));
       frameToShow.pack();
       RefineryUtilities.centerFrameOnScreen(frameToShow);
       frameToShow.setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getHelp() {
        return "Line plot." + System.lineSeparator()+
                "linePlt(title=Test scores,y_title=average grade,x_title=Tests,{75,Calculus I,Test 1;60,Calculus I,Test 2;65,Calculus I,Test 3;80,Calculus I,Final Test;" +System.lineSeparator()+
"           95,Calculus II,Tes 1 1;55,Calculus II,Test 2;85,Calculus II,Test 3;75,Calculus II,Final Test})" +System.lineSeparator()+
"" +System.lineSeparator()+
"Introducing variables" +System.lineSeparator()+
"" +System.lineSeparator()+
"A<-75,Calculus I,Test 1;60,Calculus I,Test 2;65,Calculus I,Test 3;80,Calculus I,Final Test;" +System.lineSeparator()+
"" +System.lineSeparator()+
"and" +System.lineSeparator()+
"" +System.lineSeparator()+
"B<-95,Calculus II,Tes 1 1;55,Calculus II,Test 2;85,Calculus II,Test 3;75,Calculus II,Final Test" +System.lineSeparator()+
"" +System.lineSeparator()+
"we can write the same procedure as" +System.lineSeparator()+
"" +System.lineSeparator()+
"linePlt(title=Test scores,y_title=average grade,x_title=Tests,{AB})";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     
}
