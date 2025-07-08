package org.joro.inventory.ui.model.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemImage implements HasItem {
    private Long itemImageKey;
    private Item item;
    private String caption;
    private String contentType;
    private byte[] imageData;
    private String sequenceIndex;
}
