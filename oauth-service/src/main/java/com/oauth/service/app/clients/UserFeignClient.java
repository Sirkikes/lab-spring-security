package com.oauth.service.app.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.user.service.commons.app.entity.UserEntity;

@FeignClient(name="user-service")
public interface UserFeignClient {
	
	@GetMapping("/users/search/find-username")
	public UserEntity findByUsername(@RequestParam String username);

}
