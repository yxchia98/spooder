package Spooding.Spooder;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.border.BevelBorder;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.Rectangle;
import java.awt.ComponentOrientation;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class test {

	private JFrame frame;
	JProgressBar crawlBar = new JProgressBar(0,100);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel4 = new JPanel();
		panel4.setBackground(SystemColor.control);
		panel4.setPreferredSize(new Dimension(10, 30));
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(SystemColor.control);
		panel2.setPreferredSize(new Dimension(10, 30));
		panel2.setMinimumSize(new Dimension(50, 50));
		
		JPanel panel3 = new JPanel();
		panel3.setBackground(SystemColor.control);
		panel3.setPreferredSize(new Dimension(30, 10));
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(SystemColor.control);
		panel1.setPreferredSize(new Dimension(30, 10));
		
		JPanel panel5 = new JPanel();
		panel5.setBackground(Color.WHITE);
		panel5.setLayout(new BorderLayout(0, 0));
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(Color.WHITE);
		textArea.setPreferredSize(new Dimension(5, 150));
		panel5.add(textArea, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 50));
		panel.setMinimumSize(new Dimension(10, 30));
		panel.setBackground(SystemColor.control);
		panel5.add(panel, BorderLayout.NORTH);
		
		JProgressBar crawlBar = new JProgressBar();
		crawlBar.setName("");
		crawlBar.setValue(0);
		crawlBar.setStringPainted(true);
		crawlBar.setString("Crawling...");
		crawlBar.setBackground(Color.LIGHT_GRAY);

		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(panel4);
		frame.getContentPane().add(panel2);
		frame.getContentPane().add(panel3);
		frame.getContentPane().add(panel1);
		frame.getContentPane().add(panel5);
		panel.add(crawlBar);
		
		fill();
	}
	
	public void fill() {
		int fillCounter = 0;
		
		while(fillCounter<= 100) { //set the <= to a set variable
			crawlBar.setValue(fillCounter);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(true) {  //set the parameter to something that shows progress
			fillCounter += 1; 
			}
		}
	}

}
