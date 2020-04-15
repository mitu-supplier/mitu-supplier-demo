package cn.forest.common.exception.util;


public enum BusinessErrorCode {
    /**
     *
     */
    APP_KEY_INVALID (1000, "非认证用户，请求非法"),
    SIGN_INVALID (1001, "签名验证失败"),
    IP_INVALID (1002, "访问非法"),
    REFRESH_TOKEN_INVALID (1003, "Refresh Token验证失败"),
    ACCESS_TOKEN_INVALID (1004, "Access Token验证失败"),
    REQUEST_FREQUENTLY (1005, "请求过于频繁"),
    ENTERPRISE_OFFLINE(1006,"企业下线"),
    TRY_LATER(1007,"请稍后重试"),
    SYSTEM_ERROR(1008,"系统异常，请联系管理员"),
    CALL_INTERFACE_FAILED(1009,"调用接口失败"),

    
    SERVER_UNKNOW_ERROR(2000, "服务器忙，服务器未知错误"),
    INVALID_TIME_RANGE(2001, "与卡密系统服务器时差不能相差半小时以上"),
    ACCOUNT_NOT_SUFFICIENT(2002,"账户余额不足"),
    ACCOUNT_QUERY_FAILSE(2002,"账户余额查询失败"),
    PARAME_INVALID(2003,"参数不正确"),
    INVENTORY_NOT_SUFFICIENT(2004,"尊敬的用户您好，因系统升级维护，暂无法领取使用，详询客服400-010-0017"),//库存不足
    PARAME_EMPTY(2005,"参数为空"),
    REOPEAT_VALUECARDNO(2006,"重复的单号"),
    POINTS_NOT_SUFFICIEN(2007,"可用积分不足"),
    PERTROCHINA_LIMIT_USE(2008,"中石油系统维护中，暂不支持该面值充值，请系统恢复后再试，如有疑问请联系400-010-0017"),
    
    COUPON_PW_EXPIRED(3000, "卡密已过期"),
    COUPON_PW_ENCODE_ERROR(3001, "卡密加密失败"),
    COUPON_QUERY_ERROR(3002, "卡密查询订单失败"),
    COUPON_NOT_FOUND(3003,"未找到券"),
    COUPON_STATUS_ERROR(3004,"只有发放状态的券才需要作废"),
    COUPON_STATUS_VOID(3005,"已作废券不能重复作废"),
    ENTERPRISE_ORDER_DUPLICATE(3006,"企业订单号重复"),
    COUPON_CODE_CREATE_ERROR(3007,"券码生成失败"),
    PRODUCT_COUPON_NOT_FOUND(3008,"未找到券产品"),
    ENTERPRISE_ORDER_NOT_FOUND(3009,"企业订单未找到"),
    ORDER_EXPIRED(3010,"订单已过期"),
    ORDER_STATUS_ERROR(3011,"订单状态异常"),
    MERCHANT_DAYLY_NOT_USABILITY(3012,"超出每日限制，请明天再使用"), 
    MERCHANT_MONTHLY_NOT_USABILITY(3013,"超出每月限制，请下个月再使用"),
    MERCHANT_CHARGING_NOT_USABILITY(
            3014, "您有一个订单正在充值中，为保证此订单准时、顺利到账，本次充值申请将会延迟，请您在收到之前订单的充值成功短信后再次提交充值申请。"), 
    ENTER_MONTHLY_NOT_USABILITY(3015, "本月已经兑换，请下月再兑换"), 
    TAOBAO_ACCOUNT_NOT_LOGIN(
            3016, "淘宝账号未登录，请您在登录淘宝之后再领取天猫券"),
    JOIN_PASS_ACTIVITY(3017,"已经参加过该活动"),
   
    ACTIVITY_OUT(3018, "无相关活动"),
    USER_NO_PRIVILEGE(3019,"user does not have privilege"),
    ACTIVITY_END(3020, "activity is end"),
    DRAW_LOTTERY_CODE(3021,"draw lottery code false"),
    PHONE_IS_NOT_DRAWED(3022, "this phone is not drawed"),
    CANNOT_ACQUIRE_LOCKER(3023, "can not acquire locker"),
    COUPON_NOT_BEGIN(3024,"券还不到开始使用时间"),
    COUPON_EXPEIRED(3024,"券已过期"),
    RECHARGE_QUEUE_UP(3025,"当前充值人数过多，请稍后再试哦~"),
    RECHARGE_FREQUENT_QUEUE_UP(3026,"您操作过于频繁，建议过一会再来充值哦~"),
    COUPON_NOT_AVAILABLE(3027,"优惠券已失效"),
    COUPON_USE(3028,"券已被使用"),
    COUPON_CATEGORY_ERROR(3029,"券类型错误"),
    COUPON_OPTIONAL_CONTENT_ERROR(3030,"可选包内容错误"),
    COUPON_DISABLED(3031, "券码已失效"),
    EXCHANGE_NOT_SUPPORTED(3032,"该手机号所在区域不支持兑换"),

    //第三方服务异常，不需要刷新
    THIRD_PARTY_EXCEPTION(4001, "没有可以兑换的天猫购物券商品，请确认后再兑换"),
    //第三方服务异常，需要刷新
    THIRD_PARTY_REFRESH_EXCEPTION(4002, "网络异常，请刷新或稍后重试"),
    //第三方服务异常，需要刷新
    THIRD_PARTY_EXCHANGE_EXCEPTION(4003, "兑换失败，券存在异常，请确认券状态后再重新兑换"),
    //第三方服务异常，需要刷新
    THIRD_PARTY_EXCHANGE_REFRESH_EXCEPTION(4004, "兑换失败！网络异常，请刷新或稍后重试"),
    //第三方服务异常，需要重新进入重试
    THIRD_PARTY_RETRY_LATER_EXCEPTION(4005, "网络异常，请稍后重新进入重试"),
    
    VALUE_CARD_DECRYPT_ERROR(5001,"加油充值卡解密错误"),
    VALUE_CARD_GET_ERROR(5002,"加油充值卡获取失败，请刷新或稍后重试"),//获取新的充值卡，都被并发使用时，使用此错误码
    VALUE_CARD_NOT_FOUND(5003,"未找到加油充值卡"),
    VALUE_CARD_DUPLICATE(5004,"加油充值卡重复"),
    CARD_NO_ERROR(5005,"卡号错误"),
    OUT_OF_SERVICE_TIME(5006,"不在服务时间"),
    
    SMS_TEMPLATE_NORMAL_NOT_FOUND(6001,"未找到可用短信模板"),
    
    //订单
    ORDER_HAS_CHECKED_IN(7001,"订单已登记"),
    ORDER_HAS_CHECKED_IN_BY_OTHER(7002,"订单已被他人登记"),
    ORDER_NO_REPEATED(7003,"订单号重复"),
    ORDER_NOT_EXIST(7005, "订单不存在"),
    ORDER_NO_PERMISSION(7006,"无权操作此订单"),
    ORDER_CONFIRM_FAILSE(7007,"确认预占库存失败"),
    ORDER_INVALID(7008,"无效的订单"),
    ORDER_ROG_FAILSE(7009,"确认收货失败，无可用卡券"),
    ORDER_ACTIVE_FAILSE(7010,"您的券已被别的手机号激活，请输入正确的手机号"),

    CONSUMER_CHANGE_BALANCE_FAILED(8001, "账户余额变动失败，请检查参数"),
    CONSUMER_NOT_FOUND(8002, "此账户不存在"),
    CONSUMER_BALANCE_NOT_ENOUGH(8003, "账户余额不足"),
    CONSUMER_MERCHANT_ORDER_NO_INVALID(8004, "请输入正确的订单号"),
    CONSUMER_CATEGORY_ERROR(8005, "用户类型错误"),
    CONSUMER_PHONE_ERROR(8006, "手机号错误"),
    CONSUMER_IMAGE_CODE_ERROR(8007, "图形验证码错误"),
    CONSUMER_SMS_CODE_SEND_ERROR(8008, "短信验证码发送失败"),
    CONSUMER_SMS_CODE_FAIL(8009, "短信校验失败"),
    CONSUMER_NOT_LOGIN(8010,"用户未登录"),
    CONSUMER_MUST_ENTERPRISE_CONSUMER(8011,"手机号必须为兑换时的手机号"),

    GOODS_DETAIL_NOT_FOUND(9001, "此商品不存在"), GOODS_NOT_COLLECTED(9002, "商品采集失败"),

    GOODS_EXCHANGE_IN_TMALL_FAILED(10001, "兑换天猫券失败"),
    
    MERCHANT_ORDER_NOT_FOUND(11001,"商品订单未找到"),

    AES_DECRYPT_FAILED(10031, "AES解密失败"),
    DES_DECRYPT_FAILED(10032, "DES解密失败"),
    AES_ENCRYPT_FAILED(10033,"AES加密失败"),
    ADD_SIGN_FAILED(10034,"加签失败"),
    
    PASSWORD_ERROR(10040, "密码错误"),
    PW_TIMES_EXCEED_LIMIT(10041, "输入密码次数超限,账户被锁定"),
    REQUEST_DUPLICATE(10042, "请勿重复操作"),
    TRADE_IN_PROCESS(10043, "正在处理，请稍后"),
    UNICOMPAY_ACCOUNT_LOCKED(10044, "电子券账户被锁定 请联系10188解锁"),
    
    PROCESS_INST_START_FALSE_CODE(50001,"流程实例发布失败"),
    PROCESS_USER_WITH_NONE_STARTABLE(50002,"当前用户无权限开启工作流"),
    
    SESSION_INVALID(60001,"您的会话已经失效，请重新登陆"),
    CART_EMPTY(60002,"请先选择商品"),
    AMOUNT_ERROR(60003,"金额错误"),
    NUMBER_ERROR(60004,"数量错误"),

    //商品
    GOODS_INEXISTENCE(70001,"商品不存在"),
    INVALID_GOODS(70001,"无效的商品"),
    AREA_LIMIT_CHECAK_EXCEPTION(70002,"区域验证异常"),
    FEIGHT_QUERY_FAILSE(7003,"运费查询失败"),
    PRICE_QUERY_FAILSE(7004,"价格查询失败"),
    GOODS_PRICE_CHANGE(7005,"商品价格已变化"),
    GOODS_STOCK_NOT_SUFFICIENT(7006,"商品库存不足，请重新选择"),
    GOODS_INVALID(7007,"部分商品无效，不能操作"),
    GOODS_CANNOT_DELETE(7008,"存在不能删除的商品，不能操作"),
    GOODS_CANNOT_RECYCLE(7009,"存在不能放入回收站的商品，不能操作"),
    GOODS_CANNOT_REVERT(7010,"存在不能还原的商品，不能操作"),
    GOODS_CANNOT_UNDER(7011,"存在不能下架的商品，不能操作"),
    CATEGORY_INEXISTENCE(7012,"分类不存在!"),
    CATEGORY_REACH_LIMIT(7013,"分类等级达到上限!"),
    CATEGORY_HAVE_SUBCLASS(7014,"分类存在子分类!"),
    CATEGORY_HAVE_GOODS(7015,"分类下存在商品!"),
    SPECS_INEXISTENCE(7016,"无效的规格项!"),
    LIMITED_AREA(7017,"商品在该配送区域内受限!"),
    //会员
    PARAM_DISPOSE_ERRO(80001,"参数处理错误"),
    MEMBER_DISABLE(80002,"当前账号已经禁用，请联系管理员"),
    REDIS_AUTHORIZATION_INEXISTENCE(80003,"redis授权信息不存在"),
    GAIN_LOGIN_INFO_ERROR(80004,"获取登录信息出错"),
    USERNAME_OR_PASSWORD_ERRO(80005,"用户名或密码错误"),
    SHOP_ID_REPEAT(80006,"店铺ID重复"),
    GAIN_ACCOUNT_INFO_ERROR(80004,"获取账户信息出错"),
    ALREADY_BIND_SHOP(80005,"当前手机号已绑定店铺"),

    //促销
    NO_PERMISSIONS_OPERATION(85001,"无权操作"),
    NO_PERMISSION_EDIT(85002,"活动已开始，请先关闭活动再进行修改操作"),
    PROMOTION_GOODS_CONFLICT(85003,"活动商品冲突"),
    PROMOTION_COUPON_RUN_OUT(85004,"优惠券已被领完"),
    PROMOTION_COUPON_LIMIT_NUM(85005,"超出每个人领取限额"),
    PROMOTION_COUPON_LIMIT_NUM_RUN_OUT(85006,"优惠券发券数量超出剩余数量"),
    NO_PROMOTION_GOODS(85007,"请选择促销商品"),
    PROMOTION_COUPON_UNAVAILABLE(85008,"优惠券未达到使用条件"),
    PROMOTION_INVALID(85009,"无效的活动"),
    PROMOTION_COUPON_PRICE_DISCREPANCY(85010,"消费金额需大于优惠券面额"),
    PROMOTION_EMAIL_NOT_MATCHING(85011,"收件人邮箱不匹配"),
    PROMOTION_FINISH(85012, "活动已截止"),
    PROMOTION_QUANTITY_EXCEED_LIMIT(85013, "激活码生成数量不能超过5000"), 
    POINT_INCREASE_FAILSE(85014, "积分增加失败，请联系管理员"),
    POINT_ACCOUNT_ERROR(85015, "积分账户状态异常，积分操作失败"), 
    POINT_INSUFFICIENT(85016, "积分不足，扣减失败"),
    POINT_DECREASE_FREEZE_FAILSE(85017, "冻结积分扣减失败"),

    // 卡券
    CARD_COUPON_NO_PERMISSION(91001, "无权操作此卡券"), 
    CARD_COUPON_INVALID(91002, "无效的卡券"),
    CARD_COUPON_PRICE_MISMATCH(91003, "电商红包兑换价格不匹配"), 
    CARD_COUPON_STATUS_ERROR(91004, "卡券状态异常"),

    // 系统
    IMAGE_FORMAT_NO_SUPPORTED(92000, "图片格式不支持"), 
    EXPORT_EXCEL_FAIL(92001, "导出excel失败"),
    WITHOUT_STORAGE_SOLUTION(92002, "无可用的存储方案"), 
    UPLOAD_FILE_FAILED(92003, "上传文件失败"),
    DELETE_FILE_FAILED(92004, "删除文件失败"), 
    ACCOUNT_UNAVAILABLE(92005, "账户不可用"),
    DUPLICATE_USER_NAME(92006, "用户名已被使用，请更换用户名"), 
    SYS_USER_NO_ROLE(92006, "用户未分配角色"),

    // 风控
    EXCEED_ISSUE_LIMIT(93000, "超出发放限制"), 
    EXCEED_RECEIVE_LIMIT(93001, "超出领取限制"), 
    REPEAT_DELIVERY(93002, "重复发货"),

    // 支付
    PAYMENT_CANNOT_BE_ZERO(94000, "支付金额不能为零"), 
    PAY_REFUND_STATUS_ERROR(94002, "订单退款状态错误"),
    PAY_REFUND_AMOUNT_ERROR(94003, "订单退款金额错误"), 
    PAY_REFUND_FAILSE(94004,"订单退款失败"),
    PAY_CREAT_ERROR(94005,"订单支付接口下单失败"),
    PAY_FAILSE(94006,"支付失败"), 
    PAY_AMOUNT_ERROR(94007,"支付金额错误"),
    PAY_TRANFER_FAIL(94008,"转账失败"),
    PAY_WITHDRAWAL_FAIL(94009,"提现失败"),
    PAY_REPEAT(94010,"重复支付"),
    EXCEED_WITHDRAWAL_LIMIT(94011,"本日提现已达到上限"),
    //分销
    HAVE_BIND_STORE(95000,"不可重复绑定"),
    EXCEED_EXTRACT_LIMIT(95001,"超出提取限制"),
    NOT_BIND_DISTRIBUTOR(95002,"未绑定分销人员"),
    SOTRE_USE_TYPE_ERROR(95003,"分销店铺使用方式配置错误"),
    
    // 库存
    PURCHASE_NUMBER_LESSER_THAN_ORDER(100001, "实际采购数量少于订单需要"), 
    PURCHASE_NUMBER_MORE_THAN_ORDER(100002, "实际采购数量大于订单需要"),;

    private int code;

    private String message;

    BusinessErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
      return code;
    }

    public void setCode(int code) {
      this.code = code;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
    
    
}
