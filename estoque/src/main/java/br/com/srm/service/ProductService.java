package br.com.srm.service;

import br.com.srm.exception.BusinessServiceException;
import br.com.srm.model.ProductEntity;
import br.com.srm.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RefreshScope
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Value("${srm.message.fail}")
    private String messageFail;

    @Transactional
    public ProductEntity save(ProductEntity product) {
        log.info("m=save, product={}", product);
        if (productRepository.findByIsbn(product.getIsbn()) != null)
            throw new BusinessServiceException("Já existe um produto com esse codigo ISBN");
        return productRepository.save(product);
    }

    public void delete(String isbn) {
        log.info("m=delete, isbn={}", isbn);
        productRepository.deleteById(isbn);
    }

    public ProductEntity addAmount(String isbn, Integer amount) {
        log.info("m=addAmount, isbn={}, amount={}", isbn, amount);
        ProductEntity product = findByISBN(isbn);
        product.setAmount(product.getAmount() + amount);
        return productRepository.save(product);
    }

    public ProductEntity subtractAmount(String isbn, Integer amount) {
        log.info("m=subtractAmount, isbn={}, amount={}", isbn, amount);
        ProductEntity product = findByISBN(isbn);
        if (product.getAmount() < amount)
            throw new BusinessServiceException(messageFail);
        product.setAmount(product.getAmount() - amount);
        return productRepository.save(product);
    }

    public ProductEntity findByISBN(String isbn) {
        Optional<ProductEntity> product = productRepository.findById(isbn);
        if (product.isPresent())
            return product.get();
        return null;
    }

}
