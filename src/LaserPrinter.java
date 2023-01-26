public class LaserPrinter implements ServicePrinter {
    //monitor

    //printersName
    private String id;
    //count the current papers in the tray
    private int currentPaperLevelOfPrinter;
    //count the toner level in the printer
    private int currentTonerLevelOfPrinter;

    // to count total pages and documents get printed inside the printer
    private int noOfPagesPrinted;
    private int noOfDocumentsPrinted;

    private ThreadGroup studentThreadGroup;

    //to get a count of how many times toner is replaced
    private int noOfCartridgeUsed;
    //to get a count of how many times paper tray is refill
    private int noOfPaperPacksUsed;

    public LaserPrinter(String id, int currentPaperLevelOfPrinter, int currentTonerLevelOfPrinter, int noOfPagesPrinted, int noOfDocumentsPrinted, ThreadGroup studentThreadGroup) {
        this.id = id;
        this.currentPaperLevelOfPrinter = currentPaperLevelOfPrinter;
        this.currentTonerLevelOfPrinter = currentTonerLevelOfPrinter;
        this.noOfPagesPrinted = noOfPagesPrinted;
        this.noOfDocumentsPrinted = noOfDocumentsPrinted;
        this.studentThreadGroup = studentThreadGroup;
    }

    //check whether all the threads have finished or not
    //at least 1 thread hasn't terminated successfully, programme cant finish
    private boolean studentHasFinishedPrinting() {
        return studentThreadGroup.activeCount() < 1;
    }


    public int getNoOfCartridgeUsed() {
        return noOfCartridgeUsed;
    }

    public int getNoOfPaperPacks() {
        return noOfPaperPacksUsed;
    }


    //---------------------------------------------- student prints the documents -------------------------------------------
    @Override
    public synchronized void printDocument(Document document) {
        //prints the report of printer details
        System.out.println(toString());

        //get the number of papers and assign it to the variable
        int noOfPagesInDocument = document.getNumberOfPages();

        //boolean values to check whether the number of pages and toner level is less than the required count
        boolean lessNoOfPages = currentPaperLevelOfPrinter <= noOfPagesInDocument;
        boolean lessTonerLevel = currentTonerLevelOfPrinter <= noOfPagesInDocument;

        //at least one of them are true, student has to wait
        //cant print documents
        while (lessNoOfPages || lessTonerLevel) {

            try {
                // printing a message to mention there is no sufficient amount of toner to print
                if (currentTonerLevelOfPrinter <= noOfPagesInDocument) {
                    System.out.println("(Can't refill the printer with toner cartridge, it will be a waste of toner.)");
                } else { //currentPaperLevelOfPrinter <= noOfPagesInDocument
                    //printing a message to mention there is no sufficient amount of paper to print
                    System.out.println("(Can't refill the printer with papers either. Because it will exceed the maximum amount of papers.)");
                }
                // release the monitor lock
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // there is sufficient amount of paper and toner to print documents
        // student can print

        //reduce the paper level by number of pages in the printed document from the current paper level
        currentPaperLevelOfPrinter -= noOfPagesInDocument;
        //reduce the toner level by papers count in the printed document
        // 1 unit of toner = 1 sheet of paper
        currentTonerLevelOfPrinter -= noOfPagesInDocument;

        System.out.println("");
        System.out.println();
        //to get total page count printing inside the printer
        noOfPagesPrinted += noOfPagesInDocument;
        //increasing the printed document count
        noOfDocumentsPrinted++;
        System.out.println("Number of pages for this document: " + noOfPagesInDocument+", Total number of Pages printed: " + noOfPagesPrinted + " , Total Document count: " + noOfDocumentsPrinted);
        //notifying all the threads which are in runnable state to acquire the monitor lock
        notifyAll();
    }


    //-------------------------------------------paper technician refill the paper tray when its needed---------------------------
    @Override
    public synchronized void refillPaper() {
        //boolean value to check whether the number of papers are more than 200
        //x >= 250 - 50
        boolean hasEnoughPapers = currentPaperLevelOfPrinter >= (Full_Paper_Tray - SheetsPerPack);

        //if the paper tray has more than 200 pages paper technician should wait
        //250 > paperLevel > 200
        //wait 5 seconds
        while (hasEnoughPapers) {
            try {
                //thread waits fro 5 seconds
                wait(5000);
                if (studentHasFinishedPrinting()) {
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // paper is not enough to print . less than 200 papers in the paper tray
        // technician refill the paper tray as 50 paper packets
        System.out.println("[Paper Technician] Replaced the paper tray with a paper bundle..");
        //increasing the paper count by 50
        currentPaperLevelOfPrinter += SheetsPerPack;
        //increase the times paper tray is refilled
        this.noOfPaperPacksUsed++;
        //notifying all the threads which are in runnable state to acquire the monitor lock
        notifyAll();
    }


    //----------------------------------------toner technician replace the toner when its needed-------------------------
    @Override
    public synchronized void replaceTonerCartridge() {
        //boolean value to check whether there is sufficient amount of toner
        // toner level should be more 10 or it has to replace with a new toner cartridge
        boolean hasEnoughToner = (currentTonerLevelOfPrinter >= Minimum_Toner_Level);
        //has enough toner
        while (hasEnoughToner) {
            try {
                //wait 5 seconds when toner is more than 10
                wait(5000);
                if (studentHasFinishedPrinting()) {
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //toner level is less than 10
        // replace the toner with a new one
        System.out.println("[Toner Technician] Replaced a new toner cartridge..");
        // toner level becomes 500 since the current one is replacing with a new toner
        currentTonerLevelOfPrinter = Full_Toner_Level;
        //increase the count of toner in replaced
        this.noOfCartridgeUsed++;
        //notifying all the threads which are in runnable state to acquire the monitor lock
        notifyAll();
    }


    @Override
    public String toString() {
        return "[PrinterID: " + id +
                ", Paper Level: " + currentPaperLevelOfPrinter +
                ", Toner Level: " + currentTonerLevelOfPrinter +
                ", Documents Printed: " + noOfDocumentsPrinted + "]";
    }
}
