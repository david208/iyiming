package com.scnet.iyiming.service.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnet.iyiming.entity.project.Image;
import com.scnet.iyiming.entity.project.Project;
import com.scnet.iyiming.repository.project.ImageRepository;

@Service
@Transactional
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;

	public Image uploadImage(Project project) {
		Image image = new Image();
		image.setProject(project);
		return imageRepository.save(image);

	}

	public Image findOne(Long id) {
		return imageRepository.findOne(id);
	}

	public void deleteImage(Long id) {
		imageRepository.delete(id);
	}
}
