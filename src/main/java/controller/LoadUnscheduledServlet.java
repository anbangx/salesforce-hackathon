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

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy hh:mma");

        listhtml = "<div class=\"list-group\">\n" +
                "<a href=\"#\" class=\"list-group-item\">";

        for (Task t : tasks) {
            Interval interval = t.getTodoIntervals().get(0);
            Date start = new Date(interval.start);
            Date end = new Date(interval.end);

            listhtml += "<h4 class=\"list-group-item-heading\">" + t.getEvent() + "</h4>"
                    + "<p class=\"list-group-item-text\">"
                    + "start date: " + formatter.format(start)
                    + "end date: " + formatter.format(end)
                    + "</p>";
        }

        listhtml += "</a></div>";

        request.setAttribute("unscheduledtasks", listhtml);
        request.getRequestDispatcher("/unscheduled.jsp").forward(request, response);
    }
}
