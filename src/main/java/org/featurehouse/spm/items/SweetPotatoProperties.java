package org.featurehouse.spm.items;

import org.featurehouse.spm.SweetPotatoStatus;
import org.featurehouse.spm.SweetPotatoType;

public interface SweetPotatoProperties {
    SweetPotatoStatus getStatus();
    SweetPotatoType asType();
}
