package io.featurehouse.spm.util.properties.objects;

import io.featurehouse.spm.SweetPotatoComponent;
import io.featurehouse.spm.SweetPotatoStatus;
import io.featurehouse.spm.SweetPotatoType;
import net.minecraft.item.Item;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Deprecated
public class SweetPotatoSettings extends Item.Settings {
    Logger logger = LogManager.getLogger();

    public SweetPotatoSettings() {
        super();
    }

    @Deprecated
    public SweetPotatoSettings food(SweetPotatoType type, SweetPotatoStatus status) {
        SweetPotatoComponent component = type.getComponent(status);
        if (component != null)
            this.food(component.asFoodComponent());
        else
            logger.catching(Level.WARN, new SweetPotatoComponent.InvalidComponentException(
                    "A programmer tries to input null component."
            ));
        return this;
    }
}
