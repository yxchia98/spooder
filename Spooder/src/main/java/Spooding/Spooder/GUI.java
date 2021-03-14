package Spooding.Spooder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;

import twitter4j.TwitterException;

public class GUI implements ActionListener {

	private JFrame frame;
	private JPanel panel1, panel2, panel3, panel4, panel5, panel6;
	private JLabel topText, middleText, bottomText;
	private JButton crawlAll, crawlSpecific, sentimentAnalysis, exportData, exit, submitWord;
	private static JTextField textField;
	
	public static boolean frameOpen = false, searchText = false;
	public static String crawlText = null;
	
	Boolean proceed = true;
	protected App crawlerProgram;
	protected Crawler redditCrawler, twitterCrawler, straitsCrawler;

	public GUI(App crawlerProgram, Crawler redditCrawler, Crawler twitterCrawler, Crawler straitsCrawler) {
		this.crawlerProgram = crawlerProgram;
		this.redditCrawler = redditCrawler;
		this.twitterCrawler = twitterCrawler;
		this.straitsCrawler = straitsCrawler;
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
		topText = new JLabel();
		middleText = new JLabel();
		bottomText = new JLabel();
		topText.setText("Crawl Text");
		topText.setFont(new Font("Arial", Font.BOLD, 25));
		topText.setVerticalAlignment(JLabel.CENTER);
		topText.setHorizontalAlignment(JLabel.CENTER);
		middleText.setText("Select what you want to do");
		middleText.setFont(new Font("Arial", Font.BOLD, 25));
		middleText.setVerticalAlignment(JLabel.CENTER);
		middleText.setHorizontalAlignment(JLabel.CENTER);
		bottomText.setText("");
		bottomText.setForeground(Color.RED);
		bottomText.setFont(new Font("Arial", Font.BOLD, 25));
		bottomText.setVerticalAlignment(JLabel.CENTER);
		bottomText.setHorizontalAlignment(JLabel.CENTER);

		// New Window
		frame = new JFrame();
		frame.setTitle("Crawl Policy Opinions");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(500, 500);
		//frame.setResizable(true);

		// panel5 setup
		panel5.setLayout(new GridLayout(9, 3, 10, 10));
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
		
		exportData = new JButton("Export Data to Excel");
		exportData.addActionListener(this);

		exit = new JButton("Exit");
		exit.addActionListener(this);

		// for getting Keyword
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(250, 40));
		textField.setFont(new Font("Arial", Font.PLAIN, 20));
		textField.setText("");

		// button within panel5
		panel5.add(topText);
		panel5.add(panel6);
		panel6.add(textField);
		panel6.add(submitWord);
		panel5.add(middleText);
		panel5.add(crawlAll);
		panel5.add(crawlSpecific);
		panel5.add(sentimentAnalysis);
		panel5.add(exportData);
		panel5.add(exit);
		panel5.add(bottomText);

		// borders
		frame.add(panel1, BorderLayout.NORTH);
		frame.add(panel2, BorderLayout.WEST);
		frame.add(panel3, BorderLayout.EAST);
		frame.add(panel4, BorderLayout.SOUTH);
		frame.add(panel5, BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

//	public static void main(String[] args) {
//
//		GUI newGUI = new GUI();
//		
//		String url;
//		int choice, subChoice;
//		System.out.print("Enter search string: ");
//		String searchString = textField.getText();
////		url = "https://www.reddit.com/search/?q=" + searchString;
//		//instantiate App object, enabling polymorphism via App methods
//		App crawlerProgram = new App();
//
//		// instantiate redditCrawler
//		Crawler redditCrawler = new RedditCrawler();
//		// instantiate twitterCrawler
//		Crawler twitterCrawler = new TwitterCrawler(searchString, 100);
////		twitterCrawler.twitterStart();
//		//instantiate straits times crawler
//		Crawler straitsCrawler = new STCrawler(50);
//		
//		
//		SentimentalAnalysis sentimentalAnalysis = new SentimentalAnalysis();
//	}
	

    public void actionPerformed(ActionEvent e) {
		//if crawl word is empty show error message
		//if crawl word is not empty, set 
        if(e.getSource()==submitWord) {
            if(textField.getText().isBlank()) {
            	JOptionPane.showMessageDialog(null, "Please Enter crawl text", "title", JOptionPane.ERROR_MESSAGE);
            }
            else if(!textField.getText().isBlank()) {
            	searchText = true;
            	crawlText = textField.getText(); //set crawl Text
            	System.out.println("Crawling :" + textField.getText());
            }
        }
        
        //crawling all sources
        if(e.getSource()==crawlAll) { 
        	if (searchText == true) {
            bottomText.setText("Crawling Twitter and Reddit");
//            while(proceed) {
            	try {
					crawlerProgram.crawl(twitterCrawler);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TwitterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					crawlerProgram.crawl(redditCrawler);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TwitterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					crawlerProgram.crawl(straitsCrawler);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TwitterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	CrawlProgressBar newBar = new CrawlProgressBar("Crawling...");
//            }
        	}
        	else if (searchText == false) {
        		JOptionPane.showMessageDialog(null, "Please Enter crawl text", "title", JOptionPane.ERROR_MESSAGE);
            }
        	
        }
        //if crawlWindow closed turn back on button.
        if (frameOpen == false) { 
            crawlSpecific.setEnabled(true);
        }
        //crawl Specific button
        if(e.getSource()==crawlSpecific) {
        	//if search text and frame not open
        	if (searchText == true && frameOpen == false) {
        		CrawlWindow crawlWindow = new CrawlWindow();
            	frameOpen = true;
            	bottomText.setText("Crawling Specific");
            }
        	else if (searchText == true && frameOpen == true) {
            	bottomText.setText("Window Open");
            }
        	//if no search text show error message
            else if (searchText == false ) {
            	JOptionPane.showMessageDialog(null, "Please Enter crawl text", "title", JOptionPane.ERROR_MESSAGE);
            }
        }
        //start sentiment analysis
        if(e.getSource()==sentimentAnalysis ) {
        	if (searchText == true) {
        		bottomText.setText("Generating Sentiment Analysis");
        		//add in sentiment analysis
//        		sentimentalAnalysis.Analyze();
            }
        	else if (searchText == false) {
            	JOptionPane.showMessageDialog(null, "Please Enter crawl text", "title", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        if(e.getSource()==exportData ) {
        	if (searchText == true) {
        		bottomText.setText("Exporting Data");
            	CrawlProgressBar newBar = new CrawlProgressBar("Exporting...");
        		//add in sentiment analysis
//        		sentimentalAnalysis.Analyze();
            }
        	else if (searchText == false) {
            	JOptionPane.showMessageDialog(null, "Please Enter crawl text", "title", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        //close window
        else if(e.getSource()==exit) {
            frame.dispose();
        }

    }
}
