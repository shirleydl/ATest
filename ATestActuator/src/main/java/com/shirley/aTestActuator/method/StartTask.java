package com.shirley.aTestActuator.method;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.shirley.aTestActuator.dao.AssertsDAO;
import com.shirley.aTestActuator.dao.TaskDAO;
import com.shirley.aTestActuator.entity.DoTaskId;

/**
 * @Description: TODO(定时任务触发类)
 * @author 371683941@qq.com
 * @date 2019年7月11日 下午4:52:46
 */
public class StartTask {
	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicantContent.xml");
	private static JdbcTemplate jdbcTemplate = ctx.getBean("jdbcTemplate", JdbcTemplate.class);

	public static void main(String[] args) {
		final TaskDAO taskDao = new TaskDAO();
		final AssertsDAO assertsDAO = new AssertsDAO();
		taskDao.setJdbcTemplate(jdbcTemplate);
		assertsDAO.setJdbcTemplate(jdbcTemplate);
		Runnable runnable = new Runnable() {
			public void run() {
				// task to run goes here
				List<DoTaskId> doTaskIds = taskDao.QueryDoTaskId();
				if (null != doTaskIds && doTaskIds.size() > 0) {
					Date date = new Date();
					System.out.println("=====start " + date + "=====");
					for (DoTaskId doTaskId : doTaskIds) {
						if (taskDao.UpdateTaskStatus(doTaskId.getId(), 2)) {
							assertsDAO.DeleteAssertsByTaskId(doTaskId.getId());
							DoQueryTestSuite doQueryTestSuite = new DoQueryTestSuite(taskDao, doTaskId, jdbcTemplate);
							new Thread(doQueryTestSuite).start();
						}
					}

				}
			}
		};
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(runnable, 1, 600, TimeUnit.SECONDS);
	}
}
