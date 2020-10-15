package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.CartItem;
import com.codecool.shop.model.Order;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@WebServlet(urlPatterns = {"/success"})
public class OrderConfirmedController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int clientSessionIdHashCode = req.getSession().getId().hashCode();
        OrderDao orderDao = OrderDaoMem.getInstance();
        Order order = orderDao.find(clientSessionIdHashCode);
        if (order == null) {
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            CartDao cartDao = CartDaoMem.getInstance();
            Cart cart = cartDao.find(clientSessionIdHashCode);


            // Recipient's email ID needs to be mentioned.
            String to = order.getEmailAddress();

            // Sender's email ID needs to be mentioned
            String from = System.getenv("email");
            String password = System.getenv("password");
            String messageFromOrder = createMessage(cart, clientSessionIdHashCode);


            // Assuming you are sending email from through gmails smtp
            String host = "smtp.gmail.com";

            // Get system properties
            Properties properties = System.getProperties();

            // Setup mail server
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            // Get the Session object.// and pass username and password
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {

                    return new PasswordAuthentication(from, password);

                }

            });

            // Used to debug SMTP issues
            session.setDebug(true);

            try {
                // Create a default MimeMessage object.
                MimeMessage message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(from));

                // Set To: header field of the header.
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                // Set Subject: header field
                message.setSubject("Order was successful!");

                // Now set the actual message
                message.setContent(messageFromOrder,"text/html");

                System.out.println("sending...");
                // Send message
                Transport.send(message);
                System.out.println("Sent message successfully....");
            } catch (MessagingException mex) {
                mex.printStackTrace();
            }

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = formatter.format(date);
        String fileName = order.getId() + dateString;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String orderString = gson.toJson(order);

        try {
            File myFile = new File(fileName + ".txt");
            FileWriter myFileWriter = new FileWriter(fileName + ".txt");
            myFileWriter.write(orderString);
            myFileWriter.close();
        } catch (IOException e) {
            System.out.println("Problem occurred during writing to file");
        }

            orderDao.remove(clientSessionIdHashCode);
            cartDao.remove(clientSessionIdHashCode);

            // TODO
            // order was successful message?

//            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
//            WebContext context = new WebContext(req, resp, req.getServletContext());
//
//
//            resp.setContentType("text/html; charset=UTF-8");
//            engine.process("product/index.html", context, resp.getWriter());


            resp.sendRedirect(req.getContextPath() + "/");
        }
    }

    private String createMessage(Cart cart, int id) {
        StringBuilder message = new StringBuilder();
        for (CartItem cartItem: cart.getItems()) {
            message.append("<p>" + cartItem.getHome() + " - " + cartItem.getAway() +"</p>");
            message.append("<p>" + cartItem.getHome() + " - " + cartItem.getAway() +"</p>");
        }

        return message.toString();
    }

}
