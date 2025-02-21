package com.amitu.ratingservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.amitu.ratingservice.entity.Rating;
import com.amitu.ratingservice.repository.RatingRepository;

@Service
public class RatingServiceImpl implements RatingService {
	@Autowired
	private RatingRepository repository;

	@Override
	public Rating create(Rating rating) {
		return repository.save(rating);
	}

	@Override
	public List<Rating> getRatings() {
		return repository.findAll();
	}

	@Override
	public List<Rating> getRatingByUserId(String userId) {
		return repository.findByUserId(userId);
	}

	@Override
	public List<Rating> getRatingByHotelId(String hotelId) {
		return repository.findByHotelId(hotelId);
	}

	@Override
	public Rating update(Rating rating) {
		Rating originalRating = repository.findById(rating.getRatingId()).get();
		originalRating.setUserId(rating.getUserId());
		originalRating.setRating(rating.getRating());
		originalRating.setFeedback(rating.getFeedback());
		originalRating.setHotelId(rating.getHotelId());
		return repository.save(originalRating);
	}

	@Override
	public Rating deleteById(String ratingId) {
		try {
			Rating originalRating = repository.findById(ratingId).get();
			repository.delete(originalRating);
			return originalRating;
		} catch (Exception e) {
			throw new ResourceAccessException(ratingId+ " Not found");
		}
	}

}
