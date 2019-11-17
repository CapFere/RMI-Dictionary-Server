/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryserver;

import java.net.URL;

import java.util.ResourceBundle;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Captain
 */
public class FXMLDocumentController implements Initializable {
    public int port;
    @FXML
    private Label portField;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public  void setParameters(int port){
        this.port = port;
        portField.setText("Database Server PORT "+ this.port);
    }
    

}
