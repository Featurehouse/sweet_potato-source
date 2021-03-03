package org.featurehouse.spm.items;

import org.featurehouse.spm.SweetPotatoStatus;
import org.featurehouse.spm.SweetPotatoType;

public interface WithStatus {
    SweetPotatoStatus getStatus();
    SweetPotatoType asType();
}
