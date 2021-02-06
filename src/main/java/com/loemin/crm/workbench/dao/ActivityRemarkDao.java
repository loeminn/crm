package com.loemin.crm.workbench.dao;

import com.loemin.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkListById(String id);

    int deleteById(String id);

    int save(ActivityRemark ar);

    int update(ActivityRemark ar);

    ActivityRemark getById(String id);
}
