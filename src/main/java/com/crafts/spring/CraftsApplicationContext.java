package com.crafts.spring;

import com.crafts.spring.annotation.ComponentScan;
import com.crafts.spring.banner.Banner;
import com.crafts.spring.banner.SpringLogoBanner;
import com.crafts.spring.exception.NotFoundAnnotation;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CraftsApplicationContext {

	private Class configClass;

	private static List<Banner> banners = new ArrayList<>();

	static {
		banners.add(new SpringLogoBanner());
	}

	public CraftsApplicationContext(Class configClass) {
		banners.forEach(Banner::showBanner);

		this.configClass = configClass;
		// parsing config
		String packageName;
		if(configClass.isAnnotationPresent(ComponentScan.class)){
			ComponentScan componentScan = (ComponentScan) configClass.getDeclaredAnnotation(ComponentScan.class);
			packageName = componentScan.value();
		}else{
			throw new NotFoundAnnotation(ComponentScan.class.getName());
		}
		System.out.println("scan package path: " + packageName);
		// 类加载器
		// 1. BootStrapClassLoader jre/lib
		// 2. ExtClassLoader jre/ext/lib
		// 3. AppClassLoader classpath
		// E:\develop\Java\jdk-17.0.1\bin\java.exe "-javaagent:E:\develop\IntelliJ IDEA 2024.1.1\lib\idea_rt.jar=49077:E:\develop\IntelliJ IDEA 2024.1.1\bin" -Dfile.encoding=UTF-8
		// -classpath D:\java\crafts\spring-crafts\target\classes com.wangsl.Application
		ClassLoader classLoader = CraftsApplicationContext.class.getClassLoader();
		URL resource = classLoader.getResource("com/wangsl/service");
		File file = new File(resource.getFile());
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for(File f : files){
				String filename = f.getAbsolutePath();
				if(filename.endsWith(".class")){
					System.out.println(filename);
					String classname = filename.substring(filename.indexOf("com"), filename.indexOf(".class"))
							.replace("\\", ".");
					System.out.println(classname);
				}
			}
		}
	}

	public Object getBean(String beanName) {
		return null;
	}
}
