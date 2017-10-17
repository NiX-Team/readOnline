package com.kiss;
import com.kiss.common.TxtRead;
import com.kiss.common.supper.NioTxtRead;
import org.junit.Test;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

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
        String a = null;
        Assert.notNull(a,"a不能为空");
    }
}
