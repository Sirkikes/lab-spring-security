package com.oauth.service.app.services;

import com.user.service.commons.app.entity.UserEntity;

public interface IUserService {

	public UserEntity findByUsername(String username);
}
