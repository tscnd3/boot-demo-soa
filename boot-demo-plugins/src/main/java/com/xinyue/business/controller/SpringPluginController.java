package com.xinyue.business.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xinyue.business.core.SpringPluginFactory;
import com.xinyue.business.entity.PluginConfig;
import com.xinyue.business.entity.PluginSite;
import com.xinyue.framework.util.ObjectUtil;

@Controller
@RequestMapping("springPlugin")
public class SpringPluginController extends BaseController {

	public static final Logger logger = LoggerFactory
			.getLogger(SpringPluginController.class);

	@Autowired
	private SpringPluginFactory springPluginFactory;

	@ResponseBody
	@RequestMapping("/active")
	public Map<String, Object> activePlugin(
			@RequestParam Map<String, Object> param) {
		Map<String, Object> result = new HashMap<>();
		try {
			if (ObjectUtil.checkObject(new String[] { "id" }, param)) {
				String id = param.get("id").toString();
				springPluginFactory.activePlugin(id);
				result.put("code", 200);
				result.put("message", "success");
			} else {
				result.put("code", 601);
				result.put("message", "缺少参数");
			}
		} catch (Exception e) {
			result.put("code", 500);
			result.put("message", "error");
			logger.error("激活插件异常->", e);
		}
		return result;
	}

	/**
	 * 获取已安装插件列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getHavePluginList")
	public Map<String, Object> getHavePlugins(
			@RequestParam Map<String, Object> param) {
		Map<String, Object> result = new HashMap<>();
		try {
			List<PluginConfig> pluginList = springPluginFactory
					.getPluginList();
			result.put("code", 200);
			result.put("message", "success");
			result.put("data", pluginList);
		} catch (Exception e) {
			result.put("code", 500);
			result.put("message", "error");
			logger.error("获取已安装插件列表异常->", e);
		}
		return result;
	}

	/**
	 * 禁用指定插件
	 * 
	 * @param req
	 * @param resp
	 */
	@ResponseBody
	@RequestMapping("/disable")
	public Map<String, Object> disablePlugin(
			@RequestParam Map<String, Object> param) {
		Map<String, Object> result = new HashMap<>();
		try {
			if (ObjectUtil.checkObject(new String[] { "id" }, param)) {
				String id = param.get("id").toString();
				springPluginFactory.disablePlugin(id);
				result.put("code", 200);
				result.put("message", "success");
			} else {
				result.put("code", 601);
				result.put("message", "缺少参数");
			}
		} catch (Exception e) {
			result.put("code", 500);
			result.put("message", "error");
			logger.error("禁用指定插件异常->", e);
		}
		return result;
	}

	// 安装指定插件
	@ResponseBody
	@RequestMapping("/install")
	public Map<String, Object> installPlugin(@RequestParam Map<String, Object> param) {
		Map<String, Object> result = new HashMap<>();
		try {
			if (ObjectUtil.checkObject(new String[] { "url" }, param)) {
				PluginSite site = getSite(param.get("url").toString());
				String id = param.get("id").toString();
				boolean sucess = false;
				for (PluginConfig config : site.getConfigs()) {
					if (config.getId().equals(id)) {
						springPluginFactory.installPlugin(config, true);
						sucess = true;
						break;
					}
				}
				if (sucess) {
					result.put("code", 200);
					result.put("message", "success");
				} else {
					result.put("code", 400);
					result.put("message", "fail");
				}
			} else {
				result.put("code", 601);
				result.put("message", "缺少参数");
			}
		} catch (Exception e) {
			result.put("code", 500);
			result.put("message", "error");
			logger.error("禁用指定插件异常->", e);
		}
		return result;
	}

	/**
	 * 打开插件站点
	 * 
	 * @param req
	 * @param resp
	 */
//	@ResponseBody
//	@RequestMapping("/openPluginSite")
//	public void openPluginSite(@RequestParam Map<String, Object> paramp) {
//		try {
//			PluginSite site = getSite(req.getParameter("url"));
//			req.setAttribute("site", site);
//			req.setAttribute("siteUrl", req.getParameter("url"));
//			req.getRequestDispatcher("/pluginSite.jsp").forward(req, resp);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	private PluginSite getSite(String urlValue) throws Exception {
		URL url = new URL(urlValue);
		InputStream input = url.openStream();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output);
		String jsonValue = output.toString("UTF-8");
		return JSON.parseObject(jsonValue, PluginSite.class);
	}

	private static long copy(InputStream source, OutputStream sink)
			throws IOException {
		long nread = 0L;
		byte[] buf = new byte[8192];
		int n;
		while ((n = source.read(buf)) > 0) {
			sink.write(buf, 0, n);
			nread += n;
		}
		return nread;
	}

}
