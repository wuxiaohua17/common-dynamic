package cn.com.ut.dynamic.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.com.ut.dynamic.AppDBContextHolder;
import cn.com.ut.dynamic.service.EmpService;

@RestController
@RequestMapping(value = "/user")
public class EmpController {

	@Autowired
	private EmpService empService;

	@RequestMapping(value = "/addUser")
	public void addUser(@RequestParam Map<String, Object> empAndDept) {

		String appDBKey = (String) empAndDept.get("appDBKey");
		AppDBContextHolder.setAppDB(appDBKey);

		empService.addEmpAndDept(empAndDept);
	}

	@RequestMapping(value = "/getUser")
	public Map<String, Object> getUser(@RequestParam Map<String, String> vo) {

		String appDBKey = (String) vo.get("appDBKey");
		AppDBContextHolder.setAppDB(appDBKey);
		return empService.getUser(vo.get("user_id"));
	}

}
