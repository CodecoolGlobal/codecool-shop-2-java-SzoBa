package com.codecool.shop.controller.servlets;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.jdbc_implementation.GameDatabaseManager;
import com.codecool.shop.model.Cart;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ognl.JavaCharStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name="saveBetServlet", urlPatterns = {"/save_bet"}, loadOnStartup = 2)
public class SaveBetServlet extends javax.servlet.http.HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GameDatabaseManager gameDatabaseManager = GameDatabaseManager.getInstance();

        int clientSessionIdHashCode = req.getSession().getId().hashCode();
        CartDao cartDao = gameDatabaseManager.getCartDao();
        Cart cart = cartDao.find(clientSessionIdHashCode);

        Gson gson = new GsonBuilder().create();
        Scanner s = new Scanner(req.getInputStream(), "UTF-8").useDelimiter("\\A");
        String inputData = s.hasNext() ? s.next() : "";
        Map mappedData = gson.fromJson(inputData, Map.class);

        String message = "";
        try {
            int bet = Integer.parseInt((String) mappedData.get("betValue"));
            int possibleWin = Integer.parseInt((String) mappedData.get("possibleWinAmount"));
            float totalOdds = Float.parseFloat((String) mappedData.get("totalOdds"));
            String dateString = (String) mappedData.get("date");
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse(dateString);
            cart.setBet(bet);
            cart.setPossibleWin(possibleWin);
            cart.setActualTime(date);
            cart.setTotalOdds(totalOdds);
            message = "Cart saved successfully";
        } catch (IllegalArgumentException | ParseException e) {
            message = "An error occurred at the checkout!";
        }
        String answer = gson.toJson(message);
        PrintWriter out = resp.getWriter();
        out.println(answer);
    }
}
