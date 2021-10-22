package Model;

public class Motorbike implements IVehicle {
    private String model;
    private Integer cost;

    public Motorbike() {
        model = "";
        cost = 0;
    }

    public Motorbike(Integer cost, String model) {
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
