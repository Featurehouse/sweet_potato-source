package io.github.teddyxlandlee.sweet_potato.util.network;

import io.github.teddyxlandlee.annotation.Unused_InsteadOf;

@Unused_InsteadOf
@Deprecated
public interface TriAccessor<A, B, C> {
    A getFirst();
    B getSecond();
    C getThird();

    void setFirst(A obj);
    void setSecond(B obj);
    void setThird(C obj);
}
