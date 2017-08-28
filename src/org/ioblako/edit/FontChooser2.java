/* 
 * Copyright (C) 2017 Sergey Nikitin and others
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

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.MathContext;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import org.ioblako.math.calculator.jc;

/**
 * A font selection dialog.
 * <p>
 * Note: can take a long time to start up on systems with (literally) hundreds
 * of fonts. TODO change list to JList, add a SelectionChangedListener to
 * preview.
 * 
 * @author Ian Darwin
 * @version $Id: FontChooser.java,v 1.19 2004/03/20 20:44:56 ian Exp $
 */
public class FontChooser2 extends JDialog {
static final long serialVersionUID=1004;
  // Results:
  
  /** The font the user has chosen */
  protected Font resultFont;

  /** The resulting font name */
  protected String resultName;

  /** The resulting font size */
  protected int resultSize;

  /** The resulting boldness */
  protected boolean isBold;

  /** The resulting italicness */
  protected boolean isItalic;

  // Working fields

  /** Display text */
  protected String displayText = "Qwerty Yuiop";

  /** The list of Fonts */
  protected String fontList[];

  /** The font name chooser */
  protected List fontNameChoice;

  /** The font size chooser */
  protected List fontSizeChoice;

  /** The bold and italic choosers */
  Checkbox bold, italic;

  /** The list of font sizes */
  protected String fontSizes[] = { "8", "10", "11", "12", "14", "16", "18",
      "20", "24", "30", "36", "40", "48", "60", "72" };

  /** The index of the default size (e.g., 14 point == 4) */
  protected static final int DEFAULT_SIZE = 4;

  /**
   * The display area. Use a JLabel as the AWT label doesn't always honor
   * setFont() in a timely fashion :-)
   */
  protected JLabel previewArea;
  
  

  /**
   * Construct a FontChooser -- Sets title and gets array of fonts on the
   * system. Builds a GUI to let the user choose one font at one size.
     * @param f
   */
  public FontChooser2(TextEdit ed) {
    super(ed.getFrame(), "Preferences", true);
    
    //setResizable(false);
    Container cp = getContentPane();
    
   
    Panel PrecisionPanel = new Panel();
    PrecisionPanel.setLayout(new GridLayout(0,2));
    
 int currentPrecision=7;
 if(ed != null)
     currentPrecision=ed.getConfig().getInt("PRECISION", 7);
 SpinnerNumberModel Precision = new SpinnerNumberModel(currentPrecision, 2, 34, 1); 
 JSpinner jsp =  addLabeledSpinner(PrecisionPanel,
                                        "Precision",
                                         Precision);
  jsp.addChangeListener((ChangeEvent e) -> {
      String PrecisionValue = ""+((JSpinner)e.getSource()).getValue();
      if(PrecisionValue != null)
      jc.setMathContext(new MathContext(Integer.parseInt(PrecisionValue)));
    });
 
        //spinners.add(jsp,BorderLayout.NORTH);
    Panel top = new Panel();
    top.setLayout(new FlowLayout());

    fontNameChoice = new List(8);
    top.add(fontNameChoice);

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    // For JDK 1.1: returns about 10 names (Serif, SansSerif, etc.)
    // fontList = toolkit.getFontList();
    // For JDK 1.2: a much longer list; most of the names that come
    // with your OS (e.g., Arial), plus the Sun/Java ones (Lucida,
    // Lucida Bright, Lucida Sans...)
    fontList = GraphicsEnvironment.getLocalGraphicsEnvironment()
        .getAvailableFontFamilyNames();
String Chousen = ed.getConfig().get("FONT_NAME","Monospaced");
int i = 0;
      for (String fontList1 : fontList) {
          fontNameChoice.add(fontList1);
          if(fontList1.contentEquals(Chousen))
              fontNameChoice.select(i);
          i++;
      }
    //fontNameChoice.select(0);

    fontSizeChoice = new List(8);
    top.add(fontSizeChoice);
    Chousen = Integer.toString(ed.getConfig().getInt("FONT_SIZE",12));  
    i=0;
      for (String fontSize : fontSizes) {
          fontSizeChoice.add(fontSize);
          if(Chousen.contentEquals(fontSize))
              fontSizeChoice.select(i);
          i++;
      }
    //fontSizeChoice.select(DEFAULT_SIZE);

    //cp.add(top, BorderLayout.NORTH);
   // spinners.add(top,BorderLayout.CENTER);
   Panel NorthContainer = new Panel(new FlowLayout());
   NorthContainer.add(PrecisionPanel);
   NorthContainer.add(top);
cp.add(NorthContainer, BorderLayout.NORTH);
//cp.add(top, BorderLayout.NORTH);
    Panel attrs = new Panel();
    top.add(attrs);
    attrs.setLayout(new GridLayout(0, 1));
    attrs.add(bold = new Checkbox("Bold", false));
    attrs.add(italic = new Checkbox("Italic", false));

    previewArea = new JLabel(displayText, JLabel.CENTER);
    previewArea.setSize(100, 50);
    cp.add(previewArea, BorderLayout.CENTER);

    Panel bot = new Panel();

    JButton okButton = new JButton("Apply");
    bot.add(okButton);
    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        previewFont();
        dispose();
        setVisible(false);
      }
    });

    JButton pvButton = new JButton("Preview");
    bot.add(pvButton);
    pvButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        previewFont();
      }
    });

    JButton canButton = new JButton("Cancel");
    bot.add(canButton);
    canButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Set all values to null. Better: restore previous.
        resultFont = null;
        resultName = null;
        resultSize = 0;
        isBold = false;
        isItalic = false;

        dispose();
        setVisible(false);
      }
    });

    cp.add(bot, BorderLayout.SOUTH);

    previewFont(); // ensure view is up to date!

    pack();
    setLocation(100, 100);
  }

  /**
   * Called from the action handlers to get the font info, build a font, and
   * set it.
   */
  protected void previewFont() {
    resultName = fontNameChoice.getSelectedItem();
    String resultSizeName = fontSizeChoice.getSelectedItem();
    int resultSize = Integer.parseInt(resultSizeName);
    isBold = bold.getState();
    isItalic = italic.getState();
    int attrs = Font.PLAIN;
    if (isBold)
      attrs = Font.BOLD;
    if (isItalic)
      attrs |= Font.ITALIC;
    resultFont = new Font(resultName, attrs, resultSize);
    // System.out.println("resultName = " + resultName + "; " +
    //     "resultFont = " + resultFont);
    previewArea.setFont(resultFont);
    pack(); // ensure Dialog is big enough.
  }

  /** Retrieve the selected font name. */
  public String getSelectedName() {
    return resultName;
  }

  /** Retrieve the selected size */
  public int getSelectedSize() {
    return resultSize;
  }

  /** Retrieve the selected font, or null */
  public Font getSelectedFont() {
    return resultFont;
  }

  /** Simple main program to start it running */
  /*
  public static void main(String[] args) {
    final JFrame f = new JFrame("FontChooser Startup");
    final FontChooser2 fc = new FontChooser2(f);
    final Container cp = f.getContentPane();
    cp.setLayout(new GridLayout(0, 1)); // one vertical column

    JButton theButton = new JButton("Change font");
    cp.add(theButton);

    final JLabel theLabel = new JLabel("Java is great!", JLabel.CENTER);
    cp.add(theLabel);

    // Now that theButton and theLabel are ready, make the action listener
    theButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fc.setVisible(true);
        Font myNewFont = fc.getSelectedFont();
        System.out.println("You chose " + myNewFont);
        theLabel.setFont(myNewFont);
        f.pack(); // adjust for new size
        fc.dispose();
      }
    });

    f.setSize(300, 200);
    f.setVisible(true);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  */
   static protected JSpinner addLabeledSpinner(Container c,
                                                String label,
                                                SpinnerModel model) {
        JLabel l = new JLabel(label);
        c.add(l);

        JSpinner spinner = new JSpinner(model);
        l.setLabelFor(spinner);
        c.add(spinner);

        return spinner;
    }

}

           
       
