package denary.app.models;

import java.util.Date;

/**
 * Created by gtkesh on 4/5/14.
 */
public interface RModel {
    String generateIncomeReport(User user, Date start, Date end);
    String generateSpendingReport(User user, Date start, Date end);
}
