package com.group04.studentaide;

/*
Written By: Yufeng Luo

LinkedHashMaps holding data of Course Names and study times
Using storage on the device, we can save the information that user has provided and retreive
again upon reopening of app.

This ensures that data is not lost due to killing of application.

 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ReadWriteStorage {

    public void SaveHashMapToStorage(String filename, LinkedHashMap<String, ArrayList<Double>> linkedHashMap) throws FileNotFoundException{
        try{
            File saveFile = new File(filename);
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(linkedHashMap);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public LinkedHashMap<String, ArrayList<Double>> LoadHashMapFromStorage(String filename){
        LinkedHashMap<String, ArrayList<Double>> linkedHashMap = new LinkedHashMap<String, ArrayList<Double>>();

        try{

            File readFile = new File(filename);
            FileInputStream fileInputStream = new FileInputStream(readFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            linkedHashMap = (LinkedHashMap<String, ArrayList<Double>>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return linkedHashMap;
    }

}
