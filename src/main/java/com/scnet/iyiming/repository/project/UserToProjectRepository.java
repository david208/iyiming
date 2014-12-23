package com.scnet.iyiming.repository.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.scnet.iyiming.entity.project.Project;
import com.scnet.iyiming.entity.project.UserToProject;

@Repository
public interface UserToProjectRepository extends JpaRepository<UserToProject, Long>, JpaSpecificationExecutor<UserToProject> {

	UserToProject findOneByProjectIdAndUserId(Long projectId, Long userId);

	Long countByProjectAndFlowId(Project project, String flowId);
}
