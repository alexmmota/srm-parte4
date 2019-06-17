package br.com.srm.mapper;

import br.com.srm.dto.request.ProductRequest;
import br.com.srm.exception.BusinessServiceException;
import br.com.srm.model.DepartmentEntity;
import br.com.srm.model.ProductEntity;
import br.com.srm.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

public abstract class ProductMapperDecorator implements ProductMapper {

    @Autowired
    @Qualifier("delegate")
    private ProductMapper delegate;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public ProductEntity productRequestToProductEntity(Long departmentId, ProductRequest productRequest) {
        ProductEntity product = delegate.productRequestToProductEntity(departmentId, productRequest);
        product.setDepartment(getDepartment(departmentId));
        return product;
    }

    private DepartmentEntity getDepartment(Long departmentId) {
        Optional<DepartmentEntity> optional = departmentRepository.findById(departmentId);
        if (optional.isPresent())
            return optional.get();
        throw new BusinessServiceException("Departamento invalido");
    }

}
