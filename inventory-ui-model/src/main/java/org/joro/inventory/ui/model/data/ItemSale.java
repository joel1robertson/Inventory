package org.joro.inventory.ui.model.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ItemSale implements HasItem {
    private Long itemSaleKey;
    private Item item;
    private LocalDate saleDate;
    private Integer salePrice;
    private String buyerName;

    @Override
    public String toString() {
        return "%tF - %d - %s".formatted(saleDate, salePrice, buyerName);
    }
}
