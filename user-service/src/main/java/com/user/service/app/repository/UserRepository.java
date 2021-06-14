package com.user.service.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.user.service.commons.app.entity.UserEntity;


@RepositoryRestResource(path="users")
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>{
	
	@RestResource(path="find-username")
	public UserEntity findByUsername(@Param("username") String username);
	
	@Query("select u from UserEntity u where u.username=?1")
	public UserEntity obtenerPorUsername(String username);

}
