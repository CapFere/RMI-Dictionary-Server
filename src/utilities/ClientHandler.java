/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import interfaces.DictionaryServerInterface;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import models.Dictionary;

/**
 *
 * @author Captain
 */
public class ClientHandler extends UnicastRemoteObject implements DictionaryServerInterface {

    String FILEPATH;
    ReadWriteJSON readWriteJSON;

    // constructor
    public ClientHandler(String FILEPATH) throws RemoteException {
        super();
        readWriteJSON = new ReadWriteJSON();
        this.FILEPATH = FILEPATH;

    }

    @Override
    public Dictionary findWord(Dictionary dictionary) throws RemoteException {
        try {
            Dictionary result = readWriteJSON.readFromJSON(this.FILEPATH, dictionary);
            if (result != null) {
                dictionary = result;
                dictionary.setWasSucceful(true);
            } else {
                dictionary.setWasSucceful(false);
            }

        } catch (IOException ex) {

        }
        return dictionary;
    }

    @Override
    public Dictionary addWord(Dictionary dictionary) throws RemoteException {
        try {
            Dictionary result = readWriteJSON.writeToJSON(this.FILEPATH, dictionary);
            if (result != null) {
                dictionary = result;
                dictionary.setWasSucceful(true);
            } else {
                dictionary.setWasSucceful(false);
            }

        } catch (IOException ex) {

        }
        return dictionary;
    }

    @Override
    public Dictionary deleteWord(Dictionary dictionary) throws RemoteException {
        try {
            Dictionary result = readWriteJSON.removeFromJSON(this.FILEPATH, dictionary);
            if (result != null) {
                dictionary = result;
                dictionary.setWasSucceful(true);
            } else {
                dictionary.setWasSucceful(false);
            }

        } catch (IOException ex) {

        }
        return dictionary;
    }
}
