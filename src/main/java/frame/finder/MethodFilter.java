package frame.finder;

import frame.annotation.Autowired;
import frame.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Shli on 06/08/2017.
 */
public class MethodFilter {

    public static List<Method> findAutowires(Class c) {
        return filter(c, Autowired.class);
    }

    public static List<Method> findRequestMappings(Class c) {
        return filter(c, RequestMapping.class);
    }

    private static List<Method> filter(Class c, Class annotation) {
        return Arrays.stream(c.getMethods())
                .filter(method -> hasAnnotation(method, annotation))
                .collect(Collectors.toList());
    }

    private static boolean hasAnnotation(Method method, Class annotation) {
        return (method.getAnnotation(annotation) != null);
    }
}
