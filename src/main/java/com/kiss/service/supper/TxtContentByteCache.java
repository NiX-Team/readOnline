package com.kiss.service.supper;

import com.kiss.cache.supper.CacheKey;
import com.kiss.cache.util.ObjectUtil;
import com.kiss.common.Const;
import com.kiss.common.TxtMonitoring;
import com.kiss.common.config.SystemConfig;
import com.kiss.model.TxtModel;
import com.kiss.monitor.BeMonitorObj;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 11723
 */
public class TxtContentByteCache extends CacheKey {

    public TxtContentByteCache(File file) {
        super();
        try {
            FileChannel channel = new RandomAccessFile(file,"r").getChannel();
            ByteBuffer buffers = ByteBuffer.allocate((int) channel.size());
            channel.read(buffers);
            setContent(buffers.array());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private byte[] content;

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    protected byte[] toBytes() throws Exception {
        byte[] bytes = ObjectUtil.ObjectToByte(this);
        content = null;
        return bytes;
    }
}
