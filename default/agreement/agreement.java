import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class agreement extends Application {
    //@Override
    public void start(final Stage stage) {
        stage.setTitle("Agreement");
        stage.setWidth(400);
        stage.setHeight(700);
        Scene scene = new Scene(new Group(),1000,600);
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        System.out.println(System.getProperty("user.dir"));
        webEngine.load("file://"+System.getProperty("user.dir")+"/agreement_en.html");
        //webEngine.load("https://www.bing.com");

        scene.setRoot(webView);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[]args) {
        launch(args);
    }
}