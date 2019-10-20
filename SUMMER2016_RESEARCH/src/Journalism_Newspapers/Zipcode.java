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
    public  List<Newspaper> zip_Nps = null;
    
    
    //constructor 
    public Zipcode(int zip, double slope_total, double b_total){
        this.np_zipCode = zip;
        this.sum_slope = slope_total;
        this.sum_b = b_total;
        zip_Nps = new ArrayList<Newspaper>();
    }

    //setters 
    public void setZipCode(int zip){
        this.np_zipCode = zip;
    }

    public void setSumSlope(double sumSlope){
        this.sum_slope = sumSlope;
    }

    public void setSumB(double sumB){
        this.sum_b = sumB;
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

    //instance methods
    public void addNewspaper(Newspaper np){
        this.zip_Nps.add(np);
    }

    public List<Newspaper> getNewspapersInZipCode(){
        return this.zip_Nps;
    }

    @Override
    public String toString() {
        System.out.print(this.np_zipCode + "," + this.sum_slope + ","+ this.sum_b);
        return null;
    }

    
}

