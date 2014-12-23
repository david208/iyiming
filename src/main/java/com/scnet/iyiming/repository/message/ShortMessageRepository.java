package com.scnet.iyiming.repository.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.scnet.iyiming.entity.message.ShortMessage;

@Repository
public interface ShortMessageRepository extends JpaRepository<ShortMessage, Long>, JpaSpecificationExecutor<ShortMessage> {

}
