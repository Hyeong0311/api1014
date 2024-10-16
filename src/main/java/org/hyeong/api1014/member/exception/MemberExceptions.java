package org.hyeong.api1014.member.exception;

public enum MemberExceptions {

    BAD_AUTH(400, "ID/PW incorrect");

    private MemberTaskException exception;

    MemberExceptions(int status, String msg) {

        this.exception = new MemberTaskException(status, msg);
    }

    MemberTaskException get() {
        return exception;
    }
}
