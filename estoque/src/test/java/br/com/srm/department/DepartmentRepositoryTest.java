package br.com.srm.department;

import br.com.srm.model.DepartmentEntity;
import br.com.srm.repository.DepartmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DepartmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void whenFindByName_thenReturnDepartment() {
        DepartmentEntity department = buildDepartment1();
        entityManager.persist(department);
        entityManager.flush();

        DepartmentEntity result = departmentRepository.findByName(department.getName());
        assertThat(result.getName()).isEqualTo(department.getName());
    }

    @Test
    public void whenInvalidName_thenReturnNull() {
        DepartmentEntity result = departmentRepository.findByName("NAME_NOT_FOUND");
        assertThat(result).isNull();
    }

    @Test
    public void whenFindByContainingName_thenReturnDepartment() {
        DepartmentEntity department1 = buildDepartment1();
        DepartmentEntity department2 = buildDepartment2();
        entityManager.persist(department1);
        entityManager.persist(department2);
        entityManager.flush();

        List<DepartmentEntity> result = departmentRepository.findByNameContainingIgnoreCase("lIteratura");
        assertThat(result.size()).isEqualTo(2);
    }


    private DepartmentEntity buildDepartment1(){
        return DepartmentEntity.builder()
                .name("Literatura Infantil")
                .build();
    }

    private DepartmentEntity buildDepartment2(){
        return DepartmentEntity.builder()
                .name("Literatura Nacional")
                .build();
    }

}
