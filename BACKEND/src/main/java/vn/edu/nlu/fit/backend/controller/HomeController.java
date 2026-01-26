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

        request.setAttribute("data", dto);

        request.getRequestDispatcher("/WEB-INF/views/home/homepage.jsp")
                .forward(request, response);
    }
}
