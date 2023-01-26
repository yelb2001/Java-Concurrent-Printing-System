import java.util.Random;

public class Student extends Thread {
    //thread class to represent a student that can print several documents
    //use printer class instance to print the documents
    private final Printer printer;

    //initializing the information about student
    public Student(ThreadGroup studentThreadGroup, String studentName, Printer printer) {
        super(studentThreadGroup, studentName);
        this.printer = printer;
    }

    public void run() {
        //document array to hold 5 documents students have to print
        Document[] documents = new Document[5];

        //total papers = 25 pages
        documents[0] = new Document("1234", " \"Mathematics paper Grade 6\" ", 3);
        documents[1] = new Document("3532", " \"Signed Agreement for 2023\" ", 7);
        documents[2] = new Document("6756", " \"Formal Methods CW Part 2\" ", 5);
        documents[3] = new Document("2341", " \"Application for sports\" ", 4);
        documents[4] = new Document("9786", " \"Cover Letter for new intake\" ", 1);

        // to calculate the total number of pages
        int pageCountPerStudent = 0;

        for (Document document : documents) {
            //printing documents one by one
            printer.printDocument(document);
            //print the details of printed document
            System.out.println("Document," + document.getDocumentName() + "printed by " + getName());

            //total length (total page count from each student documents)
            pageCountPerStudent = pageCountPerStudent + document.getNumberOfPages();

            //waiting random time
            Random randomTime = new Random();
            int random = randomTime.nextInt(10);
            try {
                Thread.sleep(random * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(">>>>>>>>>>>>>>>>> " + getName() + " Finished Printing: " + documents.length + " Documents, " + pageCountPerStudent + " pages <<<<<<<<<<<");
        System.out.println();
    }
}

