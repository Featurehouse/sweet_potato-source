package io.github.teddyxlandlee.sweet_potato.util;

public final class FloatIntegerizer {
    private FloatIntegerizer() {}

    public static int fromFloat(float f) {
        return Float.floatToIntBits(f);
    }

    public static float toFloat(int i) {
        return Float.intBitsToFloat(i);
    }

    /**
     * We should test if it has error*/
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java io.github.teddyxlandlee.sweet_potato.util.FloatIntegerizer <--fromFloat / -FF / -TI | --toFloat / -FI / -TF> <value>");
        } else if (args[0].toLowerCase().equals("-fromfloat") || args[0].toUpperCase().equals("-FF") || args[0].toUpperCase().equals("-TI")) {
            float from;
            int to;
            float check;
            try {
                from = Float.parseFloat(args[1]);
                System.out.printf("From float to int: %f to %d\n", from, (to = fromFloat(from)));
                System.out.printf("Check: %d back to %f\n", to, (check = toFloat(to)));
                if (from == check) {
                    System.out.println("Program runs correctly");
                } else {
                    System.err.println("Program runs incorrectly");
                }
            } catch (NumberFormatException e) {
                System.out.printf("%s is not a float!\n", args[1]);
            }
        } else if (args[0].toLowerCase().equals("-tofloat") || args[0].toUpperCase().equals("FI") || args[0].toUpperCase().equals("-TF")) {
            int from;
            float to;
            int check;
            try {
                from = Integer.parseInt(args[1]);
                System.out.printf("From int to float: %d to %f\n", from, (to = toFloat(from)));
                System.out.printf("Check: %f back to %d\n", to, (check = fromFloat(to)));
                if (from == check) {
                    System.out.println("Program runs correctly");
                } else {
                    System.err.println("Program runs incorrectly");
                }
            } catch (NumberFormatException e) {
                System.out.printf("%s is not an integer!\n", args[1]);
            }
        } else {
            System.out.println("Usage: java io.github.teddyxlandlee.sweet_potato.util.FloatIntegerizer <--fromFloat / -FF / -TI | --toFloat / -FI / -TF> <value>");
        }
    }
}
