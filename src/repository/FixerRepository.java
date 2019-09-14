package repository;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class FixerRepository {
    private final String ACCESS_KEY = "f3e326429ab875e3dc08b23ec8b04f7a";
    private String symbols;
    private String date;
    private double amount;
    private HttpServletResponse response;

    public FixerRepository(String symbols, String date, double amount, HttpServletResponse response) {
        this.symbols = symbols;
        this.date = date;
        this.amount = amount;
        this.response = response;
    }

    public void showResult() throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/HTML");
        PrintWriter writer = response.getWriter();
        writer.printf(amount + " PLN " + date + " to: %.4f " + symbols, calculator(getCurrencyValue(), symbols, amount));
    }

    private String getUrl (){
        String url = "http://data.fixer.io/api/";
        if (!"".equals(date))
            return url + date + "?access_key=" + ACCESS_KEY + "&symbols=EUR,PLN,CHF,USD&format=1";
        else
            return url + "latest?access_key=" + ACCESS_KEY + "&symbols=EUR,PLN,CHF,USD&format=1";
    }

    private Map<String, String> getCurrencyValue() throws IOException {
        URLConnection urlConnection = new URL(getUrl()).openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
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
        return lineToMap;
    }

    private Double calculator (Map<String, String> currencyMap, String currency, double amount) {
        double rate = Double.valueOf(currencyMap.get((currency)));
        double pln = Double.valueOf(currencyMap.get("PLN"));
        return amount / (pln / rate);
    }
}
