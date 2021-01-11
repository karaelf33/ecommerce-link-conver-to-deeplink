package com.link.convert.repository;


import com.link.convert.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository  extends JpaRepository<Category,Integer> {

    Optional<Category> findCategoryByCategoryName(String name);

}
