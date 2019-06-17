package br.com.srm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest implements Serializable {

    @NotBlank(message = "ISBN deve ser informado")
    private String isbn;

    @NotBlank(message = "Nome deve ser informado")
    private String name;

    @NotNull(message = "Quantidade deve ser informada")
    private Integer amount;

    @NotNull(message = "Preço de custo deve ser informado")
    private Double cost;

}
