package frame;

import frame.annotation.Autowired;
import frame.annotation.Controller;
import frame.annotation.RequestMapping;
import frame.annotation.Service;
import frame.finder.ClassFilter;
import frame.finder.MethodFilter;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Shli on 06/08/2017.
 */
public class ScannerTest {

    public interface IBase {}

    public static class NormalClass {}
    @Service
    public static class ServiceClass {}

    @Controller
    public static class ControllerClass {
        private IBase impl;
        @Autowired
        public void setBase(IBase impl) {
            this.impl = impl;
        }
        @RequestMapping(value = "test")
        public String test(String input) {
            return input;
        }
    }

    private List<Class> classes = Arrays.asList(
            NormalClass.class, ControllerClass.class, ServiceClass.class
    );

    @Test
    public void should_return_controller_class_while_search_class_with_controller_annotation() throws Exception {
        // when
        List<Class> controllers = ClassFilter.findControllers(classes);

        // then
        assertThat(controllers.size(), is(1));
        assertThat(controllers.get(0).getSimpleName(), is("ControllerClass"));
    }

    @Test
    public void should_return_service_class_while_search_class_with_service_annotation() throws Exception {
        // when
        List<Class> services = ClassFilter.findServices(classes);

        // then
        assertThat(services.size(), is(1));
        assertThat(services.get(0).getSimpleName(), is("ServiceClass"));
    }

    @Test
    public void should_return_autowired_method_while_search_method_with_autowired_annotation() throws Exception {
        // when
        List<Method> wires = MethodFilter.findAutowires(ControllerClass.class);

        // then
        assertThat(wires.size(), is(1));
        assertThat(wires.get(0).getName(), is("setBase"));
    }

    @Test
    public void should_return_request_mapping_method_while_search_method_with_request_mapping_annotation() throws Exception {
        // when
        List<Method> requests = MethodFilter.findRequestMappings(ControllerClass.class);

        // then
        assertThat(requests.size(), is(1));
        assertThat(requests.get(0).getName(), is("test"));
    }
}
