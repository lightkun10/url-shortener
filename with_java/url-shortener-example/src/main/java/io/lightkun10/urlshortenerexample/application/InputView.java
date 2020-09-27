package io.lightkun10.urlshortenerexample.application;

import io.lightkun10.urlshortenerexample.UrlShortenerExampleApplication;
import io.lightkun10.urlshortenerexample.UrlShortenerResource;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class InputView {

    public Parent getView() {
        GridPane layout = new GridPane();
        Label urlLabel = new Label("URL");
        TextField wordField = new TextField();

        layout.setAlignment(Pos.CENTER);
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setPadding(new Insets(10, 10, 10, 10));

        Button addButton = new Button("Shorten URL");

        layout.add(urlLabel, 0, 0);
        layout.add(wordField, 0, 1);
        layout.add(addButton, 0, 2);

        return layout;
    }

}