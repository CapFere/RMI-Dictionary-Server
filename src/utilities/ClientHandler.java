/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import models.Dictionary;

/**
 *
 * @author Captain
 */
public class ClientHandler implements Runnable {

    final ObjectInputStream objectInputStream;
    final ObjectOutputStream objectOutputStream;
    Socket s;
    String FILEPATH;
    ReadWriteJSON readWriteJSON;

    // constructor
    public ClientHandler(Socket s,
            ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, String FILEPATH) {
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        this.s = s;
        readWriteJSON = new ReadWriteJSON();
        this.FILEPATH = FILEPATH;

    }

    @Override
    public void run() {

        try {
            // receive the string

            Dictionary received = (Dictionary) objectInputStream.readObject();
            synchronized (received) {
                if (received.getRequest().equalsIgnoreCase("find")) {
                    Dictionary dictionary = readWriteJSON.readFromJSON(this.FILEPATH, received);
                    if (dictionary != null) {
                        received = dictionary;
                        received.setWasSucceful(true);
                    } else {
                        received.setWasSucceful(false);
                    }

                } else if (received.getRequest().equalsIgnoreCase("add")) {

                    Dictionary dictionary = readWriteJSON.writeToJSON(this.FILEPATH, received);
                    if (dictionary != null) {
                        received = dictionary;
                        received.setWasSucceful(true);
                    } else {
                        received.setWasSucceful(false);
                    }

                } else if (received.getRequest().equalsIgnoreCase("remove")) {
                    Dictionary dictionary = readWriteJSON.removeFromJSON(this.FILEPATH, received);
                    if (dictionary != null) {
                        received = dictionary;
                        received.setWasSucceful(true);
                    } else {
                        received.setWasSucceful(false);
                    }

                }
            }

            objectOutputStream.writeObject(received);
        } catch (Exception e) {

        }

    }
}
