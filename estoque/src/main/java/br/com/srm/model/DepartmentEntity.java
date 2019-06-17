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
@Table(name = "srm_department")
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "change_date")
    private Date changeDate;

    @Transient
    public boolean isNew() {
        return this.id == null;
    }

}
