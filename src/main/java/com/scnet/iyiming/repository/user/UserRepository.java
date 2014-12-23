package com.scnet.iyiming.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.scnet.iyiming.entity.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	User findOneByUsername(String username);

	Integer countByUsername(String username);

	Integer countByMobile(String mobile);

}
