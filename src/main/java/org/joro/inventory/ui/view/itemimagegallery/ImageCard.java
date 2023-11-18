package org.joro.inventory.ui.view.itemimagegallery;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import org.joro.inventory.model.ItemImage;
import org.joro.inventory.ui.util.ImageUtil;

public class ImageCard extends ListItem {

    public ImageCard(ItemImage itemImage) {
        var caption = itemImage.getCaption();
        var imageData = itemImage.getImageData();
        var contentType = itemImage.getContentType();

        var imageSrc = ImageUtil.imageSrcStreamResource(imageData, contentType, caption)
                .setCacheTime(1);

        var image = new Image();
        image.setMaxWidth("100%");
        image.setMaxHeight("100%");
        image.setSrc(imageSrc);
        image.setAlt(caption);

        var imageDiv = new Div();
        imageDiv.addClassNames(Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        imageDiv.setHeight("256px");
        imageDiv.add(image);

        var block = new Span();
        block.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        block.setText(caption);

        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);
        add(imageDiv);
        add(block);
    }
}
