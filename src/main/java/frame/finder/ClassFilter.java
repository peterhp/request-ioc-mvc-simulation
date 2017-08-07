package frame.finder;

import frame.annotation.Controller;
import frame.annotation.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Shli on 06/08/2017.
 */
public class ClassFilter {
    public static List<Class> findControllers(List<Class> classes) {
        return filter(classes, Controller.class);
    }

    public static List<Class> findServices(List<Class> classes) {
        return filter(classes, Service.class);
    }

    public static boolean isService(Class c) {
        return hasAnnotation(c, Service.class);
    }

    private static List<Class> filter(List<Class> classes, Class annotation) {
        return classes.stream()
                .filter(c -> hasAnnotation(c, annotation))
                .collect(Collectors.toList());
    }

    private static boolean hasAnnotation(Class c, Class annotation) {
        return (c.getAnnotation(annotation) != null);
    }
}
