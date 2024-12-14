package servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/currency")
public class CurrencyServlet extends HttpServlet {
    private final String API_URL = "https://api.exchangerate-api.com/v4/latest/BYN";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Double> currencyRates = getCurrencyRates();
        request.setAttribute("currencyRates", currencyRates); // Связываем все курсы валют
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currency = request.getParameter("currency").toUpperCase();
        Map<String, Double> currencyRates = getCurrencyRates();

        Double rate = currencyRates.get(currency);

        if (rate != null) {
            request.setAttribute("rate", String.format("%.2f", rate));
        } else {
            request.setAttribute("rate", "Валюта не найдена");
        }

        request.setAttribute("currencyRates", currencyRates); // Обновляем список валют
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private Map<String, Double> getCurrencyRates() throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("Error fetching data from API");
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        }

        return parseRates(response.toString());
    }

    private Map<String, Double> parseRates(String json) {
        Gson gson = new Gson();
        Map<String, Object> result = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
        Map<String, Double> rates = (Map<String, Double>) result.get("rates");
        return rates;
    }
}