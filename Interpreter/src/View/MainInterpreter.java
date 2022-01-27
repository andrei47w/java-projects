package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainInterpreter extends Application {

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage mainStage) throws Exception {

            // Read file fxml and draw interface.
            FXMLLoader fxmlLoader = new FXMLLoader(MainInterpreter.class.getResource("/MainLayout.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 550, 250, Color.DARKBLUE);
//            Scene scene = new Scene(fxmlLoader.load(), 1292, 632, Color.DARKBLUE);
            mainStage.setTitle("Toy Language - Please select a program");
            mainStage.setScene(scene);
            mainStage.show();
        }

}
