package br.com.srm.service;

import br.com.srm.exception.BusinessServiceException;
import br.com.srm.model.DepartmentEntity;
import br.com.srm.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public DepartmentEntity save(DepartmentEntity department) {
        log.info("m=save, department={}", department);
        if (department.isNew() && departmentRepository.findByName(department.getName()) != null)
            throw new BusinessServiceException("Ja existe um departamento com esse nome");
        return departmentRepository.save(department);
    }

    public List<DepartmentEntity> findByName(String name) {
        log.info("m=findByName, name={}", name);
        return departmentRepository.findByNameContainingIgnoreCase(name);
    }

    public void delete(Long id) {
        log.info("m=delete, id={}", id);
        departmentRepository.deleteById(id);
    }

    public DepartmentEntity findById(Long id) {
        log.info("m=findById, id={}", id);
        Optional<DepartmentEntity> optional = departmentRepository.findById(id);
        if (optional.isPresent())
            return optional.get();
        return null;
    }

}
