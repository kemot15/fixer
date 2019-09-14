package servlet;

import repository.FixerRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebServlet("/fixer")
public class FixerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double amount = Double.valueOf(request.getParameter("amount"));
        String symbols = request.getParameter("symbols");
        String date = request.getParameter("date");
        FixerRepository fixerRepository = new FixerRepository(symbols, date, amount, response);
        fixerRepository.showResult();
    }


}
