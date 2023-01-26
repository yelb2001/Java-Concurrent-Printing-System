import java.util.Random;

public class TonerTechnician extends Thread {
    private ServicePrinter servicePrinter;

    public TonerTechnician(ThreadGroup technicianThreadGroup, String tonerTechnicianName, ServicePrinter servicePrinter) {
        super(technicianThreadGroup, tonerTechnicianName);
        this.servicePrinter = servicePrinter;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            servicePrinter.replaceTonerCartridge();
            //count of time toner technician refilling the printer with toner cartridges

            Random randomTime = new Random();
            int random = randomTime.nextInt(10);
            try {
                Thread.sleep(random * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //printing the number of times toner has replaced
        System.out.println("[ "+ getName() + "Finished refilling the toner, Units of cartridges replaced: " + ((LaserPrinter)servicePrinter).getNoOfCartridgeUsed() +" ]");
    }
}


