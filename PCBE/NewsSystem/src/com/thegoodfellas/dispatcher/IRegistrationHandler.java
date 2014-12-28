package com.thegoodfellas.dispatcher;

import com.thegoodfellas.events.EventType;

public interface IRegistrationHandler {
    void registerHandler (IHandler handler);
    void registerHandler (IHandler handler,  IOptions options);
    void registerHandler(EventType type, IHandler handler);
	void registerHandler(EventType type, IHandler handler, IOptions options);
}
