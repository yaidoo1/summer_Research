/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Journalism_Newspapers;

/**
 *
 * @author Yaw Aidoo
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

public class Read_csv_file{
    
    public static  List<List<String>> read_csv(String the_csv_path) {
        List<List<String>> npdata = new ArrayList<List<String>>();
        BufferedReader np_data_buffer = null;

        try {
            String np_dataLine;
            String csv_path = the_csv_path;
            np_data_buffer = new BufferedReader(new FileReader(csv_path));
            int idx = 0;
            
            // Read file in java line by line 
            while ((np_dataLine = np_data_buffer.readLine()) != null) {
                ArrayList<String> arrlist = np_data_csvtoArrayList(np_dataLine);
                npdata.add(arrlist);
                idx++;
                    
            }
          
            

        } catch (IOException e) {
        } finally {
            try {
                if (np_data_buffer != null) {
                    np_data_buffer.close();
                }
            } catch (IOException readcsvfileException) {
            }
        }
        return npdata;
        

    }

    ///Convert CSV to ArrayList using Split Operation
    public static ArrayList<String> np_data_csvtoArrayList(String np_data_csv) {
        ArrayList<String> np_data_array = new ArrayList<String>();

        if (np_data_csv != null) {
            String[] splitData = np_data_csv.split("\\s*,\\s*");
            for (String splitData1 : splitData) {
                if (!(splitData1 == null) || !(splitData1.length() == 0)) {
                    np_data_array.add(splitData1.trim());

                }

            }
        }

        return np_data_array;

    }

}

