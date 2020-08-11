package com.itxiaoyuaiit.learn.core;

import com.google.gson.Gson;
import com.itxiaoyuaiit.learn.annotation.MyController;
import com.itxiaoyuaiit.learn.annotation.MyQualifier;
import com.itxiaoyuaiit.learn.annotation.MyRequestMapping;
import com.itxiaoyuaiit.learn.annotation.MyService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MyDisPatcherServlet
 * @Description TODO
 * @Author wuyuqing
 * @Date 2020/8/9 22:37
 * @Version 1.0
 */
public class MyDisPatcherServlet extends HttpServlet {
    /**
     * 存储扫描路径下所有的class文件
     */
    private ArrayList<String> clazzList = new ArrayList<>();

    /**
     * 存储所有的bean实例，key为请求名
     */
    private Map<String, Object> beanMap = new HashMap<>();

    /**
     * 存储路由地址与对应方法的映射，key为url
     */
    private Map<String, Object> requestMappingMap = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 处理请求的url
         */
        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        String url = requestURI.replace(contextPath, "");
        /**
         * 获取url对应的方法
         */
        Method method = (Method) requestMappingMap.get(url);
        /**
         * 获取url对应的bean
         */
        Object bean = beanMap.get("/" + url.split("/")[1]);
        try {
            /**
             * 反射调用执行该方法
             */
            Object result = method.invoke(bean);
            /**
             * 视图解析
             */
            resp.getWriter().print(new Gson().toJson(result));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


//        System.out.println("hello myspringmvc");
//        resp.getWriter().print("hello myspringmvc");
//        super.doPost(req, resp);
    }

    @Override
    public void init() throws ServletException {
        /**
         * 扫描指定目录下的class文件
         */
        scanAllAnnotation("com.itxiaoyuaiit.learn");
        /**
         * 利用反射实例化bean
         */
        beanInit();

        /**
         * 实现依赖注入
         */
        beanIoc();

        /**
         * 构建请求url与对应方法的映射
         */
        requestMapping();
    }

    /**
     * 扫描class
     * @param packageName 扫描文件路径
     */
    private void scanAllAnnotation(String packageName) {
        /**
         * 获取扫描文件的全路径
         */
        String path = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/")).getFile();
        System.out.println(path);
        /**
         * 遍历该路径下的所有class文件，将类名全路径存储到list中
         */
        File filePath = new File(path);
        String[] fileNames = filePath.list();
        for (String fileName : fileNames) {
            /**
             * 利用全路径，生成文件
             */
            File file = new File(path, fileName);
            if (file.isDirectory()) {
                scanAllAnnotation(packageName + "." + fileName);
            }else {
                if (fileName.indexOf(".class") > -1) {
                    clazzList.add(packageName + "." + fileName.replace(".class", ""));
                }
            }
        }

    }

    /**
     * 实例化bean
     */
    private void beanInit() {
        if (clazzList != null && clazzList.size() > 0) {
            clazzList.forEach((clazzName) -> {
                try {
                    Class<?> clazz = Class.forName(clazzName);
                    /**
                     * 判断是否是控制层
                     */
                    if (clazz.isAnnotationPresent(MyController.class)) {
                        MyRequestMapping requestMapping = clazz.getAnnotation(MyRequestMapping.class);
                        String key = requestMapping.value();
                        beanMap.put(key, clazz.newInstance());
                    }else if (clazz.isAnnotationPresent(MyService.class)) {
                        MyService service = clazz.getAnnotation(MyService.class);
                        String key = service.value();
                        beanMap.put(key, clazz.newInstance());
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }

            });
        }
    }

    /**
     * 实现依赖注入
     */
    private void beanIoc() {
        if (beanMap != null && beanMap.size() > 0) {
            beanMap.forEach((k, v) -> {
                /**
                 * 获取实例
                 *
                 */
                Field[] fields = v.getClass().getDeclaredFields();
                for (Field field : fields) {
                    MyQualifier myQualifier = field.getAnnotation(MyQualifier.class);
                    /**
                     * 接触私有化限定
                     */
                    field.setAccessible(true);
                    try {
                        field.set(v, beanMap.get(myQualifier.value()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    /**
     * 构建请求与对应方法的映射
     */
    private void requestMapping() {
        if (beanMap != null && beanMap.size() > 0) {
            beanMap.forEach((k, v) -> {
                /**
                 * 获取实现的class对象
                 */
                Class<?> beanClazz = v.getClass();
                /**
                 * 判断该类是否为controller层
                 */
                if (beanClazz.isAnnotationPresent(MyController.class)) {
                    /**
                     * 获取类中所有的方法
                     */
                    Method[] allMethods = beanClazz.getMethods();
                    for (Method method : allMethods) {
                        /**
                         * 判断该方法是否被MyRequestMapping修饰
                         */
                        if (method.isAnnotationPresent(MyRequestMapping.class)) {
                            MyRequestMapping requestMapping = method.getAnnotation(MyRequestMapping.class);
                            /**
                             * 拼接requestMapping的key
                             */
                            String requestUrl = k + requestMapping.value();
                            requestMappingMap.put(requestUrl, method);
                        }
                    }
                }

            });
        }
    }
}
