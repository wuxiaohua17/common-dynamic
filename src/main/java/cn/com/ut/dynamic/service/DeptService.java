package cn.com.ut.dynamic.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeptService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void addDept(Map<String, Object> dept) {

		jdbcTemplate.update("insert into t_app_dept(dept_id, dept_name, dept_parent) values (?, ?, ?)",
				new Object[] { dept.get("dept_id"), dept.get("dept_name"), dept.get("dept_parent") });
	}
}
