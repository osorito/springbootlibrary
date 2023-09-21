package com.luv2code.springbootlibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.luv2code.springbootlibrary.entity.Message;
import com.luv2code.springbootlibrary.requestmodels.AdminQuestionRequest;
import com.luv2code.springbootlibrary.service.MessageService;
import com.luv2code.springbootlibrary.utils.ExtractJWT;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/messages")
public class MessagesController {
	private MessageService messageServies;
	
	@Autowired
	public MessagesController(MessageService messageServies) {
		this.messageServies=messageServies;
	}
	
	@PostMapping("/secure/add/message")
	public void postMesage(@RequestHeader(value="Authorization") String token, @RequestBody Message messageRequest) {
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		messageServies.postMessage(messageRequest, userEmail);
	}
	
	@PutMapping("/secure/admin/message")
	public void putMessage(@RequestHeader(value="Authorization") String token, @RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception
	{
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
		if(admin == null || !admin.equals("admin")) {
			throw new Exception("Administration page only");
		}
		messageServies.putMessage(adminQuestionRequest, userEmail);
	}

}
