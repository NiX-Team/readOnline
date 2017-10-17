package com.kiss.common.supper;

import com.kiss.common.TxtRead;
import com.kiss.model.TxtChapterMsgModel;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class StreamTxtRead implements TxtRead{
    /**
     * 找出txt的章节信息
     * @param file txt文件
     * @param txtSn txt图书编号
     * */
    @Override
    public TxtChapterMsgModel txtChapterMsgLists(File file, String txtSn, String encode) {

        System.out.println("stream------------");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),encode));
            String line;
            int chapterCount = 0;
            long lineCount = 0;
            List chapters = new ArrayList();
            List offsets = new ArrayList();
            List titles = new ArrayList();
            TxtChapterMsgModel model = new TxtChapterMsgModel();
            while ((line = reader.readLine()) != null) {
                if (line.matches("第.*")) {
                    chapters.add(chapterCount++);
                    offsets.add(lineCount);
                    titles.add(line);
                }
                lineCount++;
            }
            model.addChapter(chapters,offsets,titles,null);
            model.setTxtSn(txtSn);
            return model;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
