import annotation.*;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author wangjie
 * @version v1.0
 * @description
 * @date 2020/5/11 10:55
 */
public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = -6375158378191744968L;

    private final List<String> CLASS_NAME = new ArrayList<>();

    private final Map<String, Object> BEAN_AMP = new HashMap<>();

    private final Map<String, Object> REQUSET_MAP = new HashMap<>();

    private final Map<String, Method> HANDLER_MAP = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        //重写servlet初始化方法
        //1.扫描所有的类
        //2.找到@Service注解类,装载成bean,供autowired使用 找到@Contorller注解类
        //3.实现某个url调用进来后,直接指向某个Contorller的对应的action中,并传入参数,然后注入service
        scanModule("");
        try {
            generatorBean();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        buildUrlMethodMap();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //通过相应的url,调用指定的Controller中的Method
        String uri = req.getRequestURI(); //访问请求路径
        String context = req.getContextPath();//servlet项目context路径
        String path = uri.replace(context, "");
        Method method = HANDLER_MAP.get(path);
        if (method == null) {
            resp.getWriter().write("404");
            return;
        }

        String[] maps = path.split("/");
        String map = "/";
        if (maps.length > 1) {
            map += maps[1];
            Object controller = REQUSET_MAP.get(map);
            if (controller == null) {
                resp.getWriter().write("找不到相应路径的控制器");
                return;
            }
            Object[] args = getMethodParams(req, resp, method);
            try {
                method.invoke(controller, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            resp.getWriter().write("请求路径错误");
            return;
        }
    }

    //获取调用action的参数
    private Object[] getMethodParams(HttpServletRequest req, HttpServletResponse resp, Method method) {
        Class<?>[] classes = method.getParameterTypes();
        Object[] args = new Object[classes.length];
        int sgin = 2;
        int index = 0;
        for (Class<?> clazz : classes) {
            //clazz是从ServletRequest派生而来的
            if (ServletRequest.class.isAssignableFrom(clazz)) {
                args[0] = req;
            }
            //clazz是从ServletResponse派生而来的
            if (ServletResponse.class.isAssignableFrom(clazz)) {
                args[1] = resp;
            }
            //获取action上的所有MyRequestParam注解
            Annotation[] paramAnnotation = method.getParameterAnnotations()[index];
            if (paramAnnotation.length > 0) {
                for (Annotation pa : paramAnnotation) {
                    if (MyRequsetParam.class.isAssignableFrom(pa.getClass())) {
                        MyRequsetParam requsetParam = (MyRequsetParam) pa;
                        String paramName = requsetParam.value();
                        String paramValueStr = req.getParameter(paramName);
                        args[sgin++] =convert(paramValueStr,clazz);
                    }
                }
            }
            index++;
        }
        return args;
    }

    //扫描文件
    private void scanModule(String basePackageUrl) {
        //通过项目路径,扫描整个项目,获取所有的类文件名
        URL url = this.getClass().getClassLoader()
                .getResource("/" + basePackageUrl.replaceAll("\\.", "/"));
        String path = url.getPath();
        //eg E:/github-osp/java-hardworking/my-springmvc/target/test-classes/
        //遍历该文件夹下的所有文件
        File file = new File(path);
        getFiles(file);
    }

    //创建bean
    private void generatorBean() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //1.service bean
        if (CLASS_NAME.size() < 0) {
            System.out.println("扫描包为空");
            return;
        }
        List<Class<?>> classes = new ArrayList<>();
        for (String classname : CLASS_NAME) {
            //java反射,通过类名获取对象
            Class<?> clazz = Class.forName(classname);
            classes.add(clazz);
        }
        //获取service bean
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(MyService.class)) {
                //服务类
                Object service = clazz.newInstance();
                String serviceName = clazz.getSimpleName();
                String pre = serviceName.substring(0, 1).toLowerCase();
                String behand = serviceName.substring(1);
                BEAN_AMP.put(pre + behand, service);
            }
        }
        //装配所有的controller类,并注入属性
        for (Class<?> clazz : classes) {
            //classname eg: epic.springmvc.TestController
            if (clazz.isAnnotationPresent(MyController.class)) {
                //控制器类
                Object controller = clazz.newInstance();
                String mapUrl = clazz.getAnnotation(MyRequestMapping.class).value();
                //属性注入 getDeclaredFields()获取所有的字段,包括public,private,protect
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(MyAutowired.class)) {
                        //需要注入的属性
                        MyAutowired myAutowired = field.getAnnotation(MyAutowired.class);
                        String value = myAutowired.value();
                        if (StringUtils.isEmpty(value))
                            value = field.getName() + "Impl";
                        Object service = BEAN_AMP.get(value);
                        if (service == null) {
                            System.out.println("service bean 找不到");
                            return;
                        }
                        field.setAccessible(true);
                        field.set(controller, service);
                    }
                }
                REQUSET_MAP.put(mapUrl, controller);
            }
        }
    }

    //创建完整的requestMapping与controller方法的映射关系
    private void buildUrlMethodMap() {

        if (REQUSET_MAP.size() < 0) {
            System.out.println("controller bean为空");
            return;
        }
        for (Map.Entry<String, Object> entry : REQUSET_MAP.entrySet()) {
            Object controller = entry.getValue();
            Method[] methods = controller.getClass().getMethods();
            Arrays.stream(methods).forEach(x -> {
                if (x.isAnnotationPresent(MyRequestMapping.class)) {
                    String methodRequestMapping = x.getAnnotation(MyRequestMapping.class).value();
                    HANDLER_MAP.put(entry.getKey() + methodRequestMapping, x);
                }
            });
        }
    }

    //递归获取文件夹下的所有class文件 项目结构没有规范好..
    private void getFiles(File file) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                getFiles(f);
            } else if (f.isFile()) {
                String filename = f.getName();
                if (filename.endsWith(".class")) {
                    String[] paths = f.getPath().split("classes");
                    String classname = paths[1].substring(1).replaceAll("\\\\", ".");
                    CLASS_NAME.add(classname.substring(0, classname.length() - 6));
                }
            }
        }
    }


    /**
     * Object转成指定的类型
     *
     * @param obj
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T convert(Object obj, Class<T> type) {
        if (obj != null && StringUtils.isNotBlank(obj.toString())) {
            if (type.equals(Integer.class) || type.equals(int.class)) {
                return (T) new Integer(obj.toString());
            } else if (type.equals(Long.class) || type.equals(long.class)) {
                return (T) new Long(obj.toString());
            } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                return (T) Boolean.valueOf(obj.toString());
            } else if (type.equals(Short.class) || type.equals(short.class)) {
                return (T) new Short(obj.toString());
            } else if (type.equals(Float.class) || type.equals(float.class)) {
                return (T) new Float(obj.toString());
            } else if (type.equals(Double.class) || type.equals(double.class)) {
                return (T) new Double(obj.toString());
            } else if (type.equals(Byte.class) || type.equals(byte.class)) {
                return (T) new Byte(obj.toString());
            } else if (type.equals(Character.class) || type.equals(char.class)) {
                return (T) new Character(obj.toString().charAt(0));
            } else if (type.equals(String.class)) {
                return (T) obj;
            } else if (type.equals(BigDecimal.class)) {
                return (T) new BigDecimal(obj.toString());
            } else if (type.equals(LocalDateTime.class)) {
                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                return (T) LocalDateTime.parse(obj.toString());
            } else if (type.equals(Date.class)) {
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    return (T) formatter.parse(obj.toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e.getMessage());
                }

            } else {
                return null;
            }
        } else {
            if (type.equals(int.class)) {
                return (T) new Integer(0);
            } else if (type.equals(long.class)) {
                return (T) new Long(0L);
            } else if (type.equals(boolean.class)) {
                return (T) new Boolean(false);
            } else if (type.equals(short.class)) {
                return (T) new Short("0");
            } else if (type.equals(float.class)) {
                return (T) new Float(0.0);
            } else if (type.equals(double.class)) {
                return (T) new Double(0.0);
            } else if (type.equals(byte.class)) {
                return (T) new Byte("0");
            } else if (type.equals(char.class)) {
                return (T) new Character('\u0000');
            } else {
                return null;
            }
        }
    }

    public static void main(String[] args) {
        URL url = List.class.getClassLoader().getResource("");

    }
}
