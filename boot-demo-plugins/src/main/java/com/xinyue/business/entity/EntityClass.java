package com.xinyue.business.entity;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class EntityClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 731358794065185766L;
	
	private Class<?> clazz;
	
	private String className;
	
	private Annotation[] classAnnotations;
	
	private Map<Method, Annotation[]> methodAnnoMap;

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Annotation[] getClassAnnotations() {
		return classAnnotations;
	}

	public void setClassAnnotations(Annotation[] classAnnotations) {
		this.classAnnotations = classAnnotations;
	}

	public Map<Method, Annotation[]> getMethodAnnoMap() {
		return methodAnnoMap;
	}

	public void setMethodAnnoMap(Map<Method, Annotation[]> methodAnnoMap) {
		this.methodAnnoMap = methodAnnoMap;
	}

	
	
	
	
}
