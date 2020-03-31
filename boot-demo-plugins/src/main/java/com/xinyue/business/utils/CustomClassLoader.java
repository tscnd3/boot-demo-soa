package com.xinyue.business.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.xinyue.business.entity.EntityClass;

public class CustomClassLoader {
	
	/**
	 * 1.自己定义URLClassLoader对象加载外部jar包，
	 * 针对jar包里面不再出现别的jar包的情况，即只解析.class文件：
	 */
	@SuppressWarnings("resource")
	public static Set<EntityClass> getJarClass(String path) {  
		// String path = "D:\\site\\SpringPluginsJar\\SpringCustomPlugin-0.0.1-SNAPSHOT.jar";//外部jar包的路径  
		 Set<EntityClass>entiyClassSet=new LinkedHashSet<>();
		 try {  
		  JarFile jarFile = new JarFile(new File(path));  
		  URL url = new URL("file:" + path);  
		  ClassLoader loader = new URLClassLoader(new URL[]{url});//自己定义的classLoader类，把外部路径也加到load路径里，使系统去该路经load对象  
		  Enumeration<JarEntry> es = jarFile.entries();  
		  while (es.hasMoreElements()) {  
			   JarEntry jarEntry = (JarEntry) es.nextElement();  
			   String name = jarEntry.getName();  
			   if(name != null && name.endsWith(".class")){//只解析了.class文件，没有解析里面的jar包  
				   entiyClassSet.add(getEntityClass(loader,jarEntry));
			   }else if(name != null && name.endsWith(".jar")){
				   File f = new File(name);  
				   JarFile j = new JarFile(f);  
				   Enumeration<JarEntry> e = j.entries();
				   while (e.hasMoreElements()) {
					  jarEntry = (JarEntry) e.nextElement(); 
					  entiyClassSet.add(getEntityClass(loader,jarEntry));
				   }  
			   }
		  }  
		  System.out.println(entiyClassSet.size());  
		 } catch (IOException e) {  
		  e.printStackTrace();  
		 } catch (ClassNotFoundException e) {  
		  e.printStackTrace();  
		 }  
		 return entiyClassSet;
	}
	
	public static EntityClass getEntityClass(ClassLoader loader,JarEntry jarEntry) throws ClassNotFoundException{
		 String name = jarEntry.getName();  
		 EntityClass entiyClass=new EntityClass();  
		    //默认去系统已经定义的路径查找对象，针对外部jar包不能用  
		 Class<?> c = loader.loadClass(name.replace("/", ".").substring(0,name.length() - 6));//自己定义的loader路径可以找到  
		 System.out.println(c); 
		 entiyClass.setClassName(c.getName());
		 entiyClass.setClazz(c);
		 Annotation[] classAnnos = c.getDeclaredAnnotations();  
		 entiyClass.setClassAnnotations(classAnnos);
		 Method[] classMethods = c.getDeclaredMethods();  
		 Map<Method, Annotation[]> methodAnnoMap = new HashMap<Method, Annotation[]>();  
		 for(int i = 0;i<classMethods.length;i++){  
			 Annotation[] a = classMethods[i].getDeclaredAnnotations();  
		     methodAnnoMap.put(classMethods[i], a);  
		 }  
		    entiyClass.setMethodAnnoMap(methodAnnoMap);
		 return entiyClass;
	}
	
	
	
	
	
	
	

	
	public static void main(String[] args) {
	}
}
