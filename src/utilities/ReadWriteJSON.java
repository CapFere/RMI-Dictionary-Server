/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Dictionary;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author Captain
 */
public class ReadWriteJSON {

    public Dictionary writeToJSON(String filename, Dictionary dictionary) throws IOException {
        File f = new File(filename);
        InputStream is = new FileInputStream(f);
        JSONTokener tokener = new JSONTokener(is);
        JSONArray dictionaries = new JSONArray();
        try {
            dictionaries = new JSONArray(tokener);
        } catch (Exception e) {

        }
        for (int i = 0; i < dictionaries.length(); i++) {
            JSONObject word = (JSONObject) dictionaries.getJSONObject(i);
            if (word.getString("keyword").equalsIgnoreCase(dictionary.getKeyword())) {
                return null;
            }
        }
        JSONObject obj = new JSONObject();

        obj.put("keyword", dictionary.getKeyword());

        JSONArray meanings = new JSONArray();
        for (String meaning : dictionary.getMeanings()) {
            meanings.put(meaning);
        }
        obj.put("meanings", meanings);
        dictionaries.put(obj);
        try {
            FileWriter file = new FileWriter(filename);
            file.write(dictionaries.toString());
            file.flush();
        } catch (Exception e) {

        }
        return dictionary;
    }

    public Dictionary readFromJSON(String filename, Dictionary dictionary) throws IOException {
        File file = new File(filename);
        InputStream is = new FileInputStream(file);
        JSONTokener tokener = new JSONTokener(is);
        JSONArray dictionaries = new JSONArray();
        try {
            dictionaries = new JSONArray(tokener);
        } catch (Exception e) {

        }
        for (int i = 0; i < dictionaries.length(); i++) {
            JSONObject word = (JSONObject) dictionaries.getJSONObject(i);
            if (word.getString("keyword").equalsIgnoreCase(dictionary.getKeyword())) {
                ArrayList<String> meanings = new ArrayList<>();
                JSONArray arrayobject = (JSONArray) word.getJSONArray("meanings");
                for (int j = 0; j < arrayobject.length(); j++) {
                    meanings.add(arrayobject.getString(j));
                }
                return new Dictionary(word.getString("keyword"), meanings);
            }
        }
        return null;
    }

    public Dictionary removeFromJSON(String filename, Dictionary dictionary) throws IOException{
            Dictionary readDictionary = readFromJSON(filename, dictionary);
            if (readDictionary != null) {
                File f = new File(filename);
                InputStream is = new FileInputStream(f);
                JSONTokener tokener = new JSONTokener(is);
                JSONArray dictionaries = new JSONArray();
                try {
                    dictionaries = new JSONArray(tokener);
                } catch (Exception e) {
                    
                }
                JSONArray newDictionaries = new JSONArray();
                for (int i = 0; i < dictionaries.length(); i++) {
                    JSONObject word = (JSONObject) dictionaries.getJSONObject(i);
                    if (!(word.getString("keyword").equalsIgnoreCase(dictionary.getKeyword()))) {
                        newDictionaries.put(word);
                    }
                }
                try {
                    FileWriter file = new FileWriter(filename);
                    file.write(newDictionaries.toString());
                    file.flush();
                    return dictionary;
                } catch (Exception e) {
                    
                }
            }
            return null;
    }
}
