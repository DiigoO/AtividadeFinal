package br.com.fiap.twitterSmallAnalytics;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class App {

	public static void main(String[] args) {
		/**
		 * http://twitter4j.org/en/index.html
		 * https://github.com/yusuke/twitter4j
		 * https://dev.twitter.com/rest/public/search
		 */		
		
		 ConfigurationBuilder cb = new ConfigurationBuilder();
		 cb.setDebugEnabled(true)
		 	.setOAuthConsumerKey("UC2hgX30dz7CLAjMqaPJ7NaRi")
		    .setOAuthConsumerSecret("8CDa39mdLrcFqyJRXdHiOYJLdqVChakerByoX8AkYQ23GBZQjG")
		    .setOAuthAccessToken("877588033750675456-8h84QaiqJbgJR8hhYpazbcSD3gOPFtC")
		    .setOAuthAccessTokenSecret("SKazR0T3tPG5Sy6PwWfqaoTgY9NxXmxxQUOLmYstKJHTu");
		 
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance(); 
		QueryResult result = null;
		
		/**
		 * Search Tweets
		 */
		try {
			Query query = new Query("#java8");
			result = twitter.search(query);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//	     for (Status status : result.getTweets()) {
//	    	 System.out.println("RetweetCount: "+status.getRetweetCount());
//	    	 System.out.println("FavoriteCount: "+status.getFavoriteCount());
//	     }
	    
	     /**
	      * post a Tweet
	      */
//	    Status status = null;
//		try {
//			status = twitter.updateStatus("My first twitte");
//		} catch (TwitterException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	     System.out.println("Successfully updated the status to [" + status.getText() + "].");
	     
	}

}
