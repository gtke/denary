package denary.app.models;

/**
 * Created by gtkesh on 3/30/14.
 */
public interface DBModel {
    boolean login(final User user);
    boolean register(final User user);
}
