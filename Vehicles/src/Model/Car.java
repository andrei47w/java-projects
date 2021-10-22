package Model;

public class Car implements IVehicle {
    private Integer cost;
    private String model;

    public Car() {
        cost = 0;
        model = "";
    }

    public Car(Integer cost, String model) {
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
