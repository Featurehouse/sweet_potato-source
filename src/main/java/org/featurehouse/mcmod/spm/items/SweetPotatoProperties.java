package org.featurehouse.mcmod.spm.items;

import org.featurehouse.mcmod.spm.sweetpotato.SweetPotatoStatus;
import org.featurehouse.mcmod.spm.sweetpotato.SweetPotatoType;

public interface SweetPotatoProperties {
    SweetPotatoStatus getStatus();
    SweetPotatoType asType();
}
