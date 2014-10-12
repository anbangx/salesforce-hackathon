package controller;

import tablemanager.TaskTableManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SchedulerServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        TaskTableManager dbManager = TaskTableManager.getInstance();

        String message = "Hello World";
        request.setAttribute("message", message);
        request.getRequestDispatcher("/scheduled.jsp").forward(request, response);
    }
}
