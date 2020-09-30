package com.dana.app.data.repositories;

import com.dana.app.data.entities.Products;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends PagingAndSortingRepository<Products, Long>, JpaSpecificationExecutor<Products> {
}
