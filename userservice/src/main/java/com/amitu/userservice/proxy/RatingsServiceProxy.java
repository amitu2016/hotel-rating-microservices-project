package com.amitu.userservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.amitu.userservice.entity.Rating;

@FeignClient(name = "RATING-SERVICE")
public interface RatingsServiceProxy {

	@GetMapping("/ratings/users/{userId}")
	public Rating[] getAllRatings(@PathVariable String userId);
	
	@PostMapping("/ratings")
	public Rating create(@RequestBody Rating rating);
	
	@PutMapping("/ratings")
	public Rating update(@RequestBody Rating rating);
	
	@DeleteMapping("/ratings/{ratingId}")
	public Rating deleteRatingById(@PathVariable String ratingId);
	
	
}
