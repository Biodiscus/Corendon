package nl.itopia.corendon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import nl.itopia.corendon.mvc.Controller;

/**
 *
 * @author wieskueter.com
 */
public class InfoController extends Controller {
    
    @FXML private Label headerTitle;
    @FXML private AnchorPane contentHolder;
    @FXML private Button exitButton;
    private String url;
    private String title;
    
    public InfoController(String title, String url) {
        
        this.title = title;
        this.url = url;
        
        // Set view
        registerFXML("gui/help_function.fxml");
        
        headerTitle.setText(this.title);

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        
        contentHolder.setTopAnchor(browser, 0.0);
        contentHolder.setBottomAnchor(browser, 0.0);
        contentHolder.setLeftAnchor(browser, 0.0);
        contentHolder.setRightAnchor(browser, 0.0);
        
        contentHolder.getChildren().add(browser);
        webEngine.load(this.url);
        
        exitButton.setOnAction(this::cancelHandler);
    }
    
    public void cancelHandler(ActionEvent e){
        removeController(this);
    }
}
