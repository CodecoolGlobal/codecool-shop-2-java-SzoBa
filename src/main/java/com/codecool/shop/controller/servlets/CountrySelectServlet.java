package com.codecool.shop.controller.servlets;

import com.codecool.shop.dao.CountryDao;
import com.codecool.shop.dao.MatchDetailsDao;
import com.codecool.shop.dao.implementation.CountryDaoMem;
import com.codecool.shop.dao.implementation.MatchDetailsDaoMem;
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

@WebServlet(name="countrySelectServlet", urlPatterns = {"/country"}, loadOnStartup = 2)
public class CountrySelectServlet extends javax.servlet.http.HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int countryId = Integer.parseInt(req.getParameter("countryId"));
        CountryDao countryDataStore = CountryDaoMem.getInstance();
        Country selectedCountry = countryDataStore.find(countryId);

        MatchDetailsDao matchDetailsDataStore = MatchDetailsDaoMem.getInstance();
        List<MatchDetails> matchesBySelectedCountry;

        if (countryId == 0) {
            matchesBySelectedCountry = matchDetailsDataStore.getAll();
        } else {
            matchesBySelectedCountry = matchDetailsDataStore.getBy(selectedCountry);
        }

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        String matches = gson.toJson(matchesBySelectedCountry);
//        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.println(matches);
    }
}
