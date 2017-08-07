package frame;

import frame.factory.BeanFactory;
import frame.finder.ClassFilter;
import frame.io.IODevice;
import frame.mvc.Router;
import frame.mvc.Worker;

import java.util.List;

/**
 * Created by Shli on 06/08/2017.
 */
public class CoreApp {

    private BeanFactory factory = null;
    private Router router = null;

    public CoreApp(List<Class> scannedClasses) {
        initFactory(scannedClasses);
        initRouter(ClassFilter.findControllers(scannedClasses));
    }

    public void run() {
        System.out.println("Framework is running...");

        while (true) {
            String request = IODevice.read();
            if (request.equals("exit")) {
                break;
            }
            String param = IODevice.read();

            Worker worker = router.dispatch(request);
            if (worker == null) {
                IODevice.write("Bad request.");
            } else {
                IODevice.write(worker.exec(param));
            }
        }

        System.out.println("Framework is shutting down...");
    }

    private void initFactory(List<Class> classes) {
        factory = new BeanFactory(classes);
    }

    private void initRouter(List<Class> controllers) {
        router = new Router();

        for (Class controller : controllers) {
            router.gatherController(factory.getBean(controller));
        }
    }
}
