//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package bilibili.ywsuoyi.gui;

import java.util.Iterator;

import com.github.teddyxlandlee.annotation.Unused_InsteadOf;
import com.github.teddyxlandlee.sweet_potato.screen.ModdedYwsuoyiScreenHandler;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;

public abstract class YwsuoyiScreen<T extends ScreenHandler> extends HandledScreen<T> {
    public DefaultedList<Button> buttons = DefaultedList.of();
    public DefaultedList<Scroll> scrolls = DefaultedList.of();
    public DefaultedList<Icon> icons = DefaultedList.of();
    public DefaultedList<Percentage> percentages = DefaultedList.of();
    public boolean hasButton = false;
    public boolean hasScroll = false;
    public boolean hasIcon = false;
    public boolean hasPercentage = false;
    public int scrollId = -1;
    public Identifier TEXTURE = new Identifier("ywsuoyi", "textures/gui/tempgui.png");

    public YwsuoyiScreen(T handler, PlayerInventory inventory, Text title, Identifier texture) {
        super(handler, inventory, title);
        this.TEXTURE = texture;
    }

    @Unused_InsteadOf @Deprecated
    public YwsuoyiScreen(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    public void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        assert this.client != null;

        BlockEntity e = (this.handler instanceof ModdedYwsuoyiScreenHandler ? ((ModdedYwsuoyiScreenHandler) this.handler).e : ((YwsuoyiScreenHandler) this.handler).e);

        this.client.getTextureManager().bindTexture(this.TEXTURE);
        int i = this.x;
        int j = this.y;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, 4);

        for(int a = 0; a < this.backgroundHeight - 8; ++a) {
            this.drawTexture(matrices, i, a + j + 4, 0, 4, this.backgroundWidth, 1);
        }

        this.drawTexture(matrices, i, j + this.backgroundHeight - 4, 0, 5, this.backgroundWidth, 4);
        this.renderslot(matrices);
        if (this.hasPercentage) {
            this.client.getTextureManager().bindTexture(this.TEXTURE);
            this.renderPercentage(matrices, e);
        }

        if (this.hasScroll) {
            this.client.getTextureManager().bindTexture(this.TEXTURE);
            this.renderScroll(matrices);
        }

        if (this.hasButton) {
            this.client.getTextureManager().bindTexture(this.TEXTURE);
            this.renderButton(matrices, mouseX, mouseY);
        }

        if (this.hasIcon) {
            this.client.getTextureManager().bindTexture(this.TEXTURE);

            for (Icon icon : this.icons) {
                this.client.getItemRenderer().renderInGuiWithOverrides(icon.i, icon.x + this.x, icon.y + this.y);
            }
        }

    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.scrollId = -1;
        if (this.hasButton) {

            for (Button b : this.buttons) {
                b.clicked = false;
                if (b.isclicked(mouseX - (double) this.x, mouseY - (double) this.y)) {
                    assert this.client != null;

                    this.handler.onButtonClick(this.client.player, b.id);
                    MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    b.clicked = true;
                    break;
                }
            }
        }

        if (this.hasScroll) {
            for(int a = 0; a < this.scrolls.size(); ++a) {
                if (this.scrolls.get(a).isclicked(mouseX - (double)this.x, mouseY - (double)this.y)) {
                    this.scrollId = a;
                    this.scrolls.get(a).clicked = true;
                    break;
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        Iterator<?> var6;
        Button b;
        for(var6 = this.buttons.iterator(); var6.hasNext(); b.clicked = false) {
            b = (Button)var6.next();
        }

        Scroll s;
        for(var6 = this.scrolls.iterator(); var6.hasNext(); s.clicked = false) {
            s = (Scroll)var6.next();
        }

        this.scrollId = -1;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.scrollId != -1) {
            Scroll s = this.scrolls.get(this.scrollId);
            double i = mouseY - (double)this.y - (double)s.y - 7.5D;
            s.scrollAmount = i / (double)(s.h - 15);
            s.scrollAmount = MathHelper.clamp(s.scrollAmount, 0.0D, 1.0D);
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (this.hasScroll) {
            if (this.scrolls.size() == 1) {
                this.scrollId = 0;
            } else {
                for(int a = 0; a < this.scrolls.size(); ++a) {
                    if (this.scrolls.get(a).isclicked(mouseX - (double)this.x, mouseY - (double)this.y)) {
                        this.scrollId = a;
                        break;
                    }
                }
            }
        }

        if (this.scrollId != -1) {
            Scroll s = this.scrolls.get(this.scrollId);
            s.scrollAmount += amount / 10.0D;
            s.scrollAmount = MathHelper.clamp(s.scrollAmount, 0.0D, 1.0D);
        }

        this.scrollId = -1;
        return true;
    }

    public void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {

        for (Percentage p : this.percentages) {
            if (p.isclicked(x - this.x, y - this.y)) {
                String a = String.valueOf(this.getPvar(p.p, ((YwsuoyiScreenHandler) this.handler).e));
                this.renderTooltip(matrices, new LiteralText("§b" + a + "%"), x, y);
            }
        }

        super.drawMouseoverTooltip(matrices, x, y);
    }

    @Unused_InsteadOf @Deprecated
    public void addItemIcon(ItemStack itemStack, int x, int y) {
        this.icons.add(new Icon(x, y, itemStack));
        this.hasIcon = true;
    }

    @Unused_InsteadOf @Deprecated
    public void addButton(int x, int y, int w, int h, int u, int v, int id) {
        this.buttons.add(id, new Button(x, y, w, h, u, v, id));
        this.hasButton = true;
    }

    @Unused_InsteadOf @Deprecated
    public void addScroll(int x, int y, int h) {
        this.scrolls.add(new Scroll(x, y, h));
        this.hasScroll = true;
    }

    @Unused_InsteadOf @Deprecated
    public void addTank(int x, int y, int length, int index) {
        this.percentages.add(new Percentage(x, y, length, 1, index));
        this.hasPercentage = true;
    }

    public void addProgressArrow(int x, int y, int index) {
        this.percentages.add(new Percentage(x, y, 0, 2, index));
        this.hasPercentage = true;
    }

    public void addProgressBar(int x, int y, int length, int index) {
        this.percentages.add(new Percentage(x, y, length, 3, index));
        this.hasPercentage = true;
    }

    @Unused_InsteadOf @Deprecated
    public void addBurningBar(int x, int y, int index) {
        this.percentages.add(new Percentage(x, y, 0, 4, index));
        this.hasPercentage = true;
    }

    public void renderScroll(MatrixStack matrices) {

        for (Scroll s : this.scrolls) {
            int i = this.x;
            int j = this.y;
            this.drawTexture(matrices, i + s.x, j + s.y, 200, 0, 14, 1);

            for (int hi = 1; hi <= s.h; ++hi) {
                this.drawTexture(matrices, i + s.x, j + s.y + hi, 200, 1, 14, 1);
            }

            this.drawTexture(matrices, i + s.x, j + s.y + s.h + 1, 200, 2, 14, 1);
            this.drawTexture(matrices, i + s.x + 1, j + s.y + 1 + (int) (s.scrollAmount * (double) (s.h - 15)), 176 + (s.enable && !s.clicked ? 0 : 12), 0, 12, 15);
        }

    }

    public void renderButton(MatrixStack matrices, int x, int y) {
        Iterator<?> var4 = this.buttons.iterator();

        while(true) {
            while(var4.hasNext()) {
                Button b = (Button)var4.next();
                if (!b.clicked && b.enable) {
                    if (b.isclicked(x - this.x, y - this.y)) {
                        this.drawbutton(matrices, b, 93);
                    } else {
                        this.drawbutton(matrices, b, 91);
                    }
                } else {
                    this.drawbutton(matrices, b, 92);
                }
            }

            return;
        }
    }

    public void drawbutton(MatrixStack matrices, Button b, int v) {
        int i = this.x;
        int j = this.y;

        int a;
        for(a = 0; a < b.w; ++a) {
            for(int aa = 0; aa < b.h; ++aa) {
                this.drawTexture(matrices, i + b.x + a, j + b.y + aa, 1, v, 1, 1);
            }
        }

        for(a = 0; a < b.w - 1; ++a) {
            this.drawTexture(matrices, i + b.x + a, j + b.y, 0, v, 1, 1);
            this.drawTexture(matrices, i + b.x + a + 1, j + b.y + b.h - 1, 2, v, 1, 1);
        }

        for(a = 0; a < b.h - 1; ++a) {
            this.drawTexture(matrices, i + b.x, j + b.y + a, 0, v, 1, 1);
            this.drawTexture(matrices, i + b.x + b.w - 1, j + b.y + a + 1, 2, v, 1, 1);
        }

    }

    public void renderslot(MatrixStack matrices) {
        int i = this.x;
        int j = this.y;

        for(int a = 0; a < this.handler.slots.size(); ++a) {
            Slot s;
            DefaultedList<Integer> list = this.handler instanceof ModdedYwsuoyiScreenHandler ? ((ModdedYwsuoyiScreenHandler) this.handler).autoRender : ((YwsuoyiScreenHandler) this.handler).autorender;
            if (list.get(a) == 1) {
                s = this.handler.slots.get(a);
                this.drawTexture(matrices, i + s.x - 1, j + s.y - 1, 0, 9, 18, 18);
            } else if (list.get(a) == 2) {
                s = this.handler.slots.get(a);
                this.drawTexture(matrices, i + s.x - 1, j + s.y - 1, 0, 27, 26, 26);
            }
        }

    }

    public void renderPercentage(MatrixStack matrices, BlockEntity e) {
        int i = this.x;
        int j = this.y;
        Iterator<?> var5 = this.percentages.iterator();

        while(true) {
            label61:
            while(var5.hasNext()) {
                Percentage p = (Percentage)var5.next();
                int a;
                switch(p.type) {
                    case 1:
                        this.drawTexture(matrices, i + p.x, j + p.y, 0, 83, 15, 1);

                        for(a = 1; a <= p.l; ++a) {
                            this.drawTexture(matrices, i + p.x, j + p.y + a, 0, 84, 15, 1);
                        }

                        this.drawTexture(matrices, i + p.x, j + p.y + p.l + 1, 0, 85, 15, 1);

                        for(a = 1; (float)a < (float)this.getPvar(p.p, e) / 100.0F * (float)p.l; a += 2) {
                            this.drawTexture(matrices, i + p.x + 1, j + p.y + p.l - a, 15, 83, 13, 1);
                        }

                        for(a = 0; (float)a < (float)this.getPvar(p.p, e) / 100.0F * (float)p.l; a += 2) {
                            this.drawTexture(matrices, i + p.x + 1, j + p.y + p.l - a, 15, 84, 13, 1);
                        }
                        break;
                    case 2:
                        this.drawTexture(matrices, i + p.x, j + p.y, 22, 67, 22, 16);
                        this.drawTexture(matrices, i + p.x, j + p.y, 0, 67, (int)((float)this.getPvar(p.p, e) / 100.0F * 22.0F), 16);
                        break;
                    case 3:
                        this.drawTexture(matrices, i + p.x, j + p.y, 0, 87, 1, 4);

                        for(a = 1; a <= p.l; ++a) {
                            this.drawTexture(matrices, i + p.x + a, j + p.y, 1, 87, 1, 4);
                        }

                        this.drawTexture(matrices, i + p.x + p.l + 1, j + p.y, 2, 87, 1, 4);
                        a = 1;

                        while(true) {
                            if ((float)a > (float)this.getPvar(p.p, e) / 100.0F * (float)p.l) {
                                continue label61;
                            }

                            this.drawTexture(matrices, i + p.x + a, j + p.y + 1, 3, 88, 1, 2);
                            ++a;
                        }
                    case 4:
                        a = (int)((float)this.getPvar(p.p, e) / 100.0F * 14.0F);
                        this.drawTexture(matrices, i + p.x, j + p.y, 14, 53, 14, 14);
                        this.drawTexture(matrices, i + p.x, j + p.y + 14 - a, 0, 67 - a, 14, a);
                }
            }

            return;
        }
    }

    @Unused_InsteadOf @Deprecated
    public void setButtonEnable(int id, boolean enable) {
        this.buttons.get(id).enable = enable;
    }

    @Unused_InsteadOf @Deprecated
    public void setScrollEnable(int id, boolean enable) {
        this.scrolls.get(id).enable = enable;
    }

    public int getPvar(int index, BlockEntity blockEntity) {
        int e = this.getBlockEntityVar(index, blockEntity);
        return MathHelper.clamp(e, 0, 100);
    }

    public abstract int getBlockEntityVar(int var1, BlockEntity var2);
}
