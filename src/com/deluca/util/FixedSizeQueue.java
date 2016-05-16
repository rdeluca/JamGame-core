package com.deluca.util;

import java.util.LinkedList;

/**
 * Fixed size Queue, that dumps oldest when it 
 *  gets a new and size limit is reached 
 *   
 *   5/2016
 *   
 * @author Rich D
 *
 * @param <E> - element
 */
public class FixedSizeQueue<E> extends LinkedList<E>{
	
	  private final int maxSize;
	  private final LinkedList<E> list = new LinkedList<E>();

	  public FixedSizeQueue(int maxSize) {
		  //Compacted if statement
		  //if maxSize<0 (? represents then) trueresult (: represents else) falseresult
	    this.maxSize = maxSize < 0 ? 0 : maxSize;
	  }

	  @Override
	public boolean offer(E e) {
		  if(super.size()==maxSize)
			  super.poll();
		  return super.offer(e);
	}
}
