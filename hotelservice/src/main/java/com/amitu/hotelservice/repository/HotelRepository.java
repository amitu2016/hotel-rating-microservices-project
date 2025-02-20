package com.amitu.hotelservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amitu.hotelservice.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, String> {
	
}
