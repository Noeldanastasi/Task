package com.dana.app.data.repositories;


import com.dana.app.data.entities.UserTable;
import com.dana.app.data.specifications.UserTablefilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Usertablerepo extends PagingAndSortingRepository<UserTable, Long>, JpaSpecificationExecutor<UserTable> {
    Page<UserTable> findAll(UserTablefilter userTablefilter, Pageable pageable);
}
