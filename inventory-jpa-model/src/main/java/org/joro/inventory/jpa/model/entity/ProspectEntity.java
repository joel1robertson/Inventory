package org.joro.inventory.jpa.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.joro.inventory.jpa.model.projection.ProspectProjection;

@Getter
@Setter
@Entity
@Table(name = "prspct")
public class ProspectEntity extends AbstractEntity<Long> implements ProspectProjection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prspct_key")
    private Long prospectKey;

    @Column(name = "name_txt")
    private String name;

    // foreign joins

//    @OneToMany(mappedBy = "prospect", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ItemProspectEntity> itemProspects;


    @Override
    Long getKey() {
        return prospectKey;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
