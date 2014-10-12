package controller;

import datamodel.Interval;
import datamodel.Task;
import tablemanager.TaskTableManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SubmitServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String startDate, startTime, endDate, endTime, startDateTime, endDateTime;
        Date start, end;
        Long startLong, endLong;
        ArrayList<Interval> intervals = new ArrayList<Interval>();


        String event = request.getParameter("task");
        int priority = Integer.parseInt(request.getParameter("priority"));
        String categoryStr = request.getParameter("category");
        Task.CATEGORY category = Task.CATEGORY.valueOf(categoryStr);

        // Parse string to date to long
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-ddhh:mm");
        int intervalNum = 1;
        while (request.getParameter("startdate" + intervalNum) != null) {
            response.getWriter().println(request.getParameter("startdate" + intervalNum));
            startDate = request.getParameter("startdate" + intervalNum);
            startTime = request.getParameter("starttime" + intervalNum);
            endDate = request.getParameter("enddate" + intervalNum);
            endTime = request.getParameter("endtime" + intervalNum);

            startDateTime = startDate + startTime;
            endDateTime = endDate + endTime;

            response.getWriter().println(startDateTime);
            response.getWriter().println(endDateTime);
            try {
                start = formatter.parse(startDateTime);
                end = formatter.parse(endDateTime);

                response.getWriter().println(start);
                response.getWriter().println(end);

                startLong = start.getTime();
                endLong = end.getTime();
                intervals.add(new Interval(startLong, endLong));

            } catch (ParseException e ) {
                e.printStackTrace();
            }

            intervalNum++;
        }

        //Store task into db
        Task newTask = new Task(1, event, category, intervals, new Interval(0,0), false, priority);

        TaskTableManager dbManager = TaskTableManager.getInstance();
        dbManager.createTable();
        dbManager.writeTaskToDB(newTask);

        request.getRequestDispatcher("/unscheduled.jsp").forward(request, response);
    }
}
