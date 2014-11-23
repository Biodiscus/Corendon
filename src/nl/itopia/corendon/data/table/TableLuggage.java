
package nl.itopia.corendon.data.table;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Igor's_Boven
 */
public class TableLuggage {
    private final SimpleStringProperty dimensions;
    private final SimpleStringProperty notes;
    
    public TableLuggage(String dimensions, String notes){
        this.dimensions = new SimpleStringProperty(dimensions);
        this.notes = new SimpleStringProperty(notes);
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
    
}
