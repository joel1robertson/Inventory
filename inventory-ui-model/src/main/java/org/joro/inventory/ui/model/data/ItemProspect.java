package org.joro.inventory.ui.model.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemProspect implements HasItem {
    private Long itemProspectKey;
    private Item item;
    private Prospect prospect;
    private boolean reserved;
}
