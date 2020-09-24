package io.github.teddyxlandlee.sweet_potato.items;

import io.github.teddyxlandlee.sweet_potato.SweetPotatoStatus;
import io.github.teddyxlandlee.sweet_potato.SweetPotatoType;

public interface WithStatus {
    SweetPotatoStatus getStatus();
    SweetPotatoType asType();
}
