package com.dana.app.filter;


import com.dana.app.data.entities.Products;
import com.dana.app.data.entities.UserTable;
import com.dana.app.data.repositories.ProductsRepository;
import com.dana.app.data.repositories.Usertablerepo;
import com.dana.app.data.specifications.DatatableFilter;
import com.dana.app.data.specifications.UserTablefilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class Users {

    private final Usertablerepo usertablerepo;

    @Autowired
    public Users(Usertablerepo usertablerepo) {
        this. usertablerepo = usertablerepo;
    }

    public Page<UserTable> getUserdata(String queryString, Pageable pageable) {

        UserTablefilter userTablefilter = new UserTablefilter(queryString);

        return usertablerepo.findAll(userTablefilter, pageable);
    }
}
