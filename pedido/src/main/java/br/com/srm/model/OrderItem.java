package br.com.srm.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document
public class OrderItem implements Serializable {

    private Integer amount;
    private String product;

}
