package br.com.fiap.twitterSmallAnalytics.util;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.fiap.twitterSmallAnalytics.entity.StatusJSONImpl;

public class Order{
	
	public MinAndMax calculateMinAndMaxByName(List<StatusJSONImpl> users){
		return calculateMinAndMax(users, Order.SortField.NAME);		
	}
	
	public MinAndMax calculateMinAndMaxByDate(List<StatusJSONImpl> users){
		return calculateMinAndMax(users, Order.SortField.DATE);		
	}
	
	private MinAndMax calculateMinAndMax(List<StatusJSONImpl> users, Order.SortField sortField){
				
		if(sortField.equals(Order.SortField.DATE)){
			Collections.sort(users, new Comparator<StatusJSONImpl>() {
				@Override
				public int compare(StatusJSONImpl obj2, StatusJSONImpl obj1){
					return  obj1.getData().compareTo(obj2.getData());
				}
			});
						
			return new MinAndMax(users.get(users.size()-1), users.get(0), sortField);
			
		}else if(sortField.equals(Order.SortField.NAME)){
			Collections.sort(users, new Comparator<StatusJSONImpl>() {
				@Override
				public int compare(StatusJSONImpl obj2, StatusJSONImpl obj1){
					return  obj1.getNome().compareTo(obj2.getNome());
				}
			});
			
			return new MinAndMax(users.get(users.size()-1), users.get(0), sortField);
		}else{
			throw new RuntimeException("SortField unknown: "+sortField);
		}
	}
	
	private enum SortField {
		NAME, DATE
	}
	
	public class MinAndMax {
		private final StatusJSONImpl min;
		private final StatusJSONImpl max;
		private final SortField sortField;
		
		public MinAndMax(StatusJSONImpl min, StatusJSONImpl max, SortField sortField){
			this.min = min;
			this.max = max;
			this.sortField = sortField;
		}

		public StatusJSONImpl getMin() {
			return min;
		}

		public StatusJSONImpl getMax() {
			return max;
		}

		@Override
		public String toString() {
			if(SortField.NAME.equals(sortField)){
				return "StatusJSONImpl::nome [min=\"" + min.getNome() + "\", max=\"" + max.getNome() + "\"]";
			}
			if(SortField.DATE.equals(sortField)){
				return "StatusJSONImpl::data [min=\"" + min.getDataStr() + "\", max=\"" + max.getDataStr() + "\"]";
			}
			
			return "MinAndMax [min=" + min + ", max=" + max + "]";
		}
		
	}
}
