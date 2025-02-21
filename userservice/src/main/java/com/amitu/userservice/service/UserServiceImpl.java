package com.amitu.userservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.amitu.userservice.entity.Hotel;
import com.amitu.userservice.entity.Rating;
import com.amitu.userservice.entity.User;
import com.amitu.userservice.proxy.HotelServiceProxy;
import com.amitu.userservice.proxy.RatingsServiceProxy;
import com.amitu.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HotelServiceProxy hotelServiceProxy;
	
	@Autowired
	private RatingsServiceProxy ratingsServiceProxy;

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {
		// generate unique userid
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		// implement RATING SERVICE CALL: USING REST TEMPLATE
		List<User> users = userRepository.findAll();
		users.forEach(user -> setUserRating(user));
		return users;
	}

	// get single user
	@Override
	public User getUser(String userId) {
		try {
			// get user from database with the help of user repository
			User user = userRepository.findById(userId).get();
			setUserRating(user);
			return user;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ResourceAccessException("Exception Occoured");
		}
	}

	private void setUserRating(User user) {
		Rating[] ratingsArray = ratingsServiceProxy.getAllRatings(user.getUserId());
		
		List<Rating> ratingsOfUser = Arrays.asList(ratingsArray);
		
		logger.info("ratingsOfUser -----------> {}", ratingsOfUser);
		List<Rating> ratingsOfUserWithHotels = ratingsOfUser.stream().map(rating -> {
			Hotel hotel = hotelServiceProxy.getHotelById(rating.getHotelId());
			logger.info("hotel -----------> {}", hotel);
			rating.setHotel(hotel);
			return rating;
		}).collect(Collectors.toList());
		user.setRatings(ratingsOfUserWithHotels);
	}

	@Override
	public Rating createRating(Rating rating) {
		Rating newRating = ratingsServiceProxy.create(rating);
		newRating.setHotel(hotelServiceProxy.getHotelById(newRating.getHotelId()));
		return newRating;
	}

	@Override
	public Rating deleteById(String ratingId) {
		return ratingsServiceProxy.deleteRatingById(ratingId);
	}

	@Override
	public Rating updateRating(Rating rating) {
		Rating newRating = ratingsServiceProxy.update(rating);
		newRating.setHotel(hotelServiceProxy.getHotelById(newRating.getHotelId()));
		return newRating;
	}

}
