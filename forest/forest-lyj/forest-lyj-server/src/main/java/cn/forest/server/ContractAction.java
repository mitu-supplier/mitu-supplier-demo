package cn.forest.server;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.common.util.DateUtil;
import cn.forest.lyj.entity.Contract;
import cn.forest.lyj.mapper.ContractMapper;

@RestController
@RequestMapping("/contract")
public class ContractAction {

  @Autowired
  private ContractMapper contractMapper;

  @RequestMapping("/exportList")
  public Object list(Long userId,String contractName,String projectName,String orgName,String leader,String orgIds) {
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("userId", userId);
    map.put("contractName", contractName);
    map.put("projectName", projectName);
    map.put("orgName", orgName);
    map.put("leader", leader);
    if(!StringUtils.isEmpty(orgIds)) {
      map.put("orgIds", orgIds.split(","));
    }
    return contractMapper.getContractList(map);
  }
  
  
  @RequestMapping("/list")
  public Object list(Long page, Long pageSize,Long userId,String contractName,String projectName,String orgName,String leader,String orgIds) {
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("userId", userId);
    map.put("contractName", contractName);
    map.put("projectName", projectName);
    map.put("orgName", orgName);
    map.put("leader", leader);
    if(!StringUtils.isEmpty(orgIds)) {
      map.put("orgIds", orgIds.split(","));
    }
    PageHelper.startPage(Integer.parseInt(page+""),Integer.parseInt(pageSize+""));
    List<Contract> projectsList =contractMapper.getContractList(map);
    PageInfo<Contract> pageInfo = new PageInfo<Contract>(projectsList);
    return new ResultPage<Contract>(pageInfo);
  }

  @RequestMapping("/getById")
  public Object getById(@RequestParam("id") Long id) {
      return contractMapper.getContractById(id);
  }

  @RequestMapping("/save")
  public int save(@RequestBody Contract contract) {
    if(contract.getContractType()==null) {
      contract.setContractType(0);
    }
    /*
     * if(contract.getContractType()==0) { contract.setOtherAttachmentName(null);
     * contract.setOtherAttachment(null); }
     */
    if(contract.getContractType()==1) {
      contract.setContractTotal(null);
      contract.setContractLeader(null);
      contract.setContractTime(null);
      contract.setContractLeaderPhone(null);
      contract.setContractAttachmentName(null);
      contract.setContractAttachment(null);
    }
      return contractMapper.insert(contract);
  }

  @RequestMapping("/update")
  public int update(@RequestBody Contract contract) {
    /*
     * if(contract.getContractType()==0) { contract.setOtherAttachmentName(null);
     * contract.setOtherAttachment(null); }
     */
    if(contract.getContractType()==1) {
      contract.setContractTotal(null);
      contract.setContractLeader(null);
      contract.setContractTime(null);
      contract.setContractLeaderPhone(null);
      contract.setContractAttachmentName(null);
      contract.setContractAttachment(null);
    }
    contract.setUpdateTime(DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
    return contractMapper.updateContract(contract);
  }

  @RequestMapping("/delete")
  public int delete(@RequestParam("id") Long id) {
    return contractMapper.deleteById(id);
      
  }
  
  
}
