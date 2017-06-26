package br.com.fiap.twitterSmallAnalytics.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import br.com.fiap.twitterSmallAnalytics.entity.StatusJSONImpl;

public class Order{

	public String orderObj(List<StatusJSONImpl> user, int code){
		
		if(code == 0){
			Collections.sort(user, new Comparator<StatusJSONImpl>() {
				@Override
				public int compare(StatusJSONImpl obj2, StatusJSONImpl obj1){
					return  obj1.getData().compareTo(obj2.getData());
				}
			});
			
			SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyy hh:mm:ss");
			Date OnderDate = null;
			Date NewestDate = null;
	        try {
	        	OnderDate = dt.parse(user.get(user.size()-1).getData().toString());
	        	NewestDate = dt.parse(user.get(0).getData().toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return OnderDate + " <> " + NewestDate;
			
		}else if(code == 1){
			Collections.sort(user, new Comparator<StatusJSONImpl>() {
				@Override
				public int compare(StatusJSONImpl obj2, StatusJSONImpl obj1){
					return  obj1.getNome().compareTo(obj2.getNome());
				}
			});
			
			return (user.get(user.size()-1).getNome() + " <> " + user.get(0).getNome());
		}else{
			throw new RuntimeException("Não foi possivel ordenar a lista! Code da lista não foi passado.");
		}
	}
	
}
