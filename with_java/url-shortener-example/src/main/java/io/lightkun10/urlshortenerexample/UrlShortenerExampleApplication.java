package io.lightkun10.urlshortenerexample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrlShortenerExampleApplication extends Application {

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
		Pane helloPane = new Pane(new Label("Hello JavaFx"));

		Button btn = new Button("CLICK");
		btn.setOnAction((event) -> {
			SpringApplication.run(UrlShortenerExampleApplication.class);
		});
		helloPane.getChildren().add(btn);


		window.setScene(new Scene(helloPane));
		window.show();
	}
}
