package com.loemin.crm.workbench.web.controller;

import com.loemin.crm.settings.domain.User;
import com.loemin.crm.settings.service.UserService;
import com.loemin.crm.settings.service.impl.UserServiceImpl;
import com.loemin.crm.utils.DateTimeUtil;
import com.loemin.crm.utils.PrintJson;
import com.loemin.crm.utils.ServiceFactory;
import com.loemin.crm.utils.UUIDUtil;
import com.loemin.crm.vo.PaginationVO;
import com.loemin.crm.workbench.domain.Activity;
import com.loemin.crm.workbench.domain.Clue;
import com.loemin.crm.workbench.service.ActivityService;
import com.loemin.crm.workbench.service.ClueService;
import com.loemin.crm.workbench.service.impl.ActivityServiceImpl;
import com.loemin.crm.workbench.service.impl.ClueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ClueController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/workbench/clue/getUserList.do":
                getUserList(request, response);
                break;
            case "/workbench/clue/save.do":
                save(request, response);
                break;
            case "/workbench/clue/pageList.do":
                pageList(request, response);
                break;
            case "/workbench/clue/detail.do":
                detail(request, response);
                break;
            case "/workbench/clue/getActivityList.do":
                getActivityList(request, response);
                break;
            case "/workbench/clue/unbund.do":
                unbund(request, response);
                break;
            case "/workbench/clue/getActivityListByName.do":
                getActivityListByName(request, response);
                break;
            case "/workbench/clue/bund.do":
                bund(request, response);
                break;
        }
    }

    private void bund(HttpServletRequest request, HttpServletResponse response) {
        String[] activityIds = request.getParameterValues("activityId");
        String clueId = request.getParameter("clueId");
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag = cs.bund(activityIds, clueId);
        PrintJson.printJsonFlag(response, flag);
    }

    private void getActivityListByName(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> a = as.getActivityListByName(id, name);
        PrintJson.printJsonObj(response, a);
    }

    private void unbund(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag = cs.unbund(id);
        PrintJson.printJsonFlag(response, flag);
    }

    private void getActivityList(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        List<Activity> dataList = cs.getActivityList(id);
        PrintJson.printJsonObj(response, dataList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Clue c = cs.detail(id);
        request.setAttribute("c", c);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request, response);
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        Clue c = new Clue();
        c.setFullname(request.getParameter("fullname"));
        c.setCompany(request.getParameter("company"));
        c.setPhone(request.getParameter("phone"));
        c.setSource(request.getParameter("source"));
        c.setOwner(request.getParameter("owner"));
        c.setMphone(request.getParameter("mphone"));
        c.setState(request.getParameter("state"));
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        PaginationVO<Clue> vo = cs.pageList(c, pageNo, pageSize);
        PrintJson.printJsonObj(response, vo);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        Clue c = new Clue();
        c.setId(UUIDUtil.getUUID());
        c.setFullname(request.getParameter("fullname"));
        c.setAppellation(request.getParameter("appellation"));
        c.setOwner(request.getParameter("owner"));
        c.setCompany(request.getParameter("company"));
        c.setJob(request.getParameter("job"));
        c.setEmail(request.getParameter("email"));
        c.setPhone(request.getParameter("phone"));
        c.setWebsite(request.getParameter("website"));
        c.setMphone(request.getParameter("mphone"));
        c.setState(request.getParameter("state"));
        c.setSource(request.getParameter("source"));
        c.setDescription(request.getParameter("description"));
        c.setContactSummary(request.getParameter("contactSummary"));
        c.setNextContactTime(request.getParameter("nextContactTime"));
        c.setAddress(request.getParameter("address"));
        c.setCreateBy(((User) request.getSession(false).getAttribute("user")).getName());
        c.setCreateTime(DateTimeUtil.getSysTime());
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag = cs.save(c);
        PrintJson.printJsonFlag(response, flag);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> uList = us.getUserList();
        PrintJson.printJsonObj(response, uList);
    }
}
