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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name="saveBetServlet", urlPatterns = {"/save_bet"}, loadOnStartup = 2)
public class SaveBetServlet extends javax.servlet.http.HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = "";
        int clientSessionIdHashCode = req.getSession().getId().hashCode();
        CartDao cartDao = CartDaoMem.getInstance();
        Cart cart = cartDao.find(clientSessionIdHashCode);

        try {
            int bet = Integer.parseInt(req.getParameter("bet"));
            int possibleWin = Integer.parseInt(req.getParameter("possible_win"));
            message = "Successful";
            cart.setBet(bet);
            cart.setPossibleWin(possibleWin);
        } catch (IllegalArgumentException e) {
            message = "An error occurred!";
        }

        PrintWriter out = resp.getWriter();
        out.println("[]");
    }
}
