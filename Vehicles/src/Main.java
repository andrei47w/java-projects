/*
    3. Intr-un service se afla in reparatie mai multe
    masini, camioane si motociclete. Sa se afiseze
    toate autovehiculele ce au costul de reparatie
    mai mare de 1000Ron.
*/

import Controller.Controller;
import Repo.Repo;
import Repo.IRepo;
import View.UI;

public class Main {
    public static void main(String[] args) {
        IRepo repo = new Repo();
        Controller controller = new Controller(repo);
        UI console = new UI(controller);

        console.run();
    }
}
