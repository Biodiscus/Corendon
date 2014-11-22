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
    public List<Pictures> pictures;
    public String label;
    public String notes;
    public String weight;
    public Brand brand;
    public int foundDate;
    public int returnDate;
    public int createDate;

    public Luggage(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
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
}
