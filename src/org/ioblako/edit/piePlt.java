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
import org.jfree.chart.swing.ChartPanel;
import org.jfree.chart.plot.pie.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.swing.UIUtils;

import org.ioblako.math.calculator.jc;


/**
 *
 * @author sergey_nikitin
 */
public class piePlt extends AbstractFramePlt implements FramePlt{
    static final long serialVersionUID=100001;
       public piePlt(){
        super("");
    }
    @Override
    public void setInput(String txt){
    toShow=txt.trim();
}
    public piePlt(String title, String Categories) throws Exception{
        super("");
       DefaultPieDataset dataset = new DefaultPieDataset();
       String[] ct = Categories.split(";");
        for(String bf: ct){
           // if(bf.startsWith("("))
               // bf=bf.substring(1);
           // if(bf.endsWith(")"))
               // bf=bf.substring(0,bf.length()-1);
                 dataset.setValue(bf.substring(0,bf.indexOf(',')),
                        Double.valueOf(jc.eval("2dbl("+jc.eval(bf.substring(bf.indexOf(',')+1))+")")));
        }
        if(title==null)
          title = "Pie Chart";
        
        
           chart = ChartFactory.createPieChart(
                               title,
                               dataset,
                               true,                     // include legend
                               true,                     // tooltips?
                               false                     // URLs?
                        );
          
    }

    
    
    /**
     *
     * @throws Exception
     */
    @Override
    public void createAndShow() throws Exception{
        piePlt frameToShow;
        //toShow = toShow.replace(" ","");
        if(!toShow.startsWith("piePlt")||toShow.length()<7)
             return;
      String tt = jc.getInside(toShow.substring(7),'(',')'); 
      String title=null,exploded=null,bf="";
      if(tt.indexOf('{')==-1)
          throw new Exception("piePlot is not defined correctly!");
       String txt=tt.substring(0,tt.indexOf('{'));
       for(int i = 0;i<txt.length();i++){
            if(txt.charAt(i) == ','){
                if(bf.contentEquals(""))
                       throw new Exception("piePlot is not defined correctly!");
                 if(bf.indexOf('=')!= -1){
                    switch (bf.substring(0,bf.indexOf('=')).toLowerCase().trim()) {
                        case "title":
                            if(title != null)
                                throw new Exception("piePlot is not defined correctly!");
                            title=bf.substring(bf.indexOf('=')+1);
                            bf="";
                            break;
                        default:
                            if(exploded != null)
                                throw new Exception("piePlot is not defined correctly!");
                            exploded=bf;
                            bf="";
                            break;
                    }
                 }
                 else{
                     if(title == null){
                         title=bf;
                         bf="";
                     }else
                     throw new Exception("piePlot is not defined correctly!");
                 }
            }
            else
                bf=bf+txt.charAt(i);
       }
       if(tt.endsWith("{"))
          throw new Exception("barPlot is not defined correctly!");
       txt = tt.substring(tt.indexOf('{')+1);
       
       frameToShow = new piePlt(title,jc.getInside(txt,'{','}'));
       ChartPanel chartP = new ChartPanel(frameToShow.getChart());
       chartP.setPreferredSize(new java.awt.Dimension(500, 500));
       chartP.setEnforceFileExtensions(false);
       if(exploded != null){
           PiePlot plot = (PiePlot) frameToShow.getChart().getPlot(); 
            plot.setExplodePercent(exploded.substring(0,exploded.indexOf('=')),
                    Double.valueOf(jc.eval("2dbl("+jc.eval(exploded.substring(exploded.indexOf('=')+1))+")")) );
         }
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
        return "PIE PLOT" +System.lineSeparator()+
"" +System.lineSeparator()+
"piePlt(Pie plot title,piece 1=0.3,{piece 1,10;piece 2,30;piece 3,40;piece 4,20}) " +System.lineSeparator()+
"" +System.lineSeparator()+
"or" +System.lineSeparator()+
"" +System.lineSeparator()+
"piePlt(Pie plot title,{piece 1,10;piece 2,30;piece 3,40;piece 4,20}) ";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
