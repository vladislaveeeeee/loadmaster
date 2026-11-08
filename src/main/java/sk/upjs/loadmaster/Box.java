package sk.upjs.loadmaster;

// Jedna krabica alebo paleta, ktorú chceme naložiť.
// Rozmery sú v centimetroch, hmotnosť v kilogramoch.
public class Box {

    private String name;
    private int length;
    private int width;
    private int height;
    private double weight;
    private int stop;           // číslo zastávky, na ktorej sa krabica vykladá
    private boolean stackable;  // dá sa na ňu položiť iná krabica?

    public Box(String name, int length, int width, int height, double weight, int stop, boolean stackable) {
        this.name = name;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.stop = stop;
        this.stackable = stackable;
    }

    // Objem v m³ (1 m³ = 1 000 000 cm³)
    public double getVolume() {
        return (double) length * width * height / 1_000_000;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public int getStop() {
        return stop;
    }

    public boolean isStackable() {
        return stackable;
    }

    // Text do tabuľky namiesto true/false
    public String getStackableText() {
        if (stackable) {
            return "áno";
        }
        return "nie";
    }
}
