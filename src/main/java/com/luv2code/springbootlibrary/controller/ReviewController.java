package com.luv2code.springbootlibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.luv2code.springbootlibrary.requestmodels.ReviewRequest;
import com.luv2code.springbootlibrary.service.ReviewService;
import com.luv2code.springbootlibrary.utils.ExtractJWT;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	private ReviewService reviewService;
	
	@Autowired
	public ReviewController(ReviewService reviewService) {
		this.reviewService=reviewService;
	}
	
	@PostMapping("/secure")
	public void postReview(@RequestHeader(value="Authorization") String token, @RequestBody ReviewRequest reviewRequest) throws Exception
	{
		String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
		if(userEmail == null) {
			throw new Exception("User Email is missing");
		}
		reviewService.postReview(userEmail, reviewRequest);
	}
	
	
	@GetMapping("/secure/user/book")
	public Boolean reviewBookByUser(@RequestHeader(value="Authorization") String token, @RequestParam Long bookId) throws Exception{
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		if(userEmail == null ) {
			throw new Exception("User Email is mmissing");
		}
		return reviewService.userReviewListed(userEmail, bookId);
	}
}
