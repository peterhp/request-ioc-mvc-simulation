package frame.mvc;

import frame.finder.MethodUtil;

import java.lang.reflect.Method;

/**
 * Created by Shli on 06/08/2017.
 */
public class Worker {
    private final Object target;
    private final Method func;

    public Worker(Object target, Method action) {
        this.target = target;
        this.func = action;
    }

    public String exec(String request) {
        return MethodUtil.invokeRequestMethod(target, func, request);
    }
}
