package org.featurehouse.mcmod.spm.items;

import org.featurehouse.mcmod.spm.SweetPotatoStatus;
import org.featurehouse.mcmod.spm.SweetPotatoType;

public interface WithStatus {
    SweetPotatoStatus getStatus();
    SweetPotatoType asType();
}
