package servlet;

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
 //       String callback = request.getParameter("callback");
        double amount = Double.valueOf(request.getParameter("amount"));
//        String access_key = request.getParameter("access_key");
        String symbols = request.getParameter("symbols");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/HTML");
        PrintWriter writer = response.getWriter();

        String url = "http://data.fixer.io/api/latest?access_key=f3e326429ab875e3dc08b23ec8b04f7a&symbols=EUR,PLN,CHF,USD&format=1";
        URLConnection urlConnection = new URL(url).openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//

        String line = "";
        Map<String, String> lineToMap = new HashMap<>();
        while ((line = bufferedReader.readLine())!=null) {
            if (!line.contains("{") && !line.contains("}")) {
                line = line.replace("\"", "");
                line = line.replace(",", "");
                line = line.replace(" ", "");

                String[] lineToArray = line.split(":");
                lineToMap.put(lineToArray[0], lineToArray[1]);
            }
        }

        writer.println("Przeliczona wartosc to: " + calculator(lineToMap, symbols, amount));
    }

    private Double calculator (Map<String, String> currencyMap, String currency, double amount){
        double result = 0;
        double rate = Double.valueOf(currencyMap.get((currency)));
        double pln = Double.valueOf(currencyMap.get("PLN"));
        result = amount / (pln/rate);
        return result;
    }

}
