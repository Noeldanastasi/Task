package com.dana.app.filter;


import com.dana.app.data.entities.Products;
import com.dana.app.data.repositories.ProductsRepository;
import com.dana.app.data.specifications.DatatableFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class Product {

    private final ProductsRepository productsRepository;

    @Autowired
    public Product(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public Page<Products> getProductsdata(String queryString, Pageable pageable) {

        DatatableFilter datatableFilter = new DatatableFilter(queryString);

        return productsRepository.findAll(datatableFilter, pageable);
    }
}
