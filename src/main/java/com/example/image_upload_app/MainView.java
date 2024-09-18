package com.example.image_upload_app;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Route("")
public class MainView extends VerticalLayout {
    private final List<UploadedImage> uploadedImages = new ArrayList<>();
    private final Grid<UploadedImage> memoryGrid = new Grid<>();

    public MainView() {

        getElement().getThemeList().add("dark");
        H1 h1 = new H1("Picture Gallery");
        h1.getStyle().set("text-align", "center");
        HorizontalLayout uploadForm = new HorizontalLayout();
        TextArea descripInput = new TextArea("Description");

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.addSucceededListener(event -> {
            String description = descripInput.getValue();
            try {
                byte[] imageBytes = IOUtils.toByteArray(buffer.getInputStream());
                StreamResource resource = new StreamResource(event.getFileName(), () -> new ByteArrayInputStream(imageBytes));

                // Add uploaded image to the list
                uploadedImages.add(new UploadedImage(resource, description));

                memoryGrid.setItems(uploadedImages);
                descripInput.clear();
                upload.clearFileList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        uploadForm.add(descripInput, upload);

        memoryGrid.addColumn(new ComponentRenderer<>(uploadedImage -> {
            Image img = new Image(uploadedImage.getResource(), "Uploaded image");
            img.setMaxWidth("100%");
            return img;
        })).setHeader("Image").setAutoWidth(true);
        memoryGrid.addColumn(UploadedImage::getDescription).setHeader("Description").setAutoWidth(true);

        memoryGrid.addComponentColumn(uploadedImage -> {
            Button deleteButton = new Button("DELETE");
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
            deleteButton.addClickListener(clickEvent -> {
                uploadedImages.remove(uploadedImage);
                memoryGrid.setItems(uploadedImages);
                Notification.show("Memory deleted!");
            });
            return deleteButton;
        }).setHeader("Actions");

        memoryGrid.setHeight("600px");
        memoryGrid.getStyle().set("overflow-y", "auto");

        add(h1, uploadForm, memoryGrid);
        setSpacing(true);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("max-width", "800px").set("margin", "0 auto");

    }

    private static class UploadedImage {
        private final StreamResource resource;
        private final String description;

        public UploadedImage(StreamResource resource, String description) {
            this.resource = resource;
            this.description = description;
        }

        public StreamResource getResource() {
            return resource;
        }

        public String getDescription() {
            return description;
        }
    }
}

