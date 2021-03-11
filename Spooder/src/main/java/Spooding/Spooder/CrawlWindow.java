package Spooding.Spooder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class CrawlWindow implements ActionListener {

	private JButton crawlReddit, crawlTwitter, crawlStraitstimes, back;
	private JFrame frame = new JFrame();
	private JPanel panel1, panel2, panel3, panel4, panel5;
	private JLabel topText, bottomText;

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
		topText = new JLabel();
		topText.setText("Select what you want to do");
		topText.setFont(new Font("Arial", Font.BOLD, 25));
		topText.setVerticalAlignment(JLabel.CENTER);
		topText.setHorizontalAlignment(JLabel.CENTER);
		bottomText = new JLabel();
		bottomText.setText("");
		bottomText.setForeground(Color.RED);
		bottomText.setFont(new Font("Arial", Font.BOLD, 25));
		bottomText.setVerticalAlignment(JLabel.CENTER);
		bottomText.setHorizontalAlignment(JLabel.CENTER);

//New Window
		frame = new JFrame();
		frame.setTitle("Crawl Specific");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(500, 500);
		//frame.setResizable(true);

//buttons
		crawlTwitter = new JButton("Crawl from Twitter");
		crawlTwitter.addActionListener(this);

		crawlReddit = new JButton("Crawl from Reddit");
		crawlReddit.addActionListener(this);
		
		crawlStraitstimes = new JButton("Crawl from Straits Times");
		crawlStraitstimes.addActionListener(this);

		back = new JButton("Exit");
		back.addActionListener(this);		
		
//panel5 setup
		panel5.setLayout(new GridLayout(7, 3, 10, 10));
		panel5.add(topText);
		panel5.add(crawlTwitter);
		panel5.add(crawlReddit);
		panel5.add(crawlStraitstimes);
		panel5.add(back);
		panel5.add(bottomText);

//borders
		frame.add(panel1, BorderLayout.NORTH);
		frame.add(panel2, BorderLayout.WEST);
		frame.add(panel3, BorderLayout.EAST);
		frame.add(panel4, BorderLayout.SOUTH);
		frame.add(panel5, BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == crawlTwitter) {
			bottomText.setText("Crawling from Twitter");
			CrawlProgressBar newBar = new CrawlProgressBar("Crawling...");
			
			//add CrawlTwitter
//			GUI.crawlerProgram.crawl(twitterCrawler);
//			WordCloudGenerator wordCloudT = new WordCloudGenerator("twitter.csv");
//			wordCloudT.generateCloud();
			
		} else if (e.getSource() == crawlReddit) {
			bottomText.setText("Crawling from Reddit");
			CrawlProgressBar newBar = new CrawlProgressBar("Crawling...");
			
			//add CrawlReddit
//			GUI.crawlerProgram.crawl(redditCrawler);
//			WordCloudGenerator wordCloudR = new WordCloudGenerator("reddit.csv");
//			wordCloudR.generateCloud();
		} else if (e.getSource() == crawlStraitstimes) {
			bottomText.setText("Crawl from Straits Times");
			CrawlProgressBar newBar = new CrawlProgressBar("Crawling...");
			
			//add CrawlStraitsTimes
//			GUI.crawlerProgram.crawl(straitsCrawler);
//			WordCloudGenerator wordCloudS = new WordCloudGenerator("straits.csv");
//			wordCloudS.generateCloud();
		} else if (e.getSource() == back) {
			GUI.frameOpen = false;
			frame.dispose();
		}

	}
}