package com.scnet.iyiming.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.scnet.iyiming.entity.user.FeedBack;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, Long>, JpaSpecificationExecutor<FeedBack> {

}
