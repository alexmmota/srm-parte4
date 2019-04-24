package br.com.srm.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document
public class Client implements Serializable {

    @Id
    private String cpf;
    private String name;

}
