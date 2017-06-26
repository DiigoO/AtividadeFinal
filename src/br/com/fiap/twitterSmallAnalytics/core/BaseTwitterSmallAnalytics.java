package br.com.fiap.twitterSmallAnalytics.core;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.twitterSmallAnalytics.connection.Connection;
import br.com.fiap.twitterSmallAnalytics.entity.StatusJSONImpl;
import br.com.fiap.twitterSmallAnalytics.util.Order;
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
//		Connection con = new Connection();
//		con.configureConf();
//		Twitter twitter = con.conexao(); 
		QueryResult result = null;
		
		/**
		 * Search Tweets
		 * so traz de 24h ou da ultima semana dependendo da carga da API
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
	     
		Order order = new Order();
		System.out.println(user);
		
		System.out.println("1. Quantidade por dia de tweets da última semana.\n"+ result.getCount()
					+ "\n2. Quantidade por dia de retweets da última semana.\n"+ user.stream().mapToInt(StatusJSONImpl::getReTweets).sum()				
					+ "\n3. Quantidade por dia de favoritações da última semana.\n"+ user.stream().mapToInt(StatusJSONImpl::getFavoritos).sum()
					+ "\n4. Ordenar os tweets pelo nome do autor, e exibir o primeiro nome e o último nome.\n" + order.calculateMinAndMaxByName(user)
					+ "\n5. Ordenar os tweets por data, e exibir a data mais recente e a menos recente.\n" + order.calculateMinAndMaxByDate(user));
	    
//	     postTweet(twitter, result, countRetweets, countFavort, user, order);
	     
	}

	private void postTweet(Twitter twitter, QueryResult result, int countRetweets, int countFavort,
			List<StatusJSONImpl> user, Order order) {
		/**
	      * post a Tweet
	      */
	    Status status = null;
		try {
			status = twitter.updateStatus("1.Tweets dia "+ result.getCount()
		+ "\n2.ReTweets dia "+ user.stream().mapToInt(StatusJSONImpl::getReTweets).sum() 
		+ "\n3.Favoritos dia "+ user.stream().mapToInt(StatusJSONImpl::getFavoritos).sum()
		+ "\n4.Sort por nome, exibindo primeiro e último " + order.calculateMinAndMaxByName(user)
		+ "\n5.Sort por data, exibindo primeiro e último " + order.calculateMinAndMaxByDate(user));
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     System.out.println("Successfully updated the status to [" + status.getText() + "].");
	}
	
	
}
