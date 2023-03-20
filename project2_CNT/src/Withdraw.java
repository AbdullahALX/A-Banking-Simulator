import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class Withdraw implements Runnable {
    private String name;
    int witdAmount;
    private int min=1;
    private int max=99;



    Withdraw(String name){
        this.name=name;
        witdAmount=0;

    }

    @Override
    public void run() {

        while (true){
            try {
                Thread.sleep((long)(Math. random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            witdAmount=ThreadLocalRandom.current().nextInt(min,max+ 1);
            if (witdAmount>Control.Balance){
                blocked(witdAmount);
            }
            else{
                Control.lock.lock();
                try {

                    Control.Balance-=witdAmount;
                    String time=Control.generateTime();

                    validTran(witdAmount);
                   if (witdAmount >= 75) flaggedShow(witdAmount, time);
                    // Thread.sleep((long)(Math. random() * 1000));



                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    Control.lock.unlock();
                }


            }

        }

    }

    void blocked(int wAmount){
        String withBlocked="Agent "+name+" withdraws $"+wAmount;
        String balanceBlocked="(******) WITHDRAWAL BLOCKED - INSUFFICIENT FUNDS!!!";
        System.out.printf("%-25s %-25s %-55s %-25s\n", Control.spacer, withBlocked, balanceBlocked, Control.spacer);

    }

    void flaggedShow(int withAm, String time) throws IOException {

        String withFlag="*** Flagged Transaction - Withdrawal Agent "+name+" made A Withdraws in Excess of $75.00 USD - " +
                "See Flagged Transaction Log. ";
        System.out.printf("\n%s\n",withFlag);


        String outFile="\t\tWithdrawal Agent "+name+" issued withdrawal of $"+withAm+" "+time+"  Transaction Number : "+
                Control.tranNum;

        Control.generateOutFile(outFile);



    }

    void validTran(int wAmount){

        Control.tranNum++;

        String tranNum="        "+Control.tranNum+"           ";

        String withValid="Agent "+name+" withdraws $"+wAmount;
        String balanceValid="(-) Balance is $"+Control.Balance;
        System.out.printf("%-25s %-25s %-55s %-25s\n", Control.spacer, withValid, balanceValid, tranNum);



    }
}
