package com.codecool.shop.controller.servlets;

import com.codecool.shop.dao.CountryDao;
import com.codecool.shop.dao.MatchDetailsDao;
import com.codecool.shop.dao.implementation.CountryDaoMem;
import com.codecool.shop.dao.implementation.MatchDetailsDaoMem;
import com.codecool.shop.model.Country;
import com.codecool.shop.model.MatchDetails;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name="modifyItemInCartServlet", urlPatterns = {"/modifyCart"}, loadOnStartup = 2)
public class ModifyItemInCartServlet extends javax.servlet.http.HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int matchId = Integer.parseInt(req.getParameter("matchID"));
        Boolean isAdded = req.getParameter("isAdded").equals("true");
        String outcome = req.getParameter("outcome");
        System.out.println();

    }
}
