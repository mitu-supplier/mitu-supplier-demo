package cn.forest.common.service.utils;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;

public class ResultPage<T> {
  // 当前页
  private int page;
  // 每页的数量
  private int pageSize;

  // 总记录数
  private long total;
  // 结果集
  private List<T> list;

  public ResultPage(Object t) {
    if (t instanceof PageInfo) {
      PageInfo<T> info = (PageInfo) t;
      this.page = info.getPageNum();
      this.pageSize = info.getPageSize();
      this.total = info.getTotal();
      this.list = info.getList();
    } else if (t instanceof Page) {
      Page<T> info = (Page) t;
      this.page = Integer.parseInt(info.getCurrent() + "");
      this.pageSize = Integer.parseInt(info.getSize() + "");
      this.total = info.getTotal();
      this.list = info.getRecords();
    }
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }

}
