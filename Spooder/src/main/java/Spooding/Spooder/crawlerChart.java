package Spooding.Spooder;

import java.io.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.*;
import org.jfree.chart.ChartUtils;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.*;
import org.jfree.ui.RefineryUtilities;
 
public class crawlerChart extends JFrame {
	private static Double positive;
	private static Double negative;
	private static Double neutral;
	private static Double veryPositive;
	private static Double veryNegative;
	
   public crawlerChart( String title , int positive,int negative, int neutral, int veryPositive, int veryNegative ) throws IOException {
      super( title ); 
      this.positive = new Double (positive);
      this.negative = new Double (negative);
      this.neutral = new Double (neutral);
      this.veryPositive = new Double (veryPositive);
      this.veryNegative = new Double (veryNegative);
      this.setDefaultCloseOperation(HIDE_ON_CLOSE);
      setContentPane(createPanel( ));

      this.setSize( 500 , 500 );    
      RefineryUtilities.centerFrameOnScreen( this );    
      this.setVisible( true );
   }
   
   private static PieDataset<Double> createDataset( ) {
      DefaultPieDataset dataset = new DefaultPieDataset<Double>( );
      dataset.setValue( "Positive" , positive );  
      dataset.setValue( "Negative" ,negative );   
      dataset.setValue( "Neutral" , neutral );    
      dataset.setValue( "Very Positive" , veryPositive );  
      dataset.setValue( "Very Negative" , veryNegative ); 
      
      return dataset;         
   }
   
   private static JFreeChart createChart( PieDataset dataset ) throws IOException {
      JFreeChart chart = ChartFactory.createPieChart(      
         "Sentiment Analysis",   // chart title 
         dataset,          // data    
         true,             // include legend   
         true, 
         false);
      int width = 500;   /* Width of the image */
      int height = 500;  /* Height of the image */ 
      File pieChart = new File( "PieChart.jpeg" ); 
      ChartUtils.saveChartAsJPEG( pieChart , chart , width , height );
      return chart;
   }
   
   public static JPanel createPanel( ) throws IOException {
      JFreeChart chart = createChart(createDataset( ) );  
      return new ChartPanel( chart ); 
   }

   public static void main( String[ ] args ) throws IOException {
	   crawlerChart demo = new crawlerChart( "Sentiment Analysis", 10,10,10,10,10);  
 
   }
}