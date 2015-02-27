package com.salexandru.corex.interfaces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Group <T extends XEntity> {
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
}
