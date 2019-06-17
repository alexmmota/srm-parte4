package br.com.srm.controller;

import br.com.srm.dto.request.ProductRequest;
import br.com.srm.dto.response.ProductResponse;
import br.com.srm.mapper.ProductMapper;
import br.com.srm.model.ProductEntity;
import br.com.srm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/departments/{id}/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @RequestMapping(method = RequestMethod.POST)
    public ProductResponse save(@PathVariable("id") Long departmentId,
                                @Valid @RequestBody ProductRequest productRequest) {
        ProductEntity product = productMapper.productRequestToProductEntity(departmentId, productRequest);
        return productMapper.productEntityToProductResponse(productService.save(product));
    }

    @RequestMapping(value = "/{isbn}", method = RequestMethod.GET)
    public ProductResponse findByISBN(@PathVariable("id") Long departmentId,
                                      @PathVariable("isbn") String isbn) {
        ProductEntity product = productService.findByISBN(isbn);
        return productMapper.productEntityToProductResponse(product);
    }

    @RequestMapping(value = "/{isbn}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long departmentId,
                       @PathVariable("isbn") String isbn) {
        productService.delete(isbn);
    }

    @RequestMapping(value = "/{isbn}/addAmount", method = RequestMethod.POST)
    public ProductResponse addAmount(@PathVariable("id") Long departmentId,
                                     @PathVariable("isbn") String isbn,
                                     @RequestParam("amount") Integer amount) {
        ProductEntity product = productService.addAmount(isbn, amount);
        return productMapper.productEntityToProductResponse(product);
    }

    @RequestMapping(value = "/{isbn}/subtractAmount", method = RequestMethod.POST)
    public ProductResponse subtractAmount(@PathVariable("id") Long departmentId,
                                          @PathVariable("isbn") String isbn,
                                          @RequestParam("amount") Integer amount) {
        ProductEntity product = productService.subtractAmount(isbn, amount);
        return productMapper.productEntityToProductResponse(product);
    }

}
