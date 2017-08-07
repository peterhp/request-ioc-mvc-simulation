package frame.factory;

import frame.finder.ClassFilter;
import frame.finder.MethodUtil;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Shli on 06/08/2017.
 */
public class ClassStorage {

    private Set<Class> classSet = new HashSet<>();

    public void add(List<Class> classes) {
        classSet.addAll(classes);
    }

    public List<Class> findWiredClasses(Method method) {
        List<Class> params = MethodUtil.getParamClasses(method);

        List<Class> wiredClasses = new LinkedList<>();
        for (Class param : params) {
            Class c = findImplClass(param);
            if (c == null) {
                wiredClasses = null;
                break;
            }
            wiredClasses.add(c);
        }

        return wiredClasses;
    }

    private Class findImplClass(Class param) {
        Class impl = null;
        for (Class c : classSet) {
            if (ClassFilter.isService(c) && param.isAssignableFrom(c)) {
                impl = c;
                break;
            }
        }
        return impl;
    }
}
