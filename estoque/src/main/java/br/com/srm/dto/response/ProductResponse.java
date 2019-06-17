package br.com.srm.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductResponse {

    private String isbn;
    private String name;
    private Integer amount;
    private Double cost;
    private String department;
    private String changeDate;

}
