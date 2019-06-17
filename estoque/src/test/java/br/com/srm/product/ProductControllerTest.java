package br.com.srm.product;

import br.com.srm.JsonUtil;
import br.com.srm.controller.ProductController;
import br.com.srm.dto.request.ProductRequest;
import br.com.srm.dto.response.ProductResponse;
import br.com.srm.mapper.ProductMapper;
import br.com.srm.model.DepartmentEntity;
import br.com.srm.model.ProductEntity;
import br.com.srm.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;

    @Before
    public void setUp() {
        ProductEntity productEntity = buildProductEntity();
        ProductRequest productRequest = buildProductRequest();
        ProductResponse productResponse = buildProductResponse();
        Mockito.when(productMapper.productRequestToProductEntity(1l, productRequest))
                .thenReturn(productEntity);
        Mockito.when(productMapper.productEntityToProductResponse(productEntity))
                .thenReturn(productResponse);
    }

    @Test
    public void whenSave_thenCreateProduct() throws Exception {
        ProductRequest productRequest = buildProductRequest();
        ProductEntity productEntity = buildProductEntity();
        given(productService.save(Mockito.any())).willReturn(productEntity);

        mvc.perform(post("/v1/departments/1/products").contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(productRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn", is("112314.123123")));

    }

    private ProductRequest buildProductRequest(){
        return ProductRequest.builder()
                .name("A Bota do Bode")
                .isbn("112314.123123")
                .amount(10)
                .cost(19.99)
                .build();
    }

    private ProductEntity buildProductEntity(){
        return ProductEntity.builder()
                .name("A Bota do Bode")
                .isbn("112314.123123")
                .amount(10)
                .cost(19.99)
                .department(buildDepartmentEntity())
                .build();
    }

    private DepartmentEntity buildDepartmentEntity(){
        return DepartmentEntity.builder()
                .id(1l)
                .name("Literatura Nacional")
                .description("Descrição do departamento Literatura Nacional")
                .build();
    }

    private ProductResponse buildProductResponse(){
        return ProductResponse.builder()
                .name("A Bota do Bode")
                .isbn("112314.123123")
                .amount(10)
                .cost(19.99)
                .changeDate("12/10/2019 19:10:10")
                .build();
    }

}
