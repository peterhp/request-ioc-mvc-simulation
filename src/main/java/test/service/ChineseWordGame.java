package test.service;

/**
 * Created by Shli on 06/08/2017.
 */
public class ChineseWordGame implements IWordGame {
    @Override
    public String sayHello(String person) {
        return String.format("%s，你好！", person);
    }

    @Override
    public String sayFuck(String person) {
        return String.format("%s，卧槽！", person);
    }
}
