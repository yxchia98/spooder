package Spooding.Spooder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CrawlWindow implements ActionListener {

	private int count;
	private JButton crawlReddit, crawlTwitter, back;
	private JFrame frame = new JFrame();
	private JPanel panel1, panel2, panel3, panel4, panel5;
	private JLabel label = new JLabel("Hello");

	CrawlWindow() {
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();

		panel1.setBackground(Color.white);
		panel2.setBackground(Color.white);
		panel3.setBackground(Color.white);
		panel4.setBackground(Color.white);
		panel5.setBackground(Color.white);

		panel5.setLayout(new BorderLayout());

		panel1.setPreferredSize(new Dimension(50, 50));
		panel2.setPreferredSize(new Dimension(50, 50));
		panel3.setPreferredSize(new Dimension(50, 50));
		panel4.setPreferredSize(new Dimension(50, 50));
		panel5.setPreferredSize(new Dimension(100, 100));
		label = new JLabel();
		label.setText("Select what you want to do");
		label.setFont(new Font("Arial", Font.BOLD, 25));
//label.setIcon(image);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setHorizontalAlignment(JLabel.CENTER);

//New Window
		frame = new JFrame();
		frame.setTitle("Crawl Policy Opinions");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setResizable(true);

//panel5 setup
		panel5.setLayout(new GridLayout(7, 3, 10, 10));
		panel5.add(label);

//buttons
		crawlTwitter = new JButton("Crawl from Twitter");
		crawlTwitter.addActionListener(this);

		crawlReddit = new JButton("Crawl from Reddit");
		crawlReddit.addActionListener(this);

		back = new JButton("Exit");
		back.addActionListener(this);

//button within panel5
		panel5.add(crawlTwitter);
		panel5.add(crawlReddit);
		panel5.add(back);

//borders
		frame.add(panel1, BorderLayout.NORTH);
		frame.add(panel2, BorderLayout.WEST);
		frame.add(panel3, BorderLayout.EAST);
		frame.add(panel4, BorderLayout.SOUTH);
		frame.add(panel5, BorderLayout.CENTER);

		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == crawlTwitter) {
			label.setText("Number of Clicks: " + count);
		} else if (e.getSource() == crawlReddit) {
			label.setText("Number of Clicks: " + count);
		} else if (e.getSource() == back) {
			GUI.frameOpen = false;
			frame.dispose();
		}

	}
}