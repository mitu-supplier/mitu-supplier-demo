package cn.forest.mall.fallback;

import cn.forest.mall.remote.SysRoleRemote;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SysRoleBack implements SysRoleRemote {

    @Override
    public int saveSupplierRole(String supplierIds, String permissionIds) {
        return 0;
    }

    @Override
    public Object getAll() {
        return null;
    }
}
