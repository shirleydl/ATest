package com.shirley.aTest.Controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shirley.aTest.entity.Interface;
import com.shirley.aTest.jsonHelper.BigAutocompleteDataHelper;
import com.shirley.aTest.jsonHelper.PageHelper;
import com.shirley.aTest.service.InterfaceService;

/**
 * @Description: TODO(接口控制类)
 * @author 371683941@qq.com
 * @date 2019年6月20日 下午1:56:26
 */

@Controller
public class InterfaceController {
	@Resource(name = "interfaceService")
	private InterfaceService interfaceService;

	@RequestMapping(value = "/interfaceList", method = RequestMethod.GET)
	public String interfaceList() {
		return "interfaceList";
	}

	@RequestMapping(value = "/addInterface", method = RequestMethod.GET)
	public String addInterface() {
		return "addInterface";
	}

	@RequestMapping(value = "/queryInterface", method = RequestMethod.GET)
	public String queryInterface() {
		return "interfaceDetail";
	}

	@RequestMapping(value = "/queryInterfaces", method = RequestMethod.GET)
	@ResponseBody
	public PageHelper<Interface> queryInterfaces(Integer pageNumber, Integer pageSize, String name, String api,
			String environmentName) {
		List<Interface> interfaces = interfaceService.QueryInterface((null == pageNumber ? 0 : pageNumber),
				(null == pageSize ? 0 : pageSize), name, api, environmentName);
		PageHelper<Interface> pageHelper = new PageHelper<Interface>();
		// 统计总记录数
		pageHelper.setTotal(interfaceService.QueryInterfaceCount(name, api, environmentName));
		pageHelper.setRows(interfaces);
		return pageHelper;
	}

	@RequestMapping(value = "/queryInterfacesById", method = RequestMethod.POST)
	@ResponseBody
	public BigAutocompleteDataHelper<Interface> queryInterfaceById(String keyword) {
		if (keyword.matches("[0-9]+") && Integer.parseInt(keyword) > 0) {
		List<Interface> interfaces = interfaceService.QueryInterfaces(1, 10, Integer.parseInt(keyword), null, null);
		BigAutocompleteDataHelper<Interface> jsonHelper = new BigAutocompleteDataHelper<Interface>();
		jsonHelper.setData(interfaces);
		return jsonHelper;
		}
		return null;

	}

	@RequestMapping(value = "/queryInterfacesByName", method = RequestMethod.POST)
	@ResponseBody
	public BigAutocompleteDataHelper<Interface> queryInterfaceByName(String keyword) {
		List<Interface> interfaces = interfaceService.QueryInterfaces(1, 10, 0, keyword, null);
		BigAutocompleteDataHelper<Interface> jsonHelper = new BigAutocompleteDataHelper<Interface>();
		jsonHelper.setData(interfaces);
		return jsonHelper;
	}

	@RequestMapping(value = "/queryInterfacesByApi", method = RequestMethod.POST)
	@ResponseBody
	public BigAutocompleteDataHelper<Interface> queryInterfacesByApi(String keyword) {
		List<Interface> interfaces = interfaceService.QueryInterfaces(1, 10, 0, null, keyword);
		BigAutocompleteDataHelper<Interface> jsonHelper = new BigAutocompleteDataHelper<Interface>();
		jsonHelper.setData(interfaces);
		return jsonHelper;
	}

	@RequestMapping(value = "/toAddInterface", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toAddInterface(String name, String api, Integer environmentId, String description) {
		if (null != name && !"".equals(name) && null != api && !"".equals(api) && null != environmentId) {
			Interface interfaceObject = new Interface();
			interfaceObject.setName(name);
			interfaceObject.setApi(api);
			interfaceObject.setDescription(description);
			interfaceObject.setEnvironmentId(environmentId);
			return interfaceService.AddInterface(interfaceObject);
		}
		return false;
	}

	@RequestMapping(value = "/toDelInterface", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toDelInterface(Integer[] ids) {
		List<Integer> idList = Arrays.asList(ids);
		return interfaceService.DeleteInterfaces(idList);
	}

	@RequestMapping(value = "/toQueryInterface", method = RequestMethod.POST)
	@ResponseBody
	public Interface toQueryInterface(Integer id) {
		if (null != id)
			return interfaceService.QueryInterfaceById(id);
		return null;
	}

	@RequestMapping(value = "/toUpdateInterface", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toUpdateInterface(Integer interfaceId, String name, String api, Integer environmentId,
			String description) {
		if (null != name && !"".equals(name) && null != api && !"".equals(api) && null != environmentId
				&& null != interfaceId) {
			Interface interfaceObject = new Interface();
			interfaceObject.setId(interfaceId);
			interfaceObject.setName(name);
			interfaceObject.setApi(api);
			interfaceObject.setDescription(description);
			interfaceObject.setEnvironmentId(environmentId);
			return interfaceService.UpdateInterface(interfaceObject);
		}
		return false;
	}
}
