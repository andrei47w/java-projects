package Controller;

import Model.*;
import Repo.IRepo;


public class Controller {
    private IRepo repo;

    public Controller(IRepo repo) {
        this.repo = repo;
    }

    public IVehicle[] vehicles() {
        return repo.data();
    }

    public IVehicle[] getVehiclesOverCost(Integer cost) {
        IVehicle[] data = repo.data();
        IVehicle[] vehicles = new IVehicle[repo.size()];
        int nr = 0;

        for (IVehicle vehicle : data) {
            if (vehicle == null)
                break;
            if (vehicle.getCost() > cost) {
                vehicles[nr] = vehicle;
                nr++;
            }
        }

        return vehicles;
    }

    public void add(Vehicles type, Integer cost, String model) throws Exceptions {
        IVehicle vehicle = getVehicle(type, cost, model);

        for (IVehicle v : repo.getVehicles()) {
            if (v != null && v.equals(vehicle))
                throw new Exceptions("Cannot add duplicate vehicles!");
        }
        repo.add(vehicle);
    }

    public void remove(Vehicles type, Integer cost, String model) throws Exceptions {
        IVehicle vehicle = getVehicle(type, cost, model);

        int ok = 0;
        for (IVehicle v : repo.getVehicles()) {
            if (v != null && v.equals(vehicle)) {
                ok = 1;
                break;
            }
        }

        if (ok == 1) repo.remove(vehicle);
        else throw new Exceptions("Could not find vehicle!");

    }

    private IVehicle getVehicle(Vehicles type, Integer cost, String model) throws Exceptions {
        switch (type) {
            case CAR:
                return new Car(cost, model);
            case TRUCK:
                return new Truck(cost, model);
            case MOTORBIKE:
                return new Motorbike(cost, model);
            default:
                throw new Exceptions("Invalid type!");
        }
    }
}
