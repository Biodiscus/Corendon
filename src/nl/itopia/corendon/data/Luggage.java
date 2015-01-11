package nl.itopia.corendon.data;

import nl.itopia.corendon.utils.Log;
import java.util.List;

/**
 *
 * @author igor
 */
public class Luggage {
    
    private int id;
    public Color color;
    public Status status;
    public Employee employee;
    public Customer customer;
    public Airport airport;
    public String dimensions;
    public List<Picture> pictures;
    public String label;
    public String notes;
    public String weight;
    public Brand brand;
    public long foundDate;
    public long returnDate;
    public long createDate;

    public Luggage(int id) {
        this.id = id;
    }

    public Luggage() {
        id = -1;
    }

    public int getID() {
        return id;
    }

    public void setID(int val) {
        id = val;
    }


    /**
     * Array pos 1: width
     * Array pos 2: height
     * Array pos 3: depth
     * Array pos 4: in what format is it
     *
     * @return  the dimensions given in an array
     */
    public String[] getDimensions() {
        // Split the dimmension on a 'x' or on a whitespace ' '
        return dimensions.split("[x/ ]");
    }
    /**
     * Set the dimension for luggage
     *
     * Array pos 1: width
     * Array pos 2: height
     * Array pos 3: depth
     * Array pos 4: in what format is it
     */
    public void setDimensions(String[] dimensions) {
        if(dimensions.length != 4) {
            Log.display("ERROR", "Error while setting the dimension. The length is not 4!");
            return;
        }

        this.dimensions = dimensions[0] + "x"+dimensions[1]+"x"+dimensions[2]+" "+dimensions[3];
    }
}
