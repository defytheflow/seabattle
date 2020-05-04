package seabattle;


public interface Model {

    void addModelListener(ModelListener ml);
    void removeModelListener(ModelListener ml);
    void update(ModelEvent event);

}
