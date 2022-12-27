package com.iGSE.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iGSE.entity.Image;
import com.iGSE.repositry.ImageRepository;
import com.iGSE.util.ImageUploadResponse;
import com.iGSE.util.ImageUtility;

@RestController
@RequestMapping("/qr")
public class ImageController {

	@Autowired
    ImageRepository imageRepository;

    @PostMapping("/upload/image")
    public ResponseEntity<ImageUploadResponse> uplaodImage(@RequestParam("image") MultipartFile file)
            throws IOException {
    	
    	Image img = new Image();
    	img.setImage(ImageUtility.compressImage(file.getBytes()));
    	img.setName(file.getOriginalFilename());
    	img.setType(file.getContentType());
        imageRepository.save(img);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse("Image uploaded successfully: " +
                        file.getOriginalFilename()));
    }

    @GetMapping(path = {"/get/image/info/{name}"})
    public Image getImageDetails(@PathVariable("name") String name) throws IOException {

        final Optional<Image> dbImage = imageRepository.findByName(name);

        Image img = new Image();
        img.setId(dbImage.get().getId());
    	img.setImage(ImageUtility.decompressImage(dbImage.get().getImage()));
    	img.setName(dbImage.get().getName());
    	img.setType(dbImage.get().getType());
        return img;
    }

    @GetMapping(path = {"/get/image/{name}"})
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws IOException {

        final Optional<Image> dbImage = imageRepository.findByName(name);

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(dbImage.get().getType()))
                .body(ImageUtility.decompressImage(dbImage.get().getImage()));
    }
}
