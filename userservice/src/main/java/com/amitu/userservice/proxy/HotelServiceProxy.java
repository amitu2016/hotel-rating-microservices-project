package com.amitu.userservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.amitu.userservice.entity.Hotel;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelServiceProxy {
	
	@GetMapping("/hotels/{hotelId}")
	public Hotel getHotelById(@PathVariable String hotelId);

}
