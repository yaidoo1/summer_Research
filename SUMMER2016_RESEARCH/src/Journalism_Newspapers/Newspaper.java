/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Journalism_Newspapers;

/**
 *
 * @Yaw Aidoo
 */
public class Newspaper {

    private final int np_Id;
    private final String np_Name;
    private final int np_cir_zipCode;
    private final int np_cir_Year;
    private final String np_cir_Sate;
    private final int np_Cir;
    private float norm_Cir;

    //constructor 
    public Newspaper(int Id, String name, int zip, int year, String state, int cir) {
        this.np_Id = Id;
        this.np_Name = name;
        this.np_cir_zipCode = zip;
        this.np_cir_Year = year;
        this.np_cir_Sate = state;
        this.np_Cir = cir;

    }

    //setter 

    public void set_norm_Cir(float nCir) {
        this.norm_Cir = nCir;
    }

    //Getters 
    public int get_np_Id() {
        return this.np_Id;
    }

    public String get_np_Name() {
        return this.np_Name;
    }

    public int get_np_cir_Year() {
        return this.np_cir_Year;
    }

    public int get_np_zipCode() {
        return this.np_cir_zipCode;
    }

    public String get_np_cir_State() {
        return this.np_cir_Sate;
    }

    public int get_np_Cir() {
        return this.np_Cir;
    }
    public float get_norm_Cir(){
        return this.norm_Cir;
    }

    @Override
    public String toString() {
        System.out.print("[" + this.np_Id + " " + this.np_Name + " "
                + this.np_cir_Year + " " + this.np_cir_zipCode + " " + this.np_cir_Sate + " " + this.np_Cir + " "+ this.norm_Cir + "]");
        return null;
    }
}
