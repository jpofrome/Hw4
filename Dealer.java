/**
 * Created by Ares on 10/10/2016.
 */
import com.sun.xml.internal.fastinfoset.algorithm.BooleanEncodingAlgorithm;
import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.nio.Buffer;
import java.util.Scanner;
import java.net.URL;
import java.net.*;
import java.io.FileOutputStream;
import java.io.*;
import java.io.FileNotFoundException;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class Dealer implements java.io.Serializable{

    private Inventory inventory = new Inventory();

    //constructor
    public  Dealer() {}
    public Dealer(Inventory inventory){this.inventory = inventory;}


    public Inventory populateInventory() {
        try {
            URL urlName = new URL("https://raw.githubusercontent.com/kyungsooim/TestData/master/data/SeptemberInventory.txt");
            BufferedReader input = new BufferedReader(new InputStreamReader(urlName.openStream()));
            String line;
            while((line=input.readLine()) != null){
                String[] piece = line.split(",");
                try {
                    if (piece.length == 3){
                        inventory.addVehicle(new Vehicle(piece[0], Integer.parseInt(piece[1]), false, Double.parseDouble(piece[2]), 0));
                    } else {
                        inventory.addVehicle(new Vehicle(piece[0], Integer.parseInt(piece[1]),
                                Boolean.parseBoolean(piece[3].toLowerCase()), Double.parseDouble(piece[2]), 0));
                    }
                }
                catch (Exception e) {}
            }
            input.close();
        }
        catch(Exception e){}
        return (inventory);
    }

    public static String serializer(Dealer obj){
        try {
            File file = new File("dat/object.txt");
            FileOutputStream fileOut = new FileOutputStream("dat/object.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(obj);
            out.close();
            fileOut.close();
            return (file.getPath());
        } catch (Exception e){
            e.printStackTrace();
            return("No path found");
        }
    }

    public static Dealer deserializer (String path){

        Dealer dealerDeserializer = null;
        try{
            FileInputStream inputStream = new FileInputStream(path);
            ObjectInputStream objStream = new ObjectInputStream(inputStream);
            dealerDeserializer = (Dealer) objStream.readObject();
            objStream.close();
            inputStream.close();
        }
        catch (Exception e){}
        return dealerDeserializer;

    }
}