package seabattle;


public interface View {

    void addViewListener(ViewListener vl);
    void removeViewListener(ViewListener vl);
    void update(ViewEvent event);

}
