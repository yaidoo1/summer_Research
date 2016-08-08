/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Journalism_Newspapers;
/**
 *
 * @Yaw P. Aidoo
 */
class Zipcode {
    private final  int np_zipCode;
    private final  double  sum_slope;
    private final double sum_b;
    
    
    //constructor 
    public Zipcode(int zip, double slope_total, double b_total){
        this.np_zipCode = zip;
        this.sum_slope = slope_total;
        this.sum_b = b_total;
    }

    //getters 
    public double get_sum_Slope(){
        return this.sum_slope;
    }
    public double get_sum_b(){
        return this.sum_b;
    }
    public int get_np_zipCode(){
        return this.np_zipCode;
    }
     @Override
    public String toString() {
        System.out.print(this.np_zipCode + "," + this.sum_slope + ","+ this.sum_b);
        return null;
    }

    
}

