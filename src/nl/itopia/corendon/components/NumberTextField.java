package nl.itopia.corendon.components;


import javafx.scene.control.TextField;

/**
 * This class will only allow numbers to be inserted into the textfield
 * From: http://stackoverflow.com/a/18959399
 */

public class NumberTextField extends TextField {
    @Override
    public void replaceText(int start, int end, String text) {
        if(validate(text)) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String replacement) {
        if (validate(replacement)) {
            super.replaceSelection(replacement);
        }
    }

    private boolean validate(String text) {
        return ("".equals(text) || text.matches("[0-9]"));
    }
}