package com.codecool.shop.controller;

import com.codecool.shop.dao.CountryDao;
import com.codecool.shop.dao.MatchDetailsDao;
import com.codecool.shop.dao.SportTypeDao;
import com.codecool.shop.dao.implementation.CountryDaoMem;
import com.codecool.shop.dao.implementation.MatchDetailsDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SportTypeDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MatchDetailsDao productDataStore = MatchDetailsDaoMem.getInstance();
        CountryDao countryDataStore = CountryDaoMem.getInstance();
        SportTypeDao sportDataStore = SportTypeDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("country", countryDataStore.find(1));
        context.setVariable("countries", countryDataStore.getAll());
        context.setVariable("sports", sportDataStore.getAll());
        context.setVariable("matches", productDataStore.getBy(countryDataStore.find(1)));

        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
        resp.setContentType("text/html; charset=UTF-8");
        engine.process("product/index.html", context, resp.getWriter());
    }

}
