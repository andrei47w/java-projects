package Repo;

import Model.IVehicle;

public interface IRepo {
    void add(IVehicle vehicle);

    void remove(IVehicle vehicle);

    int size();

    IVehicle[] data();

    IVehicle[] getVehicles();
}
