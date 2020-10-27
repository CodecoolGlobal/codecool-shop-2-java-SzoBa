package com.codecool.shop.controller.servlets;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.CountryDao;
import com.codecool.shop.dao.MatchDetailsDao;
import com.codecool.shop.dao.SportTypeDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.CountryDaoMem;
import com.codecool.shop.dao.implementation.MatchDetailsDaoMem;
import com.codecool.shop.dao.jdbc_implementation.GameDatabaseManager;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.CartItem;
import com.codecool.shop.model.Country;
import com.codecool.shop.model.MatchDetails;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name="modifyItemInCartServlet", urlPatterns = {"/modifyCart"}, loadOnStartup = 2)
public class ModifyItemInCartServlet extends javax.servlet.http.HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GameDatabaseManager gameDatabaseManager = GameDatabaseManager.getInstance();

        int matchId = Integer.parseInt(req.getParameter("matchID"));
        boolean isAdded = req.getParameter("isAdded").equals("true");
        String outcome = req.getParameter("outcome");

        int clientSessionIdHashCode = req.getSession().getId().hashCode();
        CartDao cartDao = gameDatabaseManager.getCartDao();

        if (isAdded) {
            Cart cart = cartDao.find(clientSessionIdHashCode);
            cart.removeItemFromCart(matchId);
            if (cart.getItems().size() == 0) {
                cartDao.remove(clientSessionIdHashCode);
            }
        } else {
            MatchDetailsDao matchDetailsDao = gameDatabaseManager.getMatchDetailsDao();
            MatchDetails matchDetails = matchDetailsDao.find(matchId);

            CartItem cartItem = new CartItem("description", matchDetails, outcome);
            Cart cart = cartDao.find(clientSessionIdHashCode);
            if (cart == null) {
                cart = new Cart("description");
                cartDao.add(cart, clientSessionIdHashCode);
            }
            cart.addItemToCart(cartItem);
        }

        PrintWriter out = resp.getWriter();

        out.println("[]");
    }
}
