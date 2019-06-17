package br.com.srm.repository;

import br.com.srm.model.ProductEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, String> {

    ProductEntity findByIsbn(String isbn);

}
