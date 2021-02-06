import com.loemin.crm.settings.dao.UserDao;
import com.loemin.crm.settings.domain.User;
import com.loemin.crm.settings.service.UserService;
import com.loemin.crm.settings.service.impl.UserServiceImpl;
import com.loemin.crm.utils.SqlSessionUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class testSw {
    @Test
    public void login() {
        Map<String, String> map = new HashMap<>();
        map.put("loginAct","asd");
        map.put("loginPwd","aaa");
        UserDao dao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
        User login = dao.login(map);
        System.out.println(login);
    }
}
