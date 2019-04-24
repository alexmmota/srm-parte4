package br.com.srm.client.dto;

import lombok.*;
import java.io.Serializable;

@Data
public class Product implements Serializable {

    private Long id;
    private String name;
    private String barCode;
    private String department;
    private Integer amount;

}
