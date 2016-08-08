
package Journalism_Newspapers;


import java.util.*;

/**
 *
 * @Yaw P. Aidoo
 */
public class Estimate_Circulation extends Process_Data {

    public static void main(String[] args) {
        //Get the jvm heap size.
        long heapSize = Runtime.getRuntime().totalMemory();

        //Print the jvm heap size.
        System.out.println("Heap Size = " + heapSize);
        String path = "D:\\SUMMER RESEARCH-DATA\\sqlite-tools-win32-x86-3130000\\new_table_zips.csv";
        List<List<String>> nplist = get_listof_Nps(path);
        List<Newspaper> npobjs = list_to_Obj(nplist);
        //Hashtable<Integer, List> nptbl = np_by_zipCode(npobjs);
        show_zipcode_Objs(npobjs);
    }

    // returns a list of list all nps in a particular zip code in asc order of zipcodes
    public static List<List<Newspaper>> get_zip_nps(List<Newspaper> nplist) {
        List<Newspaper> azip = new ArrayList();
        List<List<Newspaper>> zips = new ArrayList();
        int curr_zip, prev_zip = 0;
        for (int i = 0; i < nplist.size(); i++) {
            curr_zip = nplist.get(i).get_np_zipCode();
            for (int j = 0; j < nplist.size(); j++) {
                if (nplist.get(j).get_np_zipCode() == curr_zip && curr_zip != prev_zip);
                azip.add(nplist.get(j));
            }
            zips.add(azip);
            prev_zip = curr_zip;
        }
        return zips;

    }

    //return a list of np in a particular zipcode
    public static List<Newspaper> get_nps_inZip(List<List<Newspaper>> lst, int idx) {
        List<Newspaper> curr_zip = lst.get(idx);
        return curr_zip;
    }

    //cal linear regression of a newpaper in a zipcode. 
    public static List<Zipcode> cal_Linear_Regr(List<List<Newspaper>> alist) {
        int curr_zip_Id, prev_Id = 0;
        //create a list of zipcode objects to store zipcode data
        List<Zipcode> zipdata = null;

        //loop through the list of newspapers in a particular zip and calculate linear regression for each
        for (int i = 0; i < alist.size(); i++) {
            List<Newspaper> curr_Zip = get_nps_inZip(alist, i); // all nps in the current zip
            double sum_Slopes = 0, sum_Bs = 0, total_X = 0.0, total_Y = 0.0;
            int num_X = 0, num_Y = 0, idx = 0;
            float numm = 0, denom = 0;
            curr_zip_Id = curr_Zip.get(idx).get_np_Id();

            //loop though the curr zip and group by nps
            List<Newspaper> curr_np_ofZip = new ArrayList();
            for (int k = 0; k < curr_Zip.size(); k++) {
                if (curr_zip_Id == curr_Zip.get(k).get_np_Id() && curr_zip_Id != prev_Id) {
                    curr_np_ofZip.add(curr_Zip.get(k));
                    //cal numX and totalX for curr np in zip
                    total_X += curr_Zip.get(k).get_np_cir_Year();
                    num_X++;
                    //cal numY and totalY for curr np in zip
                    total_Y += curr_Zip.get(k).get_norm_Cir();
                    num_Y++;
                }
            }
            idx++;
            prev_Id = curr_zip_Id;

            //cal X_avg and Y_avg for curr np in zipcode
            double avg_X = total_X / num_X, avg_Y = total_Y / num_Y;
            int anyX = curr_np_ofZip.get(0).get_np_cir_Year();
            float anyY = curr_np_ofZip.get(0).get_norm_Cir();

            for (int l = 0; l < curr_np_ofZip.size(); l++) {
                int curr_X = curr_np_ofZip.get(l).get_np_cir_Year();
                float curr_Y = curr_np_ofZip.get(l).get_norm_Cir();
                //cal (xi - xavg)(yi - yavg) for each x and y 
                numm += ((curr_X - avg_X) * (curr_Y - avg_Y));
                //cal (xi - xavg)^2
                denom += ((curr_X - avg_X) * (curr_X - avg_X));
                //cal slope as m
                double m = (numm / denom);
                sum_Slopes += m;
                //cal b
                double b = anyY - (m * anyX);
                sum_Bs += b;
            }
            //create a zipcode object for
            int zipcode = curr_Zip.get(0).get_np_zipCode();
            Zipcode azip = new Zipcode(zipcode, sum_Slopes, sum_Bs);
            zipdata.add(azip);
        }
        zipdata.get(0).toString();
        return zipdata;
    }

    //returns a list of zipcode objects
    public static void show_zipcode_Objs(List<Newspaper> somelist) {
        //get a list of list of zipcode nps objs
        List<List<Newspaper>> ziplines = get_zip_nps(somelist);
        //cal linear regression on each np in zipcode
        List<Zipcode> zip_np = cal_Linear_Regr(ziplines);

    }
}

