package br.com.srm.controller;

import br.com.srm.dto.request.DepartmentRequest;
import br.com.srm.dto.response.DepartmentResponse;
import br.com.srm.mapper.DepartmentMapper;
import br.com.srm.model.DepartmentEntity;
import br.com.srm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/v1/departments")
public class DepartmentController {


    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentMapper departmentMapper;

    @RequestMapping(method = RequestMethod.POST)
    public DepartmentResponse save(@Valid @RequestBody DepartmentRequest departmentRequest) {
        DepartmentEntity department = departmentMapper.departmentRequestToDepartmentEntity(departmentRequest);
        return departmentMapper.departmentEntityToDepartmentResponse(departmentService.save(department));
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<DepartmentResponse> findByName(@RequestParam("name") String name) {
        List<DepartmentEntity> departments = departmentService.findByName(name);
        if (departments != null)
            return departments.stream().map(d -> departmentMapper.departmentEntityToDepartmentResponse(d)).collect(Collectors.toList());
        return Collections.EMPTY_LIST;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        departmentService.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DepartmentResponse findById(@PathVariable("id") Long id) {
        return departmentMapper.departmentEntityToDepartmentResponse(departmentService.findById(id));
    }

}
