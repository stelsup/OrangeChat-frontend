package com.maximus.chatclientjavafx.displaymanager;

import com.maximus.chatclientjavafx.fxcore.GUIController;
import com.maximus.chatclientjavafx.storage.StorageSignal;
import javafx.application.Platform;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

public class DisplayTimer {

    class TimerWorker extends TimerTask {
        public void run() {
            if(useTimeout)
                signal.awaitSignal(timeoutMs * 1000000);
            else
                signal.awaitSignal();

            System.out.println("[TID=" + Thread.currentThread().getId() + "] TimerWorker::run() sig");

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        executorObject.getClass().getMethod(executorMethodName).invoke(executorObject);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            });

            if(singleShotMode) {
//                cancel();
//                bBusy = false;
                stop();
            }
        }
    }

    private Timer refreshTimer;
    private TimerWorker timerWorker;

    private Object executorObject;
    private String executorMethodName;

    private StorageSignal signal;
    private boolean singleShotMode = false;
    private boolean useTimeout = false;

    private long timeoutMs = 5*1000;

    private boolean bBusy;


    public DisplayTimer(Object obj, String func, StorageSignal sig) {
        this.executorObject = obj;
        this.executorMethodName = func;
        this.signal = sig;
    }

    public void start(long startDelay, long tickPeriodMs, long timeoutMs) {
        this.timeoutMs = timeoutMs;
        this.useTimeout = true;
        timerWorker = new TimerWorker();
        refreshTimer = new Timer(executorMethodName, true);
        refreshTimer.schedule(timerWorker, startDelay, tickPeriodMs);
        System.out.println("[TID=" + Thread.currentThread().getId() + "] DisplayTimer::start()");
        bBusy = true;
    }

    public void start(long startDelay, long tickPeriodMs) {
        this.useTimeout = false;
        timerWorker = new TimerWorker();
        refreshTimer = new Timer(executorMethodName, true);
        refreshTimer.schedule(timerWorker, startDelay, tickPeriodMs);
        System.out.println("[TID=" + Thread.currentThread().getId() + "] DisplayTimer::start()");
        bBusy = true;
    }

    public void startSingleShot(long timeoutMs) {
        this.singleShotMode = true;
        this.start(0, 50, timeoutMs);
    }

    public void stop() {
        refreshTimer.cancel();
        bBusy = false;
    }

    public boolean isBusy() {
        return this.bBusy;
    }

    @Override
    protected void finalize() {
        stop();
    }
}
