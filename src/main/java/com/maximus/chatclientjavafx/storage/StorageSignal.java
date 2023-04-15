package com.maximus.chatclientjavafx.storage;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class StorageSignal {
    private ReentrantLock lock;
    private Condition condition;

    public StorageSignal() {
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public void awaitSignal() {
        if(condition != null) {
            lock.lock();
            try {
                condition.await();
            } catch(InterruptedException ex) {
                //
            }
            finally {
                lock.unlock();
            }
        }
    }
    public void awaitSignal(long nanosTimeout) {
        if(condition != null) {
            lock.lock();
            try {
                condition.awaitNanos(nanosTimeout);
            } catch(InterruptedException ex) {
                System.out.println("awaitSignal(long nanosTimeout) ERROR: " + ex.getMessage());
            }
            finally {
                lock.unlock();
            }
        }
    }
    public void emitSignal() {
        if(condition != null) {
            lock.lock();
            try {
                condition.signal();
            } catch(Exception ex) {
                System.out.println("emitSignal() ERROR: " + ex.getMessage());
            } finally {
                lock.unlock();
            }

        }
    }
    public void emitSignalAll() {
        if(condition != null) {
            lock.lock();
            try {
                condition.signalAll();
                System.out.println("TID=" + Thread.currentThread().getId() + ": emitSignalAll()");
            } catch(Exception ex) {
                System.out.println("emitSignalAll() ERROR: " + ex.getMessage());
            } finally {
                lock.unlock();
            }

        }
    }
}
