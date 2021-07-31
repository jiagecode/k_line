package com.line.backstage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.line.backstage.async.AsyncGenOrderMinAndMilsDataService;
import com.line.backstage.dao.mapper.OrderInfoMapper;
import com.line.backstage.dao.mapper.UserInfoMapper;
import com.line.backstage.entity.AccountInfo;
import com.line.backstage.entity.AccountRecord;
import com.line.backstage.entity.OrderInfo;
import com.line.backstage.entity.PositionInfo;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.redis.RedisUtil;
import com.line.backstage.service.AccountInfoService;
import com.line.backstage.service.AccountRecordService;
import com.line.backstage.service.OrderInfoService;
import com.line.backstage.service.PositionInfoService;
import com.line.backstage.utils.DateUtil;
import com.line.backstage.utils.PageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单信息(OrderInfo)表服务实现类
 *
 * @author jack
 * @since 2000-06-29 14:53:18
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
    @Resource
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AsyncGenOrderMinAndMilsDataService dataService;
    private static final String ORDER_KEY = "order:ids:";
    /**
     * 冻结标志
     */
    private final int FORBID_INT = 1;

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

    @Override
    public int editForWin(Integer loginUserId, OrderInfo orderInfo) {
        OrderInfo o = orderInfoMapper.queryOneById(orderInfo.getOrderId());
        if(o == null){
            return -1;
        }
        if(o.getOrderStatus() == 2){
            return  -2;
        }
        if(o.getSettlementDate()!= null && o.getSettlementDate().getTime()<=System.currentTimeMillis()+2000 ){
            return  -3;
        }
        Integer winFlag = orderInfo.getWinFlag();
        Integer winOld = o.getWinFlag();
        if(winFlag.equals(winOld)){
            return  -4;
        }
        Integer investType = o.getInvestType();
        Double inPoint = o.getInPoint();
        boolean isWin = winFlag == 1;
        double outPont = getEndPoint(isWin,inPoint,investType);
        o.setWinFlag(winFlag);
        o.setOutPoint(outPont);
        int num = orderInfoMapper.updateForWin(o);
        if(1 == num){
            Map<String,Object> map = getCallMap(o,loginUserId,isWin);
            //调推送方法
            dataService.autoGenDataMain(map);
        }
        return num;
    }

    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param orderInfo   实例对象
     * @return 是否成功
     */
    @Override
    public int insert(Integer loginUserId, OrderInfo orderInfo) {
        Map<String, Object> map = insertForBuy(loginUserId, orderInfo);
        if ("1".equals(map.get("resultCode"))) {
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> insertForBuy(Integer loginUserId, OrderInfo orderInfo) {
        Map<String, Object> map = new HashMap<>();
        // 查询用户信息
        AccountInfo accountInfo = accountInfoService.queryByLoginUserId(loginUserId);
        if (accountInfo.getAccountStatus() == FORBID_INT) {
            //用户账户被冻结
            map.put("resultCode", "-1");
            map.put("resultDesc", "用户账户被冻结");
            return map;
        }
        if (accountInfo.getMoneyStatus() == FORBID_INT) {
            //用户资金被冻结
            map.put("resultCode", "-2");
            map.put("resultDesc", "用户资金被冻结");
            return map;
        }
        if (orderInfo.getOrderAmount() > accountInfo.getAccountMoney()) {
            //用户资金不足
            map.put("resultCode", "-3");
            map.put("resultDesc", "用户资金不足");
            return map;
        }
        Integer notEnd = orderInfoMapper.notEndOrderNum(loginUserId);
        if(notEnd !=null && notEnd >0){
            //用户资金不足
            map.put("resultCode", "-4");
            map.put("resultDesc", "您有订单未结算,暂时无法下单!");
            return map;
        }
        if (orderInfo.getInvestAmount() == null) {
            orderInfo.setInvestAmount(orderInfo.getOrderAmount());
        }
        //设置当前时间为下单时间
        Date addDate = new Date();
        //购买时长 秒数
        int orderCycle = orderInfo.getOrderCycle() == null ? 30 : orderInfo.getOrderCycle();
        //订单结算时间
        Date endDate = new Date(addDate.getTime() + orderCycle * 1000);
        // 新增资金变动记录
        AccountRecord accountRecord = new AccountRecord();
        accountRecord.setAccountId(accountInfo.getAccountId());
        accountRecord.setRecordType(3);
        accountRecord.setChangeMoney(orderInfo.getInvestAmount());
        accountRecord.setBeforeMoney(accountInfo.getAccountMoney());
        accountRecord.setAddDate(addDate);
        accountRecord.setEditDate(addDate);
        accountRecord.setServiceCharge(orderInfo.getOrderCharge());
        accountRecord.setDiyId(0);
        // 修改账户余额
        accountInfo.setAccountMoney(BigDecimal.valueOf(accountInfo.getAccountMoney()).subtract(BigDecimal.valueOf(orderInfo.getInvestAmount())).doubleValue());
        accountInfo.setOrderNum(accountInfo.getOrderNum() + 1);
        accountInfo.setEditUserId(loginUserId);
        accountInfo.setEditDate(addDate);
        // 记录变动后的金额
        accountRecord.setAfterMoney(accountInfo.getAccountMoney());

        accountInfoService.updateOfOrder(accountInfo);

        // 生成持仓数据
        PositionInfo positionInfo = new PositionInfo();
        positionInfo.setUserId(loginUserId);
        positionInfo.setPositionStatus(1);
        positionInfo.setSkuName(orderInfo.getSkuName());
        positionInfo.setInvestType(orderInfo.getInvestType());
        positionInfo.setBeaginPrice(orderInfo.getSkuPrice());
        positionInfo.setNowPrice(orderInfo.getSkuPrice());
        positionInfo.setInvestAmount(orderInfo.getInvestAmount());
        positionInfo.setBeginDate(addDate);
        positionInfo.setAddDate(addDate);
        positionInfo.setEditDate(addDate);
        positionInfo.setEndDate(endDate);
        positionInfo.setAddUserId(loginUserId);
        positionInfo.setEditUserId(loginUserId);
        positionInfo.setDiyId(0);
        // 新增持仓数据
        positionInfoService.insert(loginUserId, positionInfo);

        // 新增订单信息
        orderInfo.setAddUserId(loginUserId);
        orderInfo.setUserId(loginUserId);
        orderInfo.setEditUserId(loginUserId);
        orderInfo.setPositionId(positionInfo.getPositionId());
        if (orderInfo.getWinFlag() == null) {
            orderInfo.setWinFlag(0);
        }
        orderInfo.setAddDate(addDate);
        orderInfo.setEditDate(addDate);
        orderInfo.setSettlementDate(endDate);
        orderInfo.setDel(1);
        orderInfo.setDiyId(0);
        //已下单
        orderInfo.setOrderStatus(1);
        //查询用户赢率
        Integer winRate = userInfoMapper.queryWinRate(loginUserId);
        if (winRate == null) {
            winRate = 50;
        }
        //生成一个随机数 0~1
        double romRate = Math.random();
        boolean isWin = winRate * 1.0 / 100 > romRate;
        double inPoint = orderInfo.getInPoint();
        int investType = null == orderInfo.getInvestType() ? 1 : orderInfo.getInvestType();
        double outPoint = getEndPoint(isWin, inPoint, investType);
        orderInfo.setOutPoint(outPoint);
        orderInfo.setWinFlag(isWin ? 1 : 2);
        int result = orderInfoMapper.insertSelective(orderInfo);
        Integer orderId = orderInfo.getOrderId();
        accountRecord.setOrderId(orderId);
        accountRecordService.insert(loginUserId, accountRecord);
        map = getCallMap(orderInfo,loginUserId,isWin);
        //下单成功 记录失效时间
        redisUtil.set(ORDER_KEY + orderId, 1, orderCycle);
        //调推送方法
        dataService.autoGenDataMain(map);
        return map;
    }

    private Map<String, Object> getCallMap(OrderInfo o,Integer loginUserId,boolean iswin){
        Map<String, Object> map = new HashMap<>();
        map.put("resultCode", "1");
        map.put("resultDesc", "下单成功！");
        map.put("orderId", o.getOrderId());
        map.put("userId", loginUserId);
        map.put("isWin",iswin );
        map.put("investType", o.getInvestType());
        map.put("inPoint", o.getInPoint());
        map.put("outPoint", o.getOutPoint());
        map.put("inDate", DateUtil.dateToTimeStamp(o.getAddDate()));
        map.put("outDate", DateUtil.dateToTimeStamp(o.getSettlementDate()));
        map.put("orderCycle", o.getOrderCycle());
        map.put("skuCode",o.getSkuCode());
        return map;
    }
    /**
     * 计算用户卖出点位
     *
     * @param isWin
     * @param inPoint
     * @param investType
     * @return
     */
    private double getEndPoint(boolean isWin, double inPoint, int investType) {
        double rate = getOneRandom();
        if (1 == investType) {
            /*买涨*/
            return isWin ? inPoint * (1 + rate) : inPoint * (1 - rate);
        } else {
            /*买亏*/
            return isWin ? inPoint * (1 - rate) : inPoint * (1 + rate);
        }
    }

    private double getOneRandom() {
        double romRate = Math.random();
        if (romRate > 0.5) {
            romRate = romRate * 0.4;
        }
        if (romRate > 0.005 && romRate < 0.3) {
            return romRate;
        }
        return getOneRandom();
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
        if (o != null) {
            // FIXME 待完善
            return orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
        }
        return 0;
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