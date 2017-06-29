package br.com.fiap.twitterSmallAnalytics;

import javax.swing.JOptionPane;
import br.com.fiap.twitterSmallAnalytics.core.BaseTwitterSmallAnalytics;

public class App {

	/**
	 * MAIN da aplicacao
	 * @param args
	 */
	public static void main(String[] args) {
		
		BaseTwitterSmallAnalytics base = new BaseTwitterSmallAnalytics();
		try {
			String res = JOptionPane.showInputDialog(null, "Insira sua #hashtag: ");
			if(!res.isEmpty()){
				base.searchTweets("res");
			}else{
				JOptionPane.showMessageDialog(null, "Não foi passada nenhuma informação.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
