package denary.app.presenters;

import java.util.Date;

import denary.app.models.DatabaseModel;
import denary.app.models.ReportModel;
import denary.app.models.User;
import denary.app.views.IView;

/**
 * Created by gtkesh on 4/7/14.
 */
public class ReportPresenter {
    private final IView myView;

    private final ReportModel myModel;

    public ReportPresenter(IView view, ReportModel model) {
        myView = view;
        myModel = model;
    }

    public String onGenerateReportUserClick(User user, Date start, Date end){
        String report = "";
        report += myModel.generateIncomeReport(user, start, end);
        report += "\n";
        report += myModel.generateSpendingReport(user, start, end);
        return report;
    }
}
