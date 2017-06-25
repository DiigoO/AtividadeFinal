package br.com.fiap.twitterSmallAnalytics;

import br.com.fiap.twitterSmallAnalytics.core.BaseTwitterSmallAnalytics;

public class App {

	public static void main(String[] args) {
		
		BaseTwitterSmallAnalytics base = new BaseTwitterSmallAnalytics();
		base.runSearchTweets("#java8");
		
	}

}
