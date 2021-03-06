package com.salexandru.corex.interfaces;


public interface IGroupBuilder <ElementType extends XEntity, Entity extends XEntity> {
	Group<ElementType> buildGroup(Entity entity);
}
