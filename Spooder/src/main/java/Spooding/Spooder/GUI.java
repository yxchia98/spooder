package Spooding.Spooder;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import com.opencsv.exceptions.CsvValidationException;

import twitter4j.TwitterException;

/**
 * GUI Class
 */
public class GUI extends MongoConnect implements ActionListener {

	static JFrame frame;
	static boolean chartOpen = false;
	private CardLayout card = new CardLayout();
	private JPanel panel1, panel2, panel3, panel4, panel5, panel6, panel7, panel8, panel9, panel10, panel11, panel12,
			panel13, panel14, panel15, panel16, panel17, panel18, panel19, switchPanel, mainMenuPanel, crawlPanel,
			dataPanel, sentimentPanel;

	private JLabel topText, middleText, bottomText, crawlTopText, crawlBottomText;

	private JButton crawlAll, crawlSpecific, showCrawled, sentimentAnalysis, exportData, exit, submitWord, crawlTwitter,
			crawlReddit, crawlStraitstimes, backToMenu, twitter, reddit, straitsTimes, backToMenu1,
			sentimentAnalysisData, pieChart, backToMenu2;

	private JTextArea textArea, textArea1;

	private boolean crawlFrameOpen = false, crawlInfoOpen = false, searchText = true;
	public String crawlText = null;

	protected App crawlerProgram;
	protected Crawler redditCrawler, twitterCrawler, straitsCrawler;
	protected WordCloudGenerator wordCloud;

	private int positive, negative, neutral, veryPositive, veryNegative;

	/**
	 * Constructor for the GUI Class
	 * 
	 * @param crawlerProgram      instance of crawler Program
	 * @param redditCrawler       instance of Reddit Crawler
	 * @param twitterCrawler      instance of Twitter Crawler
	 * @param straitsCrawler      instance of Straits Times Crawler
	 * @param sentimentalAnalysis instance of SentimentAnalysis
	 * @param allData             instance of SentimentData
	 * @param wordCloud           instance of WordCloudGenerator
	 */
	public GUI(App crawlerProgram, Crawler redditCrawler, Crawler twitterCrawler, Crawler straitsCrawler,
			WordCloudGenerator wordCloud) {
		this.crawlerProgram = crawlerProgram;
		this.redditCrawler = redditCrawler;
		this.twitterCrawler = twitterCrawler;
		this.straitsCrawler = straitsCrawler;
//		this.sentimentalAnalysis = sentimentalAnalysis;
//		this.allData = allData;
		this.wordCloud = wordCloud;

		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		panel6 = new JPanel();
		panel7 = new JPanel();
		panel8 = new JPanel();
		panel9 = new JPanel();
		panel10 = new JPanel();
		panel11 = new JPanel();
		panel12 = new JPanel();
		panel13 = new JPanel();
		panel14 = new JPanel();
		panel15 = new JPanel();
		panel16 = new JPanel();
		panel17 = new JPanel();
		panel18 = new JPanel();
		panel19 = new JPanel();
		switchPanel = new JPanel();
		mainMenuPanel = new JPanel();
		crawlPanel = new JPanel();
		dataPanel = new JPanel();
		sentimentPanel = new JPanel();

		textArea = new JTextArea();
		textArea1 = new JTextArea();

		panel1.setBackground(Color.white);
		panel2.setBackground(Color.white);
		panel3.setBackground(Color.white);
		panel4.setBackground(Color.white);
		dataPanel.setBackground(Color.white);
		panel6.setBackground(Color.white);
		panel7.setBackground(Color.white);
		panel8.setBackground(Color.white);
		panel9.setBackground(Color.white);
		panel10.setBackground(Color.white);
		panel11.setBackground(Color.white);
		panel12.setBackground(Color.white);
		panel13.setBackground(Color.white);
		panel14.setBackground(Color.white);
		panel15.setBackground(Color.white);
		panel16.setBackground(Color.white);
		panel17.setBackground(Color.white);
		panel18.setBackground(Color.white);
		panel19.setBackground(Color.white);

		// Panel setup
		switchPanel.setLayout(card); // panel that contains the other panels

		mainMenuPanel.setBackground(Color.white);
		mainMenuPanel.setLayout(new GridLayout(8, 3, 10, 10));

		crawlPanel.setBackground(Color.white);
		crawlPanel.setLayout(new BorderLayout());

		dataPanel.setBackground(Color.white);
		dataPanel.setLayout(new BorderLayout());

		sentimentPanel.setBackground(Color.white);
		sentimentPanel.setLayout(new BorderLayout());

		// Frame Borders
		panel1.setPreferredSize(new Dimension(50, 50));
		panel2.setPreferredSize(new Dimension(50, 50));
		panel3.setPreferredSize(new Dimension(50, 50));
		panel4.setPreferredSize(new Dimension(50, 50));

		// New Window
		frame = new JFrame();
		frame.setTitle("Crawl Policy Opinions");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setMinimumSize(new Dimension(500, 500));
		// frame.setResizable(true);

		// Main Menu buttons
		submitWord = new JButton("Submit");
		submitWord.addActionListener(this);

		crawlAll = new JButton("Crawl from all sources");
		crawlAll.addActionListener(this);

		crawlSpecific = new JButton("Crawl from Specific Sources");
		crawlSpecific.addActionListener(this);

		showCrawled = new JButton("Show Crawled Data");
		showCrawled.addActionListener(this);

		sentimentAnalysis = new JButton("Sentiment Analysis with current Dataset");
		sentimentAnalysis.addActionListener(this);

		exportData = new JButton("Export Data to Excel");
		exportData.addActionListener(this);

		exit = new JButton("Exit");
		exit.addActionListener(this);

		// Crawl Window buttons
		crawlTwitter = new JButton("Crawl from Twitter");
		crawlTwitter.addActionListener(this);

		crawlReddit = new JButton("Crawl from Reddit");
		crawlReddit.addActionListener(this);

		crawlStraitstimes = new JButton("Crawl from Straits Times");
		crawlStraitstimes.addActionListener(this);

		backToMenu = new JButton("Back to Menu");
		backToMenu.addActionListener(this);

		// CrawlDataInfo Buttons
		backToMenu1 = new JButton("Back to Menu");
		backToMenu1.addActionListener(this);
		backToMenu1.setPreferredSize(new Dimension(200, 40));
		backToMenu1.setEnabled(true);

		twitter = new JButton("Twitter");
		twitter.addActionListener(this);
		twitter.setPreferredSize(new Dimension(100, 40));

		reddit = new JButton("Reddit");
		reddit.addActionListener(this);
		reddit.setPreferredSize(new Dimension(100, 40));

		straitsTimes = new JButton("Straits Times");
		straitsTimes.addActionListener(this);
		straitsTimes.setPreferredSize(new Dimension(100, 40));

		// Sentiment Analysis Buttons
		sentimentAnalysisData = new JButton("Sentiment Analysis Data");
		sentimentAnalysisData.addActionListener(this);
		sentimentAnalysisData.setPreferredSize(new Dimension(100, 40));

		pieChart = new JButton("Show Pie Chart");
		pieChart.addActionListener(this);
		pieChart.setPreferredSize(new Dimension(100, 40));

		backToMenu2 = new JButton("Back to Menu");
		backToMenu2.addActionListener(this);
		backToMenu2.setPreferredSize(new Dimension(200, 40));
		backToMenu2.setEnabled(true);

		// Main menu Content
		topText = new JLabel();
		middleText = new JLabel();
		bottomText = new JLabel();

		topText.setText("Crawl Policy Opinions");
		topText.setForeground(Color.BLUE);
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

		mainMenuPanel.add(topText);
		mainMenuPanel.add(middleText);
		mainMenuPanel.add(crawlAll);
		mainMenuPanel.add(crawlSpecific);
		mainMenuPanel.add(showCrawled);
		mainMenuPanel.add(sentimentAnalysis);
		mainMenuPanel.add(exportData);
		mainMenuPanel.add(exit);
		// mainMenuPanel.add(bottomText);

		// Crawl Window Content
		crawlTopText = new JLabel();
		crawlTopText.setText("Select source to crawl");
		crawlTopText.setForeground(Color.BLUE);
		crawlTopText.setFont(new Font("Arial", Font.BOLD, 25));
		crawlTopText.setVerticalAlignment(JLabel.CENTER);
		crawlTopText.setHorizontalAlignment(JLabel.CENTER);
		crawlBottomText = new JLabel();
		crawlBottomText.setText("");
		crawlBottomText.setForeground(Color.RED);
		crawlBottomText.setFont(new Font("Arial", Font.BOLD, 25));
		crawlBottomText.setVerticalAlignment(JLabel.CENTER);
		crawlBottomText.setHorizontalAlignment(JLabel.CENTER);

		crawlPanel.setLayout(new GridLayout(6, 3, 10, 10));
		crawlPanel.add(crawlTopText);
		crawlPanel.add(crawlTwitter);
		crawlPanel.add(crawlReddit);
		crawlPanel.add(crawlStraitstimes);
		crawlPanel.add(backToMenu);
		crawlPanel.add(crawlBottomText);

		// Data Panel Content
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setMargin(new Insets(5, 5, 5, 5));
		textArea.setEditable(false);
		JScrollPane scrollPaneText = new JScrollPane(textArea);
		scrollPaneText.setPreferredSize(new Dimension(5, 150));
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		dataPanel.setLayout(new BorderLayout());
		dataPanel.add(panel7, BorderLayout.PAGE_START);
		dataPanel.add(scrollPaneText, BorderLayout.CENTER);
		dataPanel.add(panel6, BorderLayout.PAGE_END);
		panel6.add(backToMenu1, BorderLayout.CENTER);
		panel7.setLayout(new BorderLayout());
		panel7.add(panel8, BorderLayout.CENTER);
		panel7.add(panel9, BorderLayout.NORTH);
		panel7.add(panel10, BorderLayout.WEST);
		panel7.add(panel11, BorderLayout.EAST);
		panel7.add(panel12, BorderLayout.SOUTH);
		panel8.setLayout(new GridLayout(1, 3, 10, 10));
		panel8.add(twitter);
		panel8.add(reddit);
		panel8.add(straitsTimes);

		// Sentiment Analysis Panel Content
		textArea1.setBackground(Color.LIGHT_GRAY);
		textArea1.setMargin(new Insets(5, 5, 5, 5));
		textArea1.setEditable(false);
		JScrollPane scrollPaneText1 = new JScrollPane(textArea1);
		scrollPaneText1.setPreferredSize(new Dimension(5, 150));
		DefaultCaret caret1 = (DefaultCaret) textArea1.getCaret();
		caret1.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		sentimentPanel.setLayout(new BorderLayout());
		sentimentPanel.add(panel14, BorderLayout.PAGE_START);
		sentimentPanel.add(scrollPaneText1, BorderLayout.CENTER);
		sentimentPanel.add(panel13, BorderLayout.PAGE_END);
		panel13.add(backToMenu2, BorderLayout.CENTER);
		panel14.setLayout(new BorderLayout());
		panel14.add(panel15, BorderLayout.CENTER);
		panel14.add(panel16, BorderLayout.NORTH);
		panel14.add(panel17, BorderLayout.WEST);
		panel14.add(panel18, BorderLayout.EAST);
		panel14.add(panel19, BorderLayout.SOUTH);
		panel15.setLayout(new GridLayout(1, 3, 10, 10));
		panel15.add(sentimentAnalysisData);
		panel15.add(pieChart);

		// borders
		frame.add(panel1, BorderLayout.NORTH);
		frame.add(panel2, BorderLayout.WEST);
		frame.add(panel3, BorderLayout.EAST);
		frame.add(panel4, BorderLayout.SOUTH);
		frame.add(switchPanel, BorderLayout.CENTER);
		switchPanel.add(mainMenuPanel, "mainMenu");
		switchPanel.add(crawlPanel, "crawlWindow");
		switchPanel.add(dataPanel, "dataWindow");
		switchPanel.add(sentimentPanel, "sentimentAnalysis");
		card.show(switchPanel, "mainMenu");

		frame.setLocationRelativeTo(null);
		frame.setCursor(null);
		frame.setVisible(true);
	}

	/**
	 * Method for listening to button click Contains action to be performed on
	 * different button clicks
	 */
	public void actionPerformed(ActionEvent e) {
		// Main Menu Buttons
		// crawling all sources
		if (e.getSource() == crawlAll) {
			if (searchText == true) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				bottomText.setText("Crawling All Sources");
				// CrawlProgressBar newBar = new CrawlProgressBar("Crawling...","crawlAll");
				Thread redditThread = new Thread(redditCrawler);
				Thread twitterThread = new Thread(twitterCrawler);
				Thread stThread = new Thread(straitsCrawler);
				redditThread.start();
				twitterThread.start();
				stThread.start();
				// wait for all to end
				try {
					redditThread.join();
					twitterThread.join();
					stThread.join();

				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				redditThread.setDaemon(true);
				twitterThread.setDaemon(true);
				stThread.setDaemon(true);
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			} else if (searchText == false) {
				JOptionPane.showMessageDialog(null, "Please Enter crawl text", "title", JOptionPane.ERROR_MESSAGE);
			}
			JOptionPane.showMessageDialog(null, "Crawled all sources", "Crawled all sources",
					JOptionPane.INFORMATION_MESSAGE);

		}
		// if crawlWindow closed turn back on button.
		if (crawlFrameOpen == false) {
			crawlSpecific.setEnabled(true);
		}
		// crawl Specific button
		if (e.getSource() == crawlSpecific) {
			card.show(switchPanel, "crawlWindow");
			// if search text and frame not open
			if (searchText == true && crawlFrameOpen == false) {
				// CrawlWindow crawlWindow = new CrawlWindow(redditCrawler,
				// twitterCrawler,straitsCrawler);
				crawlFrameOpen = true;
				bottomText.setText("Crawling Specific");
			} else if (searchText == true && crawlFrameOpen == true) {
				bottomText.setText("Crawl Specific Window Open");
			}
			// if no search text show error message
			else if (searchText == false) {
				JOptionPane.showMessageDialog(null, "Please Enter crawl text", "title", JOptionPane.ERROR_MESSAGE);
			}
		}
		// Open show Data Window
		else if (e.getSource() == showCrawled) {
			card.show(switchPanel, "dataWindow");
			// if search text and frame not open
			if (searchText == true && crawlInfoOpen == false) {
				// CrawlDataInfo crawlDataInfo = new CrawlDataInfo();
				crawlInfoOpen = true;
				bottomText.setText("Crawling Specific");
			} else if (searchText == true && crawlInfoOpen == true) {
				bottomText.setText("Crawl Info Window Open");
			}
			// if no search text show error message
			else if (searchText == false) {
				JOptionPane.showMessageDialog(null, "Please Enter crawl text", "title", JOptionPane.ERROR_MESSAGE);
			}
		}
		// start sentiment analysis
		if (e.getSource() == sentimentAnalysis) {
			chartOpen = false;
			card.show(switchPanel, "sentimentAnalysis");
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			if (searchText == true) {
				bottomText.setText("Generated Sentiment Analysis");
				SentimentalAnalysis sentimentalAnalysis = new SentimentalAnalysis();
				SentimentData allData = new SentimentData();
				wordCloud.setSource("twitter");

				try {
					sentimentalAnalysis.Analyze(allData.getAllData(), "All Sources");
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				try {
					wordCloud.generateCloud();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				wordCloud.setSource("reddit");
				try {
					wordCloud.generateCloud();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				wordCloud.setSource("straitstimes");
				try {
					wordCloud.generateCloud();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 positive = sentimentalAnalysis.positiveCounter;
				negative = sentimentalAnalysis.negativeCounter;
				neutral = sentimentalAnalysis.neutralCounter;
				veryPositive = sentimentalAnalysis.veryPositiveCounter;
				veryNegative = sentimentalAnalysis.veryNegativeCounter;
			} else if (searchText == false) {
				JOptionPane.showMessageDialog(null, "Please Enter crawl text", "title", JOptionPane.ERROR_MESSAGE);
			}
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}

		if (e.getSource() == exportData) {
			if (searchText == true) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				bottomText.setText(" ");
				try {
					crawlerProgram.exportExcel(twitterCrawler);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					crawlerProgram.exportExcel(redditCrawler);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					crawlerProgram.exportExcel(straitsCrawler);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// CrawlProgressBar newBar = new CrawlProgressBar("Exporting...", "export");
			} else if (searchText == false) {
				JOptionPane.showMessageDialog(null, "Please Enter crawl text", "title", JOptionPane.ERROR_MESSAGE);
			}
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			JOptionPane.showMessageDialog(null, "Exported Data", "Exported Data", JOptionPane.INFORMATION_MESSAGE);
		}

		// close window
		else if (e.getSource() == exit) {
			System.exit(0);
		}
		// --------------------
		// Crawl Window Buttons
		else if (e.getSource() == crawlTwitter) {
			crawlBottomText.setText("Crawled from Twitter");
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			Thread twitterThread = new Thread(twitterCrawler);
			twitterThread.start();
			try {
				twitterThread.join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			twitterThread.setDaemon(true);
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			JOptionPane.showMessageDialog(null, "Crawled Twitter", "Crawled Twitter", JOptionPane.INFORMATION_MESSAGE);
		} else if (e.getSource() == crawlReddit) {
			crawlBottomText.setText("Crawled from Reddit");
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			Thread redditThread = new Thread(redditCrawler);
			redditThread.start();
			try {
				redditThread.join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			redditThread.setDaemon(true);
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			JOptionPane.showMessageDialog(null, "Crawled Reddit", "Crawled Reddit", JOptionPane.INFORMATION_MESSAGE);
		} else if (e.getSource() == crawlStraitstimes) {
			crawlBottomText.setText("Crawled from Straits Times");
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			Thread stThread = new Thread(straitsCrawler);
			stThread.start();
			try {
				stThread.join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			stThread.setDaemon(true);
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			JOptionPane.showMessageDialog(null, "Crawled Straits Times", "Crawled Straits Times",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (e.getSource() == backToMenu) {
			card.show(switchPanel, "mainMenu");
			crawlFrameOpen = false;
			crawlBottomText.setText("");
		} else if (e.getSource() == backToMenu1) {
			card.show(switchPanel, "mainMenu");
			crawlInfoOpen = false;
			bottomText.setText("");
		} else if (e.getSource() == twitter) {
			textArea.setText(null);
			showTwitterPosts();
		} else if (e.getSource() == reddit) {
			textArea.setText(null);
			showRedditPosts();
		} else if (e.getSource() == straitsTimes) {
			textArea.setText(null);
			showSTPosts();
		}
		// --------------------
		// Sentiment Analysis Window Buttons
		else if (e.getSource() == sentimentAnalysisData) {
			textArea1.setText(null);
			showSentimentAnalysis();
		} else if (e.getSource() == pieChart) {

			if (chartOpen == false) {
				try {
					crawlerChart demo = new crawlerChart("Sentiment Analysis", positive, negative, neutral,
							veryPositive, veryNegative);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (chartOpen = true) {
				JOptionPane.showMessageDialog(null, "Pie Chart already open", "Pie Chart already open",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getSource() == backToMenu2) {
			card.show(switchPanel, "mainMenu");
			crawlFrameOpen = false;
			crawlBottomText.setText("");
		}
	}

	/**
	 * Method for getting Twitter Posts from MongoDB and displaying in textArea
	 */
	private void showTwitterPosts() {
		ArrayList<TwitterPost> postList = new ArrayList<>();
		postList = importTwitterMongo();
		for (TwitterPost post : postList) {
			// print out title, and user who tweeted
			textArea.append("@" + post.getUser() + "\n");
			textArea.append("Tweet: " + post.getTitle() + "\n\n");
		}
	}

	/**
	 * Method for getting Reddit Posts from MongoDB and displaying in textArea
	 */
	private void showRedditPosts() {
		ArrayList<RedditPost> postList = new ArrayList<>();
		postList = importRedditMongo();
		for (RedditPost post : postList) {
			// print out title, and votes
			textArea.append("Title: " + post.getTitle() + "\n");
			textArea.append("Upvotes: " + String.valueOf(post.getVotes()) + "\n\n");
		}
	}

	/**
	 * Method for getting Straits Times Posts from MongoDB and displaying in
	 * textArea
	 */
	private void showSTPosts() {
		ArrayList<STPost> postList = new ArrayList<>();
		postList = importSTMongo();
		for (STPost post : postList) {
			// print out title
			textArea.append("Headlines: " + post.getTitle() + "\n\n");
		}
	}

	/**
	 * Method for getting Sentiment Analysis Posts from MongoDB and displaying in
	 * textArea
	 */
	private void showSentimentAnalysis() {
		ArrayList<SentimentPost> postList = new ArrayList<>();
		postList = importSentimentMongo();
		int count = 0;
		for (SentimentPost post : postList) {
			count += 1;
			// print out title, and votes
			textArea1.append("Title: " + post.getTitle() + "\n");
			textArea1.append("Sentiment: " + post.getSentiment() + "\n");
			textArea1.append("Source: " + post.getSource() + "\n\n");
		}
		textArea1.append("Total Posts: " + count + "\n\n");
	}
}
