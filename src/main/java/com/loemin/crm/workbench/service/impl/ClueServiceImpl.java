package com.loemin.crm.workbench.service.impl;

import com.loemin.crm.utils.SqlSessionUtil;
import com.loemin.crm.workbench.dao.ClueDao;
import com.loemin.crm.workbench.service.ClueService;

public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
}
