package frame.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shli on 06/08/2017.
 */
public class BeanStorage {
    private Map<Class, Object> beans = new HashMap<>();

    public Object getBean(Class c) {
        return beans.get(c);
    }

    public void storeBean(Class c, Object obj) {
        beans.put(c, obj);
    }
}
