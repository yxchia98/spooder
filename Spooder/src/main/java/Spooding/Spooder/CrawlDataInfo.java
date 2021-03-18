package Spooding.Spooder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
/**
 * CrawlDataInfo Class, open a new frame for looking at the existing crawled data for Reddit, Twitter, Straits Times
 */
public class CrawlDataInfo extends MongoConnect implements ActionListener{
	private static int fillCounter = 0;
	private int maxValue;

	JButton close, twitter,reddit, straitsTimes;
	private JFrame frame = new JFrame();
	private JLabel topText, bottomText;
	private JTextArea textArea = new JTextArea();
	

	//UI setup
	/**
	 * Constructor for CrawlDataInfo 
	 */
	CrawlDataInfo() {
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		JPanel panel6 = new JPanel();
		JPanel panel7 = new JPanel();
		JPanel panel8 = new JPanel();
		JPanel panel9 = new JPanel();
		JPanel panel10 = new JPanel();
		JPanel panel11 = new JPanel();
		JPanel panel12 = new JPanel();

		panel1.setBackground(Color.white);
		panel2.setBackground(Color.white);
		panel3.setBackground(Color.white);
		panel4.setBackground(Color.white);
		panel5.setBackground(Color.white);
		panel6.setBackground(Color.white);
		panel7.setBackground(Color.white);
		panel8.setBackground(Color.white);
		panel9.setBackground(Color.white);
		panel10.setBackground(Color.white);
		panel11.setBackground(Color.white);
		panel12.setBackground(Color.white);

		panel5.setLayout(new BorderLayout());
		panel7.setLayout(new BorderLayout());
		panel8.setLayout(new GridLayout(1,3,10,10));

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
		frame = new JFrame("Crawl Data Info");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(500, 500);

		//buttons	
		close = new JButton("Close Window");
		close.addActionListener(this);	
		close.setPreferredSize(new Dimension(100,40));
		close.setEnabled(true);
		
		twitter = new JButton("Twitter");
		twitter.addActionListener(this);	
		close.setPreferredSize(new Dimension(100,40));
		
		reddit = new JButton("Reddit");
		reddit.addActionListener(this);	
		close.setPreferredSize(new Dimension(100,40));
		
		straitsTimes = new JButton("Straits Times");
		straitsTimes.addActionListener(this);	
		close.setPreferredSize(new Dimension(100,40));
		
		
		//setup for scrolling Text Area
		JScrollPane scrollPaneText = new JScrollPane(textArea);
		scrollPaneText.setPreferredSize(new Dimension(5, 150));
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		//panel5 setup
		panel5.setLayout(new BorderLayout());
		panel5.add(panel7,BorderLayout.PAGE_START);
		panel5.add(scrollPaneText,BorderLayout.CENTER);
		panel5.add(panel6, BorderLayout.PAGE_END);
		//panel5.setBorder(null);
		
		
		//panel6 setup
		panel6.add(close,BorderLayout.CENTER);
		
		//panel7 setup
		panel7.add(panel8,BorderLayout.CENTER);
		panel7.add(panel9,BorderLayout.NORTH);
		panel7.add(panel10,BorderLayout.WEST);
		panel7.add(panel11,BorderLayout.EAST);
		panel7.add(panel12,BorderLayout.SOUTH);
		
		//panel8 setup
		panel8.add(twitter);
		panel8.add(reddit);
		panel8.add(straitsTimes);
		
		//frame borders
		frame.add(panel1, BorderLayout.NORTH);
		frame.add(panel2, BorderLayout.WEST);
		frame.add(panel3, BorderLayout.EAST);
		frame.add(panel4, BorderLayout.SOUTH);
		frame.add(panel5, BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
	
	/**
	 * Method for listening to button click
	 * Contains action to be performed on different button clicks
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == close) {
			GUI.crawlInfoOpen = false;
			GUI.bottomText.setText("");
			frame.dispose();
		}
		if (e.getSource() == twitter) {
			textArea.setText(null);
			showTwitterPosts();
		}
		if (e.getSource() == reddit) {
			textArea.setText(null);
			showRedditPosts();
		}
		if (e.getSource() == straitsTimes) {
			textArea.setText(null);
			showSTPosts();
		}

	}
	/**
	 * Method for getting Twitter Posts from MongoDB and displaying in textArea
	 */
    private void showTwitterPosts() {
        ArrayList<TwitterPost> postList = new ArrayList<>();
        postList = importTwitterMongo();
        for( TwitterPost post : postList) {
            //print out title, and user who tweeted
            textArea.append("@"+ post.getUser()+"\n");
            textArea.append("Tweet: " + post.getTitle() + "\n\n");
        }
    }
	/**
	 * Method for getting Reddit Posts from MongoDB and displaying in textArea
	 */
    private void showRedditPosts() {
        ArrayList<RedditPost> postList = new ArrayList<>();
        postList = importRedditMongo();
        for( RedditPost post : postList) {
            //print out title, and votes
        	 textArea.append("Title: " + post.getTitle() + "\n");
        	 textArea.append("Upvotes: " +String.valueOf(post.getVotes())+"\n\n");
        }
    }
	/**
	 * Method for getting Straits Times Posts from MongoDB and displaying in textArea
	 */
    private void showSTPosts() {
        ArrayList<STPost> postList = new ArrayList<>();
        postList = importSTMongo();
        for( STPost post : postList) {
            //print out title 
        	 textArea.append("Headlines: " + post.getTitle()+"\n\n");
        }
    }

}

