package com.top.Ertebat.controller;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.top.Ertebat.Account;
import com.top.Ertebat.Ertebat;
import com.top.Ertebat.helper.power.TracingPowerManager.TracingWakeLock;
import com.top.Ertebat.mail.Folder;

import com.top.Ertebat.mail.Message;
import com.top.Ertebat.mail.PushReceiver;
import com.top.Ertebat.mail.store.LocalStore;
import com.top.Ertebat.mail.store.LocalStore.LocalFolder;
import com.top.Ertebat.service.SleepService;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MessagingControllerPushReceiver implements PushReceiver {
    final Account account;
    final MessagingController controller;
    final Application mApplication;

    public MessagingControllerPushReceiver(Application nApplication, Account nAccount, MessagingController nController) {
        account = nAccount;
        controller = nController;
        mApplication = nApplication;
    }

    public void messagesFlagsChanged(Folder folder,
                                     List<Message> messages) {
        controller.messagesArrived(account, folder, messages, true);
    }
    public void messagesArrived(Folder folder, List<Message> messages) {
        controller.messagesArrived(account, folder, messages, false);
    }
    public void messagesRemoved(Folder folder, List<Message> messages) {
        controller.messagesArrived(account, folder, messages, true);
    }

    public void syncFolder(Folder folder) {
        if (Ertebat.DEBUG)
            Log.v(Ertebat.LOG_TAG, "syncFolder(" + folder.getName() + ")");
        final CountDownLatch latch = new CountDownLatch(1);
        controller.synchronizeMailbox(account, folder.getName(), new MessagingListener() {
            @Override
            public void synchronizeMailboxFinished(Account account, String folder,
            int totalMessagesInMailbox, int numNewMessages) {
                latch.countDown();
            }

            @Override
            public void synchronizeMailboxFailed(Account account, String folder,
            String message) {
                latch.countDown();
            }
        }, folder);

        if (Ertebat.DEBUG)
            Log.v(Ertebat.LOG_TAG, "syncFolder(" + folder.getName() + ") about to await latch release");
        try {
            latch.await();
            if (Ertebat.DEBUG)
                Log.v(Ertebat.LOG_TAG, "syncFolder(" + folder.getName() + ") got latch release");
        } catch (Exception e) {
            Log.e(Ertebat.LOG_TAG, "Interrupted while awaiting latch release", e);
        }
    }

    @Override
    public void sleep(TracingWakeLock wakeLock, long millis) {
        SleepService.sleep(mApplication, millis, wakeLock, Ertebat.PUSH_WAKE_LOCK_TIMEOUT);
    }

    public void pushError(String errorMessage, Exception e) {
        String errMess = errorMessage;

        controller.notifyUserIfCertificateProblem(mApplication, e, account, true);
        if (errMess == null && e != null) {
            errMess = e.getMessage();
        }
        controller.addErrorMessage(account, errMess, e);
    }

    public String getPushState(String folderName) {
        LocalFolder localFolder = null;
        try {
            LocalStore localStore = account.getLocalStore();
            localFolder = localStore.getFolder(folderName);
            localFolder.open(Folder.OPEN_MODE_RW);
            return localFolder.getPushState();
        } catch (Exception e) {
            Log.e(Ertebat.LOG_TAG, "Unable to get push state from account " + account.getDescription()
                  + ", folder " + folderName, e);
            return null;
        } finally {
            if (localFolder != null) {
                localFolder.close();
            }
        }
    }

    public void setPushActive(String folderName, boolean enabled) {
        for (MessagingListener l : controller.getListeners()) {
            l.setPushActive(account, folderName, enabled);
        }
    }

    @Override
    public Context getContext() {
        return mApplication;
    }

}
