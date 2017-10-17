package com.kiss.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public final class TxtUtil {

    /**
     * 输出txt文件startLine与endLine之间的内容
     * */
    public static String readStartToEndByStream(File txtFile,Long startLine,Long endLine) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(txtFile),"GBK"));
            String line;
            long lineCount = 0;
            StringBuffer data =  new StringBuffer();
            while ((line = reader.readLine()) != null && lineCount < endLine) {
                if (lineCount > startLine && lineCount < endLine) {
                    data.append(line + "\r\n");
                }
                lineCount++;
            }
            return data.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String readStartToEndByNio(File txtFile,int startOffset,int endOffset,String encode) {
        try {
            FileChannel fileChannel = new RandomAccessFile(txtFile,"r").getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) fileChannel.size());
            fileChannel.read(buffer);
            return new String(buffer.array(),startOffset,endOffset-startOffset,encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
