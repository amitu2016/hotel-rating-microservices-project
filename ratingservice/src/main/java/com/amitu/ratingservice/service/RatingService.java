package com.amitu.ratingservice.service;

import java.util.List;

import com.amitu.ratingservice.entity.Rating;

public interface RatingService {

	// create
	Rating create(Rating rating);

	// get all ratings
	List<Rating> getRatings();

	// get all by UserId
	List<Rating> getRatingByUserId(String userId);

	// get all by hotel
	List<Rating> getRatingByHotelId(String hotelId);

	Rating update(Rating rating);

	Rating deleteById(String ratingId);
}
