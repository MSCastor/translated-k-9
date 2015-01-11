package com.fsck.ertebat.service;

import com.fsck.ertebat.Account;
import com.fsck.ertebat.ertebat;
import com.fsck.ertebat.remotecontrol.ertebatRemoteControl;
import com.fsck.ertebat.Preferences;
import com.top.ertebat.mail.R;
import com.fsck.ertebat.Account.FolderMode;
import com.fsck.ertebat.ertebat.BACKGROUND_OPS;

import static com.fsck.ertebat.remotecontrol.ertebatRemoteControl.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;

public class RemoteControlService extends CoreService {
    private final static String RESCHEDULE_ACTION = "com.fsck.ertebat.service.RemoteControlService.RESCHEDULE_ACTION";
    private final static String PUSH_RESTART_ACTION = "com.fsck.ertebat.service.RemoteControlService.PUSH_RESTART_ACTION";

    private final static String SET_ACTION = "com.fsck.ertebat.service.RemoteControlService.SET_ACTION";

    public static void set(Context context, Intent i, Integer wakeLockId) {
        //  Intent i = new Intent();
        i.setClass(context, RemoteControlService.class);
        i.setAction(RemoteControlService.SET_ACTION);
        addWakeLockId(context, i, wakeLockId, true);
        context.startService(i);
    }

    public static final int REMOTE_CONTROL_SERVICE_WAKE_LOCK_TIMEOUT = 20000;

    @Override
    public int startService(final Intent intent, final int startId) {
        if (ertebat.DEBUG)
            Log.i(ertebat.LOG_TAG, "RemoteControlService started with startId = " + startId);
        final Preferences preferences = Preferences.getPreferences(this);

        if (RESCHEDULE_ACTION.equals(intent.getAction())) {
            if (ertebat.DEBUG)
                Log.i(ertebat.LOG_TAG, "RemoteControlService requesting MailService poll reschedule");
            MailService.actionReschedulePoll(this, null);
        }
        if (PUSH_RESTART_ACTION.equals(intent.getAction())) {
            if (ertebat.DEBUG)
                Log.i(ertebat.LOG_TAG, "RemoteControlService requesting MailService push restart");
            MailService.actionRestartPushers(this, null);
        } else if (RemoteControlService.SET_ACTION.equals(intent.getAction())) {
            if (ertebat.DEBUG)
                Log.i(ertebat.LOG_TAG, "RemoteControlService got request to change settings");
            execute(getApplication(), new Runnable() {
                public void run() {
                    try {
                        boolean needsReschedule = false;
                        boolean needsPushRestart = false;
                        String uuid = intent.getStringExtra(ertebat_ACCOUNT_UUID);
                        boolean allAccounts = intent.getBooleanExtra(ertebat_ALL_ACCOUNTS, false);
                        if (ertebat.DEBUG) {
                            if (allAccounts) {
                                Log.i(ertebat.LOG_TAG, "RemoteControlService changing settings for all accounts");
                            } else {
                                Log.i(ertebat.LOG_TAG, "RemoteControlService changing settings for account with UUID " + uuid);
                            }
                        }
                        Account[] accounts = preferences.getAccounts();
                        for (Account account : accounts) {
                            //warning: account may not be isAvailable()
                            if (allAccounts || account.getUuid().equals(uuid)) {

                                if (ertebat.DEBUG)
                                    Log.i(ertebat.LOG_TAG, "RemoteControlService changing settings for account " + account.getDescription());

                                String notificationEnabled = intent.getStringExtra(ertebat_NOTIFICATION_ENABLED);
                                String ringEnabled = intent.getStringExtra(ertebat_RING_ENABLED);
                                String vibrateEnabled = intent.getStringExtra(ertebat_VIBRATE_ENABLED);
                                String pushClasses = intent.getStringExtra(ertebat_PUSH_CLASSES);
                                String pollClasses = intent.getStringExtra(ertebat_POLL_CLASSES);
                                String pollFrequency = intent.getStringExtra(ertebat_POLL_FREQUENCY);

                                if (notificationEnabled != null) {
                                    account.setNotifyNewMail(Boolean.parseBoolean(notificationEnabled));
                                }
                                if (ringEnabled != null) {
                                    account.getNotificationSetting().setRing(Boolean.parseBoolean(ringEnabled));
                                }
                                if (vibrateEnabled != null) {
                                    account.getNotificationSetting().setVibrate(Boolean.parseBoolean(vibrateEnabled));
                                }
                                if (pushClasses != null) {
                                    needsPushRestart |= account.setFolderPushMode(FolderMode.valueOf(pushClasses));
                                }
                                if (pollClasses != null) {
                                    needsReschedule |= account.setFolderSyncMode(FolderMode.valueOf(pollClasses));
                                }
                                if (pollFrequency != null) {
                                    String[] allowedFrequencies = getResources().getStringArray(R.array.account_settings_check_frequency_values);
                                    for (String allowedFrequency : allowedFrequencies) {
                                        if (allowedFrequency.equals(pollFrequency)) {
                                            Integer newInterval = Integer.parseInt(allowedFrequency);
                                            needsReschedule |= account.setAutomaticCheckIntervalMinutes(newInterval);
                                        }
                                    }
                                }
                                account.save(Preferences.getPreferences(RemoteControlService.this));
                            }
                        }
                        if (ertebat.DEBUG)
                            Log.i(ertebat.LOG_TAG, "RemoteControlService changing global settings");

                        String backgroundOps = intent.getStringExtra(ertebat_BACKGROUND_OPERATIONS);
                        if (ertebatRemoteControl.ertebat_BACKGROUND_OPERATIONS_ALWAYS.equals(backgroundOps)
                                || ertebatRemoteControl.ertebat_BACKGROUND_OPERATIONS_NEVER.equals(backgroundOps)
                                || ertebatRemoteControl.ertebat_BACKGROUND_OPERATIONS_WHEN_CHECKED_AUTO_SYNC.equals(backgroundOps)) {
                            BACKGROUND_OPS newBackgroundOps = BACKGROUND_OPS.valueOf(backgroundOps);
                            boolean needsReset = ertebat.setBackgroundOps(newBackgroundOps);
                            needsPushRestart |= needsReset;
                            needsReschedule |= needsReset;
                        }

                        String theme = intent.getStringExtra(ertebat_THEME);
                        if (theme != null) {
                            ertebat.setertebatTheme(ertebatRemoteControl.ertebat_THEME_DARK.equals(theme) ? ertebat.Theme.DARK : ertebat.Theme.LIGHT);
                        }

                        SharedPreferences sPrefs = preferences.getPreferences();

                        Editor editor = sPrefs.edit();
                        ertebat.save(editor);
                        editor.commit();

                        if (needsReschedule) {
                            Intent i = new Intent();
                            i.setClassName(getApplication().getPackageName(), "com.fsck.ertebat.service.RemoteControlService");
                            i.setAction(RESCHEDULE_ACTION);
                            long nextTime = System.currentTimeMillis() + 10000;
                            BootReceiver.scheduleIntent(RemoteControlService.this, nextTime, i);
                        }
                        if (needsPushRestart) {
                            Intent i = new Intent();
                            i.setClassName(getApplication().getPackageName(), "com.fsck.ertebat.service.RemoteControlService");
                            i.setAction(PUSH_RESTART_ACTION);
                            long nextTime = System.currentTimeMillis() + 10000;
                            BootReceiver.scheduleIntent(RemoteControlService.this, nextTime, i);
                        }
                    } catch (Exception e) {
                        Log.e(ertebat.LOG_TAG, "Could not handle ertebat_SET", e);
                        Toast toast = Toast.makeText(RemoteControlService.this, e.getMessage(), Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
            , RemoteControlService.REMOTE_CONTROL_SERVICE_WAKE_LOCK_TIMEOUT, startId);
        }

        return START_NOT_STICKY;
    }

}
