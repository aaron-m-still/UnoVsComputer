public class Card {

    public static final String[] colorList= {"Red", "Green", "Blue", "Yellow", "Wild"};
    private int color;
    private String type; //card type

    /**
     *
     * @param color int between 1-5
     * @param type numbers/types
     */
    public Card (int color, String type) {
        this.color = color - 1;
        this.type = type;
    }

    public void setColor(int i)
    {
        color = i-1;
    }

    /**
     *
     * @return the int color 1-5
     */
    public int getColor()
    {
        return color + 1;
    }

    /**
     *
     * @return the String color
     */
    public String printColor()
    {
        return colorList[color];
    }

    /**
     *
     * @return card types
     */
    public String getType ()
    {
        return type;
    }

    @Override
    public String toString() {
        return "[" + type + ", " + printColor() + "]";
    }
}
