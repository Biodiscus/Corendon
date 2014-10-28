package nl.itopia.corendon.mvc;

/**
 * © Biodiscus.net 2014, Robin
 *
 * The MVC Engine
 */
public class MVC {
    private Controller currentController;
    private ViewChange viewChange;


    public MVC(ViewChange viewChange) {
        this.viewChange = viewChange;
    }

    public void setController(Controller controller) {
        this.currentController = controller;
        controller.setMVCEngine(this);

        viewChange.changeView(getView());
    }

    public Controller getController() {
        return currentController;
    }

    public View getView() {
        return currentController.getView();
    }

    public interface ViewChange {
        public void changeView(View view);
    }
}
