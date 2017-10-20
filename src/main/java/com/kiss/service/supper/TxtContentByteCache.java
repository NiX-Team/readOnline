package com.kiss.service.supper;

import com.kiss.cache.supper.CacheKey;
import com.kiss.cache.util.ObjectUtil;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 11723
 */
public class TxtContentByteCache extends CacheKey {

    private final byte[] content;

    public TxtContentByteCache(File file) {
        super();
        ByteBuffer buffers = null;
        try {
            FileChannel channel = new RandomAccessFile(file,"r").getChannel();
            buffers = ByteBuffer.allocate((int) channel.size());
            channel.read(buffers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        content = buffers.array().clone();
    }

    @Override
    protected int size() {
        try {
            return ObjectUtil.ObjectToByte(this).length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public byte[] getContent() {
        return content;
    }
}
