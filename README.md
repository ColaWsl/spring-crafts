# spring-crafts
my spring framework

## 前置知识

### 类加载器
 类加载器

1. BootStrapClassLoader jre/lib

2. ExtClassLoader jre/ext/lib

3. AppClassLoader classpath

java程序运行命令：E:\develop\Java\jdk-17.0.1\bin\java.exe "-javaagent:E:\develop\IntelliJ IDEA 2024.1.1\lib\idea_rt.jar=49077:E:\develop\IntelliJ IDEA 2024.1.1\bin" -Dfile.encoding=UTF-8
   -classpath D:\java\crafts\spring-crafts\target\classes com.wangsl.Application

## 实现功能

1. 通过名称获取bean/通过类型获取bean  默认实现bean名称是 类名首字母小写
2. 实现 beanName aware 回调
