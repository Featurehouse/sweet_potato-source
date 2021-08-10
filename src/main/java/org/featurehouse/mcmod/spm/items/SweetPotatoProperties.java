package org.featurehouse.mcmod.spm.items;

import org.featurehouse.mcmod.spm.util.objsettings.sweetpotato.SweetPotatoStatus;
import org.featurehouse.mcmod.spm.util.objsettings.sweetpotato.SweetPotatoType;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public interface SweetPotatoProperties {
    SweetPotatoStatus getStatus();
    SweetPotatoType asType();
}
