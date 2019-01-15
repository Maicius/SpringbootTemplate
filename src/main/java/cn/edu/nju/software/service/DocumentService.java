package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Cpws;

import java.util.List;

/**
 * author: maicius
 * date: 2018/12/11
 * description:
 */
public interface DocumentService {
    List<Cpws> getDocumentList(int start_id);

    int saveDocumentId(String file_name);

    StringBuilder getBpmnContent(String file_name);

    int saveBpmnFile(String xml, String file_name);

    List<String> getFinishCpws();

}
