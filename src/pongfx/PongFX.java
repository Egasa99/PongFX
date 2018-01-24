/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pongfx;

import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PongFX extends Application {
    
    int ballCenterX = 10; //Posicion bola eje X
    int ballCenterY= 30; //Posicion bola eje Y
    double ballCurrentSpeedX = 3; //velocidad defecto bola eje X
    double ballCurrentSpeedY = 3; //velocidad defecto bola eje Y
     
    final int SCENE_TAM_X = 600; //Resolucion ventana eje X
    final int SCENE_TAM_Y = 400;//Resolucion ventana eje Y
    
    final int STICK_WIDTH = 7;//Anchura del Palo
    final int STICK_HEIGHT =50;//Altura del palo
    int stickPosY = (SCENE_TAM_Y - STICK_HEIGHT) /2; //Posicion del Palo eje Y
    double stickCurrentSpeed = 0; //Velocidad palo por defecto
    int TEXT_SIZE = 15;
    int score;
    int highScore;
    double velocidadBola = 0.1;
    Text textScore;
    Pane root;
    
    @Override
    public void start(Stage primaryStage) {
        //Propiedades del fondo y el nombre
        textScore = new Text("0");
        root = new Pane();
        Scene scene = new Scene(root,SCENE_TAM_X,SCENE_TAM_Y,Color.BLACK);
        primaryStage.setTitle("pongFX");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //Propiedades de la bola
        Circle circleBall = new Circle(ballCenterX, ballCenterY,7, Color.LAWNGREEN);
        root.getChildren().add(circleBall);
        
        //Propiedades del rectangulo
        Rectangle rectStick = new Rectangle(SCENE_TAM_X*0.9, stickPosY, STICK_WIDTH, STICK_HEIGHT);
        rectStick.setFill(Color.LAWNGREEN);
        root.getChildren().add(rectStick);
        
        //Movimiento del Palo arriba/abajo
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch(event.getCode()) {
                case UP:
                    stickCurrentSpeed = -6;
                    break;
                case DOWN:
                    stickCurrentSpeed = 6;
                    break;
            }   
        });
        
        //Si se deja de presionar arriba/abajo
        scene.setOnKeyReleased((KeyEvent event) -> {
            stickCurrentSpeed = 0;
        });
        //Red de Pong
        drawNet(20,6,40);
    
        HBox paneScores = new HBox();
        paneScores.setTranslateY(20);
        paneScores.setMinWidth(SCENE_TAM_X);
        paneScores.setAlignment(Pos.CENTER);
        paneScores.setSpacing(100);
        root.getChildren().add(paneScores);
        //Layout para puntuacion actual
        HBox paneCurrentScore = new HBox();
        paneCurrentScore.setSpacing(10);
        paneScores.getChildren().add(paneCurrentScore);
        //Layout puntuacion maxima
        HBox paneHighScore = new HBox();
        paneHighScore.setSpacing(10);
        paneScores.getChildren().add(paneHighScore);
        //Texto de etiqueta puntuacion
        Text textTitleScore = new Text ("Score:");
        textTitleScore.setFont(Font.font(TEXT_SIZE));
        textTitleScore.setFill(Color.LAWNGREEN);
        //Texto Puntuacion
        Text textScore = new Text ("0");
        textScore.setFont(Font.font(TEXT_SIZE));
        textScore.setFill(Color.LAWNGREEN);
        //Texto Etiqueta Puntuacion Maxima
        Text textTitleHighScore = new Text ("Max.Score");
        textTitleHighScore.setFont (Font.font(TEXT_SIZE));
        textTitleHighScore.setFill (Color.LAWNGREEN);
        //Texto Puntuacion maxima
        Text textHighScore = new Text ("0");
        textHighScore.setFont(Font.font(TEXT_SIZE));
        textHighScore.setFill(Color.LAWNGREEN);
    
        paneCurrentScore.getChildren().add(textTitleScore);
        paneCurrentScore.getChildren().add(textScore);
        paneHighScore.getChildren().add(textTitleHighScore);
        paneHighScore.getChildren().add(textHighScore);
        resetGame();
        AnimationTimer animationBall = new AnimationTimer(){
            @Override
            public void handle(long now) {
                Shape shapeColision = Shape.intersect(circleBall,rectStick);
                boolean colisionVacia = shapeColision.getBoundsInLocal().isEmpty();
                
                if(colisionVacia == false && ballCurrentSpeedX >0) {
                    ballCurrentSpeedX = -3 - velocidadBola;
                    //Incrementar puntuaccion actual
                    score++;
                    textScore.setText(String.valueOf(score));
                    velocidadBola++; 
                }
                // Si hay colision, rebota
                
                circleBall.setCenterX(ballCenterX);
                ballCenterX+= ballCurrentSpeedX;
                //Comprueba si la bola ha tocado el lado derecho
                if(ballCenterX>=SCENE_TAM_X){
                    //Comprobar si hay una puntuacion mas alta
                    velocidadBola = 1;
                    if (score > highScore) {
                        //cambiar puntuacion
                        highScore = score;
                        textHighScore.setText(String.valueOf(highScore));
                    }
                    resetGame();
                    //Reiniciar partida
                    score = 0;
                    textScore.setText(String.valueOf(score));
                    ballCenterX = 10;
                    ballCurrentSpeedY = 3;
                    ballCurrentSpeedX = 3;
                }
                
                if(ballCenterX <=0){
                    ballCurrentSpeedX = 3 + velocidadBola;
                }
                
                circleBall.setCenterY(ballCenterY);
                ballCenterY += ballCurrentSpeedY;
                if(ballCenterY >=SCENE_TAM_Y) {
                    ballCurrentSpeedY = -3;
                }
                if(ballCenterY <=0) {
                    ballCurrentSpeedY =3;
                }
                System.out.println(ballCenterX);
                
                stickPosY += stickCurrentSpeed;
                if (stickPosY < 0) {
                    stickPosY = 0;
                } else {
                    if(stickPosY > SCENE_TAM_Y-STICK_HEIGHT) {
                        stickPosY = SCENE_TAM_Y -STICK_HEIGHT;
                    }
                }
                rectStick.setY(stickPosY);
            };
        };
        animationBall.start();
        
        }
    private void drawNet(int portionHeight, int portionWidth, int portionSpacing) {
        for(int i=0; i<SCENE_TAM_Y; i+=portionSpacing) {
            Line line =new Line(SCENE_TAM_X/2,i,SCENE_TAM_X/2,i+portionHeight);
            line.setStroke(Color.LAWNGREEN);
            line.setStrokeWidth(portionWidth);
            root.getChildren().add(line);
        }
    }
    private void resetGame() {
            score = 0;
            textScore.setText(String.valueOf(score));
            ballCenterX = 10;
            ballCurrentSpeedY = 3;
            //Posicion inicial de la bola en eje Y
            Random random = new Random();
            ballCenterY = random.nextInt(SCENE_TAM_Y);
    }
}