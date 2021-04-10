package org.featurehouse.spm.util.properties.fproperties;

import org.featurehouse.spm.util.FloatIntegerizer;
import net.minecraft.network.PacketByteBuf;

import org.jetbrains.annotations.NotNull;

@Deprecated
public class GrinderProperties {
    public int grindTime;
    public int grindTimeTotal;
    public float ingredientData;

    public GrinderProperties(int grindTime, int grindTimeTotal, float ingredientData) {
        this.grindTime = grindTime;
        this.grindTimeTotal = grindTimeTotal;
        this.ingredientData = ingredientData;
    }

    // -*- WRITE -*- //

    public void fillPacketByteBuf(@NotNull PacketByteBuf buf) {
        int fromFloat = FloatIntegerizer.fromFloat(ingredientData);
        byte[] data = new byte[] {
                (byte) ((grindTime >> 24) & 0b11111111),
                (byte) ((grindTime >> 16) & 0b11111111),
                (byte) ((grindTime >> 8)  & 0b11111111),
                (byte) ((grindTime)       & 0b11111111),

                (byte) ((grindTimeTotal >> 24) & 0b11111111),
                (byte) ((grindTimeTotal >> 16) & 0b11111111),
                (byte) ((grindTimeTotal >> 8)  & 0b11111111),
                (byte) ((grindTimeTotal)       & 0b11111111),

                (byte) ((fromFloat >> 24) & 0b11111111),
                (byte) ((fromFloat >> 16) & 0b11111111),
                (byte) ((fromFloat >> 8)  & 0b11111111),
                (byte) ((fromFloat)       & 0b11111111)
        };

        //buf.writeBytes(data);
        buf.writeByteArray(data);
    }

    public static void fillPacketByteBuf(int grindTime, int grindTimeTotal, float ingredientData, PacketByteBuf buf) {
        new GrinderProperties(grindTime, grindTimeTotal, ingredientData).fillPacketByteBuf(buf);
    }

    // -*- READ -*- //

    public static GrinderProperties readFromPacketByteBuf(@NotNull PacketByteBuf buf) {
        byte[] data = buf.readByteArray();

        int grindTime = (data[0] << 24) + (data[1] << 16) + (data[2] << 8) + data[3];
        int grindTimeTotal = (data[4] << 24) + (data[5] << 16) + (data[6] << 8) + data[7];
        int ingredientData$1 = (data[8] << 24) + (data[9] << 16) + (data[10] << 8) + data[11];
        float ingredientData = FloatIntegerizer.toFloat(ingredientData$1);
        return new GrinderProperties(grindTime, grindTimeTotal, ingredientData);
    }

    // -*- STATIC -*- //

    public int getGrindTime() {
        return grindTime;
    }

    public int getGrindTimeTotal() {
        return grindTimeTotal;
    }

    public float getIngredientData() {
        return ingredientData;
    }

    public void setGrindTime(int grindTime) {
        this.grindTime = grindTime;
    }

    public void setGrindTimeTotal(int grindTimeTotal) {
        this.grindTimeTotal = grindTimeTotal;
    }

    public void setIngredientData(float ingredientData) {
        this.ingredientData = ingredientData;
    }
}
