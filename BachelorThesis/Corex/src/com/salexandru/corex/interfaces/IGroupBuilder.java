package com.salexandru.corex.interfaces;


public interface IGroupBuilder <T extends XEntity, K extends XEntity> {
	Group<T> buildGroup(K entity);
}
