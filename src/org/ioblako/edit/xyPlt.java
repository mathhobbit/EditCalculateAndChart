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

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.event.ChangeEvent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.swing.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.swing.UIUtils;
import org.ioblako.math.calculator.jc;
/**
 *
 * @author sergey_nikitin
 */
public class xyPlt extends AbstractFramePlt implements FramePlt{
    static final long serialVersionUID=100001;
      public xyPlt(){
        super("");
    }
    
        @Override
    public void setInput(String txt){
    toShow=txt.trim();
}
      @SuppressWarnings("SuspiciousIndentAfterControlStatement")
    public xyPlt(ArrayList<String> titles, ArrayList<String> XYdata) throws Exception{
        super("");
       XYSeries series;
       XYSeriesCollection dataset = new XYSeriesCollection();
        
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
           title="XY Chart";
       
       if(x_title==null)
           x_title="x";
       
       if(y_title==null)
           y_title="y";
       
       it=XYdata.iterator();
       int Title_index=0;
       while(it.hasNext()){
          if(!titles.isEmpty()){
              series=new XYSeries(titles.get(0));
              titles.remove(0);
          }else{
              series=new XYSeries(Integer.toString(Title_index));
              Title_index++;
          }
          
           it_next=it.next();
           String[] ct = it_next.split(";");
           //float[][] data = new float[2][ct.length];
          // int i = 0;
           for(String bf: ct){
            //if(bf.startsWith("("))
              //  bf=bf.substring(1);
           // if(bf.endsWith(")"))
              //  bf=bf.substring(0,bf.length()-1);
                series.add(Double.valueOf(jc.eval("2dbl("+
                                      jc.eval(bf.substring(0,bf.indexOf(',')))+
                                                  ")")),
                        Double.valueOf(jc.eval("2dbl("+jc.eval(bf.substring(bf.indexOf(',')+1))+")")));
                //data[0][i]=Float.valueOf(jc.eval("2dbl("+jc.eval(bf.substring(0,bf.indexOf(',')))+")"));
                ///data[1][i]=Float.valueOf(jc.eval("2dbl("+jc.eval(bf.substring(bf.indexOf(',')+1))+")"));
                //i++;
        }
           
           
               dataset.addSeries(series);
               
           
       }
       
        
        
        
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
          
    }

    
    
    /**
     *
     * @throws Exception
     */
    @Override
    public void createAndShow() throws Exception{
        ArrayList<String> XYdata = new ArrayList<>();
        ArrayList<String> titles=new ArrayList<>();
        xyPlt frameToShow;
        //toShow = toShow.replace(" ","");
        if(!toShow.startsWith("xyPlt")||toShow.length()<6)
             return;
      String txt = jc.getInside(toShow.substring(6),'(',')'); 
      String data="",bf="";
      boolean addToData=false;
      if(txt.indexOf('{')==-1)
          throw new Exception("xyPlt is not defined correctly!");
       for(int i = 0;i<txt.length();i++){
            switch(txt.charAt(i)){
                case '{':
                    addToData=true;
                    break;
                case '}':
                    if(!addToData)
                        throw new Exception("xyPlt is not defined correctly!");
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
          throw new Exception("xyPlt is not defined correctly!");
      
       
       frameToShow = new xyPlt(titles,XYdata);
       ChartPanel chartP = new ChartPanel(frameToShow.getChart());
       chartP.setPreferredSize(new java.awt.Dimension(500, 500));
       chartP.setEnforceFileExtensions(false);
      
       frameToShow.setContentPane(chartP);
       frameToShow.setJMenuBar(xyPlt.getMenu(frameToShow.getChart(),frameToShow));
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
        return "XY PLOT" + System.lineSeparator()+
"" +System.lineSeparator()+
"To plot a curve use \"xyPlt\" instead." +System.lineSeparator()+
"" +System.lineSeparator()+
"C<-eval(Seq(x^2-1,x^3-x,x={0.1,-2..2}))" +System.lineSeparator()+
"D<-eval(Seq(0.1*x^4-x,x^3-0.1*x^6,x={0.1,-1.5..1.5}))" +System.lineSeparator()+
"" +System.lineSeparator()+
"xyPlt(title=cubic curves,y_title=y values,x_title=x values,C,D)";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
