package com.scnet.iyiming.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnet.iyiming.entity.message.ShortMessage;
import com.scnet.iyiming.repository.message.ShortMessageRepository;

@Transactional
@Service
public class ShortMessageService {

	@Autowired
	private ShortMessageRepository shortMessageRepository;

	public void save(ShortMessage shortMessage) {
		shortMessageRepository.save(shortMessage);
	}

}
