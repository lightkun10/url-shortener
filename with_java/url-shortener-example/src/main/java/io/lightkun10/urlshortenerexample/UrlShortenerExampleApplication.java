package io.lightkun10.urlshortenerexample;

import io.lightkun10.urlshortenerexample.application.InputView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

@RequestMapping("/rest/url")
@RestController
@SpringBootApplication
public class UrlShortenerExampleApplication extends Application {

    @Autowired
    StringRedisTemplate redisTemplate;

    public static void main(String[] args) {

        //SpringApplication.run(UrlShortenerExampleApplication.class, args);
        Application.launch();
    }

    @Override
    public void init() {
        SpringApplication.run(getClass()).getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void start(Stage window) throws Exception {
        GridPane inputView = new GridPane();
        Label urlLabel = new Label("URL");
        TextField urlField = new TextField();
        Hyperlink shortenUrlField = new Hyperlink("Shortened URL");

        inputView.setAlignment(Pos.CENTER);
        inputView.setVgap(10);
        inputView.setHgap(10);
        inputView.setPadding(new Insets(10, 10, 10, 10));

        Button addButton = new Button("Shorten URL");

        inputView.add(urlLabel, 0, 0);
        inputView.add(urlField, 0, 1);
        inputView.add(addButton, 0, 2);
        inputView.add(shortenUrlField, 0, 3);

        BorderPane layout = new BorderPane();

        addButton.setOnAction((event) -> {

            String url = urlField.getText();

            //Do post
            /* Validating passed URL */
            UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
            if (urlValidator.isValid(url)) {
                String id = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8)
                        .toString();
                System.out.println("URL Id generated: " + id);

                // If id or url is error
                if (url == null) {
                    throw new RuntimeException("There's not shorter URL for: " + id);
                }
                System.out.println(url); // Full, raw url
                shortenUrlField.setText(id);
                shortenUrlField.setOnAction((event2) -> {
                    try {
                        System.out.println("CLICKED!");
                        Desktop.getDesktop().browse(new URI(url));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                });

            }
            /* If URL is not valid, throw an exception. */
            //throw new RuntimeException("URL Invalid: " + url);

        });


        layout.setCenter(inputView);

        Scene view = new Scene(layout, 400, 300);


        window.setScene(view);
        window.show();
    }
}
