package Model;

public class Truck implements IVehicle {
    private String model;
    private Integer cost;

    public Truck() {
        model = "";
        cost = 0;
    }

    public Truck(Integer cost, String model) {
        this.cost = cost;
        this.model = model;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public Integer getCost() {
        return cost;
    }
}