package frame.factory;

import frame.finder.MethodFilter;
import frame.finder.MethodUtil;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Shli on 06/08/2017.
 */
public class BeanFactory {

    private ClassStorage classStorage = new ClassStorage();
    private BeanStorage beanStorage = new BeanStorage();

    public BeanFactory(List<Class> classes) {
        classStorage.add(classes);
    }

    public Object getBean(Class c) {
        Object obj = beanStorage.getBean(c);
        if (obj == null) {
            obj = createBean(c);
            if (obj != null) {
                beanStorage.storeBean(c, obj);
            }
        }
        return obj;
    }

    private Object createBean(Class c) {
        Object obj = createInstance(c);

        if (obj != null && !injectDependency(obj)) {
            obj = null;
        }

        return obj;
    }

    private boolean injectDependency(Object obj) {
        boolean flag = true;

        for (Method method : MethodFilter.findAutowires(obj.getClass())) {
            if (!injectBasedOnMethod(obj, method)) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    private boolean injectBasedOnMethod(Object obj, Method method) {
        boolean flag = true;

        List<Class> diClasses = classStorage.findWiredClasses(method);
        if (diClasses == null) {
            return false;
        }

        List<Object> diObjects = new LinkedList<>();
        for (Class diClass : diClasses) {
            Object diObject = getBean(diClass);
            if (diObject == null) {
                flag = false;
                break;
            }

            diObjects.add(diObject);
        }

        if (flag && !MethodUtil.invokeWithoutReturn(obj, method, diObjects)) {
            flag = false;
        }

        return flag;
    }

    private Object createInstance(Class c) {
        Object obj = null;

        try {
            obj = c.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }
}
