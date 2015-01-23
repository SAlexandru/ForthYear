package com.thegoodfellas.actors;

import com.thegoodfellas.dispatcher.Dispatcher;
import com.thegoodfellas.dispatcher.IHandler;
import com.thegoodfellas.events.Event;
import com.thegoodfellas.events.EventType;

public class Reader implements IHandler {
    private String name_;


    public Reader (String name) {
        name_ = name + "R";

        Dispatcher dispatcher = Dispatcher.getInstance();

        dispatcher.registerHandler(EventType.PUBLISHED, this);

    }


    @Override
    public void handle (Event e) {
        System.out.println (name_ + ": received an event  " + e.toString());
        e.getNews().readNews();
    }
}
