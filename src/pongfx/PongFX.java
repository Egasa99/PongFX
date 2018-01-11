/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pongfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class PongFX extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 600, 400,Color.BLACK);
        primaryStage.setTitle("pongFX");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Circle circleBall = new Circle(10,30,7);
        root.getChildren().add(circleBall);
        circleBall.setFill(Color.LAWNGREEN);
    }
}
