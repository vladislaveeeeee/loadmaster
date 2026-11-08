package sk.upjs.loadmaster;

// Vozidlo a rozmery jeho nákladného priestoru (cm) a maximálna nosnosť (kg).
public class Truck {

    private String name;
    private int length;
    private int width;
    private int height;
    private double maxWeight;

    public Truck(String name, int length, int width, int height, double maxWeight) {
        this.name = name;
        this.length = length;
        this.width = width;
        this.height = height;
        this.maxWeight = maxWeight;
    }

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

    public double getMaxWeight() {
        return maxWeight;
    }

    // Toto sa zobrazí v ComboBoxe
    @Override
    public String toString() {
        return name;
    }
}
