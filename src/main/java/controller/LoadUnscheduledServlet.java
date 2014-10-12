package controller;

import datamodel.Interval;
import datamodel.Task;
import tablemanager.TaskTableManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class LoadUnscheduledServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String listhtml = "";
        TaskTableManager dbManager = TaskTableManager.getInstance();

        ArrayList<Task> tasks = dbManager.readUnscheduledTasks();

        //for (int i = 0; i < tasks.size(); i++) {
        //    listhtml += "<p>" + tasks.get(i).getEvent() + " " + tasks.get()
        //             + "</p>"
        //             + "<hr>";
        //}

        for (Task t : tasks) {
            Interval interval = t.getTodoIntervals().get(0);
            listhtml += "<p>"
                     + t.getEvent() + " " + t.getPriority()
                     + " " + new Date(interval.start * 1000)
                     + " " + new Date(interval.end * 1000)
                     + "</p>"
                     + "<hr>";
        }

        response.getWriter().println(tasks.size());
        response.getWriter().println(listhtml);

        request.setAttribute("unscheduledtasks", listhtml);
        request.getRequestDispatcher("/unscheduled.jsp").forward(request, response);
    }
}
