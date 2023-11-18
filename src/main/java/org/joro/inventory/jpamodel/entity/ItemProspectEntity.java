package org.joro.inventory.jpamodel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.joro.inventory.jpamodel.dto.ItemProspectDto;

@Getter
@Setter
@Entity
@Table(name = "item_prspct")
public class ItemProspectEntity extends AbstractEntity<Long> implements ItemProspectDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_prspct_key")
    private Long itemProspectKey;

    @ManyToOne
    @JoinColumn(name = "item_key")
    private ItemEntity item;

    @ManyToOne
    @JoinColumn(name = "prspct_key")
    private ProspectEntity prospect;

    @Column(name = "rsrvd_ind")
    private boolean reserved;


    @Override
    Long getKey() {
        return itemProspectKey;
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
