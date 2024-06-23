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

		scan(configClass); // 得到 BeanDefinition

		// 创建所有单例bean
		for (String beanName : beanDefinitionMap.keySet()) {
			BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
			if (beanDefinition.getScope().equals("singleton")) {
				Object bean = createBean(beanDefinition);
				singletonObjects.put(beanName, bean);
			}
		}

		printBanner();
	}

	private Object createBean(BeanDefinition beanDefinition) {
		Class clazz = beanDefinition.getClazz();
		try {
			Object instance = clazz.getDeclaredConstructor().newInstance();
			return instance;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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

							// beanName 默认为类名首字母小写
							String beanName = componentAnno.value();
							if (beanName.equals("")) {
								beanName = clazz.getSimpleName().substring(0, 1).toLowerCase()
										+ clazz.getSimpleName().substring(1);
							}

							// beanDefinition 对 bean 进行定义
							BeanDefinition beanDefinition = new BeanDefinition();
							beanDefinition.setClazz(clazz);
							beanDefinition.setName(beanName);

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

	/**
	 * 通过名称获取bean
	 * @param beanName
	 * @return
	 */
	public Object getBean(String beanName) {
		if (beanDefinitionMap.containsKey(beanName)) {
			Object object;
			BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
			if (beanDefinition.getScope().equals("singleton")) {
				object = singletonObjects.get(beanName);
			} else {
				// 创建 bean
				object = createBean(beanDefinition);
			}
			return object;
		} else {
			throw new NoExistsBeanException(beanName);
		}
	}

	/**
	 * 通过类型获取bean
	 * @param clazz
	 * @return
	 */
	public Object getBean(Class clazz){
		Object object = null;
		for (BeanDefinition beanDefinition : beanDefinitionMap.values()) {
			if (beanDefinition.getClazz().equals(clazz)) {
				if(beanDefinition.getScope().equals("singleton")){
					object = singletonObjects.get(beanDefinition.getName());
				}else{
					object = createBean(beanDefinition);
				}
			}
		}
		if(object == null)
			throw new NoExistsBeanException(clazz.getName());
		return object;
	}

	private void printBanner() {
		banners.forEach(Banner::showBanner);
	}

}
