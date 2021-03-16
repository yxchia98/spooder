package Spooding.Spooder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import Spooding.Spooder.ProgressBarDemo.Task;

public class CrawlProgressBar implements ActionListener{
	JProgressBar crawlBar = new JProgressBar(); //values are the min and max value of progress
	private static int fillCounter = 0;
	private int maxValue;

	JButton close;
	private JFrame frame = new JFrame();
	private JPanel panel1, panel2, panel3, panel4, panel5,panel6;
	private JLabel topText, bottomText;
	private JTextArea textArea = new JTextArea();
	private Task task;
	
	private String titleString;
	private String crawlBarMax;


	//UI setup
	CrawlProgressBar(String titleString,String crawlBarMax) {

		this.titleString = titleString;
		this.crawlBarMax = crawlBarMax;
		
		crawlBar.setMinimum(0);
		
		if(crawlBarMax == "export") {
			maxValue = 10;
			crawlBar.setMaximum(10);
		}
		else if(crawlBarMax == "crawlAll") {
			maxValue = 150;
			crawlBar.setMaximum(150);
		}
		else if(crawlBarMax == "crawlTwitter") {
			maxValue = 50;
			crawlBar.setMaximum(50);
		}
		else if(crawlBarMax == "crawlReddit") {
			maxValue = 50;
			crawlBar.setMaximum(50);
		}
		else if(crawlBarMax == "crawlStraitstimes") {
			maxValue = 50;
			crawlBar.setMaximum(50);
		}
		else if(crawlBarMax == "test") {
			maxValue = 100;
			crawlBar.setMaximum(100);
		}
		fillCounter = 0;
		crawlBar.setValue(0);
		crawlBar.setBounds(12, 0, 450, 50);
		crawlBar.setStringPainted(true);
		crawlBar.setString(titleString);
		crawlBar.setIndeterminate(true);
		
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		JPanel panel6 = new JPanel();

		panel1.setBackground(Color.white);
		panel2.setBackground(Color.white);
		panel3.setBackground(Color.white);
		panel4.setBackground(Color.white);
		panel5.setBackground(Color.white);
		panel6.setBackground(Color.white);

		panel5.setLayout(new BorderLayout());

		panel1.setPreferredSize(new Dimension(50, 50));
		panel2.setPreferredSize(new Dimension(50, 50));
		panel3.setPreferredSize(new Dimension(50, 50));
		panel4.setPreferredSize(new Dimension(50, 50));
		panel5.setPreferredSize(new Dimension(100, 100));
		panel6.setPreferredSize(new Dimension(50, 50));
		
		textArea.setBackground(Color.LIGHT_GRAY);
		
		textArea.setMargin(new Insets(5,5,5,5));
        textArea.setEditable(false);

//New Window
		frame = new JFrame();
		frame.setTitle(titleString);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 400);
		//frame.setResizable(true);

//buttons
		close = new JButton("Close Window");
		close.addActionListener(this);	
		close.setEnabled(false);
		
//panel5 setup
		panel5.setLayout(new BorderLayout());
		panel5.add(crawlBar,BorderLayout.PAGE_START);
		crawlBar.setPreferredSize(new Dimension(10,50));
		JScrollPane scrollPaneText = new JScrollPane(textArea);
		panel5.add(scrollPaneText,BorderLayout.CENTER);
		scrollPaneText.setPreferredSize(new Dimension(5, 150));
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		//make it keep scrolling down
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		panel5.setBorder(null);
		//
		panel5.add(panel6, BorderLayout.PAGE_END);
		panel6.add(close,BorderLayout.CENTER);
		close.setPreferredSize(new Dimension(150,40));
		


//borders
		frame.add(panel1, BorderLayout.NORTH);
		frame.add(panel2, BorderLayout.WEST);
		frame.add(panel3, BorderLayout.EAST);
		frame.add(panel4, BorderLayout.SOUTH);
		frame.add(panel5, BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		frame.setVisible(true);
		
		

		//fill(); //fill method
		crawlBar.setIndeterminate(true);
		
        task = new Task();
        task.execute();

	}
    class Task extends SwingWorker<Void, Void> {
    	
        /*
         * Main task. Executed in background thread.
         */
    	@Override
        public Void doInBackground() {
           crawlBar.setIndeterminate(false);
            //Initialize progress property.
            crawlBar.setValue(0);
            while (fillCounter < maxValue) {
                //Sleep for up to one second.
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignore) {}
                fillCounter += 1;
                //textArea.append(String.format("Completed %d%% of task.\n", fillCounter));
                crawlBar.setValue(Math.min(fillCounter, maxValue));
            }
            if(crawlBar.getValue() >= maxValue)
            	close.setEnabled(true);
            return null;
            
        }


		/*
         * Executed in event dispatching thread
         */
        public void done() {
        	
            frame.setCursor(null); //turn off the wait cursor
            textArea.append("Done!\n");
        }
    }
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == close && fillCounter >= maxValue) {
			frame.dispose();
		}

	}

//	Timer timer = new Timer(50, new ActionListener() {
//		public void actionPerformed(ActionEvent e) {
//			fillCounter += 1;
//		}
//	});
	
	public static void main(String[] args) {

		CrawlProgressBar newBar = new CrawlProgressBar("test","crawlAll");

		// new MyFrame();
	}
	
	public void setTextArea(String text) {
		textArea.append(text);
	}
	
	public void setBarMax(int max) {
		crawlBar.setMaximum(max);
	}
	public void setFrameTitle(String title) {
		frame.setTitle(title);
	}
}

