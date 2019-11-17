/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryserver;

import utilities.ClientHandler;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Dictionary;

/**
 *
 * @author Captain
 */
public class DictionaryServer extends Application {

    public static ServerSocket ss = null;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.getIcons().add(
                new Image(
                        DictionaryServer.class.getResourceAsStream("app-icon.png")));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Thread server = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    int PORT = 1234;;

                    ss = new ServerSocket(PORT);

                    System.out.println("server running at port" + PORT);
                    Socket s;
                    while (true) {
                        s = ss.accept();

                        System.out.println("New client request received : " + s);

                        // obtain input and output streams
                        ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());

                        ClientHandler mtch = new ClientHandler(s, objectInputStream, objectOutputStream);

                        // Create a new Thread with this object.
                        Thread t = new Thread(mtch);

                        // start the thread.
                        t.start();

                    }
                } catch (IOException ex) {

                }
                try {
                    ss.close();
                } catch (Exception e) {
                }
            }
        });
        server.start();
        launch(args);

    }

    @Override
    public void stop() {
        try {
            ss.close();
        } catch (Exception e) {
        }
    }

}
