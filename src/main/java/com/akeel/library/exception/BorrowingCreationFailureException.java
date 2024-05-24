package com.akeel.library.exception;

public class BorrowingCreationFailureException extends RuntimeException{
    public BorrowingCreationFailureException() {
        super("Borrowing Record Creation Failure Exception");
    }
}
