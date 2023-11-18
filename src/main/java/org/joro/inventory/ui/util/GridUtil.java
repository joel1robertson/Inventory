package org.joro.inventory.ui.util;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.function.ValueProvider;

import java.util.List;
import java.util.Optional;

public final class GridUtil extends LibraryClass {

    private GridUtil() {
        // utility class - do not instantiate
    }

//    public static <T> ValueProvider<T, String> captionOf(ValueProvider<T, HasCaption> hasCaptionValueProvider) {
//        return item -> Optional.ofNullable(hasCaptionValueProvider.apply(item))
//                .map(HasCaption::getCaption)
//                .orElse("");
//    }

    public static <T, V, R> ValueProvider<T, R> propertyOf(ValueProvider<T, V> valueProvider, ValueProvider<V, R> propertyValueProvider) {
        return item -> Optional.ofNullable(valueProvider.apply(item))
                .map(propertyValueProvider)
                .orElse(null);
    }

    public static <T, V extends List<R>, R> ValueProvider<T, R> indexOf(ValueProvider<T, V> valueProvider, int index) {
        return item -> Optional.ofNullable(valueProvider.apply(item))
                .filter(a -> index < a.size())
                .map(a -> a.get(index))
                .orElse(null);
    }

    public static <T, V> TextRenderer<T> textRenderPropertyOf(ValueProvider<T, V> valueProvider, ValueProvider<V, String> propertyValueProvider) {
        return new TextRenderer<>(item -> Optional.ofNullable(valueProvider.apply(item))
                .map(propertyValueProvider)
                .orElse(""));
    }

    public static <T> LitRenderer<T> urlRenderer(ValueProvider<T, String> hrefValueProvider, ValueProvider<T, String> textValueProvider) {
        return LitRenderer.<T>of("""
                <a href=${item.href} target="_blank" rel="noopener noreferrer">${item.text}</a>
                """)
                .withProperty("href", hrefValueProvider)
                .withProperty("text", textValueProvider);
    }

    public static <T> LitRenderer<T> urlRenderer(ValueProvider<T, String> hrefProvider) {
        return urlRenderer(hrefProvider, hrefProvider);
    }

    public static <T> LitRenderer<T> imageRenderer(ValueProvider<T, byte[]> dataValueProvider, ValueProvider<T, String> contentTypeValueProvider) {
        return LitRenderer.<T>of("""
                <img src=${item.imageSrc} style="width: 100%; height: 100%;"
                 ?hidden=${!item.imageSrc} />
                """)
                .withProperty("imageSrc", item -> {
                    var imageData = dataValueProvider.apply(item);
                    var contentType = contentTypeValueProvider.apply(item);
                    return ImageUtil.imageSrcData(imageData, contentType);
                });
    }

    public static <T> LitRenderer<T> imageRenderer(ValueProvider<T, byte[]> dataValueProvider) {
        return imageRenderer(dataValueProvider, ct -> "image");
    }
}
