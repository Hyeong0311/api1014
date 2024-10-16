package org.hyeong.api1014.member.exception;

public class MemberTaskException extends RuntimeException {

    private int status;

    private String msg;

    public MemberTaskException(final int status, final String msg) {
        super(status + "_" + msg);
        this.status = status;
        this.msg = msg;
    }
}
