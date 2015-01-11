package com.fsck.ertebat.remotecontrol;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Utillity definitions for Android applications to control the behavior of K-9 Mail.  All such applications must declare the following permission:
 * <uses-permission android:name="com.fsck.ertebat.permission.REMOTE_CONTROL"/>
 * in their AndroidManifest.xml  In addition, all applications sending remote control messages to K-9 Mail must
 *
 * An application that wishes to act on a particular Account in K-9 needs to fetch the list of configured Accounts by broadcasting an
 * {@link Intent} using ertebat_REQUEST_ACCOUNTS as the Action.  The broadcast must be made using the {@link ContextWrapper}
 * sendOrderedBroadcast(Intent intent, String receiverPermission, BroadcastReceiver resultReceiver,
 * Handler scheduler, int initialCode, String initialData, Bundle initialExtras).sendOrderedBroadcast}
 * method in order to receive the list of Account UUIDs and descriptions that K-9 will provide.
 *
 * @author Daniel I. Applebaum
 *
 */
public class ertebatRemoteControl {
    /**
     * Permission that every application sending a broadcast to K-9 for Remote Control purposes should send on every broadcast.
     * Prevent other applications from intercepting the broadcasts.
     */
    public final static String ertebat_REMOTE_CONTROL_PERMISSION = "com.fsck.ertebat.permission.REMOTE_CONTROL";
    /**
     * {@link Intent} Action to be sent to K-9 using {@link ContextWrapper.sendOrderedBroadcast} in order to fetch the list of configured Accounts.
     * The responseData will contain two String[] with keys ertebat_ACCOUNT_UUIDS and ertebat_ACCOUNT_DESCRIPTIONS
     */
    public final static String ertebat_REQUEST_ACCOUNTS = "com.fsck.ertebat.ertebatRemoteControl.requestAccounts";
    public final static String ertebat_ACCOUNT_UUIDS = "com.fsck.ertebat.ertebatRemoteControl.accountUuids";
    public final static String ertebat_ACCOUNT_DESCRIPTIONS = "com.fsck.ertebat.ertebatRemoteControl.accountDescriptions";

    /**
     * The {@link {@link Intent}} Action to set in order to cause K-9 to check mail.  (Not yet implemented)
     */
    //public final static String ertebat_CHECK_MAIL = "com.fsck.ertebat.ertebatRemoteControl.checkMail";

    /**
     * The {@link {@link Intent}} Action to set when remotely changing K-9 Mail settings
     */
    public final static String ertebat_SET = "com.fsck.ertebat.ertebatRemoteControl.set";
    /**
     * The key of the {@link Intent} Extra to set to hold the UUID of a single Account's settings to change.  Used only if ertebat_ALL_ACCOUNTS
     * is absent or false.
     */
    public final static String ertebat_ACCOUNT_UUID = "com.fsck.ertebat.ertebatRemoteControl.accountUuid";
    /**
     * The key of the {@link Intent} Extra to set to control if the settings will apply to all Accounts, or to the one
     * specified with ertebat_ACCOUNT_UUID
     */
    public final static String ertebat_ALL_ACCOUNTS = "com.fsck.ertebat.ertebatRemoteControl.allAccounts";

    public final static String ertebat_ENABLED = "true";
    public final static String ertebat_DISABLED = "false";

    /*
     * Key for the {@link Intent} Extra for controlling whether notifications will be generated for new unread mail.
     * Acceptable values are ertebat_ENABLED and ertebat_DISABLED
     */
    public final static String ertebat_NOTIFICATION_ENABLED = "com.fsck.ertebat.ertebatRemoteControl.notificationEnabled";
    /*
     * Key for the {@link Intent} Extra for controlling whether K-9 will sound the ringtone for new unread mail.
     * Acceptable values are ertebat_ENABLED and ertebat_DISABLED
     */
    public final static String ertebat_RING_ENABLED = "com.fsck.ertebat.ertebatRemoteControl.ringEnabled";
    /*
     * Key for the {@link Intent} Extra for controlling whether K-9 will activate the vibrator for new unread mail.
     * Acceptable values are ertebat_ENABLED and ertebat_DISABLED
     */
    public final static String ertebat_VIBRATE_ENABLED = "com.fsck.ertebat.ertebatRemoteControl.vibrateEnabled";

    public final static String ertebat_FOLDERS_NONE = "NONE";
    public final static String ertebat_FOLDERS_ALL = "ALL";
    public final static String ertebat_FOLDERS_FIRST_CLASS = "FIRST_CLASS";
    public final static String ertebat_FOLDERS_FIRST_AND_SECOND_CLASS = "FIRST_AND_SECOND_CLASS";
    public final static String ertebat_FOLDERS_NOT_SECOND_CLASS = "NOT_SECOND_CLASS";
    /**
     * Key for the {@link Intent} Extra to set for controlling which folders to be synchronized with Push.
     * Acceptable values are ertebat_FOLDERS_ALL, ertebat_FOLDERS_FIRST_CLASS, ertebat_FOLDERS_FIRST_AND_SECOND_CLASS,
     * ertebat_FOLDERS_NOT_SECOND_CLASS, ertebat_FOLDERS_NONE
     */
    public final static String ertebat_PUSH_CLASSES = "com.fsck.ertebat.ertebatRemoteControl.pushClasses";
    /**
     * Key for the {@link Intent} Extra to set for controlling which folders to be synchronized with Poll.
     * Acceptable values are ertebat_FOLDERS_ALL, ertebat_FOLDERS_FIRST_CLASS, ertebat_FOLDERS_FIRST_AND_SECOND_CLASS,
     * ertebat_FOLDERS_NOT_SECOND_CLASS, ertebat_FOLDERS_NONE
     */
    public final static String ertebat_POLL_CLASSES = "com.fsck.ertebat.ertebatRemoteControl.pollClasses";

    public final static String[] ertebat_POLL_FREQUENCIES = { "-1", "1", "5", "10", "15", "30", "60", "120", "180", "360", "720", "1440"};
    /**
     * Key for the {@link Intent} Extra to set with the desired poll frequency.  The value is a String representing a number of minutes.
     * Acceptable values are available in ertebat_POLL_FREQUENCIES
     */
    public final static String ertebat_POLL_FREQUENCY = "com.fsck.ertebat.ertebatRemoteControl.pollFrequency";

    /**
     * Key for the {@link Intent} Extra to set for controlling K-9's global "Background sync" setting.
     * Acceptable values are ertebat_BACKGROUND_OPERATIONS_ALWAYS, ertebat_BACKGROUND_OPERATIONS_NEVER
     * ertebat_BACKGROUND_OPERATIONS_WHEN_CHECKED_AUTO_SYNC
     */
    public final static String ertebat_BACKGROUND_OPERATIONS = "com.fsck.ertebat.ertebatRemoteControl.backgroundOperations";
    public final static String ertebat_BACKGROUND_OPERATIONS_ALWAYS = "ALWAYS";
    public final static String ertebat_BACKGROUND_OPERATIONS_NEVER = "NEVER";
    public final static String ertebat_BACKGROUND_OPERATIONS_WHEN_CHECKED_AUTO_SYNC = "WHEN_CHECKED_AUTO_SYNC";

    /**
     * Key for the {@link Intent} Extra to set for controlling which display theme K-9 will use.  Acceptable values are
     * ertebat_THEME_LIGHT, ertebat_THEME_DARK
     */
    public final static String ertebat_THEME = "com.fsck.ertebat.ertebatRemoteControl.theme";
    public final static String ertebat_THEME_LIGHT = "LIGHT";
    public final static String ertebat_THEME_DARK = "DARK";

    protected static String LOG_TAG = "ertebatRemoteControl";

    public static void set(Context context, Intent broadcastIntent) {
        broadcastIntent.setAction(ertebatRemoteControl.ertebat_SET);
        context.sendBroadcast(broadcastIntent, ertebatRemoteControl.ertebat_REMOTE_CONTROL_PERMISSION);
    }

    public static void fetchAccounts(Context context, ertebatAccountReceptor receptor) {
        Intent accountFetchIntent = new Intent();
        accountFetchIntent.setAction(ertebatRemoteControl.ertebat_REQUEST_ACCOUNTS);
        AccountReceiver receiver = new AccountReceiver(receptor);
        context.sendOrderedBroadcast(accountFetchIntent, ertebatRemoteControl.ertebat_REMOTE_CONTROL_PERMISSION, receiver, null, Activity.RESULT_OK, null, null);
    }

}


