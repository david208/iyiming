package com.scnet.iyiming.repository.project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scnet.iyiming.entity.project.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

	Project findOneByIdAndFlowIdIn(Long id, List<String> flowId);

	@Query("update Project p set p.top = 1 where p.top=2")
	@Modifying
	void cancelTop();
}
