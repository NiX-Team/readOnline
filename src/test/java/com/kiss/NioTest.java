package com.kiss;
import com.kiss.cache.Cache;
import com.kiss.cache.CacheFactory;
import com.kiss.common.Const;
import com.kiss.common.TxtRead;
import com.kiss.common.config.SystemConfig;
import com.kiss.common.supper.NioTxtRead;
import org.junit.Test;
import org.springframework.util.Assert;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NioTest {

    @Test
    public void nio() {
        try {
            FileChannel fileChannel = new RandomAccessFile(new File("E:\\test\\txt\\诛仙.txt"),"r").getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) fileChannel.size());
            fileChannel.read(buffer);
            System.out.println(new String(buffer.array(),1200,3141-1200,"GBK"));

        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void assertTest() {
        FileChannel fileChannel = null;
        try {
            fileChannel = new RandomAccessFile(new File("E:\\test\\txt\\诛仙.txt"),"r").getChannel();
            Cache<ChannelCache> cache = CacheFactory.getDeafultCache();
            ChannelCache cache1 = new ChannelCache();
            cache1.setChannel("hello world");
            cache.put(cache1);
            String fileChannel1 =  cache.get(cache1.getCacheKey()).getChannel();
            System.out.println(fileChannel1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("hh".getBytes() instanceof Serializable);

    }

    @Test
    public void test() throws InterruptedException {
        BlockingDeque<Runnable> WORK_QUEUE = new LinkedBlockingDeque<>();
        ThreadPoolExecutor threadPoolExecutor =   new ThreadPoolExecutor(200,200,0, TimeUnit.SECONDS,WORK_QUEUE);

        WORK_QUEUE.add(() -> System.out.println("1"));
        threadPoolExecutor.execute(WORK_QUEUE.poll());
        threadPoolExecutor.execute(() -> System.out.println("1"));
//        WORK_QUEUE.add(() -> System.out.println("2"));
//        WORK_QUEUE.add(() -> System.out.println("3"));
//        WORK_QUEUE.add(() -> System.out.println("4"));
    }

}
