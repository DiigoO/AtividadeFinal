package br.com.fiap.twitterSmallAnalytics;

import java.io.FileNotFoundException;

import br.com.fiap.twitterSmallAnalytics.core.BaseTwitterSmallAnalytics;

public class App {

	public static void main(String[] args) {
		
		BaseTwitterSmallAnalytics base = new BaseTwitterSmallAnalytics();
		try {
			base.runSearchTweets("#java8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
