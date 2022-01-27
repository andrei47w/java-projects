package View.Commands;

import Controller.Controller;

public class RunExample extends Command {
    private final Controller ctr;

    public RunExample(String key, String desc, Controller ctr) {
        super(key, desc);
        this.ctr = ctr;
    }

    @Override

    public void execute() {
        try {
            System.out.print(ctr.allStep());
        } catch (Exception e) {
            System.out.print("EXCEPTION:" + e);
        }
    }
}