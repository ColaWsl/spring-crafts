package com.crafts.spring;

import com.crafts.spring.annotation.Component;
import com.crafts.spring.annotation.ComponentScan;
import com.crafts.spring.annotation.Scope;
import com.crafts.spring.banner.Banner;
import com.crafts.spring.banner.SpringLogoBanner;
import com.crafts.spring.exception.NoExistsBeanException;
import com.crafts.spring.exception.NotFoundAnnotation;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CraftsApplicationContext {

	// 配置类
	private Class configClass;

	private static List<Banner> banners = new ArrayList<>();

	// 单例池
	private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();

	// Bean 定义
	private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

	static {
		banners.add(new SpringLogoBanner());
	}

	public CraftsApplicationContext(Class configClass) {
		this.configClass = configClass;

		scan(configClass);

		printBanner();
	}

	private void scan(Class configClass) {
		// parsing config
		String packageName;
		if (configClass.isAnnotationPresent(ComponentScan.class)) {
			ComponentScan componentScan = (ComponentScan) configClass.getDeclaredAnnotation(ComponentScan.class);
			packageName = componentScan.value();
		} else {
			throw new NotFoundAnnotation(ComponentScan.class.getName());
		}
		System.out.println("scan package path: " + packageName);
		String path = packageName.replace(".", "/");

		ClassLoader classLoader = CraftsApplicationContext.class.getClassLoader();
		URL resource = classLoader.getResource(path);
		File file = new File(resource.getFile());
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				String filename = f.getAbsolutePath();
				if (filename.endsWith(".class")) {
					// System.out.println(filename);
					String classname = filename.substring(filename.indexOf("com"), filename.indexOf(".class"))
							.replace("\\", ".");
					System.out.println(classname);
					try {
						Class<?> clazz = classLoader.loadClass(classname);
						if (clazz.isAnnotationPresent(Component.class)) {
							// 得到一个bean
							Component componentAnno = clazz.getDeclaredAnnotation(Component.class);
							String beanName = componentAnno.value();

							// beanDefinition 对 bean 进行定义
							BeanDefinition beanDefinition = new BeanDefinition();
							beanDefinition.setClazz(clazz);

							// 解析 scope
							if (clazz.isAnnotationPresent(Scope.class)) {
								Scope scopeAnno = clazz.getDeclaredAnnotation(Scope.class);
								beanDefinition.setScope(scopeAnno.value());
							}

							beanDefinitionMap.put(beanName, beanDefinition);
						}
					} catch (ClassNotFoundException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	}

	public Object getBean(String beanName) {
		try {
			if (beanDefinitionMap.containsKey(beanName)) {
				BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
				if (beanDefinition.getScope().equals("singleton")) {
					Object object = singletonObjects.get(beanName);
					return object;
				} else {
					// 创建 bean

				}
			} else {
				throw new NoExistsBeanException(beanName);
			}
		} catch (NoExistsBeanException e) {
			System.out.println(e);
		}
		return null;
	}

	private void printBanner() {
		banners.forEach(Banner::showBanner);
	}

}
