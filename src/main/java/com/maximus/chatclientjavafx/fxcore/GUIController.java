package com.maximus.chatclientjavafx.fxcore;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public abstract class GUIController {

    class TimerWorker extends TimerTask {
        public void run() {
            TimerFunc();
        }
    }

    private Timer refreshTimer;
    private TimerWorker timerWorker;
//    private ReentrantLock refreshLock;
//    private Condition refreshCondition;

    protected Scene scene;

    public abstract void onShow();

    public void setScene(Scene s){
        this.scene = s;
    }

    public void closeWindow() {
        Stage stage = (Stage)scene.getWindow();
        stage.close();
    }

//    protected void waitRefresh() {
//        if(refreshCondition != null) {
//            refreshLock.lock();
//            try {
//                refreshCondition.await();
//            } catch(InterruptedException ex) {
//                //
//            }
//            finally {
//                refreshLock.unlock();
//            }
//        }
//    }
//    public void emitRefresh() {
//        if(refreshCondition != null) {
//            refreshLock.lock();
//            try {
//                refreshCondition.signal();
//            } catch(Exception ex) {
//                System.out.println("achtung!!!!");
//            } finally {
//                refreshLock.unlock();
//            }
//
//        }
//    }
    protected abstract void TimerFunc();
    protected void EnableTimer(long delay, long period) {
        timerWorker = new TimerWorker();
//        refreshLock = new ReentrantLock();
//        refreshCondition = refreshLock.newCondition();
        refreshTimer = new Timer("GuiTimer", true);
        refreshTimer.schedule(timerWorker, delay, period);
    }
    protected void DisableTimer() {
        refreshTimer.cancel();
    }

}
