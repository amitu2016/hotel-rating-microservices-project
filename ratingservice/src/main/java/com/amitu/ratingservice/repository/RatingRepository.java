package com.amitu.ratingservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.amitu.ratingservice.entity.Rating;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

	//custom finder methods
    List<Rating> findByUserId(String userId);
    List<Rating> findByHotelId(String hotelId);
}
