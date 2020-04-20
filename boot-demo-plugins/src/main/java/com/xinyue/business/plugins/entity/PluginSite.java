package com.xinyue.business.plugins.entity;

import java.util.List;

public class PluginSite implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4322228529932799400L;
	
	private String name;// 站点名称
	private List<PluginConfig> configs;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PluginConfig> getConfigs() {
		return configs;
	}

	public void setConfigs(List<PluginConfig> configs) {
		this.configs = configs;
	}

}
