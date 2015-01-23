package com.thegoodfellas.dispatcher;

import com.thegoodfellas.events.Event;

public interface IOptions {
	boolean isEventAccepted (Event e);
}
