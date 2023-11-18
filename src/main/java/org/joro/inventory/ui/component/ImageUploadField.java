package org.joro.inventory.ui.component;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.shared.HasClearButton;
import com.vaadin.flow.component.upload.*;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.theme.lumo.LumoIcon;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.joro.inventory.ui.util.ImageUtil;

import java.io.IOException;

public class ImageUploadField extends AbstractCompositeField<Upload, ImageUploadField, ImageUploadField.UploadImage>
        implements HasSize, HasLabel, HasHelper, HasClearButton, HasValidation {
    public static final int MAX_FILE_SIZE = 10 * (1 << 20);  // 10 * 2^20 = 10 MB

    private final Image image;
    private final FlexLayout imageLayout;
    private final Button clearButton;

    /**
     * Creates a new field. The provided default value is used by
     * {@link #getEmptyValue()} and is also used as the initial value of this
     * instance.
     *
     * @param defaultValue the default value
     */
    public ImageUploadField(UploadImage defaultValue) {
        super(defaultValue);

        image = new Image();
        image.setSizeFull();

        clearButton = new Button(LumoIcon.CROSS.create());
        clearButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ERROR);
        clearButton.addClassNames(LumoUtility.Position.ABSOLUTE);
        clearButton.getStyle().set("right", "0");
        clearButton.addClickListener(this::onClearClick);
        clearButton.setVisible(false);

        imageLayout = new FlexLayout();
        imageLayout.addClassNames(LumoUtility.Position.RELATIVE);
        imageLayout.setSizeFull();
        imageLayout.add(image);
        imageLayout.add(clearButton);
        imageLayout.setVisible(false);

        var upload = getContent();
        upload.setUploadButton(new Span("Upload Image..."));
        upload.setDropLabel(new Span("or drop it here"));
        upload.setAcceptedFileTypes("image/*");
        upload.setMaxFiles(1);
        upload.setMaxFileSize(MAX_FILE_SIZE);
        upload.setReceiver(new MemoryBuffer());
        upload.addFileRejectedListener(this::onFileRejected);
        upload.addFailedListener(this::onFailed);
        upload.addSucceededListener(this::onSucceeded);
        upload.addAllFinishedListener(this::onAllFinished);

        upload.getElement().appendChild(imageLayout.getElement());
    }

    /**
     * Creates a new image field with an initial value and {@link #getEmptyValue()}
     * of {@code null}.
     */
    public ImageUploadField() {
        this(null);
    }

    private void onClearClick(ClickEvent<Button> buttonClickEvent) {
        setValue(null);
    }

    private void onFileRejected(FileRejectedEvent event) {
        var notification = new Notification();
        notification.setDuration(3000);
        notification.setPosition(Notification.Position.MIDDLE);
        notification.setText("Upload failed: " + event.getErrorMessage());
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.open();
    }

    private void onFailed(FailedEvent event) {
        var notification = new Notification();
        notification.setDuration(3000);
        notification.setPosition(Notification.Position.MIDDLE);
        notification.setText("Upload failed: " + event.getReason().getLocalizedMessage());
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.open();
    }

    private void onSucceeded(SucceededEvent event) {
        if (event.getUpload().getReceiver() instanceof MemoryBuffer memoryBuffer) {
            var inputStream = memoryBuffer.getInputStream();
            try {
                var bytes = inputStream.readAllBytes();
                setValue(UploadImage.create(event.getMIMEType(), bytes));
            }
            catch (IOException e) {
                Notification.show("Unable to read image");
            }
        }
    }

    private void onAllFinished(AllFinishedEvent event) {
        var source = event.getSource();
        source.clearFileList();
        source.setReceiver(new MemoryBuffer());
    }

//    private void onDeleteClick(ClickEvent<Button> event) {
//        fileContainerLayout.remove(droppedFile);
//    }

    @Override
    protected void setPresentationValue(UploadImage newPresentationValue) {
        var imageSrc = ImageUtil.imageSrcData(newPresentationValue.getData(), newPresentationValue.getContentType());
        image.setSrc(imageSrc);
        imageLayout.setVisible(imageSrc != null);
    }

    @Override
    public void setErrorMessage(String errorMessage) {

    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public void setInvalid(boolean invalid) {

    }

    @Override
    public boolean isInvalid() {
        return false;
    }

    @Override
    public boolean isClearButtonVisible() {
        return clearButton.isVisible();
    }

    @Override
    public void setClearButtonVisible(boolean clearButtonVisible) {
        clearButton.setVisible(clearButtonVisible);
    }

    public static class UploadImage {
        private final String contentType;
        private final byte[] data;

        public static UploadImage create(String contentType, byte[] data) {
            return new UploadImage(contentType, data);
        }

        private UploadImage(String contentType, byte[] data) {
            this.contentType = contentType;
            this.data = data;
        }

        public String getContentType() {
            return contentType;
        }

        public byte[] getData() {
            return data;
        }
    }
}
