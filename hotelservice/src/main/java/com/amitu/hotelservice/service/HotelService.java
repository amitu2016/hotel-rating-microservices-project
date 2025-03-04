package com.amitu.hotelservice.service;

import java.util.List;

import com.amitu.hotelservice.entity.Hotel;

public interface HotelService {
	
	//create
    Hotel create(Hotel hotel);

    //get all
    List<Hotel> getAll();

    //get single
    Hotel get(String id);
}
