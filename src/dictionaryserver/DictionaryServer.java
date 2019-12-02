/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryserver;

import java.io.File;
import utilities.ClientHandler;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
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

    public static Registry registary = null;
    public static int PORT=1212;
    public static String FILEPATH;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = fXMLLoader.load();
        FXMLDocumentController controller = (FXMLDocumentController) fXMLLoader.getController();
        controller.setParameters(PORT);
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
        if (args.length < 2) {
            System.out.println("ERROR: Too few arguments(PORT AND FILE LOCATION needed)");
            Platform.exit();
            System.exit(0);
        }
        File f = new File(args[1]);
        System.out.println(f);
        if (!f.exists() || f.isDirectory()) {
            System.out.println("ERROR: File does not exist or its a directory");
            Platform.exit();
            System.exit(0);
        }
        if (!f.toString().endsWith(".json")) {
            System.out.println("ERROR: Only JSON file supported");
            Platform.exit();
            System.exit(0);
        }
        try {
            PORT = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.out.println("ERROR: PORT can only be integer");
            Platform.exit();
            System.exit(0);
        }
        FILEPATH = args[1];

        Thread server = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    ClientHandler clientHandler = new ClientHandler(FILEPATH);
                    registary = LocateRegistry.createRegistry(PORT);
                    registary.rebind("dict", clientHandler);
                    System.out.println("Server Started");

                    
                } catch (Exception ex) {
                    System.out.println("ERROR: PORT Aready Taken or Invalid PORT");
                    try {
                        registary.unbind("dictionary");
                    } catch (Exception ex1) {
                        
                    }
                    Platform.exit();
                    System.exit(0);
                }
                try {
                    registary.unbind("dictionary");
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
           registary.unbind("dictionary");
           registary = null;
        } catch (Exception e) {
        }
    }

}
