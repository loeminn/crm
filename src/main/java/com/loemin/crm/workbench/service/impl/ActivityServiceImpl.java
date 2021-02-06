package com.loemin.crm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.loemin.crm.settings.dao.UserDao;
import com.loemin.crm.settings.domain.User;
import com.loemin.crm.utils.SqlSessionUtil;
import com.loemin.crm.vo.PaginationVO;
import com.loemin.crm.workbench.dao.ActivityDao;
import com.loemin.crm.workbench.dao.ActivityRemarkDao;
import com.loemin.crm.workbench.domain.Activity;
import com.loemin.crm.workbench.domain.ActivityRemark;
import com.loemin.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public boolean save(Activity a) {
        int save = activityDao.save(a);
        boolean flag = true;
        if (save != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public PaginationVO<Activity> pageList(Activity a, Integer pageNo, Integer pageSize) {
        int total = activityDao.getTotal(a);
        PageHelper.startPage(pageNo, pageSize);
        List<Activity> dataList = activityDao.getActivityListByCondition(a);
        PaginationVO<Activity> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    public boolean delete(String[] ids) {
        boolean flag = true;
        int count1 = activityRemarkDao.getCountByAids(ids);
        int count2 = activityRemarkDao.deleteByAids(ids);
        if (count1 != count2) {
            flag = false;
        }
        int delete = activityDao.delete(ids);
        if (delete != ids.length) {
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        Map<String, Object> map = new HashMap<>();
        List<User> uList = userDao.getUserList();
        Activity a = activityDao.getById(id);
        map.put("uList", uList);
        map.put("a", a);
        return map;
    }

    @Override
    public boolean update(Activity a) {
        boolean flag = true;
        int update = activityDao.update(a);
        if (update != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {
        return activityDao.detail(id);
    }

    @Override
    public List<ActivityRemark> getRemarkList(String id) {
        return activityRemarkDao.getRemarkListById(id);
    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag = true;
        int delete = activityRemarkDao.deleteById(id);
        if (delete != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark ar) {
        boolean flag = true;
        int save = activityRemarkDao.save(ar);
        if (save != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateRemark(ActivityRemark ar) {
        boolean flag = true;
        int update = activityRemarkDao.update(ar);
        if (update != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public ActivityRemark getRemarkById(String id) {
        return activityRemarkDao.getById(id);
    }
}

