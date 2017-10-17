package com.kiss.common;

import com.kiss.model.TxtChapterMsgModel;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * txt文件读取操作
 * nio实现{@link com.kiss.common.supper.NioTxtRead}
 * stream实现 {@link com.kiss.common.supper.StreamTxtRead}
 *
 * */

@Component
public interface TxtRead {
    /**
     * 将txt图书全部章节信息读出来
     * @param file txt文件
     * @param txtSn txt图书编号
     * @param encode txt文件编码格式
     *
     * */
    TxtChapterMsgModel txtChapterMsgLists(File file, String txtSn, String encode);
}
