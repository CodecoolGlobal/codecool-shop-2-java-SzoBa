package com.codecool.shop.controller.servlets;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.CountryDao;
import com.codecool.shop.dao.MatchDetailsDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.CountryDaoMem;
import com.codecool.shop.dao.implementation.MatchDetailsDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.CartItem;
import com.codecool.shop.model.Country;
import com.codecool.shop.model.MatchDetails;
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
        int clientSessionIdHashCode = req.getSession().getId().hashCode();
        CartDao cartDao = CartDaoMem.getInstance();
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
