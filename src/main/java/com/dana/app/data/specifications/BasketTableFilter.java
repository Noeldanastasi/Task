package com.dana.app.data.specifications;

import com.dana.app.data.entities.BasketTable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;

public class BasketTableFilter implements org.springframework.data.jpa.domain.Specification<BasketTable>{


        String userQuery;
    public  BasketTableFilter(String queryString) {
        this.userQuery = queryString;
    }


    @Override
    public Predicate toPredicate(Root<BasketTable> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (userQuery != null && userQuery != "") {
            predicates.add(criteriaBuilder.like(root.get("ID"), '%' + userQuery + '%'));
            predicates.add(criteriaBuilder.like(root.get("ProductID"), '%' + userQuery + '%'));
            predicates.add(criteriaBuilder.like(root.get("UserID"), '%' + userQuery + '%'));
        }

        return (! predicates.isEmpty() ? criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()])) : null);
    }
}



