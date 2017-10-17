package com.kiss.common;

import com.kiss.model.TxtChapterMsgModel;
import org.springframework.stereotype.Component;

import java.io.File;
@Component
public interface TxtRead {
    TxtChapterMsgModel txtChapterMsgLists(File file, String txtSn, String encode);
}
