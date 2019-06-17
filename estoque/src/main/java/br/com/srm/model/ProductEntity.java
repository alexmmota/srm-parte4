package br.com.srm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@Data
@Entity
@Table(name = "srm_product")
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity implements Serializable {

    @Id
    @Column(name = "isbn")
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    private String name;
    private Integer amount;
    private Double cost;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "change_date")
    private Date changeDate;

}
