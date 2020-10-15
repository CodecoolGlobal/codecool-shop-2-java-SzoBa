package com.codecool.shop.controller.servlets;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Order;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="orderSaveServlet", urlPatterns = {"/confirmation"}, loadOnStartup = 2)
public class OrderSaveServlet extends javax.servlet.http.HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDao orderDao = OrderDaoMem.getInstance();
        Order order = new Order();
        order.setFirstName(req.getParameter("firstName"));
        order.setLastName(req.getParameter("lastName"));
        order.setPhoneNumber(req.getParameter("telephone"));
        order.setEmailAddress(req.getParameter("emailinput"));
        order.setCountry(req.getParameter("country"));
        order.setCity(req.getParameter("city"));
        order.setZip(Integer.parseInt(req.getParameter("zip")));
        order.setAddress(req.getParameter("address"));
        orderDao.add(order);
        resp.sendRedirect(req.getContextPath() + "/success");
    }

}
