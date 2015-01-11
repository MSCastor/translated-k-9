
package com.fsck.Ertebat.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.fsck.Ertebat.Account;
import com.fsck.Ertebat.Ertebat;
import com.fsck.Ertebat.remotecontrol.ErtebatRemoteControl;
import com.fsck.Ertebat.Preferences;

import static com.fsck.Ertebat.remotecontrol.ErtebatRemoteControl.*;

public class RemoteControlReceiver extends CoreReceiver {
    @Override
    public Integer receive(Context context, Intent intent, Integer tmpWakeLockId) {
        if (Ertebat.DEBUG)
            Log.i(Ertebat.LOG_TAG, "RemoteControlReceiver.onReceive" + intent);

        if (ErtebatRemoteControl.Ertebat_SET.equals(intent.getAction())) {
            RemoteControlService.set(context, intent, tmpWakeLockId);
            tmpWakeLockId = null;
        } else if (ErtebatRemoteControl.Ertebat_REQUEST_ACCOUNTS.equals(intent.getAction())) {
            try {
                Preferences preferences = Preferences.getPreferences(context);
                Account[] accounts = preferences.getAccounts();
                String[] uuids = new String[accounts.length];
                String[] descriptions = new String[accounts.length];
                for (int i = 0; i < accounts.length; i++) {
                    //warning: account may not be isAvailable()
                    Account account = accounts[i];

                    uuids[i] = account.getUuid();
                    descriptions[i] = account.getDescription();
                }
                Bundle bundle = getResultExtras(true);
                bundle.putStringArray(Ertebat_ACCOUNT_UUIDS, uuids);
                bundle.putStringArray(Ertebat_ACCOUNT_DESCRIPTIONS, descriptions);
            } catch (Exception e) {
                Log.e(Ertebat.LOG_TAG, "Could not handle Ertebat_RESPONSE_INTENT", e);
            }

        }

        return tmpWakeLockId;
    }

}
