package br.com.fiap.twitterSmallAnalytics;

import java.io.FileNotFoundException;

import br.com.fiap.twitterSmallAnalytics.core.BaseTwitterSmallAnalytics;
import twitter4j.TwitterException;

public class App {

	public static void main(String[] args) {
		
		BaseTwitterSmallAnalytics base = new BaseTwitterSmallAnalytics();
		try {
			base.searchTweets("#java8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
	}

}
