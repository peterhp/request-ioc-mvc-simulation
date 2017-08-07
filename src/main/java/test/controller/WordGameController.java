package test.controller;

import frame.annotation.Autowired;
import frame.annotation.Controller;
import frame.annotation.RequestMapping;
import test.service.IWordGame;

/**
 * Created by Shli on 06/08/2017.
 */
@Controller
public class WordGameController {
    private IWordGame game;

    @Autowired
    public void setWordGame(IWordGame game) {
        this.game = game;
    }

    @RequestMapping("hello")
    public String hello(String request) {
        return game.sayHello(request);
    }

    @RequestMapping("fuck")
    public String fuck(String request) {
        return game.sayFuck(request);
    }
}
