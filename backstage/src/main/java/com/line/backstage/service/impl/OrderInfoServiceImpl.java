package com.line.backstage.service.impl;
 
import com.line.backstage.entity.OrderInfo;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.utils.PageWrapper;
import com.line.backstage.dao.mapper.OrderInfoMapper;
import com.line.backstage.service.OrderInfoService;
import org.springframework.stereotype.Service;
import java.util.Date;
 
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
 
/**
 * 订单信息(OrderInfo)表服务实现类
 *
 * @author Zy
 * @since 2021-06-29 14:53:18
 */
@Service("orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService {
 
    /**
     * 服务对象
     */
    @Resource
    private OrderInfoMapper orderInfoMapper;
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param orderInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int save(Integer loginUserId, OrderInfo orderInfo) {
        if(orderInfo.getOrderId() == null){
            return insert(loginUserId, orderInfo);
        } else {
            return update(loginUserId,orderInfo);
        }
    }
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param orderInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int insert(Integer loginUserId, OrderInfo orderInfo) {
        orderInfo.setAddUserId(loginUserId);
        return orderInfoMapper.insertSelective(orderInfo);
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param orderId 主键
     * @return 是否成功
     */
    @Override
    public int delete(Integer loginUserId, Integer orderId) {
		OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        orderInfo.setEditUserId(loginUserId);
        orderInfo.setEditDate(new Date());
        orderInfo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        return orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
    }
 
    /**
     * 修改数据
     *
     * @param orderInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int update(Integer loginUserId, OrderInfo orderInfo){
		OrderInfo o = orderInfoMapper.selectByPrimaryKey(orderInfo.getOrderId());
		// FIXME 待完善
        return orderInfoMapper.updateByPrimaryKeySelective(o);
	}
 
    /**
     * 通过ID查询单条数据
     *
     * @param orderId 主键
     * @return 实例对象
     */
    @Override
    public OrderInfo queryById(Integer orderId){
		return orderInfoMapper.selectByPrimaryKey(orderId);
	}
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param orderInfo 查询条数
     * @return 对象列表
     */
    @Override
    public PageWrapper<OrderInfo> list(Integer loginUserId, OrderInfo orderInfo) {
        PageHelper.startPage(orderInfo.getPageNum(), orderInfo.getPageSize());
        orderInfo.setDel(DataEnum.FLAG_STATUS_VALID.getCode());
        PageInfo<OrderInfo> page = new PageInfo<>(orderInfoMapper.select(orderInfo));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }
}