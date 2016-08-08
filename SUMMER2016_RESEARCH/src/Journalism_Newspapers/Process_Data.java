/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Journalism_Newspapers;


import java.util.*;

/**
 *
 * @author Yaw P Aidoo
 */
public class Process_Data {

    //returns a list of all newspapers in the csv source file
    public static List<List<String>> get_listof_Nps(String csvpath) {
        List<List<String>> np_List = Read_csv_file.read_csv(csvpath);
        return np_List;
    }

    /* create an array of objects of zipcodes  
     * each object has two fields: zipcode and zipcode population
     * Returns a list of list of objects 
     */
    public static Zip_Population[] get_zip_Census() {
        String path = "D:\\SUMMER RESEARCH-DATA\\sqlite-tools-win32-x86-3130000\\newZip_table.csv";
        List<List<String>> all_zip_Pops = Read_csv_file.read_csv(path);
        Zip_Population[] zip_pops = new Zip_Population[all_zip_Pops.size()];

        //populate array with objects of zipcode and thier population
        for (int i = 0; i < all_zip_Pops.size(); i++) {
            int curr_Zip = Integer.parseInt(all_zip_Pops.get(i).get(0));
            int curr_Pop = Integer.parseInt(all_zip_Pops.get(i).get(1));

            //create object
            Zip_Population curr_zip_Pop = new Zip_Population(curr_Zip, curr_Pop);
            zip_pops[i] = curr_zip_Pop;
        }
        return zip_pops;
    }
    
    public static List<List<String>> get_pop() {
        String path = "D:\\SUMMER RESEARCH-DATA\\sqlite-tools-win32-x86-3130000\\newZip_table.csv";
        List<List<String>> all_zip_Pops = Read_csv_file.read_csv(path);
        return all_zip_Pops;
    }

    //read data from list and store them as newpaper objects 
    public static List<Newspaper> list_to_Obj(List<List<String>> nps) {
        Zip_Population[] pops = get_zip_Census();
        List<Newspaper> newsPapers = new ArrayList<Newspaper>();

        for (List<String> newsPaper : nps) {
            int Id = Integer.parseInt(newsPaper.get(0));
            int zip = Integer.parseInt(newsPaper.get(3));
            int year = Integer.parseInt(newsPaper.get(2));
            int cir = Integer.parseInt(newsPaper.get(5));
            String name = newsPaper.get(1);
            String state = newsPaper.get(4);

            //create object for curr_newspaper
            Newspaper np_Obj = new Newspaper(Id, name, zip, year, state, cir);

            //store np object in the list of np objects 
            newsPapers.add(np_Obj);
        }
        //calculate and set the normalized cirlcation for each np object
        set_normCir(newsPapers);
        //System.out.println(newsPapers.get(1).toString());
        return newsPapers;
    }

    //cal and set the normalized circulation for each newspaper using their zipcode population. 
    public static void set_normCir(List<Newspaper> nplist) {
        //array of objects with two fields: zipcode and its corresponding population
        Zip_Population[] pops = get_zip_Census();

        for (int i = 0; i < nplist.size(); i++) {
            Newspaper curr_np = nplist.get(i);
            int zip1 = nplist.get(i).get_np_zipCode();
            for (int j = 0; j < pops.length; j++) {
                int zip2 = pops[j].get_np_zipCode();
                int pop = pops[j].get_zipcode_population();

                //if zipcodes match then calculate and set their normalized np circulation 
                if (zip1 == zip2 && (is_zip_Present(nplist, zip2) == true) && pop != 0) {

                    float normCir = (float) curr_np.get_np_Cir() / pop;
                    curr_np.set_norm_Cir(normCir);
                    //System.out.println(zip1 + " -----" + zip2);
                    //System.out.println("norm cir: " + ((float) curr_np.get_np_Cir() / pop));
                } else if (zip1 == zip2 && (is_zip_Present(nplist, zip2) == true) && pop == 0) {
                    curr_np.set_norm_Cir(-1);
                }

            }
        }
    }

    //find max and min zipcodes 
    public static int[] get_MinMax(List<Newspaper> np_Objs) {
        //assign first objects  of the list to largest and smallest
        int min = np_Objs.get(0).get_np_zipCode();
        int max = np_Objs.get(0).get_np_zipCode();
        for (int i = 1; i < np_Objs.size(); i++) {
            int curr_Zip = np_Objs.get(i).get_np_zipCode();
            if (curr_Zip > max) {
                max = curr_Zip;
            } else if (curr_Zip < min) {
                min = curr_Zip;
            }
        }
        int[] mm = new int[2];
        mm[0] = min;
        mm[1] = max;
        return mm;
    }

    //return true if current id is within a zipcode data
    public static boolean is_zip_Present(List<Newspaper> newspapers, int zip) {
        boolean present = false;
        for (Newspaper newspaper : newspapers) {
            int curr_Zip = newspaper.get_np_zipCode();
            if (zip == curr_Zip) {
                present = true;
            }
        }
        return present;
    }

    //create a hastable and store all newspaper objects according to zipcodes 
    //zipcode as keys and a reference to the remaining data as values. 
    public static Hashtable<Integer, List> np_by_zipCode(List<Newspaper> newspapers) {
        //get the min and max zipcodes from the list of newspaper objects 
        int[] minmax = get_MinMax(newspapers);
        int min_Zip = minmax[0], max_Zip = minmax[1];

        //create newspaper hashtable 
        Hashtable<Integer, List> np_byZips = new Hashtable<Integer, List>();

        //dummy list to populate values of hashtable before their actual np data
        List<String> default_List = Arrays.asList("Hello");

        //loop through the list of np objects and set the key range of the hashtable from min to max zipcodes 
        for (int i = min_Zip; i < max_Zip; i++) {

            if (is_zip_Present(newspapers, i) == true) {

                np_byZips.put(i, default_List);
            }
            List<Newspaper> azips_nps = new ArrayList();
            for (Newspaper curr_np : newspapers) {
                if (curr_np.get_np_zipCode() == i) {

                    azips_nps.add(curr_np);
                }
            }
            np_byZips.replace(i, default_List, azips_nps);
            

        }
        return np_byZips;
    }

    public static void view_np_hashtable(Hashtable<Integer, List> np_htable) {
        int a_zip;

        Set<Integer> keys = np_htable.keySet();

        //Obtaining iterator over set entries
        Iterator<Integer> itr = keys.iterator();

        //Displaying Key and value pairs
        while (itr.hasNext()) {
            // Getting Key
            a_zip = itr.next();

            /* public V get(Object key): Returns the value to which 
             * the specified key is mapped, or null if this map 
             * contains no mapping for the key.
             */
            //System.out.println("Zip_Code: " + a_zip + " Nps in this zip: " + np_htable.get(a_zip).toString());
        }
    }
}
