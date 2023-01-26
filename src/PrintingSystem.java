public class PrintingSystem {
    public static void main(String[] args) throws InterruptedException {

        //2 thread group for student and technician
        ThreadGroup studentThreadGroup = new ThreadGroup("student Thread group");
        ThreadGroup technicianThreadGroup = new ThreadGroup("Tech Thread group");

        ServicePrinter servicePrinter = new LaserPrinter("Ip-CFD.e84", 2, 5, 0, 0, studentThreadGroup);

        //creating 6 threads for all users
        Thread student1Thread = new Student(studentThreadGroup, "Joe Alvin (Student1)", servicePrinter);
        Thread student2Thread = new Student(studentThreadGroup, "Taylor Matte (Student2)", servicePrinter);
        Thread student3Thread = new Student(studentThreadGroup, "Tessa Young (Student3)", servicePrinter);
        Thread student4Thread = new Student(studentThreadGroup, "Bobby Paul (Student4)", servicePrinter);

        Thread paperTechnicianThread = new PaperTechnician(technicianThreadGroup, "Paper Technician [T0067] ", servicePrinter);
        Thread tonerTechnicianThread = new TonerTechnician(technicianThreadGroup, "Toner Technician [gE.403] ", servicePrinter);

        //starting all the threads
        student1Thread.start();
        student2Thread.start();
        student3Thread.start();
        student4Thread.start();

        paperTechnicianThread.start();
        tonerTechnicianThread.start();

        //all threads join to
        student1Thread.join();
        student2Thread.join();
        student3Thread.join();
        student4Thread.join();

        paperTechnicianThread.join();
        tonerTechnicianThread.join();


        System.out.println("");
        System.out.println("==========================================================================");
        System.out.println("                 . PROGRAMME HAS CONCLUDED SUCCESSFULLY .");
        System.out.println(servicePrinter.toString());

    }
}
