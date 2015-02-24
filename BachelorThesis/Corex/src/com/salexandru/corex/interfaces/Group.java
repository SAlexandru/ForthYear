package com.salexandru.corex.interfaces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.lang.reflect.InvocationHandler;

public class Group <T extends XEntity> implements InvocationHandler {
	private List<T> elements_;
	
	public Group(List<T> elements) {
		elements_ = new ArrayList<>(elements);
	}
	
	public boolean add(T element) {
		return elements_.add(element);
	}
	
	public boolean addAll(Collection<? extends T> elements) {
		return elements_.addAll(elements);
	}
	public List<T> getElements() {return elements_;}

	/**
	 *   Must test this actually works and also if provides the benefits that I think it does
	 */
	public Object invoke(Object proxy, Method m, Object... args) throws Throwable {
		// may useless ?
		if (null == proxy || !(proxy instanceof Group<T extends XEntity>)) {
			throw new IllegalArgumentException();
		}
		Object result;
		try {
			// we don't get into an infinit loop here ??
			Group<T extends XEntity> p = (Group<T extends XEntity>)proxy;
			result = m.invoke(p.getElements(), args);
		}
		catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
		return result;
	}
}
