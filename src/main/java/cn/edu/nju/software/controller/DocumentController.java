package cn.edu.nju.software.controller;

import cn.edu.nju.software.entity.Cpws;
import cn.edu.nju.software.entity.ResultVO;
import cn.edu.nju.software.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * author: maicius
 * date: 2018/12/11
 * description:
 */

@ResponseBody
@RestController
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @GetMapping("/getDocument")
    public List<Cpws> getDocument(HttpServletRequest request, Integer start_id) {
        return documentService.getDocumentList(start_id);
    }

    @PostMapping(value = "/saveBPMN", produces = "application/json")
    public ResultVO saveDocument(HttpServletRequest request, String xml, String file_name) {
        ResultVO resultVO = new ResultVO();
        int result = documentService.saveBpmnFile(xml, file_name);
        resultVO.setSucceed(true);
        resultVO.setObject(result);
        return resultVO;
    }

    @GetMapping(value = "/getBpmnContent")
    public ResultVO getBpmnContent(HttpServletRequest request, String file_name) {
        ResultVO resultVO = new ResultVO();
        try {
            StringBuilder bpmn_content = documentService.getBpmnContent(file_name);
            if (bpmn_content != null) {
                resultVO.setSucceed(true);
                resultVO.setObject(bpmn_content);
            } else {
                resultVO.setSucceed(false);
            }
            return resultVO;
        }catch (Exception e){
            e.printStackTrace();
            resultVO.setSucceed(false);
            return resultVO;
        }
    }

    @GetMapping(value = "/getFinishCpws")
    public ResultVO getFinishCpws(HttpServletRequest request) {
        ResultVO resultVO = new ResultVO();
        try {
            resultVO.setObject(documentService.getFinishCpws());
            resultVO.setSucceed(true);
        }catch (Exception e){
            resultVO.setSucceed(false);
        }
        return resultVO;
    }
}
