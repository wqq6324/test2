package multi;

import java.util.Scanner;

public class myThread {
    private  static int sumNum=0;
    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        Thread [] threads = new Thread[20];
        int x = in.nextInt();
        long n = in.nextLong();
        long avg = n/20;
        for(int i=0;i<20;i++)
        {
            int finalI = i+1;
            threads[i]=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(finalI<20){
                            for(long j = (finalI-1) *avg+1; j<=(finalI)*avg; j++){
                                synchronized (myThread.class){
                                    if(contain(j,x)){
                                        sumNum += j;
                                    }
                                }
                            }
                        }else if(finalI == 20){
                            for(long j = (finalI-1) *avg+1; j <= n; j++){
                                synchronized (myThread.class){
                                    if(contain(j,x)){
                                        sumNum += j;
                                    }
                                }
                            }
                        }
                    }
            });
            threads[i].start();
        }
        for(int i=0;i<20;i++)
            threads[i].join();
        System.out.println(sumNum);
    }
    private static boolean contain(long num, int x){
        return String.valueOf(num).contains(String.valueOf(x));
    }
}
