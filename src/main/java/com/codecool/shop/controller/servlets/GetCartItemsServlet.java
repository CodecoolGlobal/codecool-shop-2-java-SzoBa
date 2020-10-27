package com.codecool.shop.controller.servlets;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.CountryDao;
import com.codecool.shop.dao.MatchDetailsDao;
import com.codecool.shop.dao.SportTypeDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.MatchDetailsDaoMem;
import com.codecool.shop.dao.jdbc_implementation.GameDatabaseManager;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.CartItem;
import com.codecool.shop.model.MatchDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name="getCartItemsServlet", urlPatterns = {"/getCartItems"}, loadOnStartup = 2)
public class GetCartItemsServlet extends javax.servlet.http.HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GameDatabaseManager gameDatabaseManager = GameDatabaseManager.getInstance();

        int clientSessionIdHashCode = req.getSession().getId().hashCode();
        CartDao cartDao = gameDatabaseManager.getCartDao();

        Cart cart = cartDao.find(clientSessionIdHashCode);

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        String cartString = gson.toJson(cart);
        resp.setCharacterEncoding("UTF-8");


        PrintWriter out = resp.getWriter();
        out.println(cartString);
    }
}
