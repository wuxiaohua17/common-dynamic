package cn.com.ut.dynamic.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmpService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private DeptService deptService;

	public void addUser(Map<String, Object> user) {

		user.put("user_age", Integer.parseInt((String) user.get("user_age")));
		jdbcTemplate.update(
				"insert into t_app_emp(user_id, user_name, user_age, dept_id) values (?, ?, ?, ?)",
				new Object[] { user.get("user_id"), user.get("user_name"), user.get("user_age"),
						user.get("dept_id") });
	}

	public Map<String, Object> getUser(String userId) {

		return jdbcTemplate.queryForMap("select * from t_app_emp where user_id = ?", userId);
	}

	public void addEmpAndDept(Map<String, Object> vo) {

		addUser(vo);
		deptService.addDept(vo);
	}

}
