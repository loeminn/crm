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
import com.loemin.crm.workbench.domain.ActivityRemark;
import com.loemin.crm.workbench.service.ActivityService;
import com.loemin.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/workbench/activity/getUserList.do":
                getUserList(request, response);
                break;
            case "/workbench/activity/save.do":
                save(request, response);
                break;
            case "/workbench/activity/pageList.do":
                pageList(request, response);
                break;
            case "/workbench/activity/delete.do":
                delete(request, response);
                break;
            case "/workbench/activity/getUserListAndActivity.do":
                getUserListAndActivity(request, response);
                break;
            case "/workbench/activity/update.do":
                update(request, response);
                break;
            case "/workbench/activity/detail.do":
                detail(request, response);
                break;
            case "/workbench/activity/getRemarkList.do":
                getRemarkList(request, response);
                break;
            case "/workbench/activity/deleteRemark.do":
                deleteRemark(request, response);
                break;
            case "/workbench/activity/saveRemark.do":
                saveRemark(request, response);
                break;
            case "/workbench/activity/updateRemark.do":
                updateRemark(request, response);
                break;
            case "/workbench/activity/getRemark.do":
                getRemark(request, response);
                break;
        }
    }

    private void getRemark(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        ActivityRemark ar = as.getRemarkById(id);
        PrintJson.printJsonObj(response, ar);
    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {
        ActivityRemark ar = new ActivityRemark();
        ar.setId(request.getParameter("id"));
        ar.setNoteContent(request.getParameter("noteContent"));
        ar.setEditBy(((User) request.getSession(false).getAttribute("user")).getName());
        ar.setEditTime(DateTimeUtil.getSysTime());
        ar.setEditFlag("1");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.updateRemark(ar);
        PrintJson.printJsonFlag(response, flag);
    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
        ActivityRemark ar = new ActivityRemark();
        ar.setId(UUIDUtil.getUUID());
        ar.setNoteContent(request.getParameter("noteContent"));
        ar.setActivityId(request.getParameter("activityId"));
        ar.setCreateBy(((User) request.getSession(false).getAttribute("user")).getName());
        ar.setCreateTime(DateTimeUtil.getSysTime());
        ar.setEditFlag("0");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.saveRemark(ar);
        PrintJson.printJsonFlag(response, flag);
    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.deleteRemark(id);
        PrintJson.printJsonFlag(response, flag);
    }

    private void getRemarkList(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<ActivityRemark> remarkList = as.getRemarkList(id);
        PrintJson.printJsonObj(response, remarkList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity a = as.detail(id);
        request.setAttribute("a", a);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        Activity a = new Activity();
        a.setId(request.getParameter("id"));
        a.setOwner(request.getParameter("owner"));
        a.setName(request.getParameter("name"));
        a.setStartDate(request.getParameter("startDate"));
        a.setEndDate(request.getParameter("endDate"));
        a.setCost(request.getParameter("cost"));
        a.setDescription(request.getParameter("description"));
        a.setEditTime(DateTimeUtil.getSysTime());
        a.setEditBy(((User) request.getSession(false).getAttribute("user")).getName());
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.update(a);
        PrintJson.printJsonFlag(response, flag);
    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Map<String, Object> map = as.getUserListAndActivity(id);
        PrintJson.printJsonObj(response, map);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        String[] ids = request.getParameterValues("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.delete(ids);
        PrintJson.printJsonFlag(response, flag);
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        Activity a = new Activity();
        a.setName(request.getParameter("name"));
        a.setOwner(request.getParameter("owner"));
        a.setStartDate(request.getParameter("startDate"));
        a.setEndDate(request.getParameter("endDate"));
        Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
        Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        PaginationVO<Activity> vo = as.pageList(a, pageNo, pageSize);
        PrintJson.printJsonObj(response, vo);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        Activity a = new Activity();
        a.setId(UUIDUtil.getUUID());
        a.setOwner(request.getParameter("owner"));
        a.setName(request.getParameter("name"));
        a.setStartDate(request.getParameter("startDate"));
        a.setEndDate(request.getParameter("endDate"));
        a.setCost(request.getParameter("cost"));
        a.setDescription(request.getParameter("description"));
        a.setCreateTime(DateTimeUtil.getSysTime());
        a.setCreateBy(((User) request.getSession(false).getAttribute("user")).getName());
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.save(a);
        PrintJson.printJsonFlag(response, flag);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> uList = us.getUserList();
        PrintJson.printJsonObj(response, uList);
    }
}
