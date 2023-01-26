import java.util.Random;

public class PaperTechnician extends Thread {
    //thread class to represent a paper technician that can refill the printers paper trays
    //use instance of service printer class
    private ServicePrinter servicePrinter;

    public PaperTechnician(ThreadGroup technicianThreadGroup, String paperTechnicianName, ServicePrinter servicePrinter) {
        super(technicianThreadGroup, paperTechnicianName);
        this.servicePrinter = servicePrinter;
    }

    //paper technician has to refill the paper tray
    //paper tray has less than 200 pages
    @Override
    public void run() {

        // paper technician is allowed to refill only 3 times
        for (int i = 1; i <= 3; i++) {
            servicePrinter.refillPaper();

            //waits random time
            Random randomTime = new Random();
            int random = randomTime.nextInt(10);
            try {
                Thread.sleep(random * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //print the number of paper bundle refilled in paper tray
        System.out.println("[ "+ getName() + " Finished refilling, packs of paper used : " + ((LaserPrinter)servicePrinter).getNoOfPaperPacks() +" ]");
    }
}
