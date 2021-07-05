package com.line.backstage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.line.backstage.dao.mapper.OrderInfoMapper;
import com.line.backstage.entity.AccountInfo;
import com.line.backstage.entity.AccountRecord;
import com.line.backstage.entity.OrderInfo;
import com.line.backstage.entity.PositionInfo;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.service.AccountInfoService;
import com.line.backstage.service.AccountRecordService;
import com.line.backstage.service.OrderInfoService;
import com.line.backstage.service.PositionInfoService;
import com.line.backstage.utils.PageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

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
    @Autowired
    private PositionInfoService positionInfoService;
    @Autowired
    private AccountInfoService accountInfoService;
    @Autowired
    private AccountRecordService accountRecordService;

    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param orderInfo   实例对象
     * @return 是否成功
     */
    @Override
    public int save(Integer loginUserId, OrderInfo orderInfo) {
        if (orderInfo.getOrderId() == null) {
            return insert(loginUserId, orderInfo);
        } else {
            return update(loginUserId, orderInfo);
        }
    }

    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param orderInfo   实例对象
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(Integer loginUserId, OrderInfo orderInfo) {
        // 查询用户信息
        AccountInfo accountInfo = accountInfoService.queryByLoginUserId(loginUserId);

        // 新增资金变动记录
        AccountRecord accountRecord = new AccountRecord();
        accountRecord.setAccountId(accountInfo.getAccountId());
        accountRecord.setRecordType(3);
        accountRecord.setChangeMoney(orderInfo.getInvestAmount());
        accountRecord.setBeforeMoney(accountInfo.getAccountMoney());
        accountRecord.setAddDate(new Date());
        accountRecord.setEditDate(new Date());
        accountRecord.setServiceCharge(orderInfo.getOrderCharge());

        // 修改账户余额
        accountInfo.setAccountMoney(BigDecimal.valueOf(accountInfo.getAccountMoney()).subtract(BigDecimal.valueOf(orderInfo.getInvestAmount())).doubleValue());
        accountInfo.setOrderNum(accountInfo.getOrderNum() + 1);

        // 记录变动后的金额
        accountRecord.setAfterMoney(accountInfo.getAccountMoney());

        accountInfoService.update(loginUserId, accountInfo);

        // 生成持仓数据
        PositionInfo positionInfo = new PositionInfo();
        positionInfo.setUserId(loginUserId);
        positionInfo.setPositionStatus(1);
        positionInfo.setSkuName(orderInfo.getSkuName());
        positionInfo.setInvestType(orderInfo.getInvestType());
        positionInfo.setBeaginPrice(orderInfo.getSkuPrice());
        positionInfo.setNowPrice(orderInfo.getSkuPrice());
        positionInfo.setInvestAmount(orderInfo.getInvestAmount());
        positionInfo.setBeginDate(new Date());
        positionInfo.setAddDate(new Date());
        positionInfo.setEditDate(new Date());
        positionInfo.setAddUserId(loginUserId);
        positionInfo.setEditUserId(loginUserId);

        // 新增持仓数据
        positionInfoService.insert(loginUserId, positionInfo);

        // 新增订单信息
        orderInfo.setAddUserId(loginUserId);
        orderInfo.setPositionId(positionInfo.getPositionId());
        int result = orderInfoMapper.insertSelective(orderInfo);

        accountRecord.setOrderId(orderInfo.getOrderId());
        accountRecordService.insert(loginUserId, accountRecord);
        return result;
    }

    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param orderId     主键
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
    public int update(Integer loginUserId, OrderInfo orderInfo) {
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
    public OrderInfo queryById(Integer orderId) {
        return orderInfoMapper.selectByPrimaryKey(orderId);
    }

    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param orderInfo   查询条数
     * @return 对象列表
     */
    @Override
    public PageWrapper<OrderInfo> list(Integer loginUserId, OrderInfo orderInfo) {
        PageHelper.startPage(orderInfo.getPageNum(), orderInfo.getPageSize());
        orderInfo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        orderInfo.setUserId(loginUserId);
        PageInfo<OrderInfo> page = new PageInfo<>(orderInfoMapper.select(orderInfo));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }
}