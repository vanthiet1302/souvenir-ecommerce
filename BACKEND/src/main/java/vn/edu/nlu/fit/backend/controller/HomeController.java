package vn.edu.nlu.fit.backend.controller;

import vn.edu.nlu.fit.backend.dto.HomePageDTO;
import vn.edu.nlu.fit.backend.service.HomeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/home")
public class HomeController extends HttpServlet {

    private HomeService homeService;

    @Override
    public void init() {
        homeService = new HomeService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HomePageDTO dto = homeService.getHomePageData();

        /* ===== PAGE DATA ===== */
        request.setAttribute("data", dto);

        /* ===== HEADER MODE ===== */
        request.setAttribute("headerMode", "MENU");

        /* ===== LAYOUT CONFIG ===== */
        request.setAttribute("pageTitle", "Trang chủ");
        request.setAttribute("pageCss", "HomePageMain.css");
        request.setAttribute("pageJs", "HomePage.js");
        request.setAttribute("contentPage", "/home.jsp");

        /* ===== FORWARD TO LAYOUT ===== */
        request.getRequestDispatcher("/layoutMain.jsp")
                .forward(request, response);
    }

}
