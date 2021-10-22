package Repo;

import Model.IVehicle;

public class Repo implements IRepo {
    private IVehicle[] vehicles;
    private int size, len;

    public Repo() {
        size = 50;
        len = 0;
        vehicles = new IVehicle[size];
    }

    public int size() {
        return len;
    }

    public IVehicle[] data() {
        return vehicles;
    }

    public IVehicle[] getVehicles() {
        return vehicles;
    }

    public void add(IVehicle vehicle) {
        if (size == len)
            resize();
        vehicles[len] = vehicle;
        len++;
    }

    private void resize() {
        IVehicle[] new_vehicles = new IVehicle[size * 2];
        for (int i = 0; i < size; ++i)
            new_vehicles[i] = vehicles[i];
        vehicles = new_vehicles;
        size *= 2;
    }

    public void remove(IVehicle vehicle) {
        int nr = 0;
        for (int i = 0; i < len; i++) {
            if (vehicles[i].equals(vehicle)) {
                nr = i;
                break;
            }
        }

        for (int i = nr; i < len; ++i)
            vehicles[i] = vehicles[i + 1];
        len--;
    }
}

