package frame;

import frame.annotation.Controller;
import frame.annotation.RequestMapping;
import frame.mvc.Router;
import frame.mvc.Worker;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by Shli on 06/08/2017.
 */
public class RouterTest {

    @Controller
    public static class TestController {
        public String func1(String request) {return "func1:" + request;}
        @RequestMapping("test-func2")
        public String func2(String request) {return "func2:" + request;}
        @RequestMapping("test-func3")
        public String func3(String request) {return "func3:" + request;}
    }

    @Test
    public void should_create_request_action_map_for_controller() throws Exception {
        // given
        TestController controller = new TestController();
        Router router = new Router();

        // when
        router.gatherController(controller);

        // then
        assertNull(router.dispatch(""));
        assertNull(router.dispatch("test-func1"));
        assertNotNull(router.dispatch("test-func2"));
        assertNotNull(router.dispatch("test-func3"));
    }

    @Test
    public void should_return_string_after_worker_finish_request() throws Exception {
        // given
        Router router = new Router();
        router.gatherController(new TestController());

        // when
        Worker worker = router.dispatch("test-func2");

        // then
        assertThat(worker.exec("request"), is("func2:request"));
    }
}
