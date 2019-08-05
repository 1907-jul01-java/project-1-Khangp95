package com.revature;

public interface Dao<E> {
	
	void insert(E e);
	
	void select();
	
	void update();
	
	void delete();
	

}
