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

		tableModel.addRow(Arrays.asList("1. Quantidade por dia de tweets da última semana.", countResult).toArray());
		tableModel.addRow(Arrays.asList("2. Quantidade por dia de retweets da última semana.", user.stream().mapToInt(StatusJSONImpl::getReTweets).sum()).toArray());
		tableModel.addRow(Arrays.asList("3. Quantidade por dia de favoritações da última semana.", user.stream().mapToInt(StatusJSONImpl::getFavoritos).sum()).toArray());
		tableModel.addRow(Arrays.asList("4. Ordenar os tweets pelo nome do autor, e exibir o primeiro nome e o último nome.", order.calculateMinAndMaxByName(user)).toArray());
		tableModel.addRow(Arrays.asList("5. Ordenar os tweets por data, e exibir a data mais recente e a menos recente.", order.calculateMinAndMaxByDate(user)).toArray());

		table = new JTable(tableModel);

		JOptionPane.showMessageDialog(null, new JScrollPane(table));

		System.out.println("1. Quantidade por dia de tweets da Ãºltima semana.\n"+ countResult
					+ "\n2. Quantidade por dia de retweets da Ãºltima semana.\n"+ user.stream().mapToInt(StatusJSONImpl::getReTweets).sum()				
					+ "\n3. Quantidade por dia de favoritaÃ§Ãµes da Ãºltima semana.\n"+ user.stream().mapToInt(StatusJSONImpl::getFavoritos).sum()
					+ "\n4. Ordenar os tweets pelo nome do autor, e exibir o primeiro nome e o Ãºltimo nome.\n" + order.calculateMinAndMaxByName(user)
					+ "\n5. Ordenar os tweets por data, e exibir a data mais recente e a menos recente.\n" + order.calculateMinAndMaxByDate(user));
	    
//	     postTweet(twitter, result, user);
	     
	}

	/**
	 * Post a tweet
	 * @param twitter
	 * @param result
	 * @param user
	 * @param order
	 */
	private void postTweet(Twitter twitter, QueryResult result,List<StatusJSONImpl> user) {
		Order order = new Order();
	    Status status = null;
		try {
			status = twitter.updateStatus("1.Tweets dia "+ result.getCount()
		+ "\n2.ReTweets dia "+ user.stream().mapToInt(StatusJSONImpl::getReTweets).sum() 
		+ "\n3.Favoritos dia "+ user.stream().mapToInt(StatusJSONImpl::getFavoritos).sum()
		+ "\n4.Sort por nome, exibindo primeiro e Ãºltimo " + order.calculateMinAndMaxByName(user)
		+ "\n5.Sort por data, exibindo primeiro e Ãºltimo " + order.calculateMinAndMaxByDate(user));
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     System.out.println("Successfully updated the status to [" + status.getText() + "].");
	}
	
	
}
