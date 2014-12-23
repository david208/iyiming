package com.scnet.iyiming.repository.user;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.scnet.iyiming.entity.user.ValidateCode;

@Repository
public interface ValidateCodeRepository extends JpaRepository<ValidateCode, Long>, JpaSpecificationExecutor<ValidateCode> {

	ValidateCode findOneByMobileAndExpireTimeGreaterThan(String mobile, Date now);

	Integer countByMobileAndCodeAndExpireTimeGreaterThan(String mobile, String code, Date now);

}
