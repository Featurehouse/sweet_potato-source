package org.featurehouse.mcmod.spm.util;

@Deprecated
public class InvalidStatusException extends Exception {
    public InvalidStatusException(String reason) {
        super(reason);
    }
}
