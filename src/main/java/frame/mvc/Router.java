package frame.mvc;

import frame.finder.MethodFilter;
import frame.finder.MethodUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shli on 06/08/2017.
 */
public class Router {

    private Map<String, Worker> routeTable = new HashMap<>();

    public void gatherController(Object controller) {
        for (Method method : MethodFilter.findRequestMappings(controller.getClass())) {
            String request = MethodUtil.getRequestValue(method);
            if (!request.isEmpty()) {
                routeTable.put(request, new Worker(controller, method));
            }
        }
    }

    public Worker dispatch(String request) {
        return routeTable.get(request);
    }
}
