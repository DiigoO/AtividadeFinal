package br.com.fiap.twitterSmallAnalytics.connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Connection {

	public static Twitter conexao() throws FileNotFoundException {
		Properties prop = new Properties();
	    InputStream input = new FileInputStream("resource/twitter4j.properties");
		try {
			prop.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
