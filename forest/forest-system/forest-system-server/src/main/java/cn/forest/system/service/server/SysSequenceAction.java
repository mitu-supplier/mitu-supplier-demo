package cn.forest.system.service.server;

import cn.forest.common.service.utils.ResultSave;
import cn.forest.system.entity.SysSequence;
import cn.forest.system.mapper.SysSequenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys_sequence")
public class SysSequenceAction {

    @Autowired
    private SysSequenceMapper sysSequenceMapper;

    @RequestMapping("/save")
    public Object save(@RequestBody SysSequence sysSequence) {
        int insert = sysSequenceMapper.insert(sysSequence);
        return ResultSave.resultSave(insert, sysSequence);
    }

    @RequestMapping("/getSeqValue")
    public String getSeqValue(@RequestParam("name") String name) {
        return sysSequenceMapper.getSeqValue(name);
    }

    @RequestMapping("/countByName")
    public int countByName(@RequestParam("name") String name) {
        return sysSequenceMapper.countByName(name);
    }
}
