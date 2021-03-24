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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import org.ioblako.math.linearalgebra.Fraction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.swing.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.swing.UIUtils;

import org.ioblako.math.calculator.jc;
import org.ioblako.math.calculator.SmartReplace;


/**
 *
 * @author sergey_nikitin
 */
public class grPlt extends AbstractFramePlt implements FramePlt {
   static final long  serialVersionUID=199; 
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
//private static String[] function;
private static ArrayList<String> function;

   
ChartPanel chartP=null;
   public grPlt(){
        super("");
    }
    public grPlt(String title) throws Exception{
          super("");
        toShow=title.trim();
          preProcess("grPlt(",toShow);
       FunctionToPlot = title.substring(title.indexOf("grPlt(")+6);
       FunctionToPlot = ( FunctionToPlot.startsWith("{"))? FunctionToPlot.substring(1, FunctionToPlot.indexOf("}")):FunctionToPlot.substring(0, FunctionToPlot.indexOf(","));
      Var=vars.get(0);
      String   chartName=FunctionToPlot;
      for(int j = 1; j < vars.size();j++)
          chartName = SmartReplace.get(chartName,vars.get(j),varsValues.get(j).toPlainString()); 
         //chartName = chartName.replace(vars.get(j),varsValues.get(j).toPlainString()); 

        chart = createChart(getDataset(),chartName);
    }
    private static JFreeChart createChart(XYDataset dataset, String chartName) {
// create the chart...

if(chartName == null)
 chartName = FunctionToPlot;

JFreeChart chart = ChartFactory.createXYLineChart(
chartName,
Var,
Value,
dataset,
PlotOrientation.VERTICAL,
true,
true, 
false
);
chart.setBackgroundPaint(Color.white);

 XYPlot plot = (XYPlot) chart.getPlot();
 plot.setBackgroundPaint(Color.lightGray);
 //plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
 plot.setDomainGridlinePaint(Color.white);
 plot.setRangeGridlinePaint(Color.white);
XYLineAndShapeRenderer renderer
= (XYLineAndShapeRenderer) plot.getRenderer();
//renderer.setShapesVisible(true);
//renderer.setShapesFilled(true);
// change the auto tick unit selection to integer units only...
 NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
rangeAxis.setLabelAngle(Math.PI/2.0);
 rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); // OPTIONAL CUSTOMISATION COMPLETED.
return chart;
 }

private static XYDataset getDataset() throws Exception{
  if(vars.size()!= varsValues.size())
    throw new Exception("function formatting error: a variable name is missing");

String[] newFunction = new String[function.size()];
BigDecimal pt,rightEnd=rangesRight.get(0), step=steps.get(0);
//BigDecimal pt=rangesLeft.get(0), rightEnd=rangesRight.get(0), step=steps.get(0);
String bf;

XYSeries[] series=new XYSeries[function.size()];
Iterator<String> it = function.iterator();
int i = 0;
while(it.hasNext()){
bf = it.next();
series[i] = new XYSeries(bf);
newFunction[i] = bf;
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
i++;
}
XYSeriesCollection dataset = new XYSeriesCollection();
     for (XYSeries serie : series) {
         dataset.addSeries(serie);
     }
return dataset;

}
private void preProcess(String fnctn, String inp) throws Exception{
String input = inp.substring(inp.indexOf(fnctn)+fnctn.length());
input=input.trim();
PltArgumentParser parser = new PltArgumentParser(input);

vars = parser.getVariables();
steps = parser.getSteps();
rangesLeft = parser.getLeft();
rangesRight = parser.getRight();
function = parser.getFunctions();
varsValues = new ArrayList<BigDecimal>();
Iterator<BigDecimal> it = rangesLeft.iterator();
while(it.hasNext())
   varsValues.add(it.next());
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
       chartP.setChart(createChart(getDataset(),chartName));
}
catch(Exception ex){
 System.out.println(ex.getMessage());
}
    // chartPanel = new ChartPanel(chart);
    
//contentPanel.repaint();
//this.repaint();
//this.setVisible(true);
}
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setInput(String txt) {
        toShow=txt.trim();
    }

    @Override
    public void createAndShow() throws Exception {
              grPlt frameToShow;
        
       frameToShow = new grPlt(toShow);
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
                   sliders[i].setMajorTickSpacing(1);
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
       UIUtils.centerFrameOnScreen(frameToShow);
       frameToShow.setVisible(true);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getHelp() {
        return "A Cartesian plot is created with" +System.lineSeparator()+
"plot(f(x),variable={step_size,left..right})" +System.lineSeparator()+
"or" +System.lineSeparator()+
"grPlt(f(x),variable={step_size,left..right})" +System.lineSeparator()+
"for one  function and" +System.lineSeparator()+
"plot({f1(x)..fn(x)},variable={step_size,left..right})" +System.lineSeparator()+
"grPlt({f1(x)..fn(x)},variable={step_size,left..right})" +System.lineSeparator()+
"for multiple functions." +System.lineSeparator()+
"" +System.lineSeparator()+
"Example: " +System.lineSeparator()+
"" +System.lineSeparator()+
"plot(sin(x),x={0.1,-pi..pi})" +System.lineSeparator()+
"" +System.lineSeparator()+
"or" +System.lineSeparator()+
"" +System.lineSeparator()+
"grPlt(sin(x),x={0.1,-pi..pi})" +System.lineSeparator()+
"" +System.lineSeparator()+
"For multiple plots " +System.lineSeparator()+
"\n" +
"plot({0.5*x,sin(x)},x={0.1,-pi..pi})\n" +
"" +System.lineSeparator()+
"or" +System.lineSeparator()+
"" +System.lineSeparator()+
"grPlt({0.5*x,sin(x)},x={0.1,-pi..pi})";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
