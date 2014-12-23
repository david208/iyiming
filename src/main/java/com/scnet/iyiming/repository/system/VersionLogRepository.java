package com.scnet.iyiming.repository.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.scnet.iyiming.entity.system.VersionLog;

@Repository
public interface VersionLogRepository extends JpaRepository<VersionLog, Long>, JpaSpecificationExecutor<VersionLog> {

	VersionLog findOneByTypeAndFlowId(String type, String flowId);

}
