package org.joro.inventory.ui.util;

import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Optional;

public final class ImageUtil extends LibraryClass {

    private ImageUtil() {
        // utility class - do not instantiate
    }

    public static String imageSrcData(byte[] imageData, String contentType) {
        if (imageData == null) {
            return "";
        }

        return "data:%s;base64,%s"
                .formatted(Optional.ofNullable(contentType).orElse("image"),
                        Base64.getEncoder().encodeToString(imageData));
    }

    public static StreamResource imageSrcStreamResource(byte[] imageData, String contentType, String fileName) {
        if (imageData == null) {
            return null;
        }

        if (contentType == null) {
            contentType = "image";
        }
        else if ((fileName == null) || fileName.isBlank()) {
            var split = contentType.split("/");
            fileName = (split.length == 2) ? "image.%s".formatted(split[1]) : "";
        }

        var streamResource = new StreamResource(fileName, () -> new ByteArrayInputStream(imageData));
        streamResource.setContentType(contentType);

        return streamResource;
    }

    public static StreamResource imageSrcStreamResource(byte[] imageData, String contentType) {
        return imageSrcStreamResource(imageData, contentType, null);
    }
}
