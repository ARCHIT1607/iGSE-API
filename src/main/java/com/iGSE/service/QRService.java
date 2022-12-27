package com.iGSE.service;

import java.awt.Image;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iGSE.entity.EVC;
import com.iGSE.repositry.ImageRepository;
import com.iGSE.util.ImageUploadResponse;
import com.iGSE.util.ImageUtility;

@Service
public class QRService {

	@Autowired
	ImageRepository imageRepository;

	public Object uplaodQr(MultipartFile file, String evc) throws IOException {
		EVC img = new EVC();
    	img.setImage(ImageUtility.compressImage(file.getBytes()));
    	img.setName(file.getOriginalFilename());
    	img.setType(file.getContentType());
    	img.setEvc(evc);
        imageRepository.save(img);
		return new ImageUploadResponse("Image uploaded successfully: " +
              file.getOriginalFilename());
	}

	public EVC getQrDetails(String evc) {
		final Optional<EVC> dbImage = imageRepository.findByEvc(evc);

		EVC img = new EVC();
        img.setId(dbImage.get().getId());
    	img.setImage(ImageUtility.decompressImage(dbImage.get().getImage()));
    	img.setName(dbImage.get().getName());
    	img.setType(dbImage.get().getType());
    	img.setEvc(dbImage.get().getEvc());
    	img.setExpired(dbImage.get().isExpired());
		return img;
	}

	public ResponseEntity<byte[]> getQr(String name) {
		final Optional<EVC> dbImage = imageRepository.findByName(name);
		return ResponseEntity
              .ok()
              .contentType(MediaType.valueOf(dbImage.get().getType()))
              .body(ImageUtility.decompressImage(dbImage.get().getImage()));
	}

}
