package io.github.teddyxlandlee.sweet_potato.util;

@Deprecated
public class InvalidStatusException extends Exception {
    public InvalidStatusException(String reason) {
        super(reason);
    }
}
