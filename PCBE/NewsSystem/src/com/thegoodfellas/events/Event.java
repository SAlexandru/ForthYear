package com.thegoodfellas.events;

import com.thegoodfellas.news.News;

public class Event {
    private News news_;
    private EventType type_;

    public Event (EventType type, News news) {
        type_ = type;
        news_ = news;
    }

    public EventType getType() {
		return type_;
	}

    public News getNews() {
        return news_;
    }

    @Override
    public String toString() {
        return "(" +
                   type_.name() +
                   "," + news_.getAuthor() +
                   "," + news_.getTitle() +
               ")";
    }
}
