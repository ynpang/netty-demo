package com.enjoy.netty.servlets;

public class MyServlet extends GPServlet {
    @Override
    public void doGet(GPRequest request, GPResponse response) {
        try {
            response.write(request.getParameter("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doPost(GPRequest request, GPResponse response) {
        doGet(request,response);

    }
}
