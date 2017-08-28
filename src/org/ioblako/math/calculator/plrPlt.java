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
import java.awt.Component;
import java.awt.Font;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import org.ioblako.math.linearalgebra.Fraction.Fraction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.chart.renderer.DefaultPolarItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author sergey_nikitin
 */
public class plrPlt extends AbstractFramePlt implements FramePlt {
    static final long serialVersionUID=100001;
private static String FunctionToPlot;
private static String Var="x";
private static String Value="y";

private static JSlider[] sliders;
private static JLabel[] sliderLabels;
private static ArrayList<BigDecimal> rangesLeft;
private static ArrayList<BigDecimal> rangesRight;
private static ArrayList<BigDecimal> steps;
private static ArrayList<BigDecimal> varsValues;
private static ArrayList<String> vars;
private static String[] function;
   
ChartPanel chartP=null;
    
    @Override
    public void setInput(String txt){
    toShow=txt.trim();
}
    public plrPlt(){
        super("");
    }
    public plrPlt(String txt) throws Exception{
        super("");
        toShow=txt.trim();
      preProcess("plrPlt(",toShow);
      Var=vars.get(0);
      String   chartName=FunctionToPlot;
      for(int j = 1; j < vars.size();j++)
          chartName = SmartReplace.get(chartName,vars.get(j),varsValues.get(j).toPlainString()); 
         //chartName = chartName.replace(vars.get(j),varsValues.get(j).toPlainString()); 

        chart = createPolarChart(getDataset(),chartName);
     
          
    }
    private JFreeChart createPolarChart(final XYDataset dataset,String chartName) {
       if(chartName == null)
          chartName=FunctionToPlot;
        final JFreeChart chRt = ChartFactory.createPolarChart(
            chartName, dataset, true, true, false
        ); 
        final PolarPlot plot = (PolarPlot) chRt.getPlot();
        final DefaultPolarItemRenderer renderer = (DefaultPolarItemRenderer) plot.getRenderer();
        renderer.setShapesVisible(false);
//renderer.setShapesFilled(false);
       // renderer.setSeriesFilled(0, false);
        return chRt;
    }
    
private  XYDataset getDataset() throws Exception{
if(vars.size()!= varsValues.size())
  throw new Exception("function formatting error: a variable name is missing");

String[] newFunction = new String[function.length];
BigDecimal pt, rightEnd=rangesRight.get(0), step=steps.get(0);
String bf;

XYSeries[] series=new XYSeries[function.length];

for(int i = 0; i < function.length;i++){
series[i] = new XYSeries(function[i]);
newFunction[i] = function[i];
for(int j = 1;j < vars.size();j++)
    newFunction[i]=SmartReplace.get(newFunction[i],vars.get(j),varsValues.get(j).toPlainString());  
    //newFunction[i]=newFunction[i].replace(vars.get(j),varsValues.get(j).toPlainString());  
pt=rangesLeft.get(0);
while(pt.compareTo(rightEnd)<0){
//System.out.println(newFunction[i].replace(Var,pt.toPlainString()));
   bf=jc.eval(SmartReplace.get(newFunction[i],Var,pt.toPlainString()));
 //bf=jc.eval(newFunction[i].replace(Var,pt.toPlainString()));

if(bf.indexOf('/')!=-1)
 series[i].add(pt,(new Fraction(bf)).toBigDecimal());
else
 series[i].add(pt,new BigDecimal(bf));
   pt=pt.add(step);
}
}
XYSeriesCollection dataset = new XYSeriesCollection();
      for (XYSeries serie : series) {
          dataset.addSeries(serie);
      }
return dataset;

}
private void preProcess(String fnctn, String inp) throws Exception{

toShow=inp.replace(" ","");
String[] plot;

if(!toShow.contains(fnctn))
   throw new Exception ("Need to have "+fnctn+"!");
if(toShow.contains("{")){

String[] hndl = jc.hInside(fnctn,toShow,'(',')');

String[] prePlot = jc.hInside("{",hndl[1],'{','}');
FunctionToPlot=prePlot[1];
 function = prePlot[1].split(",");
for(int j = 0 ; j< function.length; j++)
  if(function[j].indexOf('=')!=-1)
    function[j] = function[j].substring(function[j].indexOf('=')+1);


plot = prePlot[2].substring(1).split(",");
if(plot.length%2 != 0)
     throw new Exception("Example: plrPlt({r1(a0,a1,..,an),r2(a0,a1,..,am)},h0=0.1,a0=A0..B0,..,an=An..Bn)");
}
else{
String[] prePlot = toShow.substring(toShow.indexOf(fnctn)+fnctn.length(),toShow.length()-1).split(",");
if((prePlot.length - 1)%2!=0)
     throw new Exception("Example: plrPlt({r1(a0,a1,..,an),r2(a0,a1,..,am)},h0=0.1,a0=A0..B0,..,an=An..Bn)");

function = new String[1];
function[0] = prePlot[0];
if(prePlot[0].contains("=")){
  Value=prePlot[0].substring(0,prePlot[0].indexOf("="));
  function[0]=prePlot[0].substring(prePlot[0].indexOf("=")+1);
}
FunctionToPlot=prePlot[0];
plot = new String[prePlot.length - 1];
for(int i = 0; i<plot.length;i++)
   plot[i] = prePlot[i+1];
}
//Var="x";
BigDecimal step;//=new BigDecimal("0.1");
BigDecimal[] range = new BigDecimal[2];
rangesLeft = new ArrayList<>();
rangesRight = new ArrayList<>();
steps = new ArrayList<>();
vars = new ArrayList<>();
varsValues = new ArrayList<>();

      for (String plot1 : plot) {
          if (plot1.contains("..")) {
              if (plot1.indexOf('=') == -1) {
                  throw new Exception("Variable name is missing in " + plot1);
              }
              vars.add(plot1.substring(0, plot1.indexOf("=")));
              String bf = jc.eval(plot1.substring(plot1.indexOf("=") + 1, plot1.indexOf("..")));
              //System.out.println(plot[i]);
              if(bf.indexOf('/')==-1)
                  range[0]=new BigDecimal(bf);
              else
                  range[0]=(new Fraction(bf)).toBigDecimal();
              bf = jc.eval(plot1.substring(plot1.indexOf("..") + 2));
              if(bf.indexOf('/')==-1)
                  range[1]=new BigDecimal(bf);
              else
                  range[1]=(new Fraction(bf)).toBigDecimal();
              varsValues.add(range[0]);
              rangesLeft.add(range[0]);
              rangesRight.add(range[1]);
          } else {
              if (plot1.contains("=")) {
                  step = new BigDecimal(plot1.substring(plot1.indexOf("=") + 1));
              } else {
                  step = new BigDecimal(plot1);
              }
              steps.add(step);
          }
      }
if(rangesLeft.size() != steps.size() || rangesRight.size()!=vars.size())
     throw new Exception("Example: plrPlt({r1(a0,a1,..,an),r2(a0,a1,..,am)},h0=0.1,a0=A0..B0,..,an=An..Bn)");
if(function.length == 1){
  toShow=fnctn+function[0]+","+steps.get(0).toPlainString()+","
              +vars.get(0)+"="+rangesLeft.get(0).toPlainString()+".."+
               rangesRight.get(0).toPlainString()+")";
}
else{
toShow=fnctn+"{";
for(int i = 0; i< function.length;i++)
      toShow=(i==0)?toShow+function[i]:toShow+","+function[i];
toShow=toShow+"},"+steps.get(0).toPlainString()+","
              +vars.get(0)+"="+rangesLeft.get(0).toPlainString()+".."+
               rangesRight.get(0).toPlainString()+")";    
}
}

    
    /**
     *
     * @throws Exception
     */
    @Override
    public void createAndShow() throws Exception{
        plrPlt frameToShow;
        
       frameToShow = new plrPlt(toShow);
       chartP = new ChartPanel(frameToShow.getChart());
       
       
       if(rangesLeft.size()-1>0){
           sliders = new JSlider[rangesLeft.size()-1];
           sliderLabels = new JLabel[rangesLeft.size()-1];
           JPanel contentPanel;
           contentPanel = new JPanel();
           contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS)); 
           contentPanel.setBackground(Color.white);
           contentPanel.add(chartP);
           BigDecimal[] sliderRange=null;
           BigDecimal sliderStep;
           int sliderMax;
           for (int i = 0; i < sliders.length; i++) {
               if (sliderRange == null) {
                   sliderRange = new BigDecimal[2];
               }
               sliderRange[0] = rangesLeft.get(i + 1);
               sliderRange[1] = rangesRight.get(i + 1);
               sliderStep = steps.get(i + 1);
               sliderStep = sliderRange[1].subtract(sliderRange[0]).divide(sliderStep, jc.MC);
               sliderMax = sliderStep.intValue();
               if ((new BigDecimal(sliderMax)).compareTo(sliderStep) < 0) {
                   sliderMax++;
               }
               sliders[i] = new JSlider(0, sliderMax, 0);
               if ((sliderMax - sliderMax % 10) / 10 == 0) {
                   sliders[i].setMajorTickSpacing(3);
               } else {
                   sliders[i].setMajorTickSpacing((sliderMax - sliderMax % 10) / 10);
               }

               if ((sliderMax - sliderMax % 100) / 100 == 0) {
                   sliders[i].setMinorTickSpacing(1);
               } else {
                   sliders[i].setMinorTickSpacing((sliderMax - sliderMax % 100) / 100);
               }

               sliders[i].setName(vars.get(i + 1));
               sliders[i].setPaintLabels(true);
               sliders[i].setBorder(
                       BorderFactory.createEmptyBorder(0, 0, 10, 0));
               sliders[i].setFont(new Font("Serif", Font.ITALIC, 15));
               sliders[i].addChangeListener(this);
               sliderLabels[i] = new JLabel(vars.get(i + 1) + "=" + rangesLeft.get(i + 1).toPlainString(), JLabel.CENTER);
               sliderLabels[i].setAlignmentX(Component.CENTER_ALIGNMENT);
               contentPanel.add(sliders[i]);
               contentPanel.add(sliderLabels[i]);
           }
           frameToShow.setContentPane(contentPanel);
           frameToShow.setSize(new java.awt.Dimension(500, 500));
       }
       else{
       chartP.setPreferredSize(new java.awt.Dimension(500, 500));
       chartP.setEnforceFileExtensions(false);
       frameToShow.setContentPane(chartP);
       }
       frameToShow.setJMenuBar(barPlt.getMenu(frameToShow.getChart(),frameToShow));
       frameToShow.pack();
       RefineryUtilities.centerFrameOnScreen(frameToShow);
       frameToShow.setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
               JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            int fps = source.getValue();
            String varName = source.getName();
            //regenerate data set and repaint
               int i;
            for( i = 1; i<vars.size();i++)
               if(vars.get(i).contentEquals(varName))
                   break;
           varsValues.set(i,rangesLeft.get(i).add(steps.get(i).multiply(new BigDecimal(fps))));
          //System.out.println(i); 
String   chartName=FunctionToPlot;


    for(int j = 1; j < vars.size();j++){
        chartName = SmartReplace.get(chartName,vars.get(j),varsValues.get(j).toPlainString()); 
         //chartName = chartName.replace(vars.get(j),varsValues.get(j).toPlainString()); 
        sliderLabels[j-1].setText(vars.get(j)+'='+varsValues.get(j).toPlainString());
      }
try{
       chartP.setChart(createPolarChart(getDataset(),chartName));
}
catch(Exception ex){
 System.out.println(ex.getMessage());
}
    // chartPanel = new ChartPanel(chart);
    
//contentPanel.repaint();
//this.repaint();
//this.setVisible(true);
}
}

    @Override
    public String getHelp() {
        return "Polar plot is created with" +System.lineSeparator()+
"" +System.lineSeparator()+
"plrPlt(1,0.1,a=0..360) " +System.lineSeparator()+
"" +System.lineSeparator()+
"The angle \"a\" is in degrees." +System.lineSeparator()+
"For plots in radians (when using sin(a), cos(a) etc.)" +System.lineSeparator()+
"use \"rdns\"." +System.lineSeparator()+
"" +System.lineSeparator()+
"plrPlt(1-cos(rdns(a)),0.1,a=0..360)" +System.lineSeparator()+
"" +System.lineSeparator()+
"Multiple polar plots:" +System.lineSeparator()+
"" +System.lineSeparator()+
"plrPlt({1,1-cos(rdns(a))},0.1,a=0..360)";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
