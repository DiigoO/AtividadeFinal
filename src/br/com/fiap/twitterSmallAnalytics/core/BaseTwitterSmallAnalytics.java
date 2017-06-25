package br.com.fiap.twitterSmallAnalytics.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.fiap.twitterSmallAnalytics.entity.StatusJSONImpl;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class BaseTwitterSmallAnalytics {

	/**
	 * TODO implementar tweets antigos
	 * @param search
	 */
	public void runSearchTweets(String search){
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
		 * só traz de 24h ou da ultima semana dependendo da carga
		 * https://stackoverflow.com/questions/7974999/retrieving-tweets-from-twitter-using-twitter4j
		 */
		try {
			Query query = new Query(search);
			result = twitter.search(query);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int countRetweets = 0;
		int countFavort = 0;
		
		List<StatusJSONImpl> user = new ArrayList<>();
		
	     for (Status status : result.getTweets()) {
	    	 StatusJSONImpl statusUser = new StatusJSONImpl();

	    	 countRetweets = countRetweets + status.getRetweetCount();
	    	 countFavort = countFavort + status.getFavoriteCount();
	    	 
	    	 statusUser.setData(status.getCreatedAt());
	    	 statusUser.setNickname(status.getUser().getScreenName());
	    	 statusUser.setNome(status.getUser().getName());
	    	 statusUser.setReTweets(status.getRetweetCount());
	    	 statusUser.setFavoritos(status.getFavoriteCount());
	    	 	    	 
	    	 user.add(statusUser);
	     }
	     
		System.out.println("\nQuantidade de Tweets: "+result.getCount());
		System.out.println("Quantidade de reTweets: "+countRetweets);
		System.out.println("Quantidade de Favoritacoes: "+countFavort);
		
		Collections.sort(user);
	    
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
