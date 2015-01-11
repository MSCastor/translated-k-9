package com.fsck.ertebat.mail.transport.imap;

import com.fsck.ertebat.mail.AuthType;
import com.fsck.ertebat.mail.ConnectionSecurity;
import com.fsck.ertebat.mail.store.ImapStore;
import com.fsck.ertebat.mail.store.ImapStore.ImapConnection;

/**
 * Settings source for IMAP. Implemented in order to remove coupling between {@link ImapStore} and {@link ImapConnection}.
 */
public interface ImapSettings {
    String getHost();

    int getPort();

    ConnectionSecurity getConnectionSecurity();

    AuthType getAuthType();

    String getUsername();

    String getPassword();

    String getClientCertificateAlias();

    boolean useCompression(int type);

    String getPathPrefix();

    void setPathPrefix(String prefix);

    String getPathDelimeter();

    void setPathDelimeter(String delimeter);

    String getCombinedPrefix();

    void setCombinedPrefix(String prefix);
}
