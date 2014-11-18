//This class is a blueprint for baggage

package nl.itopia.corendon.model;

public class Baggage {
    
    String brandName; 
    int baggageId;
    int weight, length, width, heigth;
    String color;
    int status; //0 missing,  1 found en 2 resvolved
    
    Baggage(String brandName, int weight, int length, int width, int heigth, String color, int status, int baggageId) {
        this.brandName = brandName;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.heigth = heigth;
        this.color = color;
        this.status = status;
        this.baggageId = baggageId;
    }
    
    int getBaggageId () {
        return baggageId;
    }
    
    void setBaggageId (int id) {
        this.baggageId = id;
    }
    
    String getBrandName() {
        return brandName;
    }
    
    void setBrandName(String brand) {
        this.brandName = brand;
    }
   
    //check whether baggage x is equal to baggage y
    @Override
    public boolean equals(Object obj) {
        boolean value;
        if (obj instanceof Baggage) {
            value = this.baggageId == ((Baggage)obj).baggageId;
        } else {
            value = super.equals(obj);
        }
        return value;
    }    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.baggageId;
        return hash;
    }
}
