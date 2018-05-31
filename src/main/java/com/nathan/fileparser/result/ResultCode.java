package com.nathan.fileparser.result;

import org.apache.http.HttpStatus;

/**
 * Important: These codes must remains in sync with the S.error.resultCode data structure in JavaScript.
 */
public enum ResultCode {
    /**
     * Success
     */
    OK(HttpStatus.SC_OK),

    /**
     * An unexpected exception occurred. There's nothing the user can do to correct the problem.
     */
    GENERAL_ERROR,

    /**
     * The item could not be found
     */
    NOT_FOUND(HttpStatus.SC_NOT_FOUND),

    /**
     * This feature has not yet been implemented.
     */
    UNIMPLEMENTED(HttpStatus.SC_NOT_IMPLEMENTED),

    /**
     * Feature is not supported by this component.
     */
    NOT_SUPPORTED(HttpStatus.SC_BAD_REQUEST),

    /**
     * One or more fields contains data that cannot be accepted. The operation cannot be completed.
     */
    VALIDATION_ERROR(HttpStatus.SC_BAD_REQUEST);

    private final int httpStatusCode;

    private ResultCode() {
        this.httpStatusCode = HttpStatus.SC_INTERNAL_SERVER_ERROR;
    }

    private ResultCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    /**
     * Gets the HTTP status code associated with this result code. This code should be used on the status line of HTTP responses.
     */
    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
