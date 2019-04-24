package br.com.srm.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Document
public class Order implements Serializable {

    public enum Status {
        CREATED, FINISHED, CANCELED;
    }

    @Id
    private String id;

    private Status status;
    private Date createDate;
    private List<OrderItem> itens;
    private Date finishDate;
    private Client client;

}
