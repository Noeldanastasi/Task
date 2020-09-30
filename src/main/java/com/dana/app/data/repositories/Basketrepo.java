package com.dana.app.data.repositories;

import com.dana.app.data.entities.BasketTable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Basketrepo extends PagingAndSortingRepository<BasketTable, Long>, JpaSpecificationExecutor<BasketTable> {
}
