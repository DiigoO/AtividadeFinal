package br.com.fiap.twitterSmallAnalytics.core;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.fiap.twitterSmallAnalytics.connection.Connection;
import br.com.fiap.twitterSmallAnalytics.entity.StatusJSONImpl;
import br.com.fiap.twitterSmallAnalytics.util.Order;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
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
	public void searchTweets(String search) throws FileNotFoundException{

		List<StatusJSONImpl> user = new ArrayList<>();
		Order order = new Order();
		int countResult = 0;
		Twitter twitter = Connection.conexao();		
		int daysBefore = 7;		
		LocalDate hoje = LocalDate.now();
		JTable table = null;
		Object[] cols = { "Info.", "Quantidade" };
		DefaultTableModel tableModel = new DefaultTableModel(cols, 0);
		Dimension d = new Dimension(1000, 400);
		
		for(int i = daysBefore; i > 0; i--){
			Query query = new Query(search);
			query.setSince(hoje.minusDays(i).toString());
			query.setUntil(hoje.minusDays(i-1).toString());
			
			System.out.println(query);
			
			QueryResult result = null;
			
			try {
				result = twitter.search(query);
			} catch (TwitterException e1) {
				e1.printStackTrace();
			}
			
			while(result.hasNext()){
				query = result.nextQuery();

				 for (Status status : result.getTweets()) {
			    	 StatusJSONImpl statusUser = new StatusJSONImpl();	    	 
			    	 statusUser.setData(status.getCreatedAt());
			    	 statusUser.setNickname(status.getUser().getScreenName());
			    	 statusUser.setNome(status.getUser().getName());
			    	 statusUser.setReTweets(status.getRetweetCount());
			    	 statusUser.setFavoritos(status.getFavoriteCount());
			    	 
			    	 System.out.println(status.getCreatedAt());
			    	 
			    	 user.add(statusUser);
			     }
				 
				try {
					result = twitter.search(query);
				} catch (TwitterException e) {
					e.printStackTrace();
				}
				countResult = countResult + result.getCount();
			}
			
			tableModel.addRow(Arrays.asList("1. Tweets: ", countResult).toArray());
			tableModel.addRow(Arrays.asList("2. Retweets: ", user.stream().mapToInt(StatusJSONImpl::getReTweets).sum()).toArray());
			tableModel.addRow(Arrays.asList("3. Favoritacoes: ", user.stream().mapToInt(StatusJSONImpl::getFavoritos).sum()).toArray());
			tableModel.addRow(Arrays.asList("4. Sort p/ nome: ", order.calculateMinAndMaxByName(user)).toArray());
			tableModel.addRow(Arrays.asList("5. Sort p/ data: ", order.calculateMinAndMaxByDate(user)).toArray());

			table = new JTable(tableModel);
			table.getColumnModel().getColumn(0).setMinWidth(120);
			table.getColumnModel().getColumn(0).setMaxWidth(120);
			
			JScrollPane jPaneScroll = new JScrollPane(table);
			jPaneScroll.setSize(d);
			jPaneScroll.setPreferredSize(d);
			jPaneScroll.setMinimumSize(d);
			jPaneScroll.setMaximumSize(d);
			jPaneScroll.setHorizontalScrollBar(jPaneScroll.createHorizontalScrollBar());
			JOptionPane.showMessageDialog(null, jPaneScroll);

		    if(JOptionPane.showConfirmDialog(null, "Deseja publicar essa informacao no Twitter ?", null, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			     postTweet(twitter, user, countResult);
		    }
		}
		System.exit(1);
	}

	/**
	 * Post a tweet
	 * @param twitter
	 * @param result
	 * @param user
	 * @param order
	 */
	private void postTweet(Twitter twitter, List<StatusJSONImpl> user, int countResult) {		
		Order order = new Order();
		
	    Status status = null;
		try {
			status = twitter.updateStatus("1 "+ countResult
		+ "\n2 "+ user.stream().mapToInt(StatusJSONImpl::getReTweets).sum() 
		+ "\n3 "+ user.stream().mapToInt(StatusJSONImpl::getFavoritos).sum()
		+ "\n4 " + order.calculateMinAndMaxByName(user)
		+ "\n5 " + order.calculateMinAndMaxByDate(user));
		} catch (Exception e) {
			e.printStackTrace();
		    JOptionPane.showMessageDialog(null, "Erro ao tentar postar tweet [" + e + "].");
		}
	    JOptionPane.showMessageDialog(null, "Tweet postado com sucesso[" + status.getText() + "].");
	}
	
	
}
