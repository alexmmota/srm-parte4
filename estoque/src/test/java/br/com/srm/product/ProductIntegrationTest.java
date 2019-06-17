package br.com.srm.product;

import br.com.srm.Application;
import br.com.srm.JsonUtil;
import br.com.srm.dto.request.ProductRequest;
import br.com.srm.model.DepartmentEntity;
import br.com.srm.repository.DepartmentRepository;
import br.com.srm.repository.ProductRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ProductIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @After
    public void resetDb() {
        productRepository.deleteAll();
        departmentRepository.deleteAll();
    }

    @Test
    public void whenSave_thenCreateProduct() throws Exception {
        DepartmentEntity departmentEntity = createDeparment();
        ProductRequest productRequest = buildProductRequest();

        mvc.perform(post("/v1/departments/1/products").contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(productRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn", is("112314.123123")))
                .andExpect(jsonPath("$.department", is(departmentEntity.getName())));

    }

    private ProductRequest buildProductRequest(){
        return ProductRequest.builder()
                .name("A Bota do Bode")
                .isbn("112314.123123")
                .amount(10)
                .cost(19.99)
                .build();
    }

    private DepartmentEntity buildDepartmentEntity(){
        return DepartmentEntity.builder()
                .id(1l)
                .name("Literatura Nacional")
                .description("Descrição do departamento Literatura Nacional")
                .build();
    }

    private DepartmentEntity createDeparment() {
        return departmentRepository.save(buildDepartment());
    }

    private DepartmentEntity buildDepartment(){
        return DepartmentEntity.builder()
                .name("Literatura Infantil")
                .description("Descrição do departamento Literatura Infantil")
                .build();
    }


}
