package com.shirley.aTestActuator.method;

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.shirley.aTestActuator.dao.AssertsDAO;
import com.shirley.aTestActuator.dao.TaskDAO;
import com.shirley.aTestActuator.entity.DoTaskId;

/**
 * @Description: TODO(单独执行类)
 * @author 371683941@qq.com
 * @date 2019年7月11日 下午4:52:46
 */
public class DoTask {
	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicantContent.xml");
	private static JdbcTemplate jdbcTemplate = ctx.getBean("jdbcTemplate", JdbcTemplate.class);

	public static void main(String[] args) {
		if (args.length > 0) {
			TaskDAO taskDao = new TaskDAO();
			AssertsDAO assertsDAO = new AssertsDAO();
			taskDao.setJdbcTemplate(jdbcTemplate);
			assertsDAO.setJdbcTemplate(jdbcTemplate);
			List<DoTaskId> doTaskIds = taskDao.QueryByTaskName(args[0]);
			if (null != doTaskIds && doTaskIds.size() > 0) {
				Date date = new Date();
				System.out.println("=====start " + date + "=====");
				for (DoTaskId doTaskId : doTaskIds) {
					if (taskDao.UpdateTaskStatusFromName(doTaskId.getId(), 2)) {
						assertsDAO.DeleteAssertsByTaskId(doTaskId.getId());
						DoQueryTestSuite doQueryTestSuite = new DoQueryTestSuite(taskDao, doTaskId, jdbcTemplate);
						new Thread(doQueryTestSuite).start();
					}
				}
			}
		} else
			System.out.println("请输入任务名！");
	}
}
