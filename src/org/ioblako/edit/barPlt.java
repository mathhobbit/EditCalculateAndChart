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

import javax.swing.event.ChangeEvent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ui.UIUtils;

import org.ioblako.math.calculator.jc;


/**
 *
 * @author sergey_nikitin
 */
public class barPlt extends AbstractFramePlt implements FramePlt {
    static final long  serialVersionUID=1203110; 
    public barPlt(){
        super("");
    }
    @Override
    public void setInput(String txt){
    toShow=txt;
}
    public barPlt(String title, String x_title, String y_title, String space, String Categories) throws Exception{
        super("");
       DefaultCategoryDataset dataset = new DefaultCategoryDataset();
       String[] ct = Categories.split(";");
        for(String bf: ct){
           // if(bf.startsWith("("))
           //     bf=bf.substring(1);
           // if(bf.endsWith(")"))
             //   bf=bf.substring(0,bf.length()-1);
         dataset.addValue(Double.valueOf(jc.eval("2dbl("+jc.eval(bf.substring(0,bf.indexOf(',')))+")")),
                                  bf.substring(bf.indexOf(',')+1,bf.lastIndexOf(',')),
                                  bf.substring(bf.lastIndexOf(',')+1));
        }
        if(title==null)
          title = "Bar Chart";
        if(y_title == null)
           y_title = "y";
        if(x_title == null)
            x_title="x";
        
          chart = ChartFactory.createBarChart(
                               title,
                               x_title,
                               y_title,
                               dataset,
                               PlotOrientation.VERTICAL, // orientation
                               true,                     // include legend
                               true,                     // tooltips?
                               false                     // URLs?
                        );
         if(space!=null){
             BarRenderer renderer = (BarRenderer) ((CategoryPlot)chart.getPlot()).getRenderer();
              renderer.setItemMargin(Double.valueOf(space));
         }
    
        
    }

    
    
    /**
     *
     * @throws Exception
     */
    @Override
    public void createAndShow() throws Exception{
        barPlt frameToShow;
        //toShow = toShow.replace(" ","");
        if(!toShow.startsWith("barPlt")||toShow.length()<7)
             return;
      String tt = jc.getInside(toShow.substring(7),'(',')'); 
      String title=null,y_title=null,x_title=null,space=null,bf="";
      if(tt.indexOf('{')==-1)
          throw new Exception("barPlot is not defined correctly!");
       String txt=tt.substring(0,tt.indexOf('{'));
       for(int i = 0;i<txt.length();i++){
            if(txt.charAt(i) == ','){
                if(bf.contentEquals(""))
                       throw new Exception("barPlot is not defined correctly!");
                 if(bf.indexOf('=')!= -1){
                    switch (bf.substring(0,bf.indexOf('=')).toLowerCase().trim()) {
                        case "title":
                            if(title != null)
                                throw new Exception("barPlot is not defined correctly!");
                            title=bf.substring(bf.indexOf('=')+1);
                            bf="";
                            break;
                        case "y_title":
                            if(y_title != null)
                                throw new Exception("barPlot is not defined correctly!");
                            y_title=bf.substring(bf.indexOf('=')+1);
                            bf="";
                            break;
                        case "x_title":
                            if(x_title != null)
                                throw new Exception("barPlot is not defined correctly!");
                            x_title=bf.substring(bf.indexOf('=')+1);
                            bf="";
                            break;
                        case "space":
                            if(space != null)
                                throw new Exception("barPlot is not defined correctly!");
                            space=bf.substring(bf.indexOf('=')+1);
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
                     }else
                     if(space == null){
                         space=bf;
                         bf="";
                     }else
                     throw new Exception("barPlot is not defined correctly!");
                 }
            }
            else
                bf=bf+txt.charAt(i);
       }
       if(tt.endsWith("{"))
          throw new Exception("barPlot is not defined correctly!");
       txt = tt.substring(tt.indexOf('{')+1);
       
       frameToShow = new barPlt(title,x_title,y_title,space,jc.getInside(txt,'{','}'));
       ChartPanel chartP = new ChartPanel(frameToShow.getChart());
       chartP.setPreferredSize(new java.awt.Dimension(500, 500));
       chartP.setEnforceFileExtensions(false);
       frameToShow.setContentPane(chartP);
       frameToShow.setJMenuBar(barPlt.getMenu(frameToShow.getChart(),frameToShow));
       frameToShow.pack();
        UIUtils.centerFrameOnScreen(frameToShow);
       frameToShow.setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getHelp() {
        return "Bar chart. "+System.lineSeparator()+
            "barPlt(title=bar chart example,y_title=y values,x_title=x values,{1,test 1,fall;2,test 2,fall;2.3,test 3,fall;1.8,test 1,spring;2.5,test 2,spring;3,test 3,spring})"+System.lineSeparator()+
                "One can implement it as follows"+System.lineSeparator()+
                "C<-1,test 1,fall;2,test 2,fall;2.3,test 3,fall;1.8,test 1,spring;2.5,test 2,spring;3,test 3,spring"+System.lineSeparator()+
                "and then"+System.lineSeparator()+
                "barPlt(title=bar chart example,y_title=y values,x_title=x values,{C})";
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
