package com.shirley.aTest.Controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shirley.aTest.entity.ProductProject;
import com.shirley.aTest.jsonHelper.PageHelper;
import com.shirley.aTest.service.ProductProjectService;

/**
 * @Description: TODO(环境控制类)
 * @author 371683941@qq.com
 * @date 2019年6月15日 下午9:24:25
 */
@Controller
public class ProductProjectController {
	@Resource(name = "productProjectService")
	private ProductProjectService productProjectService;

	@RequestMapping(value = "/productProjectList", method = RequestMethod.GET)
	public String productProjectList() {
		return "productProjectList";
	}

	@RequestMapping(value = "/addProductProject", method = RequestMethod.GET)
	public String addProductProject() {
		return "addProductProject";
	}

	@RequestMapping(value = "/queryProductProject", method = RequestMethod.GET)
	public String queryProductProject() {
		return "productProjectDetail";
	}

	@RequestMapping(value = "/queryProductProjects", method = RequestMethod.GET)
	@ResponseBody
	public PageHelper<ProductProject> queryProductProjects(Integer pageNumber, Integer pageSize, String name) {
		List<ProductProject> productProjects = productProjectService.QueryProductProject((null == pageNumber ? 0 : pageNumber),
				(null == pageSize ? 0 : pageSize), name);
		PageHelper<ProductProject> pageHelper = new PageHelper<ProductProject>();
		// 统计总记录数
		pageHelper.setTotal(productProjectService.QueryProductProjectCount(name));
		pageHelper.setRows(productProjects);
		return pageHelper;
	}

	@RequestMapping(value = "/toAddProductProject", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toAddProductProject(String name, String description) {
		if (null != name && !"".equals(name)) {
			ProductProject productProject = new ProductProject();
			productProject.setName(name);
			productProject.setDescription(description);
			return productProjectService.AddProductProject(productProject);
		}
		return false;
	}

	@RequestMapping(value = "/toDelProductProject", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toDelProductProject(Integer[] ids) {
		List<Integer> idList = Arrays.asList(ids);
		return productProjectService.DeleteProductProjects(idList);
	}

	@RequestMapping(value = "/toQueryProductProject", method = RequestMethod.POST)
	@ResponseBody
	public ProductProject toQueryProductProject(Integer id) {
		if (null != id)
			return productProjectService.QueryProductProjectById(id);
		return null;
	}

	@RequestMapping(value = "/updateProductProject", method = RequestMethod.POST)
	@ResponseBody
	public Boolean updateProductProject(Integer id, String name, String description) {
		if (null != name && null != id && !"".equals(name)) {
			ProductProject productProject = new ProductProject();
			productProject.setId(id);
			productProject.setName(name);
			productProject.setDescription(description);
			return productProjectService.UpdateProductProject(productProject);
		}
		return false;
	}

}
