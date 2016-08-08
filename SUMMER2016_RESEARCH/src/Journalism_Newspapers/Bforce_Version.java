/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Journalism_Newspapers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ Yaw Aidoo 7/2/2016 estimates total Newspaper circulated for missing years.
 */
public class Bforce_Version extends Process_Data {

    public static List<List<String>> pop_Arr = get_pop();

    public static void main(String[] args) {
        int arr_len = 114992;
        ArrayList<String>[] zipcode_Array = (ArrayList<String>[]) new ArrayList[arr_len];
        BufferedReader np_data_buffer = null;

        try {
            String np_dataLine;
            String np_csv_path = "D:\\SUMMER RESEARCH-DATA\\sqlite-tools-win32-x86-3130000\\new_table_zips.csv";
            np_data_buffer = new BufferedReader(new FileReader(np_csv_path));
            int idx = 0;
            // Read file in java line by line
            while ((np_dataLine = np_data_buffer.readLine()) != null && idx != zipcode_Array.length) {
                ArrayList<String> arrlist = np_data_csvtoArrayList(np_dataLine);
                zipcode_Array[idx] = arrlist;
                idx++;
            }
            get_Zip_Nps(zipcode_Array);

        } catch (IOException e) {
        } finally {
            try {
                if (np_data_buffer != null) {
                    np_data_buffer.close();
                }
            } catch (IOException readcsvfileException) {
            }
        }

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

    //function to compute estimated data using y=mx+b
    public static void get_Zip_Nps(ArrayList<String>[] anArr) {
        for (int i = 1001; i < 99901; i++) {
            int idx = 0;
            ArrayList<String>[] curr_zip_Data = (ArrayList<String>[]) new ArrayList[50];
            for (ArrayList<String> anArr1 : anArr) {
                int curr_zip = Integer.parseInt(anArr1.get(3));
                if (curr_zip == i) {
                    //store current zip code np in curr_Zip_data array
                    curr_zip_Data[idx] = anArr1;
                    idx++;
                }
            }
            if (curr_zip_Data[0] != null) {
                ArrayList<String>[] pro_Data = seive_Array(curr_zip_Data);
                ArrayList<String>[] upLst = set_norm_Cir(pro_Data);
                cal_Linear_Regr(upLst);

            }
        }
    }

    //return an array with no nulls 
    public static ArrayList<String>[] seive_Array(ArrayList<String>[] someArr) {
        int newSize = 0;
        for (ArrayList<String> someArr1 : someArr) {
            if (someArr1 != null) {
                newSize++;
            }
        }
        ArrayList<String>[] new_Arr = (ArrayList<String>[]) new ArrayList[newSize];
        System.arraycopy(someArr, 0, new_Arr, 0, newSize);
        return new_Arr;
    }

    //find min and max ids of nps
    public static int[] find_minMax(ArrayList<String>[] anArray) {
        //assign first element of an array to largest and smallest
        int min = Integer.parseInt(anArray[0].get(0));
        int max = Integer.parseInt(anArray[0].get(0));
        for (int i = 1; i < anArray.length; i++) {
            if (Integer.parseInt(anArray[i].get(0)) > max) {
                max = Integer.parseInt(anArray[i].get(0));
            } else if (Integer.parseInt(anArray[i].get(0)) < min) {
                min = Integer.parseInt(anArray[i].get(0));

            }
        }
        int[] mm = new int[2];
        mm[0] = min;
        mm[1] = max;
        return mm;
    }

    //return true if current id is within a zipcode data
    public static boolean is_Id_Present(ArrayList<String>[] zipData, int id) {
        boolean present = false;
        for (ArrayList<String> zipData1 : zipData) {
            int curr_Id = Integer.parseInt(zipData1.get(0));
            if (id == curr_Id) {
                present = true;
            }
        }
        return present;
    }

    //set norm cir
    public static ArrayList<String>[] set_norm_Cir(ArrayList<String>[] arrlst) {
        for (int i = 0; i < arrlst.length; i++) {
            int zip1 = Integer.parseInt(arrlst[i].get(3));
            int cir = Integer.parseInt(arrlst[i].get(5));
            for (int j = 0; j < pop_Arr.size(); j++) {
                int zip2 = Integer.parseInt(pop_Arr.get(j).get(0));
                float currPop = Float.parseFloat(pop_Arr.get(j).get(1));
                if (zip1 == zip2) {
                    //call normalized cir
                    float normC = cir / currPop;

                    arrlst[i].set(5, String.valueOf(normC));
                }
            }
        }
        return arrlst;
    }

    public static void cal_Linear_Regr(ArrayList<String>[] a_Zip) {
        double[] sum_ZipLines = new double[3];
        sum_ZipLines[0] = Integer.parseInt(a_Zip[0].get(3));
        String curr_state = a_Zip[0].get(4);

        int[] minMax = find_minMax(a_Zip);
        int minId = minMax[0], maxId = minMax[1];
        boolean id_Present;
        double sum_Slopes = 0, sum_Bs = 0;

        for (int i = minId; i < maxId + 1; i++) {
            double total_X = 0.0;
            float total_Y = 0;
            int num_X = 0, num_Y = 0;
            double numm = 0, denom = 0;
            id_Present = is_Id_Present(a_Zip, i);
            //int index = 0;
            if (id_Present == true) {
                ArrayList<String>[] curr_Np = (ArrayList<String>[]) new ArrayList[10];
                int idx = 0;
                for (ArrayList<String> a_Zip1 : a_Zip) {
                    int curr_Id = Integer.parseInt(a_Zip1.get(0));
                    if (curr_Id == i) {
                        curr_Np[idx] = a_Zip1;
                        idx++;
                    }
                }
                ArrayList<String>[] proc_Data = seive_Array(curr_Np);
                if (proc_Data.length != 1) {
                    for (ArrayList<String> proc_Data1 : proc_Data) {
                        total_Y += Float.parseFloat(String.valueOf(proc_Data1.get(5)));
                        num_Y++;
                        //cal numX and totalX
                        total_X += Integer.parseInt(proc_Data1.get(2));
                        num_X++;
                    }

                    //cal X_avg and Y_avg
                    double avg_X = total_X / num_X;
                    float avg_Y = total_Y / num_Y;
                    int anyX = 0;
                    float anyY = 0;

                    for (ArrayList<String> proc_Data1 : proc_Data) {
                        int curr_X = Integer.parseInt(proc_Data1.get(2));
                        float curr_Y = Float.parseFloat(String.valueOf(proc_Data1.get(5)));
                        //cal (xi - xavg)(yi - yavg) for each x and y 
                        numm += ((curr_X - avg_X) * (curr_Y - avg_Y));

                        //cal (xi - xavg)^2
                        denom += ((curr_X - avg_X) * (curr_X - avg_X));
                        anyX = curr_X;
                        anyY = curr_Y;
                    }
                    if (denom != 0) {
                        //cal slope as m
                        double m = (numm / denom);
                        sum_Slopes += m;
                        //cal b
                        double b = anyY - (m * anyX);
                        sum_Bs += b;

                    }

                }
            }
        }
        sum_ZipLines[1] = sum_Slopes;
        sum_ZipLines[2] = sum_Bs;
        get_Est_Data(sum_ZipLines, curr_state);

    }

    // cal estimated data for a given year using the master line for each zipcode. y = x(m1+m2+...) + (b1+b2+...)
    public static void get_Est_Data(double[] zipLine, String zipState) {
        int zip = (int) zipLine[0];
        double slope = zipLine[1] * 100;
        double b = zipLine[2];

        String state = zipState;
        //System.out.println("Estimated Newspaper Circulation for ZipCode: "+ zip + " with a slope of : " +slope);
        for (int year = 2003; year < 2016; year++) {
            double est_Data = ((slope * year) + b) * 100;
            System.out.format("%d,%d,%.2f,%.0f,%s\n", zip, year, slope, est_Data, state);
        }

    }
}

