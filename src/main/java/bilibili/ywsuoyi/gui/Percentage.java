//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package bilibili.ywsuoyi.gui;

public class Percentage {
    public final int type;
    public final int x;
    public final int y;
    public final int l;
    public final int p;

    public Percentage(int x, int y, int l, int type, int p) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.l = l;
        this.p = p;
    }

    public boolean isclicked(double x, double y) {
        if (this.type != 1) {
            return false;
        } else {
            return (double)this.x < x && (double)this.y < y && (double)(this.x + 15) > x && (double)(this.y + this.l) > y;
        }
    }
}
