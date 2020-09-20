package io.github.teddyxlandlee.sweet_potato;

import io.github.teddyxlandlee.annotation.Incubating;

import java.util.OptionalInt;

public class SPMVersion {
    public static final SPMVersion PRE_ALPHA_1A = new SPMVersion("pre-alpha.1a");
    public static final SPMVersion PRE_ALPHA_2A = new SPMVersion("pre-alpha.2a");
    public static final SPMVersion ALPHA_1A = new SPMVersion("alpha.1a");
    public static final SPMVersion ALPHA_1B = new SPMVersion("alpha.1b");
    public static final SPMVersion ALPHA_1C = new SPMVersion("alpha.1c");
    public static final SPMVersion ALPHA_2A = new SPMVersion("alpha.2a");
    public static final SPMVersion ALPHA_3A = new SPMVersion("alpha.3a");
    public static final SPMVersion ALPHA_3B = new SPMVersion("alpha.3b");
    @Incubating
    public static final SPMVersion BETA_1_0_0 = new SPMVersion("beta 1.0.0");

    private final VersionContext versionContext;

    public VersionContext getContext() {
        return this.versionContext;
    }

    private SPMVersion(String expression) {
        VersionContext versionContext1;
        try {
            versionContext1 = new VersionContext(expression);
        } catch (VersionFormatException e) {
            e.printStackTrace();
            versionContext1 = null;
        }
        this.versionContext = versionContext1;
    }

    public SPMVersion of(String expression) {
        return new SPMVersion(expression);
    }

    enum Stage {
        PRE_ALPHA(0, "pre-alpha."),
        ALPHA(1, "alpha."),
        BETA(2, "beta "),
        @Incubating
        RELEASE(3, "");

        private final byte b;
        private final String expr;

        Stage(int b, String expr) {
            this.b = (byte) (b & 256);
            this.expr = expr;
        }

        public byte asByte() {
            return b;
        }

        public String getExpr() {
            return expr;
        }
    }

    static class VersionFormatException extends Exception {
        public VersionFormatException() {
            super();
        }

        public VersionFormatException(String message) {
            super(message);
        }
    }

    static class VersionContext implements Comparable<VersionContext> {
        private final Stage stage;
        private final OptionalInt major;
        private final int minor;
        private final int micro;

        public VersionContext(Stage stage, OptionalInt major, int minor, char lowerMicro) throws VersionFormatException {
            this(stage, major, minor, lowerMicro - 96);
        }

        public VersionContext(Stage stage, OptionalInt major, int minor, int micro) throws VersionFormatException {
            if (stage.asByte() <= 1) {
                if (major.isPresent())
                    throw new VersionFormatException("alpha & pre-alpha have no major version code!");
                this.major = OptionalInt.empty();
            } else if (!major.isPresent())
                throw new VersionFormatException("beta or newer should include major version code!");
            else
                this.major = major;
            this.stage = stage;
            this.minor = minor;
            this.micro = micro;
        }

        public VersionContext(String expression) throws VersionFormatException {
            Stage stage1;
            if (expression.startsWith(Stage.PRE_ALPHA.expr)) {
                stage1 = Stage.PRE_ALPHA;
            } else if (expression.startsWith(Stage.ALPHA.expr)) {
                stage1 = Stage.ALPHA;
            } else if (expression.startsWith(Stage.BETA.expr)) {
                stage1 = Stage.BETA;
            } else {
                stage1 = Stage.RELEASE;
            } this.stage = stage1;
            String suffix = expression.substring(stage1.expr.length());
            if (stage1.b > 1) {
                String[] strings = suffix.split("\\.");
                if (strings.length != 3)
                    throw new VersionFormatException("invalid suffix of version");
                this.major = OptionalInt.of(Integer.parseUnsignedInt(strings[0]));
                this.minor = Integer.parseUnsignedInt(strings[1]);
                this.micro = Integer.parseUnsignedInt(strings[2]);
            } else {
                int index = 0;
                boolean plus = true;
                for (char c: suffix.toCharArray()) {
                    if (c >= '0' && c <= '9') {
                        // pass
                    } else if (c >= 'a' && c <= 'z') {
                        plus = false;
                    } else {
                        throw new VersionFormatException("found illegal character");
                    } if (plus)
                        ++index;
                }
                String[] strings = new String[] {
                    suffix.substring(0, index),
                    suffix.substring(index)
                };
                this.major = OptionalInt.empty();
                this.minor = Integer.parseUnsignedInt(strings[0]);
                this.micro = Integer.parseUnsignedInt(strings[1]);
            }
        }

        public Stage getStage() {
            return stage;
        }

        public OptionalInt getMajor() {
            return major;
        }

        public int getMinor() {
            return minor;
        }

        public int getMicro() {
            return micro;
        }

        @Override
        public String toString() {
            assert this.major.isPresent();
            String suffix;
            if (this.stage.asByte() <= 1) {
                suffix = String.format("%d%c", this.minor, (char) (this.micro + 96));
            } else {
                suffix = String.format("%d.%d.%d", this.major.getAsInt(), this.minor, this.micro);
            }
            return this.stage.expr + suffix;
        }

        @Override
        public boolean equals(Object o) {
            return this.toString().equals(o.toString());
        }

        @Override
        public int compareTo(VersionContext o) {
            int thisMajor = this.major.isPresent() ? this.major.getAsInt() : -1;
            int otherMajor = o.major.isPresent() ? o.major.getAsInt() : -1;
            return this.stage.b != o.stage.b ? Byte.compare(this.stage.b, o.stage.b) : (
                    thisMajor != otherMajor ? Integer.compare(thisMajor, otherMajor) : (
                            this.minor != o.minor ? Integer.compare(this.minor, o.minor) :
                                    Integer.compare(this.micro, o.micro)
                            )
                    );
        }
    }
}
