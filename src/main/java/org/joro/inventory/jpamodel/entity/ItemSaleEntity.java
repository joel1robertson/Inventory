package org.joro.inventory.jpamodel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.joro.inventory.jpamodel.dto.ItemSaleDto;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "item_sale")
public class ItemSaleEntity extends AbstractEntity<Long> implements ItemSaleDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_sale_key")
    private Long itemSaleKey;

    @ManyToOne
    @JoinColumn(name = "item_key")
    private ItemEntity item;

    @Column(name = "sale_date")
    private LocalDate saleDate;

    @Column(name = "sale_prce_amt")
    private Integer salePrice;

    @Column(name = "byr_name_txt")
    private String buyerName;


    @Override
    Long getKey() {
        return itemSaleKey;
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
