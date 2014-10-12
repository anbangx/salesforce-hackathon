package controller;

import datamodel.Task;
import tablemanager.TaskTableManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class LoadScheduledServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String listhtml = "";
        TaskTableManager dbManager = TaskTableManager.getInstance();

        ArrayList<Task> tasks = dbManager.readScheduledTasks();

        for (int i = 0; i < tasks.size(); i++) {
            listhtml += "<p> " + tasks.get(i).getEvent() + " </p>"
                    + "<hr>";
        }

        request.setAttribute("scheduledtasks", listhtml);
        request.getRequestDispatcher("/scheduled.jsp").forward(request, response);
    }
}
