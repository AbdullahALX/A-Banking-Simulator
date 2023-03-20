public class Auditor implements Runnable {

    String auditOut="**********************************************************************************************" +
            "**************************************";
    int auditorCount=0;


    Auditor(){

    }

    @Override
    public void run() {
        while (true){

            try {
                Thread.sleep((long)(Math. random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Control.lock.lock();
            try {

                auditorCount=Control.tranNum-auditorCount;
                showOut(auditorCount);

            } finally {
                Control.lock.unlock();
            }

        }

    }
    void showOut(int aCount){

        String tranNum="Number of transactions since last audit is: "+aCount;

        String balance="AUDITOR FINDS ACCOUNT BALANCE TO BE: $"+Control.Balance;

        System.out.printf("%s\n",auditOut);

        System.out.printf("           %-25s\t\t\t\t%-25s \n",balance ,tranNum);

        System.out.printf("\n%s\n",auditOut);




    }
}
