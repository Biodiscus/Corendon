package nl.itopia.corendon.mvc;

/**
 * © Biodiscus.net 2014, Robin
 */
public abstract class Controller {
    private MVC mvc;

    public Controller() {

    }

    public final void setMVCEngine(MVC mvc) {
        this.mvc = mvc;
    }

    public final void changeController(Controller controller) {
        mvc.setController(controller);
    }

    public abstract View getView();
}
