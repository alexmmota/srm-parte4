package br.com.srm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequest implements Serializable {

    @NotBlank(message = "Nome deve ser informado")
    private String name;
    private String description;

}
