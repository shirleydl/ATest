package com.shirley.aTestActuator.method;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.google.gson.Gson;
import com.shirley.aTestActuator.dao.AssertsDAO;
import com.shirley.aTestActuator.dao.EmailDAO;
import com.shirley.aTestActuator.entity.AssertResult;
import com.shirley.aTestActuator.entity.Email;
import com.shirley.aTestActuator.util.MakeReportHtml;
import com.shirley.aTestActuator.util.SendMailAcceUtils;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年11月1日 下午5:38:27
 */
public class SendReport {
	private int taskId;
	private int emailId;
	private String taskName;
	private AssertsDAO assertsDAO;
	private EmailDAO emailDAO;

	public SendReport(int taskId, int emailId, String taskName, JdbcTemplate jdbcTemplate) {
		this.taskId = taskId;
		this.emailId = emailId;
		this.taskName = taskName;
		assertsDAO = new AssertsDAO();
		emailDAO = new EmailDAO();
		assertsDAO.setJdbcTemplate(jdbcTemplate);
		emailDAO.setJdbcTemplate(jdbcTemplate);

	}

	public void toDo() {
		Gson gson = new Gson();
		List<AssertResult> assertResult = new ArrayList<AssertResult>();
		assertResult = assertsDAO.QueryAsserts(taskId);
		int allCount = assertResult.size();
		int successCount = assertsDAO.QueryAssertsCount(taskId, "success");
		String assertsJson = gson.toJson(assertResult);
		String fileName = "任务序号" + taskId + "：" + taskName + "接口测试报告";
		String dirName = ".//report//" + taskId;
		String content = "您好，以下是测试任务" + taskId + taskName + "报告：\n总用例数：" + allCount + "\n成功用例数：" + successCount
				+ "\n失败用例数：" + (allCount - successCount) + "\n详细可下载附件或访问平台查看！";

		if (MakeReportHtml.MakeJsonFile(dirName, "asserts", "let asserts=" + assertsJson)
				&& MakeReportHtml.MakeHtml(dirName, fileName)
				&& MakeReportHtml.copyFileScript("report/bootstrap.min.css", dirName + "//bootstrap.min.css")
				&& MakeReportHtml.copyFileScript("report/bootstrap.min.js", dirName + "//bootstrap.min.js")
				&& MakeReportHtml.copyFileScript("report/jquery.min.js", dirName + "//jquery.min.js")
				&& MakeReportHtml.fileToZip(dirName, ".//report", fileName)) {
			try {
				Email email = emailDAO.QueryEmailById(emailId);
				if (SendMailAcceUtils.sendMail(email.getFrom(), email.getPass(), email.getHost(), email.getReceives(),
						"ATest" + fileName, content, ".//report//" + fileName + ".zip",
						"任务序号" + taskId + "接口测试报告" + ".zip")) {
					File dir = new File(dirName);
					File zip = new File(".//report//" + fileName + ".zip");
					MakeReportHtml.delFile(dir);
					MakeReportHtml.delFile(zip);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
