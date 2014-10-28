package nl.itopia.corendon.model;

/**
 * © 2014, Biodiscus.net Robin
 */
public class MainModel {
    public static MainModel model = new MainModel();

    private MainModel() {
        // Initialize
    }


    public static MainModel getDefault() {
        return model;
    }
}
