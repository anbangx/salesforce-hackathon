package controller;

import datamodel.Task;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class SubmitServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String event = request.getParameter("task");
        String categoryStr = request.getParameter("category");
        Task.CATEGORY category = Task.CATEGORY.valueOf(categoryStr);

        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yy hhmma");

        Task newTask = new Task();


        String message = "Hello World";
        request.setAttribute("message", message);
        request.getRequestDispatcher("/scheduled.jsp").forward(request, response);
    }
}
