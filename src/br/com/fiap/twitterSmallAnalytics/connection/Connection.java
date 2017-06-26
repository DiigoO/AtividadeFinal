package br.com.fiap.twitterSmallAnalytics.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Connection {

	private static Connection connection;	
	private Properties prop = new Properties();
	
	public void configureConf(){
	    InputStream file = getClass().getClassLoader().getResourceAsStream("twitter4j.properties");
		try {
			prop.load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Twitter conexao() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		 cb.setDebugEnabled(true)
		 	.setOAuthConsumerKey(prop.getProperty("oauth.consumerKey"))
		    .setOAuthConsumerSecret(prop.getProperty("oauth.consumerSecret"))
		    .setOAuthAccessToken(prop.getProperty("oauth.accessToken"))
		    .setOAuthAccessTokenSecret(prop.getProperty("oauth.accessTokenSecret"));
		 
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance(); 
		
		return twitter;
	}
	
}
