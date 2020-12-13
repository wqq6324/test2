import multi.myThread;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Multithreading {
    private  static int sumNum=0;
    //线程池
    private static ExecutorService executorService = Executors.newFixedThreadPool(20);
    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        CountDownLatch latch=new CountDownLatch(20);
        System.out.println("请输入n, x(以空格分开):");
        long n = in.nextLong();
        int x = in.nextInt();
        long avg= n/20;
        /*System.out.println(avg);*/
        for(int i=0;i<20;i++) {
            int finalI = i+1;
            executorService.submit(()->{
                /*System.out.println("第"+(finalI)+"个线程，从"+((finalI-1) *avg+1)+"到"+(finalI)*avg);*/
                /*System.out.println(finalI+"   "+((finalI-1) *avg+1)+"  "+(finalI)*avg);*/
                //考虑存在不一定除尽情况下，对最后一个线程特殊处理至n
                if(finalI<20){
                    for(long j = (finalI-1) *avg+1; j<=(finalI)*avg; j++){
                        synchronized (myThread.class){
                            if(contain(j,x)){
                                sumNum+=j;
                            }
                        }
                    }
                    latch.countDown();
                }else if(finalI == 20){
                    for(long j = (finalI-1) *avg+1; j <= n; j++){
                        synchronized (myThread.class){
                            if(contain(j,x)){
                                sumNum += j;
                            }
                        }
                    }
                    latch.countDown();
                }
            });
        }
        latch.await();
        System.out.println(sumNum);
        /*数据测试，已验证100 9,1000 9，109 9,1009 9通过
        long ans2 = 0;
        for(long j=0;j<=n;j++){
            if(contain(j,x)){
                ans2+=j;
            }
        }
        System.out.println(ans2);
        */
    }
    private static boolean contain(long num, int x){
        return String.valueOf(num).contains(String.valueOf(x));
    }
}