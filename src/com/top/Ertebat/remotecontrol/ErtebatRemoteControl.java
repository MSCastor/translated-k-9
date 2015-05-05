package com.top.Ertebat.remotecontrol;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Utillity definitions for Android applications to control the behavior of K-9 Mail.  All such applications must declare the following permission:
 * <uses-permission android:name="com.top.Ertebat.permission.REMOTE_CONTROL"/>
 * in their AndroidManifest.xml  In addition, all applications sending remote control messages to K-9 Mail must
 *
 * An application that wishes to act on a particular Account in K-9 needs to fetch the list of configured Accounts by broadcasting an
 * {@link Intent} using Ertebat_REQUEST_ACCOUNTS as the Action.  The broadcast must be made using the {@link ContextWrapper}
 * sendOrderedBroadcast(Intent intent, String receiverPermission, BroadcastReceiver resultReceiver,
 * Handler scheduler, int initialCode, String initialData, Bundle initialExtras).sendOrderedBroadcast}
 * method in order to receive the list of Account UUIDs and descriptions that K-9 will provide.
 *
 * @author Daniel I. Applebaum
 *
 */
public class ErtebatRemoteControl {
    /**
     * Permission that every application sending a broadcast to K-9 for Remote Control purposes should send on every broadcast.
     * Prevent other applications from intercepting the broadcasts.
     */
    public final static String Ertebat_REMOTE_CONTROL_PERMISSION = "com.top.Ertebat.permission.REMOTE_CONTROL";
    /**
     * {@link Intent} Action to be sent to K-9 using {@link ContextWrapper.sendOrderedBroadcast} in order to fetch the list of configured Accounts.
     * The responseData will contain two String[] with keys Ertebat_ACCOUNT_UUIDS and Ertebat_ACCOUNT_DESCRIPTIONS
     */
    public final static String Ertebat_REQUEST_ACCOUNTS = "com.top.Ertebat.ErtebatRemoteControl.requestAccounts";
    public final static String Ertebat_ACCOUNT_UUIDS = "com.top.Ertebat.ErtebatRemoteControl.accountUuids";
    public final static String Ertebat_ACCOUNT_DESCRIPTIONS = "com.top.Ertebat.ErtebatRemoteControl.accountDescriptions";

    /**
     * The {@link {@link Intent}} Action to set in order to cause K-9 to check mail.  (Not yet implemented)
     */
    //public final static String Ertebat_CHECK_MAIL = "com.top.Ertebat.ErtebatRemoteControl.checkMail";

    /**
     * The {@link {@link Intent}} Action to set when remotely changing K-9 Mail settings
     */
    public final static String Ertebat_SET = "com.top.Ertebat.ErtebatRemoteControl.set";
    /**
     * The key of the {@link Intent} Extra to set to hold the UUID of a single Account's settings to change.  Used only if Ertebat_ALL_ACCOUNTS
     * is absent or false.
     */
    public final static String Ertebat_ACCOUNT_UUID = "com.top.Ertebat.ErtebatRemoteControl.accountUuid";
    /**
     * The key of the {@link Intent} Extra to set to control if the settings will apply to all Accounts, or to the one
     * specified with Ertebat_ACCOUNT_UUID
     */
    public final static String Ertebat_ALL_ACCOUNTS = "com.top.Ertebat.ErtebatRemoteControl.allAccounts";

    public final static String Ertebat_ENABLED = "true";
    public final static String Ertebat_DISABLED = "false";

    /*
     * Key for the {@link Intent} Extra for controlling whether notifications will be generated for new unread mail.
     * Acceptable values are Ertebat_ENABLED and Ertebat_DISABLED
     */
    public final static String Ertebat_NOTIFICATION_ENABLED = "com.top.Ertebat.ErtebatRemoteControl.notificationEnabled";
    /*
     * Key for the {@link Intent} Extra for controlling whether K-9 will sound the ringtone for new unread mail.
     * Acceptable values are Ertebat_ENABLED and Ertebat_DISABLED
     */
    public final static String Ertebat_RING_ENABLED = "com.top.Ertebat.ErtebatRemoteControl.ringEnabled";
    /*
     * Key for the {@link Intent} Extra for controlling whether K-9 will activate the vibrator for new unread mail.
     * Acceptable values are Ertebat_ENABLED and Ertebat_DISABLED
     */
    public final static String Ertebat_VIBRATE_ENABLED = "com.top.Ertebat.ErtebatRemoteControl.vibrateEnabled";

    public final static String Ertebat_FOLDERS_NONE = "NONE";
    public final static String Ertebat_FOLDERS_ALL = "ALL";
    public final static String Ertebat_FOLDERS_FIRST_CLASS = "FIRST_CLASS";
    public final static String Ertebat_FOLDERS_FIRST_AND_SECOND_CLASS = "FIRST_AND_SECOND_CLASS";
    public final static String Ertebat_FOLDERS_NOT_SECOND_CLASS = "NOT_SECOND_CLASS";
    /**
     * Key for the {@link Intent} Extra to set for controlling which folders to be synchronized with Push.
     * Acceptable values are Ertebat_FOLDERS_ALL, Ertebat_FOLDERS_FIRST_CLASS, Ertebat_FOLDERS_FIRST_AND_SECOND_CLASS,
     * Ertebat_FOLDERS_NOT_SECOND_CLASS, Ertebat_FOLDERS_NONE
     */
    public final static String Ertebat_PUSH_CLASSES = "com.top.Ertebat.ErtebatRemoteControl.pushClasses";
    /**
     * Key for the {@link Intent} Extra to set for controlling which folders to be synchronized with Poll.
     * Acceptable values are Ertebat_FOLDERS_ALL, Ertebat_FOLDERS_FIRST_CLASS, Ertebat_FOLDERS_FIRST_AND_SECOND_CLASS,
     * Ertebat_FOLDERS_NOT_SECOND_CLASS, Ertebat_FOLDERS_NONE
     */
    public final static String Ertebat_POLL_CLASSES = "com.top.Ertebat.ErtebatRemoteControl.pollClasses";

    public final static String[] Ertebat_POLL_FREQUENCIES = { "-1", "1", "5", "10", "15", "30", "60", "120", "180", "360", "720", "1440"};
    /**
     * Key for the {@link Intent} Extra to set with the desired poll frequency.  The value is a String representing a number of minutes.
     * Acceptable values are available in Ertebat_POLL_FREQUENCIES
     */
    public final static String Ertebat_POLL_FREQUENCY = "com.top.Ertebat.ErtebatRemoteControl.pollFrequency";

    /**
     * Key for the {@link Intent} Extra to set for controlling K-9's global "Background sync" setting.
     * Acceptable values are Ertebat_BACKGROUND_OPERATIONS_ALWAYS, Ertebat_BACKGROUND_OPERATIONS_NEVER
     * Ertebat_BACKGROUND_OPERATIONS_WHEN_CHECKED_AUTO_SYNC
     */
    public final static String Ertebat_BACKGROUND_OPERATIONS = "com.top.Ertebat.ErtebatRemoteControl.backgroundOperations";
    public final static String Ertebat_BACKGROUND_OPERATIONS_ALWAYS = "ALWAYS";
    public final static String Ertebat_BACKGROUND_OPERATIONS_NEVER = "NEVER";
    public final static String Ertebat_BACKGROUND_OPERATIONS_WHEN_CHECKED_AUTO_SYNC = "WHEN_CHECKED_AUTO_SYNC";

    /**
     * Key for the {@link Intent} Extra to set for controlling which display theme K-9 will use.  Acceptable values are
     * Ertebat_THEME_LIGHT, Ertebat_THEME_DARK
     */
    public final static String Ertebat_THEME = "com.top.Ertebat.ErtebatRemoteControl.theme";
    public final static String Ertebat_THEME_LIGHT = "LIGHT";
    public final static String Ertebat_THEME_DARK = "DARK";

    protected static String LOG_TAG = "ErtebatRemoteControl";

    public static void set(Context context, Intent broadcastIntent) {
        broadcastIntent.setAction(ErtebatRemoteControl.Ertebat_SET);
        context.sendBroadcast(broadcastIntent, ErtebatRemoteControl.Ertebat_REMOTE_CONTROL_PERMISSION);
    }

    public static void fetchAccounts(Context context, ErtebatAccountReceptor receptor) {
        Intent accountFetchIntent = new Intent();
        accountFetchIntent.setAction(ErtebatRemoteControl.Ertebat_REQUEST_ACCOUNTS);
        AccountReceiver receiver = new AccountReceiver(receptor);
        context.sendOrderedBroadcast(accountFetchIntent, ErtebatRemoteControl.Ertebat_REMOTE_CONTROL_PERMISSION, receiver, null, Activity.RESULT_OK, null, null);
    }

}


