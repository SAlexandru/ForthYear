package com.thegoodfellas.dispatcher;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.thegoodfellas.events.Event;
import com.thegoodfellas.events.EventType;

public class Dispatcher implements IRegistrationHandler {
	private BlockingQueue<Event> eventList;
	private Map<EventType, Queue<Pair>> eventHandlers;
    private Thread[] threads_;
	
	private static final ConcurrentLinkedQueue<Pair> defaultQueue = new ConcurrentLinkedQueue<>();

    public void kill () {
        for (Thread t: threads_) {
            t.interrupt();
        }
    }

    private static class SingleDispatcher {
		public static final Dispatcher instance = new Dispatcher();
	}
	
	private Dispatcher() {
        eventList = new LinkedBlockingQueue<> () ;
		eventHandlers = new HashMap<>();
		
		// avoid synchronization on registerHandlers
		for (EventType type: EventType.values()) {
			eventHandlers.put(type, new ConcurrentLinkedQueue<>());
		}

        /*
		 *  The thread that dispatches the events to handles
		 *  Needs another thread, because we have to wait for events to have dispatch
		 */
        threads_ = new Thread[5];
        for (int i = 0; i < 5; ++i) {
            threads_[i] = new Thread(new Runnable() {
                @Override
                public void run () {
                    while (true) {
                        try {
                            // block till event is added
                            dispatch(eventList.take());
                        } catch (InterruptedException e) {
                            //e.printStackTrace();
                            return;
                        }
                    }
                }

                private void dispatch (Event e) {
                    eventHandlers.getOrDefault(e.getType(), defaultQueue).forEach((Pair handlerPair) -> {
                        if (null == handlerPair.options || handlerPair.options.isEventAccepted(e)) {
                            handlerPair.handler.handle(e);
                        }
                    });
                }
            });
            threads_[i].start();
        }
    }
	
	public static Dispatcher getInstance() {
		return SingleDispatcher.instance;
	}
	
	public Collection<Pair> getHandlers(EventType type) {
        return Collections.unmodifiableCollection (eventHandlers.getOrDefault (type, defaultQueue));
    }

    @Override
    public void registerHandler(IHandler handler) {
        registerHandler (handler, null);
    }

    @Override
    public void registerHandler(IHandler handler, IOptions options) {
        for (EventType type : EventType.values ()) {
            registerHandler (type, handler, options);
        }
    }

	@Override
	public void registerHandler(EventType type, IHandler handler) {
        registerHandler(type, handler, null);
	}

	@Override
	public void registerHandler(EventType type, IHandler handler, IOptions options) {
		eventHandlers.get(type).add(new Pair(handler, options));
	}
	
	public void notifyHandler(Event e) throws ClassCastException, 
											  NullPointerException,
											  IllegalStateException,
											  IllegalArgumentException  {
		eventList.add(e);
	}
}

class Pair {
	public IHandler handler = null;
	public IOptions options = null;
	
	public Pair(IHandler handler) {
		this.handler = handler;
	}
	
	public Pair(IHandler handler, IOptions options) {
		this.handler = handler;
		this.options = options;
	}
}

