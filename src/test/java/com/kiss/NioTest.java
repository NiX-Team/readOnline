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
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.lang.System.*;

public class NioTest {

    @Test
    public void nio() {
        try {
            FileChannel fileChannel = new RandomAccessFile(new File("E:\\test\\txt\\诛仙.txt"),"r").getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) fileChannel.size());
            fileChannel.read(buffer);
            out.println(new String(buffer.array(),1200,3141-1200,"GBK"));

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
            out.println(fileChannel1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println("hh".getBytes() instanceof Serializable);

    }

    public static final Monitor monitor = new Monitor();
    public static final BeMonitor beMonitor = new BeMonitor();

    @Test
    public void test() throws InterruptedException {
        beMonitor.addObserver(monitor);
        new Thread(() -> {
            beMonitor.setName("ok1");
        }).start();
        beMonitor.setName("ok3");
        beMonitor.setName("ok2");
    }

}
class BeMonitor extends Observable{
    public void setName(String name) {
        setChanged();
        notifyObservers(name);
    }

}
class Monitor implements Observer{

    @Override
    public void update(Observable o, Object arg) {
        System.out.println(arg);
    }
}
