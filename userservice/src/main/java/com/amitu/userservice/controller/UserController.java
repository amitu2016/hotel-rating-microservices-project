package com.amitu.userservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amitu.userservice.entity.Rating;
import com.amitu.userservice.entity.User;
import com.amitu.userservice.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	// create
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User user1 = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	
	int retryCount = 1;

	// single user get
	@GetMapping("/{userId}")
	//@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
	//@Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
	@RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
		logger.info("Get Single User Handler: UserController");
        logger.info("retryCount : {}",retryCount);
        retryCount++;
		User user = userService.getUser(userId);
		return ResponseEntity.ok(user);
	}
	
	
	
	//Fallback Method
	public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
		logger.info("Fallback Method Executed");
		User user = new User(userId, "Dummy User", "dummy@gmail.com", "THis is dummy User in Fallback", new ArrayList<Rating>());
		return new ResponseEntity<>(user, HttpStatus.TOO_MANY_REQUESTS);
	}

	// all user get
	@GetMapping
	public ResponseEntity<List<User>> getAllUser() {
		List<User> allUser = userService.getAllUser();
		return ResponseEntity.ok(allUser);
	}
	
	@PostMapping("/crateRating")
	public ResponseEntity<Rating> createRating(@RequestBody Rating rating){
		Rating rating1 = userService.createRating(rating);
		return ResponseEntity.status(HttpStatus.CREATED).body(rating1);
	}
	
	@DeleteMapping("/deleteRating/{ratingId}")
	public ResponseEntity<Rating> deleteRating(@PathVariable String ratingId){
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userService.deleteById(ratingId));
	}
	
	@PutMapping("/updateRating")
	public ResponseEntity<Rating> updateRating(@RequestBody Rating rating){
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.updateRating(rating));
	}

}
