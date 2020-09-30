package com.dana.app.filter;



import com.dana.app.data.entities.BasketTable;
import com.dana.app.data.repositories.Basketrepo;
import com.dana.app.data.specifications.BasketTableFilter;
import com.dana.app.data.specifications.DatatableFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class Basket {

    private final Basketrepo basketrepo;

    @Autowired
    public Basket(Basketrepo basketrepo) {
        this.basketrepo = basketrepo;
    }

    public Page<BasketTable> getbasketdata(String queryString, Pageable pageable) {

       BasketTableFilter basketTableFilter = new BasketTableFilter(queryString);

        return basketrepo.findAll(basketTableFilter, pageable);
    }
}
