package io.featurehouse.spm.items;

import io.featurehouse.spm.SweetPotatoStatus;
import io.featurehouse.spm.SweetPotatoType;

public interface WithStatus {
    SweetPotatoStatus getStatus();
    SweetPotatoType asType();
}
