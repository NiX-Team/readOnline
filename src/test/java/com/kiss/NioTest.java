package com.kiss;
import org.junit.Test;

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
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            fileChannel.read(buffer,1200);
            byte[] lineByte = buffer.array();
            System.out.println(new String(lineByte, 0, lineByte.length, "GBK"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
