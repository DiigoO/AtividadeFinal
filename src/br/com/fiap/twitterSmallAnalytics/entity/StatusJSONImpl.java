package br.com.fiap.twitterSmallAnalytics.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StatusJSONImpl implements Comparable<StatusJSONImpl> {
	private String nickname;
	private String nome;
	private Date data;
	private int reTweets;
	private int favoritos;
	
	private static final SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:SS");

	public StatusJSONImpl() {
		super();
	}
	
	
	public String getNickname() {
		return nickname;
	}



	public void setNickname(String nickname) {
		this.nickname = nickname;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome.substring(0, 1).toUpperCase() + nome.substring(1);
	}



	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public String getDataStr(){
		return dt.format(data);
	}

	public int getReTweets() {
		return reTweets;
	}



	public void setReTweets(int reTweets) {
		this.reTweets = reTweets;
	}



	public int getFavoritos() {
		return favoritos;
	}



	public void setFavoritos(int favoritos) {
		this.favoritos = favoritos;
	}


	@Override
	public String toString() {
		return "StatusJSONImpl [nickname=" + nickname + ", nome=" + nome + ", data=" + data + ", reTweets=" + reTweets
				+ ", favoritos=" + favoritos + "]";
	}


	@Override
	public int compareTo(StatusJSONImpl o) {
		// TODO Auto-generated method stub
		return nome.compareTo(o.nome);
	}
	
}
