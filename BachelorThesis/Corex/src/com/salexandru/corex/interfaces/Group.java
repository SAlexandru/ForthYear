package com.salexandru.corex.interfaces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Group <T extends XEntity> implements Iterable<T> {
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

	@Override
	public Iterator<T> iterator() {
		return elements_.iterator();
	}
}
