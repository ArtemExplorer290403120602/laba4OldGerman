package servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/currency")
public class CurrencyServlet extends HttpServlet {
    private static final Map<String, Double> currencyRates;

    static {
        currencyRates = new HashMap<>();
        currencyRates.put("dollar", 3.3); // пример курса
        currencyRates.put("euro", 3.6);   // пример курса
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currency = request.getParameter("currency").toLowerCase();
        Double rate = currencyRates.get(currency);

        if (rate != null) {
            request.setAttribute("rate", String.valueOf(rate));
        } else {
            request.setAttribute("rate", "Валюта не найдена");
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}