package com.loemin.crm.workbench.dao;

import com.loemin.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int save(Activity a);

    int getTotal(Activity a);

    List<Activity> getActivityListByCondition(Activity a);

    int delete(String[] ids);

    Activity getById(String id);

    int update(Activity a);

    Activity detail(String id);
}
