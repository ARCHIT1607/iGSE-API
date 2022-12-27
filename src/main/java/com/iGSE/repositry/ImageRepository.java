package com.iGSE.repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iGSE.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
	Optional<Image> findByName(String name);
}
