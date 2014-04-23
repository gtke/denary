package denary.app.models;


import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gtkesh on 3/30/14.
 */
public class ReportModel implements RModel{


    @Override
    public String generateSpendingReport(User user, Date start, Date end) {
        String report = "";
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Transaction");
        query.whereEqualTo("owner", user.getEmail());
        query.whereGreaterThanOrEqualTo("createdAt", start);
        query.whereLessThanOrEqualTo("createdAt", end);
        List<ParseObject> results = null;
        try {
            results = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        HashMap<String, Double> map = new HashMap<String, Double>();

        for(ParseObject o : results){
            Object temp = o.get("tag");
            if(temp!=null){
                String key = temp.toString();
                Double value = Double.parseDouble(o.get("amount").toString());
                if(map.get(key)==null){
                    if(value < 0.0){
                        map.put(key,value);
                    }
                }else{
                    if(value < 0.0){
                        Double old_value = map.get(key);
                        map.put(key, old_value + value);
                    }
                }
            }
        }

        DateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy");

        report = report.concat("Spending Category Report: "+"\n" +"\n");
        report = report.concat(displayFormat.format(start) + " - " + displayFormat.format(end) + "\n");
        double total = 0;
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            report = report.concat("\t" + key + " : " + Math.abs(value) + "\n");
            total += Math.abs(value);

        }
        report = report.concat("\n");
        report = report.concat("\t" + "Total: " + total);
        report = report.concat("\n");
        return report;
    }

    @Override
    public String generateIncomeReport(User user, Date start, Date end) {
        String report = "";

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Transaction");
        query.whereEqualTo("owner", user.getEmail());
        query.whereGreaterThanOrEqualTo("createdAt", start);
        query.whereLessThanOrEqualTo("createdAt", end);
        List<ParseObject> results = null;
        try {
            results = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        HashMap<String, Double> map = new HashMap<String, Double>();
        System.out.println("RESULTS: " + results.toString());
        if(results!=null){
            for(ParseObject o : results){
                Object temp = o.get("tag");
                System.out.println("TEMP " + temp);
                if(temp!=null){
                    String key = temp.toString();
                    Double value = Double.parseDouble(o.get("amount").toString());
                    if(map.get(key)==null){
                        if(value > 0.0){
                            map.put(key,value);
                        }
                    }else{
                        if(value > 0.0){
                            Double old_value = map.get(key);
                            map.put(key, old_value + value);
                        }
                    }
                }

            }
        }
        System.out.println("MAPSIZE: " + map.size());
        DateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy");


        report = report.concat("\n" + "Income Category Report: "+"\n" +"\n");
        report = report.concat(displayFormat.format(start) + " - " + displayFormat.format(end) + "\n");
        double total = 0.0;
        if(results!=null){
            for (Map.Entry<String, Double> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                System.out.println("KEY-VALUE: " + key + "-"+value);
                report = report.concat("\t" + key+ " : " + Math.abs(value)+"\n");
                total += Math.abs(value);

            }
        }
        report = report.concat("\n");
        report = report.concat("\t"+ "Total: " + total);
        report = report.concat("\n");

        System.out.println("INCOME REPORT: " + report);
        return report;
    }

}
