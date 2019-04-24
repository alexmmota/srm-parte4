package br.com.srm.service;

import br.com.srm.exception.BusinessServiceException;
import br.com.srm.model.Product;
import br.com.srm.repository.ProductRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public Product save(Product product) {
        logger.info("m=save, product={}", product);
        if (product.isNew() && productRepository.findByBarCode(product.getBarCode()) != null)
            throw new BusinessServiceException("Já existe um produto com esse codigo de barra");
        return productRepository.save(product);
    }

    @HystrixCommand(fallbackMethod = "buildFallbackProductList")
    public Product findByBarCode(String barCode) {
        logger.info("m=findByBarCode, barCode={}", barCode);
        sleep();
        Product product = productRepository.findByBarCode(barCode);
        if (product == null)
            throw new BusinessServiceException("Produto não encontrado para esse código de barra ");
        return product;
    }

    private Product buildFallbackProductList(String barCode) {
        Product product = new Product();
        product.setAmount(0);
        product.setBarCode("00000-00000");
        product.setId(0l);
        product.setName("Ops! Não foi possível buscar seu produto agora!");
        return product;
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(String barCode) {
        logger.info("m=delete, barCode={}", barCode);
        productRepository.delete(findByBarCode(barCode));
    }

    public Product addAmount(String barCode, Integer amount) {
        logger.info("m=addAmount, barCode={}, amount={}", barCode, amount);
        Product product = findByBarCode(barCode);
        product.setAmount(product.getAmount() + amount);
        return productRepository.save(product);
    }

    public Product subtractAmount(String barCode, Integer amount) {
        logger.info("m=subtractAmount, barCode={}, amount={}", barCode, amount);
        Product product = findByBarCode(barCode);
        if (product.getAmount() < amount)
            throw new ServiceException("Quantidade não está disponível no estoque");
        product.setAmount(product.getAmount() - amount);
        return productRepository.save(product);
    }

    private Product findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent())
            throw new BusinessServiceException("Produto não encontrado para esse id");
        return product.get();
    }

}
