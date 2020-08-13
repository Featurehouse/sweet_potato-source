//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package bilibili.ywsuoyi.gui;

public class Scroll {
    public final int x;
    public final int y;
    public final int w;
    public final int h;
    public double scrollAmount = 0.0D;
    public boolean enable = true;
    public boolean clicked = false;

    public Scroll(int x, int y, int h) {
        this.x = x;
        this.y = y;
        this.h = Math.max(h, 16);
        this.w = 12;
    }

    public boolean isclicked(double x, double y) {
        return this.enable && (double)this.x <= x && (double)this.y <= y && (double)(this.x + this.w) >= x && (double)(this.y + this.h) >= y;
    }
}
