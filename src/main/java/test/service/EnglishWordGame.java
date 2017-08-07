package test.service;

import frame.annotation.Service;

/**
 * Created by Shli on 06/08/2017.
 */
@Service
public class EnglishWordGame implements IWordGame {
    @Override
    public String sayHello(String person) {
        return String.format("Hello, %s!", person);
    }

    @Override
    public String sayFuck(String person) {
        return String.format("Fuck, %s!", person);
    }
}
