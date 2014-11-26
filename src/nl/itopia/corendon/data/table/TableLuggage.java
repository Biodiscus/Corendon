
package nl.itopia.corendon.data.table;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Igor's_Boven
 */
public class TableLuggage {
    private final SimpleStringProperty dimensions;
    private final SimpleStringProperty notes;
    private final SimpleStringProperty airport;
    private final SimpleStringProperty brand;
    private final SimpleStringProperty color;
    private final SimpleStringProperty status;
    
    public TableLuggage(String dimensions, String notes, String airport, String brand, String color, String status){
        this.dimensions = new SimpleStringProperty(dimensions);
        this.notes = new SimpleStringProperty(notes);
        this.airport = new SimpleStringProperty(airport);
        this.brand = new SimpleStringProperty(brand);
        this.color = new SimpleStringProperty(color);
        this.status = new SimpleStringProperty(status);
    }
    
    
    public String getDimensions() {
        return dimensions.get();
    }

    public void setDimensions(String dimensions) {
        this.dimensions.set(dimensions);
    }
    
    public String getNotes() {
        return notes.get();
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }
    
    public String getAirport() {
        return airport.get();
    }

    public void setAirport(String airport) {
        this.airport.set(airport);
    }    

    public String getBrand() {
        return brand.get();
    }

    public void setBrand(String brand) {
        this.brand.set(brand);
    }
    public String getColor() {
        return color.get();
    }

    public void setColor(String color) {
        this.color.set(color);
    }
    public String getStatus() {
        return status.get();
    }
    public void setStatus(String status) {
        this.status.set(status);
    }    
        
}
