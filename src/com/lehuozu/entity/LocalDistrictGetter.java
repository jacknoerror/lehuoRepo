package com.lehuozu.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.lehuozu.vo.Place;

import android.util.SparseArray;

public class LocalDistrictGetter {
	private static LocalDistrictGetter localdistrictgetter;
	private LocalDistrictGetter(){
		placeMap = new SparseArray<List<Place>>();
		placeMap.put(0, new ArrayList<Place>());
		placeMap.get(0).add(new Place(31,"浙江省"));
		placeMap.put(31, new ArrayList<Place>());
		placeMap.get(31).add(new Place(383,"杭州市"));
		placeMap.put(383, new ArrayList<Place>());
		placeMap.get(383).add(new Place(3234,"江干区"));
		placeMap.get(383).add(new Place(3230,"上城区"));
		placeMap.get(383).add(new Place(3232,"拱墅区"));
		placeMap.get(383).add(new Place(3231,"下城区"));
		placeMap.get(383).add(new Place(3229,"西湖区"));
		placeMap.get(383).add(new Place(3233,"滨江区"));
		placeMap.get(383).add(new Place(3235,"萧山区"));
		placeMap.get(383).add(new Place(3236,"余杭区"));
	}
	public static LocalDistrictGetter getInstance(){
	   if(null==localdistrictgetter){
	      localdistrictgetter = new LocalDistrictGetter();
	   }
	   return localdistrictgetter;
	}

	SparseArray<List<Place>> placeMap;
	
	public List<Place> getPlaceList(int id){
		return placeMap.get(id);
	}
}
