package com.codecool.shop.controller.servlets;

import com.codecool.shop.dao.CountryDao;
import com.codecool.shop.dao.MatchDetailsDao;
import com.codecool.shop.dao.implementation.CountryDaoMem;
import com.codecool.shop.dao.implementation.MatchDetailsDaoMem;
import com.codecool.shop.model.Country;
import com.codecool.shop.model.MatchDetails;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name="countrySelectServlet", urlPatterns = {"/country"}, loadOnStartup = 2)
public class CountrySelectServlet extends javax.servlet.http.HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int countryId = Integer.parseInt(req.getParameter("countryId"));
        CountryDao countryDataStore = CountryDaoMem.getInstance();
        Country selectedCountry = countryDataStore.find(countryId);

        MatchDetailsDao matchDetailsDataStore = MatchDetailsDaoMem.getInstance();
        List<MatchDetails> matchesBySelectedCountry = matchDetailsDataStore.getBy(selectedCountry);

        matchesBySelectedCountry.forEach(System.out::println);



    }
}
