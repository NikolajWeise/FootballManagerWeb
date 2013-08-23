package de.weise.fm.web.model.play;


public class FieldPosition implements Cloneable {

    public static final FieldPosition C = new FieldPosition(0, 0);
    public static final FieldPosition RG = new FieldPosition(0, 3);
    public static final FieldPosition LG = new FieldPosition(0, -3);
    public static final FieldPosition RT = new FieldPosition(0, 6);
    public static final FieldPosition LT = new FieldPosition(0, -6);
    public static final FieldPosition QB = new FieldPosition(-3, 0);
    public static final FieldPosition FB = new FieldPosition(-6, 0);
    public static final FieldPosition HB = new FieldPosition(-9, 0);
    public static final FieldPosition TE = new FieldPosition(0, 9);
    public static final FieldPosition WR_RIGHT = new FieldPosition(-1, 20);
    public static final FieldPosition WR_LEFT = new FieldPosition(0, -20);

    public static final FieldPosition CB_RIGHT = new FieldPosition(1, 20);
    public static final FieldPosition CB_LEFT = new FieldPosition(1, -20);
    public static final FieldPosition SS = new FieldPosition(15, -5);
    public static final FieldPosition FS = new FieldPosition(17, 4);

    // endzones = 10 yards (per)
    // width = 53 yards

    private int height = 0; // relative to the ball, e.g. C has a height of 0, DT height of 1
    private int width = 0; // relative to the ball, e.g. C has a height of 0, WR height of 20 or -20

    public FieldPosition(int height, int width) {
        super();
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Creates a new FieldPosition with:
     * <pre>
     * height = this.height + height
     * width = this.width + width</pre>
     *
     * @param height The height to add to this height.
     * @param width The width to add to this width.
     * @return A new FieldPosition with cummulated height and width.
     */
    public FieldPosition add(int height, int width) {
        return new FieldPosition(this.height + height, this.width + width);
    }

    public boolean isLeft() {
        return width < 0;
    }

    public boolean isRight() {
        return width > 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + height;
        result = prime * result + width;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        FieldPosition other = (FieldPosition)obj;
        if(height != other.height)
            return false;
        if(width != other.width)
            return false;
        return true;
    }

    @Override
    public FieldPosition clone() {
        try {
            return (FieldPosition)super.clone();
        } catch(CloneNotSupportedException e) {
            throw new AssertionError(); // can not happen
        }
    }
}
