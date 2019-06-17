package br.com.srm.product;

import br.com.srm.model.DepartmentEntity;
import br.com.srm.model.ProductEntity;
import br.com.srm.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindByISBN_thenReturnProduct() {
        DepartmentEntity department = buildDepartment();
        department = entityManager.persist(department);
        ProductEntity product = buildProduct(department);
        entityManager.persist(product);
        entityManager.flush();

        ProductEntity result = productRepository.findByIsbn(product.getIsbn());
        assertThat(result.getName()).isEqualTo(product.getName());
    }

    @Test
    public void whenInvalidISBN_thenReturnNull() {
        ProductEntity result = productRepository.findByIsbn("ISBN_NOT_FOUND");
        assertThat(result).isNull();
    }


    private ProductEntity buildProduct(DepartmentEntity department){
        return ProductEntity.builder()
                .name("A Bota do Bode")
                .isbn("112314.123123")
                .amount(10)
                .cost(19.99)
                .department(department)
                .build();
    }

    private DepartmentEntity buildDepartment(){
        return DepartmentEntity.builder()
                .name("Literatura Infantil")
                .build();
    }

}
