package com.thegoodfellas.dispatcher;

import com.thegoodfellas.events.Event;

public interface IHandler {
	void handle(Event e);
}
