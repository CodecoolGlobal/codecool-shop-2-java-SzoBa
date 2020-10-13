package com.codecool.shop.controller.servlets;

import com.codecool.shop.dao.CountryDao;
import com.codecool.shop.dao.MatchDetailsDao;
import com.codecool.shop.dao.SportTypeDao;
import com.codecool.shop.dao.implementation.CountryDaoMem;
import com.codecool.shop.dao.implementation.MatchDetailsDaoMem;
import com.codecool.shop.dao.implementation.SportTypeDaoMem;
import com.codecool.shop.model.Country;
import com.codecool.shop.model.MatchDetails;
import com.codecool.shop.model.SportType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name="sportTypeSelectServlet", urlPatterns = {"/sport-type"}, loadOnStartup = 2)
public class SportTypeSelectServlet extends javax.servlet.http.HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int typeId = Integer.parseInt(req.getParameter("typeId"));

        SportTypeDao sportTypeDataStore = SportTypeDaoMem.getInstance();
        SportType selectedSportType = sportTypeDataStore.find(typeId);

        MatchDetailsDao matchDetailsDataStore = MatchDetailsDaoMem.getInstance();
        List<MatchDetails> matchesBySelectedSportType = matchDetailsDataStore.getBy(selectedSportType);

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        String matches = gson.toJson(matchesBySelectedSportType);
//        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.println(matches);
    }
}
