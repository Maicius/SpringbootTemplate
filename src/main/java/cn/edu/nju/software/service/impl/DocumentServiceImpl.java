package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.constant.Constant;
import cn.edu.nju.software.entity.Cpws;
import cn.edu.nju.software.service.DocumentService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * author: maicius
 * date: 2018/12/11
 * description:
 */

@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String CPWS_KEY = "cpws";
    private String CPWS_FINISH_KEY = "cpws_finish";

    @Override
    public List<Cpws> getDocumentList(int start_id) {
        List<String> resultList = (List<String>) stringRedisTemplate.opsForList().range(CPWS_KEY, start_id, start_id + 25);
        assert resultList != null;
        List<Cpws> cpws_list = new ArrayList<>();
        for (String result : resultList) {
            Cpws cpws = JSON.parseObject(result, Cpws.class);
            cpws.setFile_name(cpws.getFile_name().split("[.]")[0]);
            cpws_list.add(cpws);
        }
        return cpws_list;
    }

    @Override
    public int saveDocumentId(String file_name) {
        Long res = stringRedisTemplate.opsForSet().add(CPWS_FINISH_KEY, file_name);
        System.out.println(res);
        return getFinishCpws().size();
    }

    @Override
    public StringBuilder getBpmnContent(String file_name) {
        File file = new File(Constant.FILE_DIR + file_name + ".xml");
        if (!file.exists()) {
            return null;
        } else {
            StringBuilder bpmn_content = new StringBuilder();
            try {
                BufferedReader reader = null;
                reader = new BufferedReader(new FileReader(file));
                String tempString;
                // 一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {
                    bpmn_content.append(tempString).append("\n");
                }
                return bpmn_content;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public int saveBpmnFile(String xml, String file_name) {
        try {
            File file = new File(Constant.FILE_DIR + file_name + ".xml");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(xml);
            bw.close();
            if (xml.contains("结束")) {
                return saveDocumentId(file_name);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<String> getFinishCpws() {
        Set<String> resSet = stringRedisTemplate.opsForSet().members(CPWS_FINISH_KEY);
        List<String> list;
        if(resSet != null){
            list = new ArrayList<>(resSet);
        }else{
            list = new ArrayList<>();
        }
        return list;
    }
}
