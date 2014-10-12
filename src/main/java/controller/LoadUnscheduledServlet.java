package controller;

import datamodel.Interval;
import datamodel.Task;
import tablemanager.TaskTableManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LoadUnscheduledServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String listhtml = "";
        TaskTableManager dbManager = TaskTableManager.getInstance();

        ArrayList<Task> tasks = dbManager.readUnscheduledTasks();

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy hh:mma");

        for (Task t : tasks) {
            ArrayList<Interval> intervals = t.getTodoIntervals();

            listhtml += " <div class=\"panel panel-default\">\n" +
                    "  <div class=\"panel-heading\">\n" +
                    "  <div class=\"col-xs-6 col-md-6\">" +
                    t.getEvent() +
                    " </div>" +
                    "  <div class=\"col-xs-6 col-md-6\">" +
                    "Priotity " + t.getPriority() +
                    " </div>";
            for (Interval interval : intervals) {
                Date start = new Date(interval.start);
                Date end = new Date(interval.end);
                listhtml += "  </div>\n" +
                        "  <div class=\"panel-body\">\n" +
                        "  <div class=\"col-xs-6 col-md-6\">" +
                        "start: " + formatter.format(start) +
                        " </div>" +
                        "  <div class=\"col-xs-6 col-md-6\">" +
                        "end: " + formatter.format(end) +
                        " </div>" +
                        "  </div>\n";
            }
            listhtml += "</div>";

        }

        request.setAttribute("unscheduledtasks", listhtml);
        request.getRequestDispatcher("/unscheduled.jsp").forward(request, response);
    }
}
