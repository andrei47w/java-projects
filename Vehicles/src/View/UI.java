package View;

import Controller.Controller;
import Model.IVehicle;
import Model.Vehicles;
import Controller.Exceptions;

public class UI {
    private Controller controller;

    public UI(Controller controller) {
        this.controller = controller;
    }

    private void printVehicles(IVehicle[] vehicles) {
        for (IVehicle v : vehicles) {
            if (v == null) break;
            System.out.print(v.getModel() + "  " + v.getCost() + '\n');
        }
    }

    public void run() {
        try {
            controller.add(Vehicles.CAR, 10000, "ford");
            controller.add(Vehicles.CAR, 100, "merc");
            controller.add(Vehicles.TRUCK, 40000, "scania");
            controller.add(Vehicles.TRUCK, 800, "man");
            controller.add(Vehicles.TRUCK, 62000, "isuzu");
            controller.add(Vehicles.MOTORBIKE, 500, "bmw");
            controller.add(Vehicles.MOTORBIKE, 230, "honda");

            printVehicles(controller.getVehiclesOverCost(1000));
        } catch (Exceptions e) {
            System.out.println(e.getMessage());
        }

    }
}
