package org.joro.inventory.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemLink implements HasItem {
    private Long itemLinkKey;
    private Item item;
    private LinkCategory linkCategory;
    private String caption;
    private String href;
    private String sequenceIndex;
}
