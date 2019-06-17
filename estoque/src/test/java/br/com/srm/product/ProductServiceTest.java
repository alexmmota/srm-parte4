package br.com.srm.product;

import br.com.srm.exception.BusinessServiceException;
import br.com.srm.model.DepartmentEntity;
import br.com.srm.model.ProductEntity;
import br.com.srm.repository.ProductRepository;
import br.com.srm.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @TestConfiguration
    static class ProductServiceImplTestContextConfiguration {
        @Bean
        public ProductService departmentService() {
            return new ProductService();
        }
    }

    @Before
    public void setUp() {
        ProductEntity product = buildProduct();
        Mockito.when(productRepository.findByIsbn(product.getIsbn())).thenReturn(product);
        Mockito.when(productRepository.findById(product.getIsbn())).thenReturn(Optional.of(product));
    }

    @Test(expected = BusinessServiceException.class)
    public void whenSaveWithSameISBN_thenFail() {
        ProductEntity product = buildProduct();
        productService.save(product);
    }

    @Test
    public void whenFindByISBN_thenReturnProduct() {
        ProductEntity product = buildProduct();
        ProductEntity result = productService.findByISBN(product.getIsbn());
        assertThat(result.getName()).isEqualTo(product.getName());
    }

    @Test
    public void whenFindByWrongISBN_thenReturnNull() {
        ProductEntity result = productService.findByISBN("ISBN_NOT_FOUND");
        assertThat(result).isNull();
    }

    @Test(expected = BusinessServiceException.class)
    public void whenSubtractAmountNegative_thenFail() {
        productService.subtractAmount("112314.123123", 11);
    }

    private ProductEntity buildProduct(){
        return ProductEntity.builder()
                .name("A Bota do Bode")
                .isbn("112314.123123")
                .amount(10)
                .cost(19.99)
                .department(buildDepartment())
                .build();
    }


    private DepartmentEntity buildDepartment(){
        return DepartmentEntity.builder()
                .name("Literatura Infantil")
                .description("Descrição do departamento Literatura Infantil")
                .build();
    }

}
