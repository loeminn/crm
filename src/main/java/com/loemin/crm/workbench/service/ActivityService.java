package com.loemin.crm.workbench.service;

import com.loemin.crm.vo.PaginationVO;
import com.loemin.crm.workbench.domain.Activity;
import com.loemin.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    boolean save(Activity a);

    PaginationVO<Activity> pageList(Activity a, Integer pageNo, Integer pageSize);

    boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    boolean update(Activity a);

    Activity detail(String id);

    List<ActivityRemark> getRemarkList(String id);

    boolean deleteRemark(String id);

    boolean saveRemark(ActivityRemark ar);

    boolean updateRemark(ActivityRemark ar);

    ActivityRemark getRemarkById(String id);
}
