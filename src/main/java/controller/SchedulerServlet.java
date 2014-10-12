package controller;

import core.Scheduler;
import datamodel.Task;
import tablemanager.TaskTableManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class SchedulerServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        TaskTableManager dbManager = TaskTableManager.getInstance();
        ArrayList<Task> scheduledTasks = dbManager.readScheduledTasks();
        ArrayList<Task> unscheduledTasks = dbManager.readUnscheduledTasks();

        Scheduler.init(scheduledTasks);
        ArrayList<Task> resultTasks = Scheduler.schedule(unscheduledTasks);

        for (Task t : resultTasks) {
            response.getWriter().println(t.getEvent());
        }
        //response.getWriter().println(resultTasks.size());

        dbManager.updateTasks(resultTasks);

        request.getRequestDispatcher("/scheduled").forward(request, response);
    }
}
