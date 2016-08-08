/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Journalism_Newspapers;

/**
 *
 * @ Yaw P Aidoo
 */
public class Zip_Population {
    private final  int np_zipCode;
    private final  int zipcode_population;
    
    
    //constructor 
    public Zip_Population(int zip, int pop){
        this.np_zipCode = zip;
        this.zipcode_population = pop;
    }

    public int get_np_zipCode(){
        return this.np_zipCode;
    }
    
    public int get_zipcode_population(){
        return this.zipcode_population;
    }
}
