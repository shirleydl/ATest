package com.shirley.aTest.Controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.shirley.aTest.entity.Mock;
import com.shirley.aTest.jsonHelper.BigAutocompleteDataHelper;
import com.shirley.aTest.jsonHelper.PageHelper;
import com.shirley.aTest.service.MockService;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年9月4日 下午2:30:34
 */
@Controller
public class MockController {
	@Resource(name = "mockService")
	private MockService mockService;

	@RequestMapping(value = "/mockList", method = RequestMethod.GET)
	public String mockList() {
		return "mockList";
	}

	@RequestMapping(value = "/addMock", method = RequestMethod.GET)
	public String addMock() {
		return "addMock";
	}

	@RequestMapping(value = "/queryMock", method = RequestMethod.GET)
	public String queryMock() {
		return "mockDetail";
	}

	@RequestMapping(value = "/queryMocks", method = RequestMethod.GET)
	@ResponseBody
	public PageHelper<Mock> queryMocks(Integer pageNumber, Integer pageSize, Integer id, String name) {
		List<Mock> mocks = mockService.QueryMocks((null == pageNumber ? 0 : pageNumber),
				(null == pageSize ? 0 : pageSize), (null == id ? 0 : id), name);
		PageHelper<Mock> pageHelper = new PageHelper<Mock>();
		// 统计总记录数
		pageHelper.setTotal(mockService.QueryMockCount((null == id ? 0 : id), name));
		pageHelper.setRows(mocks);
		return pageHelper;
	}

	@RequestMapping(value = "/toAddMock", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toAddMock(String name, String bindVariables) {
		if (null != name && !"".equals(name)) {
			try {
				Map<String, String> bindVariableMocksMap = new HashMap<String, String>();
				Gson gson = new Gson();
				bindVariableMocksMap = gson.fromJson(bindVariables, bindVariableMocksMap.getClass());

				Mock mock = new Mock();
				mock.setName(name);
				mock.setBindVariableMocks(bindVariableMocksMap);
				return mockService.AddMock(mock);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	@RequestMapping(value = "/toDelMock", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toDelMock(Integer[] ids) {
		List<Integer> idList = Arrays.asList(ids);
		return mockService.DeleteMocks(idList);
	}

	@RequestMapping(value = "/toQueryMock", method = RequestMethod.POST)
	@ResponseBody
	public Mock toQueryMock(Integer id) {
		if (null != id) {
			return mockService.QueryMockById(id);

		}
		return null;
	}

	@RequestMapping(value = "/toUpdateMock", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toUpdateMock(Integer id, String name, String bindVariables) {
		if (null != name && !"".equals(name) && null != id) {
			try {
				Map<String, String> bindVariableMocksMap = new HashMap<String, String>();
				Gson gson = new Gson();
				bindVariableMocksMap = gson.fromJson(bindVariables, bindVariableMocksMap.getClass());

				Mock mock = new Mock();
				mock.setId(id);
				mock.setName(name);
				mock.setBindVariableMocks(bindVariableMocksMap);
				return mockService.UpdateMock(mock);
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	@RequestMapping(value = "/queryMockById", method = RequestMethod.POST)
	@ResponseBody
	public BigAutocompleteDataHelper<Mock> queryMockById(String keyword) {
		if (keyword.matches("[0-9]+") && Integer.parseInt(keyword) > 0) {
			List<Mock> mocks = mockService.QueryMocks(0, 0, Integer.parseInt(keyword), null);
			BigAutocompleteDataHelper<Mock> jsonHelper = new BigAutocompleteDataHelper<Mock>();
			jsonHelper.setData(mocks);
			return jsonHelper;
		}
		return null;
	}

	@RequestMapping(value = "/queryMockByName", method = RequestMethod.POST)
	@ResponseBody
	public BigAutocompleteDataHelper<Mock> queryMockByName(String keyword) {
		List<Mock> mocks = mockService.QueryMocks(0, 0, 0, keyword);
		BigAutocompleteDataHelper<Mock> jsonHelper = new BigAutocompleteDataHelper<Mock>();
		jsonHelper.setData(mocks);
		return jsonHelper;
	}
}
