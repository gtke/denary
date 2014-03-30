package denary.app.models;

/**
 * Created by gtkesh on 3/30/14.
 */
public interface DBModel {
    boolean login(final User user);
    void register(final User user);
}
