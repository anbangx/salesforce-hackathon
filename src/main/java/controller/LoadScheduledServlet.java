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

public class LoadScheduledServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String listhtml = "";
        TaskTableManager dbManager = TaskTableManager.getInstance();

        ArrayList<Task> tasks = dbManager.readScheduledTasks();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy hh:mma");

        for (Task t : tasks) {
            Interval interval = t.getScheduledInterval();
            Date start = new Date(interval.start);
            Date end = new Date(interval.end);

            listhtml += " <div class=\"panel panel-default\">\n" +
                    "  <div class=\"panel-heading\">\n" +
                    "    <h3 class=\"panel-title\">" +  t.getEvent() +"</h3>\n" +
                    "  </div>\n" +
                    "  <div class=\"panel-body\">\n" +
                    "  <div class=\"col-md-6\">" +
                    formatter.format(start) +
                    " </div>" +
                    "  <div class=\"col-md-6\">" +
                    formatter.format(end) +
                    " </div>" +
                    "  </div>\n" +
                    "</div>  ";

        }

        request.setAttribute("scheduledtasks", listhtml);
        request.getRequestDispatcher("/scheduled.jsp").forward(request, response);
    }
}
