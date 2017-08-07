package study.reflect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Shli on 05/08/2017.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface AutoNew {
    String name() default "";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface AutoFind {
    String name() default "";
    String key();
}

abstract class Base {
    public void printClassAndMethods() {
        Class<?> c = this.getClass();
        System.out.println("Class: " + c.getName());

        for (Method method : c.getMethods()) {
            System.out.println("    Method: " + method.getName());
        }

        System.out.println();
    }

    public void printTagMethods() {
        Method[] methods = this.getClass().getMethods();

        for (Method method : methods) {
            System.out.println(method.getName());
        }
    }
}

@AutoNew
class Derive1 extends Base {
    public String sayFuck(String person) {
        return String.format("Fuck you, %s!", person);
    }

    @AutoFind(key = "hello")
    public String sayHello(String person) {
        return String.format("Hello, %s!", person);
    }
}

@AutoNew
class Derive2 extends Base {
    @AutoFind(key = "crazy")
    public String beCrazy(String person) {
        return String.format("%s becomes crazy!", person);
    }

    @AutoFind(key = "happy")
    public String beHappy(String person) {
        return String.format("%s becomes happy!", person);
    }
}

class Worker {
    private final Object target;
    private final Method func;

    public Worker(Object target, Method func) {
        this.target = target;
        this.func = func;
    }

    public String exec(String param) {
        String result = "";

        try {
            result = (String)func.invoke(target, param);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}

class Dispatcher {
    private static final List<String> classNameList = Arrays.asList(
            AutoNew.class.getName(),
            AutoFind.class.getName(),
            Base.class.getName(),
            Derive1.class.getName(),
            Derive2.class.getName(),
            Worker.class.getName(),
            Dispatcher.class.getName(),
            ClassReflectStudy.class.getName()
    );

    private Map<String, Worker> router = new HashMap<>();

    public Dispatcher() {
        this.initWorkers();
    }

    public String exec(String url, String param) {
        String msg = "Not Found";

        Worker worker = router.get(url);
        if (worker != null) {
            msg = worker.exec(param);
        }

        return msg;
    }

    private void initWorkers() {
        for (Object target : initAutoNewClasses()) {
            initObjectWorkers(target);
        }
    }

    private void initObjectWorkers(Object obj) {
        for (Method method : obj.getClass().getMethods()) {
            AutoFind meta = method.getAnnotation(AutoFind.class);
            if (meta != null) {
                router.put(meta.key(), new Worker(obj, method));
            }
        }
    }

    private List<Object> initAutoNewClasses() {
        List<Object> targets = new LinkedList<>();
        try {
            for (String className : classNameList) {
                Class<?> c = Class.forName(className);
                if (c.getAnnotation(AutoNew.class) != null) {
                    targets.add(c.newInstance());
                }
            }
        } catch (Exception e) {

        }
        return targets;
    }
}

public class ClassReflectStudy {

    public static void logClassMethods(Class<?> c) {
        Method[] methods = c.getMethods();

        System.out.println("Class: " + c.getSimpleName());

        for (Method method : methods) {
            System.out.println("\tMethod: " + method.getName());
        }

        System.out.println();
    }

    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher();

        List<String> urls = Arrays.asList(
                "haha", "crazy", "fuck", "hello", "hello", "happy", "hello"
        );

        for (String url : urls) {
            String msg = dispatcher.exec(url, "xiaoming");
            System.out.println(msg);
        }
    }
}
