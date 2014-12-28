package com.thegoodfellas.actors;

import com.thegoodfellas.dispatcher.Dispatcher;
import com.thegoodfellas.dispatcher.IHandler;
import com.thegoodfellas.events.Event;
import com.thegoodfellas.events.EventType;
import com.thegoodfellas.news.News;

import java.util.Random;
import java.util.UUID;

public class Editor implements IHandler {
    private String name_;
    private Random random;
    public static String[] topics = new String[] {"Sport",
                                                  "Weather",
                                                  "Drama",
                                                  "Crime",
                                                  "Other"
                                                 };

    public Editor (String name) {
        name_ = name + "E";
        random = new Random(System.nanoTime());
        Dispatcher dispatcher = Dispatcher.getInstance();

        dispatcher.registerHandler(EventType.READ, this, (Event e) -> e.getNews().getAuthor().equals(name_));
    }

    public void createNews() {
        new News(topics[random.nextInt(topics.length)],
                 topics[random.nextInt(topics.length)],
                 name_,
                 UUID.randomUUID().toString(),
                 ""
                ).publish();
    }


    @Override
    public void handle (Event e) {
        System.out.println (name_ + ": received an event  " + e.toString());
    }
}
