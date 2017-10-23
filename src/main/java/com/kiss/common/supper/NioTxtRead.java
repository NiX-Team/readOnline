package com.kiss.common.supper;

import com.kiss.common.TxtRead;
import com.kiss.model.TxtChapterMsgModel;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

@Component
public class NioTxtRead implements TxtRead{

    /**
     * @param encode 为空时默认UTF-8
     * */
    @Override
    public TxtChapterMsgModel txtChapterMsgLists(File file, String txtSn, String encode) {
        Assert.notNull(file);
        Assert.notNull(txtSn);
        encode = encode == null ? "UTF-8" : encode;
        try {
            FileChannel fileChannel = new RandomAccessFile(file,"r").getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) fileChannel.size());
            fileChannel.read(buffer);
            String line = null;
            int chapterCount = 0,beforeOffset = 0;
            List chapters = new ArrayList();
            List nioOffsets = new ArrayList();
            List titles = new ArrayList();
            for (int i = 0;i < buffer.capacity();i ++) {
                byte by = buffer.get(i);
                //读取到换行符（\r\n）
                if (by == '\n') {
                    i++;
                    //i - beforeOffset - 2 减2是为了去掉line末尾的\r\n 有\r\n时正则匹配错误
                    line = new String(buffer.array(),beforeOffset,i - beforeOffset - 2,encode);
                    if (line.matches("[\\s\\S]{0,15}第[\\S]{1,10}章[\\s\\S]{0,20}") || line.matches("全书完.*") || line.matches("剧终.*")) {
                        chapters.add(chapterCount++);
                        titles.add(line.replace(",","，"));
                        nioOffsets.add(beforeOffset);
                    }
                    beforeOffset = i;
                }
            }
            TxtChapterMsgModel model = new TxtChapterMsgModel();
            model.addChapter(chapters,null,titles,nioOffsets);
            model.setTxtSn(txtSn);
            return model;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
