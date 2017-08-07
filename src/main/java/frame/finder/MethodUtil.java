package frame.finder;

import frame.annotation.RequestMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shli on 06/08/2017.
 */
public class MethodUtil {

    public static boolean invokeWithoutReturn(Object invoker, Method method, List<Object> params) {
        boolean flag = true;

        try {
            method.invoke(invoker, params.toArray());
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }

        return flag;
    }

    public static List<Class> getParamClasses(Method method) {
        return Arrays.asList(method.getParameterTypes());
    }

    public static String getRequestValue(Method method) {
        String requestValue = "";

        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null) {
            requestValue = requestMapping.value();
        }

        return requestValue;
    }

    public static String invokeRequestMethod(Object invoker, Method method, String param) {
        String result = "";

        try {
            result = (String) method.invoke(invoker, param);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
