import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class Deposit implements Runnable {

    private String name;
    int DepAmount;
    private int min = 1;
    private int max = 500;


    Deposit(String name) {
        this.name = name;


    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            DepAmount = ThreadLocalRandom.current().nextInt(min, max + 1);


            Control.lock.lock();
            try {
                Control.Balance += DepAmount;
                String time=Control.generateTime();
                validTran(DepAmount);
                if (DepAmount >= 350) flaggedShow(DepAmount, time);
                //Thread.sleep((long) (Math.random() * 1000));


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                Control.lock.unlock();
            }


        }

    }


    void flaggedShow(int depAm, String time) throws IOException {

        String depFlag="*** Flagged Transaction - Depositor Agent "+name+" made A Deposit in Excess of $350.00 USD - " +
                "See Flagged Transaction Log. ";
        System.out.printf("\n%s\n",depFlag);


        String outFile="Depositor Agent "+name+" issued deposit of $"+depAm+" "+time+"  Transaction Number : "+
                Control.tranNum;

        Control.generateOutFile(outFile);



    }


    void validTran(int dAmount){
        Control.tranNum++;

        String tranNum="        "+Control.tranNum+"           ";
        String depValid="Agent "+name+" deposits $"+dAmount;
        String balanceValid="(+) Balance is $"+Control.Balance;
        System.out.printf("%-25s %-25s %-55s %-25s\n",depValid , Control.spacer, balanceValid, tranNum);


    }

}
