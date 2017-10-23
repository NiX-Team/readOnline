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

    /**
     * 输出txt文件startOffset到endOffset偏移之间的内容
     * @param encode txt文件编码
     * */
    public static String readStartToEndByNio(File txtFile,int startOffset,int endOffset,String encode) {
        try {
            FileChannel fileChannel = new RandomAccessFile(txtFile,"r").getChannel();
            int size = endOffset == -1 ? (int) (fileChannel.size() - startOffset) : (endOffset - startOffset);
            ByteBuffer buffer = ByteBuffer.allocate(size);
            fileChannel.read(buffer,startOffset);
            return new String(buffer.array(),encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 输出txt文件startOffset到endOffset偏移之间的内容
     * @param encode txt文件编码
     * */
    public static String readStartToEndByBytes(byte[] bytes,int startOffset,int endOffset,String encode) {
        try {
            int size = endOffset - startOffset;
            return new String(bytes,startOffset,size,encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
