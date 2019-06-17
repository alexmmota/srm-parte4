package br.com.srm.repository;

import br.com.srm.model.DepartmentEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends PagingAndSortingRepository<DepartmentEntity, Long> {

    DepartmentEntity findByName(String name);

    List<DepartmentEntity> findByNameContainingIgnoreCase(String name);

}
