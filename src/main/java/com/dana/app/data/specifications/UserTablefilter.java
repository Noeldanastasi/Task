package com.dana.app.data.specifications;

import com.dana.app.data.entities.Products;

import javax.persistence.criteria.*;
import java.util.ArrayList;

public class UserTablefilter implements org.springframework.data.jpa.domain.Specification<Products>{

    String userQuery;

    public  UserTablefilter(String queryString) {
        this.userQuery = queryString;
    }

    @Override
    public Predicate toPredicate(Root<Products> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (userQuery != null && userQuery != "") {
            predicates.add(criteriaBuilder.like(root.get("ID"), '%' + userQuery + '%'));
            predicates.add(criteriaBuilder.like(root.get("ProductID"), '%' + userQuery + '%'));
            predicates.add(criteriaBuilder.like(root.get("UserID"), '%' + userQuery + '%'));


        }

        return (! predicates.isEmpty() ? criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()])) : null);
    }


}

