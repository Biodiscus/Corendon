package nl.itopia.corendon.data;

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
}
