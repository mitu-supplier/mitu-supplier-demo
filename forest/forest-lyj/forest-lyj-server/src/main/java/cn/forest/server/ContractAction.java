package cn.forest.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.lyj.entity.Contract;
import cn.forest.lyj.mapper.ContractMapper;

@RestController
@RequestMapping("/contract")
public class ContractAction {

  @Autowired
  private ContractMapper contractMapper;

  @RequestMapping("/list")
  public Object list(Long page, Long pageSize,Long userId,String contractName,String projectName,String orgName,String leader,Long orgId) {
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("userId", userId);
    map.put("contractName", contractName);
    map.put("projectName", projectName);
    map.put("orgName", orgName);
    map.put("leader", leader);
    map.put("orgId", orgId);
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
      return contractMapper.insert(contract);
  }

  @RequestMapping("/update")
  public int update(@RequestBody Contract contract) {
    return contractMapper.updateById(contract);
  }

  @RequestMapping("/delete")
  public int delete(@RequestParam("id") Long id) {
    return contractMapper.deleteById(id);
      
  }
  
  
}
