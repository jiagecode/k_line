package com.line.backstage.dao.mapper;

import com.line.backstage.bases.TkBaseMapper;
import com.line.backstage.entity.SysDiyInfo;
import com.line.backstage.entity.sysentity.ManOptionVo;

import java.util.List;
import java.util.Map;

/**
 * 后台管理系统角色与菜单对应关系(SysPower)表数据库访问层
 *
 * @author jack
 * @since 2000-07-01 11:35:45
 */
public interface SysDiyInfoMapper extends TkBaseMapper<SysDiyInfo> {

    List<SysDiyInfo> selectAsList(SysDiyInfo sysDiyInfo);
    List<Map<String, Object>> querySkuDataList();
    List<ManOptionVo> queryOptionsForAgent();
}