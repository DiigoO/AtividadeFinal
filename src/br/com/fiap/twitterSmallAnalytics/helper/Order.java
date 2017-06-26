package br.com.fiap.twitterSmallAnalytics.helper;

import java.util.Collections;
import java.util.Comparator;
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
			
			return (user.get(user.size()-1).getData() + " <> " + user.get(0).getData());
			
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
