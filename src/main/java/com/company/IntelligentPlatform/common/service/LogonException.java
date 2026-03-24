package com.company.IntelligentPlatform.common.service;

public class LogonException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -655568796433364044L;

    public static final String NON_LOGON_USER = "No logon user";

    public static final String NON_LOGON_ORG = "No logon organization";

    public LogonException(String header) {
        super(header);
    }

    public LogonException(String header, String var) {
        super(header + var);
    }

}
