package com.example.sampleaffablebean.dao;

import com.example.sampleaffablebean.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

public interface CategoryDao extends JpaRepository<Category, Integer> {
@Query("""
select p.category.id from Product p where p.id=?1
""")
    int findCategoryByProductId(int pid);

}
