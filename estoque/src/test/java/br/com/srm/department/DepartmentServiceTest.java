package br.com.srm.department;

import br.com.srm.exception.BusinessServiceException;
import br.com.srm.model.DepartmentEntity;
import br.com.srm.repository.DepartmentRepository;
import br.com.srm.service.DepartmentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;

    @TestConfiguration
    static class DepartmentServiceImplTestContextConfiguration {
        @Bean
        public DepartmentService departmentService() {
            return new DepartmentService();
        }
    }

    @Before
    public void setUp() {
        DepartmentEntity department1 = buildDepartment1();
        DepartmentEntity department2 = buildDepartment2();
        Mockito.when(departmentRepository.findByNameContainingIgnoreCase("literatura"))
                .thenReturn(Arrays.asList(department1, department2));
        Mockito.when(departmentRepository.findByName(department1.getName()))
                .thenReturn(department1);
    }

    @Test
    public void whenFindByName_thenDepartmentSholdBeFound() {
        String name = "literatura";
        List<DepartmentEntity> result = departmentService.findByName(name);
        assertThat(result.size()).isEqualTo(2);
    }

    @Test(expected = BusinessServiceException.class)
    public void whenSaveWithSameName_thenFail() {
        DepartmentEntity department = buildDepartment1();
        departmentService.save(department);
    }


    private DepartmentEntity buildDepartment1(){
        return DepartmentEntity.builder()
                .name("Literatura Infantil")
                .description("Descrição do departamento Literatura Infantil")
                .build();
    }

    private DepartmentEntity buildDepartment2(){
        return DepartmentEntity.builder()
                .name("Literatura Nacional")
                .description("Descrição do departamento Literatura Nacional")
                .build();
    }

}
