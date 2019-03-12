/*
*Copyright (C) Sergey Nikitin, sergey@indexoffice.com, nikitin@asu.edu
*$Id: State.java,v 1.3 2005/05/18 06:12:16 nikitis Exp $
*
*This program is free software; you can redistribute it and/or
*modify it under the terms of the GNU General Public License
*as published by the Free Software Foundation; either version 2
*of the License, or (at your option) any later version.
*
*This program is distributed in the hope that it will be useful,
*but WITHOUT ANY WARRANTY; without even the implied warranty of
*MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*GNU General Public License for more details.
*
*You should have received a copy of the GNU General Public License
*along with this program; if not, write to the Free Software
*Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/

package org.ioblako.core;

//import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;



/**
 * This class represents the state of the project implemented 
 * in GNU6 environment. Basically, it is a modification of the java class - 
 * HashMap. The modification allows shared access to this class from 
 * multiple threads.
 *
 * @author Sergey Nikitin
 * @since 1.1
 */

public class State implements Cloneable {
//	private Hashtable<Object,Object> currentState= new Hashtable<Object,Object>();
	private final HashMap<String,Object> currentState= new HashMap<String,Object>();
       // private boolean Success=true;
        private final  AtomicBoolean lock=new AtomicBoolean(false);
        private final AtomicBoolean Success = new AtomicBoolean(true);
        private final AtomicBoolean isItDone = new AtomicBoolean(false);
	//private boolean isItDone=false;


private synchronized void requestLock() throws IllegalMonitorStateException{
  while(lock.get())
   try{
     wait();
    }
   catch(java.lang.InterruptedException e){}
  lock.set(true);
  notifyAll();
}

private synchronized void releaseLock() throws IllegalMonitorStateException{
  if(lock.get())
      lock.set(false);
  notifyAll();
}
	
/**
 * Maps the specified key to the specified value in this
 * hashtable.
 * @param key - the hashtable key.
 * @param value - the value.
 * @exception  NullPointerException - if the key or value is null.
 * @exception  IllegalMonitorStateException - if the current thread is not the owner of this object's monitor.
 */
	public void put(String key, Object value) throws NullPointerException, IllegalMonitorStateException {
	requestLock();
            if(currentState == null){
                    releaseLock();
                    throw new NullPointerException("currentState is not defoned!!!");
            }
                currentState.put(key,value);
                releaseLock();
	}

	 /**
	  *  Returns the value to which the specified key is mapped in this hashtable.
	  *  @param key - a key in the hashtable.
	  *  @return    the value to which the key is mapped in this hashtable; null if the 
	  *  key is not mapped to any value in this hashtable.
          * @exception  IllegalMonitorStateException - if the current thread is not the owner of this object's monitor.
	  */

	public Object get(String key) throws IllegalMonitorStateException{
            requestLock();
		Object ret = currentState.get(key);
            releaseLock();
		return ret;

    }

public Set<String> keySet(){
  requestLock();
       Set<String> ret = currentState.keySet(); 
  releaseLock();
return ret;
}
/**
 * Tests if the specified object is a key in this hashtable.
 * @param key - possible key.
 * @return true if and only if the specified object is a key in this hashtable, as
 * determined by the equals method; false otherwise.
 * @exception  IllegalMonitorStateException - if the current thread is not the owner of this object's monitor.
 */
	public boolean containsKey(String key) throws IllegalMonitorStateException{
                requestLock();
                boolean ret = currentState.containsKey(key);
                releaseLock();
		return ret;
	}
	
/**
 * Returns true if this Hashtable maps one or more keys to this value.
 * Note that this method is identical in functionality to contains (which
 * predates the Map interface).
 * @param value - value whose presence in this Hashtable is to be tested.
 * @return true if this map maps one or more keys to the specified value.
 * @exception  IllegalMonitorStateException - if the current thread is not the owner of this object's monitor.
 */
	public boolean containsValue(Object value) throws IllegalMonitorStateException{
                 requestLock();
                  boolean ret = currentState.containsValue(value);
                 releaseLock();
		  return ret;
	}
	/**
	 * Returns an enumeration of the values in this hashtable.
	 * @return an enumeration of the values in this hashtable.
	 */

	public Set<String> elements() throws IllegalMonitorStateException{
                   requestLock();
                   Set<String> ret = currentState.keySet();
                   releaseLock();
		   return ret;
	}


	/**
	 * Tests if this hashtable maps no keys to values.
	 * @return true if this hashtable maps no keys to values; false otherwise.
         * @exception  IllegalMonitorStateException - if the current thread is not the owner of this object's monitor.
	 */

	public boolean isEmpty() throws IllegalMonitorStateException{
                    requestLock();
                    boolean ret=currentState.isEmpty();
                    releaseLock();
		    return ret;
	}

	public Set<String> keys(){
               requestLock();
               Set<String> ret = currentState.keySet();
               releaseLock();
		     return ret;
	}
	/**
	 * Clears this hashtable so that it contains no keys.
	 * @exception UnsupportedOperationException - clear is not supported by this map.
         * @exception  IllegalMonitorStateException - if the current thread is not the owner of this object's monitor.
	 */
	public void clear() throws UnsupportedOperationException, IllegalMonitorStateException{
                  requestLock();
                  currentState.clear();
                  releaseLock();
	}

	/**
	 *Removes the key (and its corresponding value) from this hashtable.
	 *@param key - the key that needs to be removed.
	 *@return the value to which the key had been mapped in this hashtable, or null if the key did not have a mapping.
         * @exception  IllegalMonitorStateException - if the current thread is not the owner of this object's monitor.
	 */
	 
	public Object remove(String key) throws IllegalMonitorStateException{
		   requestLock();
                   Object ret = currentState.remove(key);
                   releaseLock();
                   return ret;
	}

  /**
   * Notifies about the status of the move execution. A move can fail or be successful.
   * @return true if the execution of move was perform with success. Otherwise, false.
   * @exception  IllegalMonitorStateException - if the current thread is not the owner of this object's monitor.
   *  @exception InterruptedException if another thread has interrupted the current thread. The interrupted status of the current thread is cleared when this exception is thrown.
   */
         public synchronized boolean isItSuccess() throws IllegalMonitorStateException, InterruptedException
	 
	 {
		 while(!isItDone.get())
			try{
			      wait();
			}
		    catch(InterruptedException ie){
		        System.out.println(ie);
		    }
                 notifyAll();
                 return Success.get();

	 }
	/**
	 * Reports about the status of a move.
	 * Any move after completing its execution must report about its status.
     * @param scs
         * @exception  IllegalMonitorStateException - if the current thread is not the owner of this object's monitor.
	 */
	 public synchronized void doneWithSuccess(boolean scs) throws IllegalMonitorStateException{
               Success.set(scs);
		      isItDone.set(true);
		      notifyAll();
	 }
	 public synchronized void SetIsItDone(boolean scs) throws IllegalMonitorStateException{
		 isItDone.set(scs);
		 notifyAll();
	 }

    @Override
        @SuppressWarnings("CloneDeclaresCloneNotSupported")
     public State clone() {
         
         State ret;
       
             ret = new State();
         requestLock();
             Iterator<String> itr = currentState.keySet().iterator();
             String bf;
             while(itr.hasNext()){
                 bf=itr.next();
                 ret.put(bf,currentState.get(bf));
             }
          releaseLock();
         return ret;
     }
}
