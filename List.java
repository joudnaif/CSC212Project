/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212project;

/**
 *
 * @author joudnaif
 */
public interface List<T>{
public void findFirst( );
public void findNext( );
public T retrieve( );
public void update(T e);
public void insert(T e);
public void remove( );
public boolean full( );
public boolean empty( );
public boolean last( );

    
}
