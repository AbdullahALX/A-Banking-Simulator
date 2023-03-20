import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Control {
    static int Balance;
    static ReentrantLock lock=new ReentrantLock();

    static String depA="Deposit Agents";
    static String witpA="Withdrawal Agents";
    static String balanOut="Balance";
    static String transNu="Transaction Number";
    static String dash="|----------------------";
    static String spacer="                       ";
    static int tranNum=0;





    public static void main(String[] args) throws IOException {

        clearTheFile();
        ExecutorService service = Executors.newFixedThreadPool(16);

        Balance=0;



        Deposit DT0= new Deposit("DT0");
        Deposit DT1= new Deposit("DT1");
        Deposit DT2= new Deposit("DT2");
        Deposit DT3= new Deposit("DT3");
        Deposit DT4= new Deposit("DT4");

        Withdraw WT0 = new Withdraw("WT0");
        Withdraw WT1 = new Withdraw("WT1");
        Withdraw WT2 = new Withdraw("WT2");
        Withdraw WT3 = new Withdraw("WT3");
        Withdraw WT4 = new Withdraw("WT4");
        Withdraw WT5 = new Withdraw("WT5");
        Withdraw WT6 = new Withdraw("WT6");
        Withdraw WT7 = new Withdraw("WT7");
        Withdraw WT8 = new Withdraw("WT8");
        Withdraw WT9 = new Withdraw("WT9");

        Auditor AD0 = new Auditor();

        System.out.printf("%-25s %-25s %-55s %-25s\n", depA, witpA, balanOut, transNu);
        System.out.printf("%-25s %-25s %-55s %-25s\n", dash,dash,dash,dash);
//        System.out.println(Balance);
//        System.exit(1);

        try // try to start producer and consumer
        {
            service.execute( DT0 );
            service.execute( DT1 );
            service.execute( DT2 );
            service.execute( DT3 );
            service.execute( DT4 );
            service.execute( WT0 );
            service.execute( WT1 );
            service.execute( WT2 );
            service.execute( WT3 );
            service.execute( WT4 );
            service.execute( WT5 );
            service.execute( WT6 );
            service.execute( WT7 );
            service.execute( WT8 );
            service.execute( WT9 );
            service.execute( AD0 );
        } // end try
        catch ( Exception exception )
        {
            exception.printStackTrace();
        } // end catch





    }

    static String generateTime() {
        SimpleDateFormat formatDate = new SimpleDateFormat(
                "dd/MM/yyyy  HH:mm:ss a z");

        Date date = new Date();
        formatDate.setTimeZone(TimeZone.getTimeZone("EST"));

        return "at: " + formatDate.format(date).toString();

    }

    static void generateOutFile(String outStr) throws IOException {

         BufferedWriter out = null;

        FileWriter fstream = null; //true tells to append data.
        try {
            fstream = new FileWriter("transactions.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out = new BufferedWriter(fstream);
        out.write(outStr+"\n");

        out.close();




    }

    public static void clearTheFile() throws IOException {
        FileWriter fwOb = new FileWriter("transactions.txt", false);
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }
}
