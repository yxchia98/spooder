package Spooding.Spooder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI implements ActionListener {

	private int count = 0;
	private JFrame frame;
	private JPanel panel1, panel2, panel3, panel4, panel5, panel6;
	private JLabel label1, label2, label3;
	private JButton crawlAll, crawlSpecific, sentimentAnalysis, exit, submitWord;
	private JTextField textField;

	public static boolean frameOpen = false, word = false;

	public GUI() {
		ImageIcon image = new ImageIcon("redditIcon.png");

		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		panel6 = new JPanel();

		panel1.setBackground(Color.white);
		panel2.setBackground(Color.white);
		panel3.setBackground(Color.white);
		panel4.setBackground(Color.white);
		panel5.setBackground(Color.white);

		panel5.setLayout(new BorderLayout());
		panel6.setLayout(new BorderLayout());

		panel1.setPreferredSize(new Dimension(50, 50));
		panel2.setPreferredSize(new Dimension(50, 50));
		panel3.setPreferredSize(new Dimension(50, 50));
		panel4.setPreferredSize(new Dimension(50, 50));
		panel5.setPreferredSize(new Dimension(100, 100));
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		label1.setText("Crawl Text");
		label1.setFont(new Font("Arial", Font.BOLD, 25));
		label1.setVerticalAlignment(JLabel.CENTER);
		label1.setHorizontalAlignment(JLabel.CENTER);
		label2.setText("Select what you want to do");
		label2.setFont(new Font("Arial", Font.BOLD, 25));
		label2.setVerticalAlignment(JLabel.CENTER);
		label2.setHorizontalAlignment(JLabel.CENTER);
		label3.setText("");
		label3.setForeground(Color.RED);
		label3.setFont(new Font("Arial", Font.BOLD, 25));
		label3.setVerticalAlignment(JLabel.CENTER);
		label3.setHorizontalAlignment(JLabel.CENTER);

		// New Window
		frame = new JFrame();
		frame.setTitle("Crawl Policy Opinions");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setResizable(true);

		// panel5 setup
		panel5.setLayout(new GridLayout(8, 3, 10, 10));
		panel6.setLayout(new GridLayout(1, 2, 10, 10));

		// buttons
		submitWord = new JButton("Submit");
		submitWord.addActionListener(this);

		crawlAll = new JButton("Crawl from all sources");
		crawlAll.addActionListener(this);

		crawlSpecific = new JButton("Crawl from Specific Sources");
		crawlSpecific.addActionListener(this);

		sentimentAnalysis = new JButton("Sentiment Analysis with current Dataset");
		sentimentAnalysis.addActionListener(this);

		exit = new JButton("Exit");
		exit.addActionListener(this);

		// for getting Keyword
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(250, 40));
		textField.setFont(new Font("Arial", Font.PLAIN, 35));

		// button within panel5
		panel5.add(label1);
		panel5.add(panel6);
		panel6.add(textField);
		panel6.add(submitWord);
		panel5.add(label2);
		panel5.add(crawlAll);
		panel5.add(crawlSpecific);
		panel5.add(sentimentAnalysis);
		panel5.add(exit);
		panel5.add(label3);

		// borders
		frame.add(panel1, BorderLayout.NORTH);
		frame.add(panel2, BorderLayout.WEST);
		frame.add(panel3, BorderLayout.EAST);
		frame.add(panel4, BorderLayout.SOUTH);
		frame.add(panel5, BorderLayout.CENTER);

		frame.setVisible(true);

	}

	public static void main(String[] args) {

		GUI newGUI = new GUI();
		// new MyFrame();
	}

	@Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==submitWord) {
            System.out.println("Crawling :" + textField.getText());
            word = true;
            submitWord.setEnabled(false);
            textField.setEditable(false);
        }
        if(e.getSource()==crawlAll && word == true) {
            count++;
            label3.setText("Crawling");
        }
        if (frameOpen == false) {
            crawlSpecific.setEnabled(true);
        }
        if(e.getSource()==crawlSpecific & frameOpen == false && word == true) {

            CrawlWindow crawlWindow = new CrawlWindow();
            frameOpen = true;
        }
        else if(e.getSource()==crawlSpecific & frameOpen == true && word == false) {
            crawlSpecific.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Please Enter crawl text", "title", JOptionPane.ERROR_MESSAGE);
        }
        else if(e.getSource()==sentimentAnalysis ) {
            label3.setText("Generating Sentiment Analysis");
        }
        else if(e.getSource()==exit) {
            frame.dispose();
        }

    }
}
