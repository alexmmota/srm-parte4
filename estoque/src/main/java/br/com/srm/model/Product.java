package br.com.srm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter @Setter
@ToString @EqualsAndHashCode
@Entity
@Table(name = "srm_product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "bar_code")
    private String barCode;
    private String department;
    private Integer amount;

    @JsonIgnore
    @Transient
    public boolean isNew() {
        return id == null;
    }

}
