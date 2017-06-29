package br.com.fiap.twitterSmallAnalytics.core;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.fiap.twitterSmallAnalytics.connection.Connection;
import br.com.fiap.twitterSmallAnalytics.entity.StatusJSONImpl;
import br.com.fiap.twitterSmallAnalytics.util.Order;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class BaseTwitterSmallAnalytics {

	/**
	 * @param search
	 * @throws FileNotFoundException 
	 * http://twitter4j.org/en/index.html
	 * https://dev.twitter.com/rest/public/search
	 * http://twitter4j.org/en/javadoc.html
	 * @throws TwitterException 
	 */
	public void searchTweets(String search) throws FileNotFoundException, TwitterException{

		List<StatusJSONImpl> user = new ArrayList<>();
		int countResult = 0;
		Twitter twitter = Connection.conexao();		
		Query query = new Query(search);
		
		LocalDate hoje = LocalDate.now();
		query.setSince(hoje.minusDays(7).toString());
		query.setUntil(hoje.plusDays(1).toString());
		
		QueryResult result = twitter.search(query);
		
		while(result.hasNext()){
			query = result.nextQuery();

			 for (Status status : result.getTweets()) {
		    	 StatusJSONImpl statusUser = new StatusJSONImpl();	    	 
		    	 statusUser.setData(status.getCreatedAt());
		    	 statusUser.setNickname(status.getUser().getScreenName());
		    	 statusUser.setNome(status.getUser().getName());
		    	 statusUser.setReTweets(status.getRetweetCount());
		    	 statusUser.setFavoritos(status.getFavoriteCount());
		    	 
		    	 user.add(statusUser);
		     }
			 
			result = twitter.search(query);
			countResult = countResult + result.getCount();
		}
		
		Order order = new Order();
		
		JTable table = null;
		Object[] cols = { "Info.", "Quantidade" };
		DefaultTableModel tableModel = new DefaultTableModel(cols, 0);

		tableModel.addRow(Arrays.asList("1. Tweets: ", countResult).toArray());
		tableModel.addRow(Arrays.asList("2. Retweets: ", user.stream().mapToInt(StatusJSONImpl::getReTweets).sum()).toArray());
		tableModel.addRow(Arrays.asList("3. Favoritacoes: ", user.stream().mapToInt(StatusJSONImpl::getFavoritos).sum()).toArray());
		tableModel.addRow(Arrays.asList("4. Sort p/ nome: ", order.calculateMinAndMaxByName(user)).toArray());
		tableModel.addRow(Arrays.asList("5. Sort p/ data: ", order.calculateMinAndMaxByDate(user)).toArray());

		table = new JTable(tableModel);
		JScrollPane jPaneScroll = new JScrollPane(table);
		jPaneScroll.setHorizontalScrollBar(jPaneScroll.createHorizontalScrollBar());
		JOptionPane.showMessageDialog(null, jPaneScroll);

	    if(JOptionPane.showConfirmDialog(null, "Deseja publicar essa informacao no Twitter ?") == JOptionPane.YES_OPTION) {
		     postTweet(twitter, result, user, countResult);
	    }
	     
	}

	/**
	 * Post a tweet
	 * @param twitter
	 * @param result
	 * @param user
	 * @param order
	 */
	private void postTweet(Twitter twitter, QueryResult result,List<StatusJSONImpl> user, int countResult) {		
		Order order = new Order();
		
	    Status status = null;
		try {
			status = twitter.updateStatus("1 "+ result.getCount()
		+ "\n2 "+ user.stream().mapToInt(StatusJSONImpl::getReTweets).sum() 
		+ "\n3 "+ user.stream().mapToInt(StatusJSONImpl::getFavoritos).sum()
		+ "\n4 " + order.calculateMinAndMaxByName(user)
		+ "\n5 " + order.calculateMinAndMaxByDate(user));
		} catch (TwitterException e) {
			e.printStackTrace();
		    JOptionPane.showMessageDialog(null, "Erro ao tentar postar tweet [" + e + "].");
		}
	    JOptionPane.showMessageDialog(null, "Tweet postado com sucesso[" + status.getText() + "].");
	}
	
	
}
