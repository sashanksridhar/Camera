/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.opencv.core.Core;

/**
 *
 * @author Sashank
 */
public class JavaFXApplication4 extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // load the FXML resource
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
                        // store the root element so that the controllers can use it
                        BorderPane root = (BorderPane) loader.load();
                        // create and style a scene
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(JavaFXApplication4.class.getResource("application.css").toExternalForm());
                        // create the stage with the given title and the previously created scene
                        primaryStage.setTitle("JavaFX meets OpenCV");
                        primaryStage.setScene(scene);
                        // show the GUI
                        primaryStage.show();
                        // set a reference of this class for its controller
                        FXMLDocumentController controller = loader.getController();
                        controller.setRootElement(root);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }
    
}
