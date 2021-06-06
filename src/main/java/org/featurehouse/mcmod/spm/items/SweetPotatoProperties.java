package org.featurehouse.mcmod.spm.items;

import org.featurehouse.mcmod.spm.util.objsettings.sweetpotato.SweetPotatoStatus;
import org.featurehouse.mcmod.spm.util.objsettings.sweetpotato.SweetPotatoType;

public interface SweetPotatoProperties {
    SweetPotatoStatus getStatus();
    SweetPotatoType asType();
}
