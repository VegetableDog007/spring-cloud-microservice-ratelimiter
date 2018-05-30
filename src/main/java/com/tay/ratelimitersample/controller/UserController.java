package com.tay.ratelimitersample.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tay.ratelimitersample.entity.User;
import com.tay.ratelimitersample.ratelimiter.RateLimiter;
import com.tay.ratelimitersample.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @GetMapping("/{id}")
  public User findById(@PathVariable Long id, @RequestHeader HttpHeaders headers) {
	  
    User findOne = this.userRepository.findOne(id);
  
    return findOne;
  }
  
  @RateLimiter(base = RateLimiter.Base.General, permits = 2, timeUnit = TimeUnit.MINUTES)
  @GetMapping("/test")
  public String test() {
	  return "test!";
  }
  
  @RateLimiter(base = RateLimiter.Base.IP, path="/testforid", permits = 4, timeUnit = TimeUnit.MINUTES)
  @GetMapping("/testforid/{id}")
  public String testforid(@PathVariable Long id) {
	  return "test! " + id;
  }

}
