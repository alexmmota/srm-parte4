package br.com.srm.department;

import br.com.srm.JsonUtil;
import br.com.srm.controller.DepartmentController;
import br.com.srm.dto.request.DepartmentRequest;
import br.com.srm.dto.response.DepartmentResponse;
import br.com.srm.mapper.DepartmentMapper;
import br.com.srm.model.DepartmentEntity;
import br.com.srm.service.DepartmentService;
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

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DepartmentService departmentService;

    @MockBean
    private DepartmentMapper departmentMapper;

    @Before
    public void setUp() {
        DepartmentEntity departmentEntity = buildDepartmentEntity();
        DepartmentRequest departmentRequest = buildDepartmentRequest();
        DepartmentResponse departmentResponse = buildDepartmentResponse();
        Mockito.when(departmentMapper.departmentRequestToDepartmentEntity(departmentRequest))
                .thenReturn(departmentEntity);
        Mockito.when(departmentMapper.departmentEntityToDepartmentResponse(departmentEntity))
                .thenReturn(departmentResponse);
    }

    @Test
    public void whenSave_thenCreateDepartment() throws Exception {
        DepartmentRequest departmentRequest = buildDepartmentRequest();
        DepartmentEntity departmentEntity = buildDepartmentEntity();
        given(departmentService.save(Mockito.any())).willReturn(departmentEntity);

        mvc.perform(post("/v1/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(departmentRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Literatura Nacional")));

    }

    @Test
    public void whenFindByName_thenReturnList() throws Exception {
        DepartmentEntity departmentEntity = buildDepartmentEntity();
        DepartmentEntity departmentEntity2 = buildDepartmentEntity2();
        given(departmentService.findByName(Mockito.any()))
                .willReturn(Arrays.asList(departmentEntity, departmentEntity2));

        mvc.perform(get("/v1/departments/search?name=literatura")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Literatura Nacional")));

    }

    private DepartmentRequest buildDepartmentRequest(){
        return DepartmentRequest.builder()
                .name("Literatura Nacional")
                .description("Descrição do departamento Literatura Nacional")
                .build();
    }

    private DepartmentEntity buildDepartmentEntity(){
        return DepartmentEntity.builder()
                .id(1l)
                .name("Literatura Nacional")
                .description("Descrição do departamento Literatura Nacional")
                .build();
    }

    private DepartmentEntity buildDepartmentEntity2(){
        return DepartmentEntity.builder()
                .id(2l)
                .name("Literatura Internacional")
                .description("Descrição do departamento Literatura Internacional")
                .build();
    }

    private DepartmentResponse buildDepartmentResponse(){
        return DepartmentResponse.builder()
                .name("Literatura Nacional")
                .description("Descrição do departamento Literatura Nacional")
                .changeDate("12/10/2019 19:10:10")
                .build();
    }

}
