package com.link.convert.repository;


import com.link.convert.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand,Integer> {

    Optional<Brand> findBrandByBrandName(String brandName);

}
