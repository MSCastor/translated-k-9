package com.fsck.Ertebat.helper;

import android.util.Log;

import com.fsck.Ertebat.Account;
import com.fsck.Ertebat.Identity;
import com.fsck.Ertebat.Ertebat;
import com.fsck.Ertebat.mail.Address;
import com.fsck.Ertebat.mail.Message;
import com.fsck.Ertebat.mail.MessagingException;

public class IdentityHelper {

    /**
     * Find the identity a message was sent to.
     *
     * @param account
     *         The account the message belongs to.
     * @param message
     *         The message to get the recipients from.
     *
     * @return The identity the message was sent to, or the account's default identity if it
     *         couldn't be determined which identity this message was sent to.
     *
     * @see Account#findIdentity(com.fsck.Ertebat.mail.Address)
     */
    public static Identity getRecipientIdentityFromMessage(Account account, Message message) {
        Identity recipient = null;

        try {
            for (Address address : message.getRecipients(Message.RecipientType.TO)) {
                Identity identity = account.findIdentity(address);
                if (identity != null) {
                    recipient = identity;
                    break;
                }
            }
            if (recipient == null) {
                Address[] ccAddresses = message.getRecipients(Message.RecipientType.CC);
                if (ccAddresses.length > 0) {
                    for (Address address : ccAddresses) {
                        Identity identity = account.findIdentity(address);
                        if (identity != null) {
                            recipient = identity;
                            break;
                        }
                    }
                }
            }
        } catch (MessagingException e) {
            Log.w(Ertebat.LOG_TAG, "Error finding the identity this message was sent to", e);
        }

        if (recipient == null) {
            recipient = account.getIdentity(0);
        }

        return recipient;
    }
}
