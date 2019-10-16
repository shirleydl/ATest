package com.shirley.aTest.Controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.shirley.aTest.entity.Replace;
import com.shirley.aTest.jsonHelper.BigAutocompleteDataHelper;
import com.shirley.aTest.jsonHelper.PageHelper;
import com.shirley.aTest.service.ReplaceService;

/**
 * @Description: TODO(替换控制类)
 * @author 371683941@qq.com
 * @date 2019年8月14日 上午10:08:37
 */
@Controller
public class ReplaceController {
	@Resource(name = "replaceService")
	private ReplaceService replaceService;

	@RequestMapping(value = "/replaceList", method = RequestMethod.GET)
	public String replaceList() {
		return "replaceList";
	}

	@RequestMapping(value = "/addReplace", method = RequestMethod.GET)
	public String addReplace() {
		return "addReplace";
	}

	@RequestMapping(value = "/queryReplace", method = RequestMethod.GET)
	public String queryReplace() {
		return "replaceDetail";
	}

	@RequestMapping(value = "/queryReplaces", method = RequestMethod.GET)
	@ResponseBody
	public PageHelper<Replace> queryReplaces(Integer pageNumber, Integer pageSize, Integer id, String name) {
		List<Replace> replaces = replaceService.QueryReplaces((null == pageNumber ? 0 : pageNumber),
				(null == pageSize ? 0 : pageSize), (null == id ? 0 : id), name);
		PageHelper<Replace> pageHelper = new PageHelper<Replace>();
		// 统计总记录数
		pageHelper.setTotal(replaceService.QueryReplaceCount((null == id ? 0 : id), name));
		pageHelper.setRows(replaces);
		return pageHelper;
	}

	@RequestMapping(value = "/toAddReplace", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toAddReplace(String name, String description, String replaceUrl, String replaceData, String split) {
		if (null != name && !"".equals(name)) {
			try {
				Map<String, Object> replaceDataJsonMap = new HashMap<String, Object>();
				Map<String, Object> replaceDataMap = new HashMap<String, Object>();
				Map<String, String> replaceUrlMap = new HashMap<String, String>();
				Gson gson = new Gson();
				replaceUrlMap = gson.fromJson(replaceUrl, replaceUrlMap.getClass());
				if (null != replaceData && !"".equals(replaceData)) {
					replaceDataJsonMap = gson.fromJson(replaceData, replaceDataJsonMap.getClass());
					for (Entry<String, Object> e : replaceDataJsonMap.entrySet()) {
						if (e.getValue() instanceof Map) {
							replaceDataMap.put(e.getKey(), e.getValue());
						}
					}
				}
				Replace replace = new Replace();
				replace.setName(name);
				replace.setDescription(description);
				replace.setReplaceUrl(replaceUrlMap);
				replace.setReplaceData(replaceDataMap);
				replace.setSplit(split);
				return replaceService.AddReplace(replace);
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	@RequestMapping(value = "/toDelReplace", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toDelReplace(Integer[] ids) {
		List<Integer> idList = Arrays.asList(ids);
		return replaceService.DeleteReplaces(idList);
	}

	@RequestMapping(value = "/toQueryReplace", method = RequestMethod.POST)
	@ResponseBody
	public Replace toQueryReplace(Integer id) {
		if (null != id) {
			return replaceService.QueryReplaceById(id);

		}
		return null;
	}

	@RequestMapping(value = "/toUpdateReplace", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toUpdateReplace(Integer id, String name, String description, String replaceUrl, String replaceData, String split) {
		if (null != name && !"".equals(name) && null != id) {
			try {
				Map<String, Object> replaceDataJsonMap = new HashMap<String, Object>();
				Map<String, Object> replaceDataMap = new HashMap<String, Object>();
				Map<String, String> replaceUrlMap = new HashMap<String, String>();
				Gson gson = new Gson();
				replaceUrlMap = gson.fromJson(replaceUrl, replaceUrlMap.getClass());
				if (null != replaceData && !"".equals(replaceData)) {
					replaceDataJsonMap = gson.fromJson(replaceData, replaceDataJsonMap.getClass());
					for (Entry<String, Object> e : replaceDataJsonMap.entrySet()) {
						if (e.getValue() instanceof Map) {
							replaceDataMap.put(e.getKey(), e.getValue());
						}
					}
				}
				Replace replace = new Replace();
				replace.setId(id);
				replace.setName(name);
				replace.setDescription(description);
				replace.setReplaceUrl(replaceUrlMap);
				replace.setReplaceData(replaceDataMap);
				replace.setSplit(split);
				return replaceService.UpdateReplace(replace);
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	@RequestMapping(value = "/queryReplaceById", method = RequestMethod.POST)
	@ResponseBody
	public BigAutocompleteDataHelper<Replace> queryReplaceById(String keyword) {
		if (keyword.matches("[0-9]+") && Integer.parseInt(keyword) > 0) {
			List<Replace> replaces = replaceService.QueryReplaces(0, 0, Integer.parseInt(keyword), null);
			BigAutocompleteDataHelper<Replace> jsonHelper = new BigAutocompleteDataHelper<Replace>();
			jsonHelper.setData(replaces);
			return jsonHelper;
		}
		return null;
	}

	@RequestMapping(value = "/queryReplaceByName", method = RequestMethod.POST)
	@ResponseBody
	public BigAutocompleteDataHelper<Replace> queryReplaceByName(String keyword) {
		List<Replace> replaces = replaceService.QueryReplaces(0, 0, 0, keyword);
		BigAutocompleteDataHelper<Replace> jsonHelper = new BigAutocompleteDataHelper<Replace>();
		jsonHelper.setData(replaces);
		return jsonHelper;
	}

}
