package com.codecool.shop.controller.servlets;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name="orderSaveServlet", urlPatterns = {"/save_order"}, loadOnStartup = 2)
public class OrderSaveServlet extends javax.servlet.http.HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int clientSessionIdHashCode = req.getSession().getId().hashCode();

        OrderDao orderDao = OrderDaoMem.getInstance();
        Order order = orderDao.find(clientSessionIdHashCode);
        order.setFirstName(req.getParameter());
        order.setLastName(req.getParameter());
        order.setPhoneNumber(req.getParameter());
        order.setEmailAddress(req.getParameter());
        order.setCountry(req.getParameter());
        order.setCity(req.getParameter());
        order.setZip(Integer.parseInt(req.getParameter()));
        order.setAddress(req.getParameter());

        PrintWriter out = resp.getWriter();
        out.println("[]");
    }
}
