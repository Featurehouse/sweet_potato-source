package io.github.teddyxlandlee.sweet_potato.util;

import io.github.teddyxlandlee.sweet_potato.SweetPotatoComponent;
import io.github.teddyxlandlee.sweet_potato.SweetPotatoStatus;
import io.github.teddyxlandlee.sweet_potato.SweetPotatoType;
import net.minecraft.item.Item;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SweetPotatoSettings extends Item.Settings {
    Logger logger = LogManager.getLogger();

    public SweetPotatoSettings() {
        super();
    }

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
