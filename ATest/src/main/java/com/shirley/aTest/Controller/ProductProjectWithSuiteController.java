package com.shirley.aTest.Controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shirley.aTest.entity.ProductProjectWithSuite;
import com.shirley.aTest.jsonHelper.PageHelper;
import com.shirley.aTest.service.ProductProjectWithSuiteService;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年8月21日 下午2:46:16
 */
@Controller
public class ProductProjectWithSuiteController {
	@Resource(name = "productProjectWithSuiteService")
	private ProductProjectWithSuiteService productProjectWithSuiteService;

	@RequestMapping(value = "/queryProductProjectWithSuite", method = RequestMethod.GET)
	public String queryProductProjectWithSuite() {
		return "productProjectWithSuiteList";
	}
	
	@RequestMapping(value = "/addProductProjectWithSuite", method = RequestMethod.GET)
	public String addProductProjectWithSuite() {
		return "addProductProjectWithSuite";
	}
	
	@RequestMapping(value = "/toQueryProductProjectWithSuites", method = RequestMethod.GET)
	@ResponseBody
	public PageHelper<ProductProjectWithSuite> toQueryProductProjectWithSuites(Integer pageNumber, Integer pageSize,
			Integer productProjectId, Integer testSuiteId, String testSuiteName) {
		if (null != productProjectId && productProjectId != 0) {
			List<ProductProjectWithSuite> productProjectWithSuites = productProjectWithSuiteService
					.QueryProductProjectWithSuite((null == pageNumber ? 0 : pageNumber),
							(null == pageSize ? 0 : pageSize), productProjectId,
							(null == testSuiteId ? 0 : testSuiteId), testSuiteName);
			PageHelper<ProductProjectWithSuite> pageHelper = new PageHelper<ProductProjectWithSuite>();
			// 统计总记录数
			pageHelper.setTotal(productProjectWithSuiteService.QueryProductProjectWithSuiteCount(productProjectId,
					(null == testSuiteId ? 0 : testSuiteId), testSuiteName));
			pageHelper.setRows(productProjectWithSuites);
			return pageHelper;
		}
		return null;
	}

	@RequestMapping(value = "/toAddProductProjectWithSuite", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toAddProductProjectWithSuite(Integer productProjectId, Integer testSuiteId) {
		if (null != productProjectId && null != testSuiteId) {
			ProductProjectWithSuite productProjectWithSuite = new ProductProjectWithSuite();
			productProjectWithSuite.setProductProjectId(productProjectId);
			productProjectWithSuite.setTestSuiteId(testSuiteId);
			return productProjectWithSuiteService.AddProductProjectWithSuite(productProjectWithSuite);
		}
		return false;
	}

	@RequestMapping(value = "/toDelProductProjectWithSuite", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toDelProductProjectWithSuite(Integer[] ids) {
		List<Integer> idList = Arrays.asList(ids);
		return productProjectWithSuiteService.DeleteProductProjectWithSuite(idList);
	}


}
