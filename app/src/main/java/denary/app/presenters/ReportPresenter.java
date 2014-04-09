package denary.app.presenters;

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

    public void onGenereteReportUserClick(){
        //myModel.generateReport(user);
        myView.advance();
    }
}
