package LingMouWxPay.LingMouWxPayService;

import LingMouWxPay.LingMouWxPayModel.LingMouWxPayAuthToken;

/**
 * 微信支付业务接口
 * Created by fanxin on 2017/12/1.
 */

public interface LingMouWeixinPayPayService {
	
	/**
	 * 统一下单接口
	 * @param authToken 授权token
	 * @param remoteAddr 请求主机ip
	 * @return prepayId 预支付id
	 */
	String LingMouWxPayUnifiedOrder(LingMouWxPayAuthToken pLingMouWxPayauthToken, String ingMouWxPayRemoteAddr);
	
	/**
	 * 申请退款接口
	 */
	String LingMouWxPayRefund();
}
