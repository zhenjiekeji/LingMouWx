package LingMouWxPay.LingMouWxPayService;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import LingMouWxPay.LingMouWxPayconfig.LingMouWxPayConstant;
import LingMouWxPay.LingMouWxPayModel.LingMouWxPayAuthToken;
import LingMouWxPay.LingMouWxPayModel.LingMouWxPaySendData;
import LingMouWxPay.LingMouWxPayUtils.HttpUtil;
import LingMouWxPay.LingMouWxPayUtils.LingMouWxPayUtil;



/**
 * 微信支付业务逻辑实现类
 * Created by fanxin on 2017/12/1.
 */

@Service("payService")
public class LingMouWxPayServiceImpl implements LingMouWeixinPayPayService {

	/**
	 * 微信支付统一下单
	 **/
	public String LingMouWxPayUnifiedOrder(LingMouWxPayAuthToken plingMouWxPayAuthToken, String pLingMouWxPayRemoteAddr) {
		Map<String, String> resultMap = null;
		// 统一下单返回的预支付id
		String prepayId = null;
		LingMouWxPaySendData mLingMouWxPaySendData = new LingMouWxPaySendData();
		// 构建微信支付请求参数集合
		mLingMouWxPaySendData.setAppId(LingMouWxPayConstant.LINGMOUWXPAY_APP_ID);
		mLingMouWxPaySendData.setMch_id(LingMouWxPayConstant.LINGMOUWXPAY_MCH_ID);
		mLingMouWxPaySendData.setNotify_url(LingMouWxPayConstant.LINGMOUWXPAY_NOTIFY_URL);
		mLingMouWxPaySendData.setTrade_type(LingMouWxPayConstant.LINGMOUWXPAY_TRADE_TYPE_JSAPI);
		mLingMouWxPaySendData.setDevice_info(LingMouWxPayConstant.LINGMOUWXPAY_WEB);
		mLingMouWxPaySendData.setBody("灵眸科技有限公司");
		mLingMouWxPaySendData.setNonce_str(LingMouWxPayUtil.getRandomStr(32));
		mLingMouWxPaySendData.setOut_trade_no(LingMouWxPayUtil.getRandomStr(8));
		mLingMouWxPaySendData.setTotal_fee(1);
		mLingMouWxPaySendData.setSpbill_create_ip(pLingMouWxPayRemoteAddr);
		mLingMouWxPaySendData.setOpenId(plingMouWxPayAuthToken.getOpenid());
		// 将参数拼成map,生产签名
		mLingMouWxPaySendData.setSign(LingMouWxPayUtil.getSign(buildParamMap(mLingMouWxPaySendData)));
		// 将请求参数对象转换成xml
		String reqXml = LingMouWxPayUtil.sendDataToXml(mLingMouWxPaySendData);
		try {
			// 发送请求
			CloseableHttpResponse response = HttpUtil.Post(LingMouWxPayConstant.LINGMOUWXPAY_UNIFIED_ORDER_URL, reqXml, false);
			try {
				resultMap = LingMouWxPayUtil.parseXml(response.getEntity().getContent());
				// 关闭流
				EntityUtils.consume(response.getEntity());
			} finally {
				response.close();
			}
		} catch (Exception e) {
			System.out.println("微信支付统一下单异常");
		}
		String return_code = resultMap.get("return_code");
		String result_code = resultMap.get("result_code");
		if (LingMouWxPayConstant.LINGMOUWXPAY_RETURN_SUCCESS.equals(return_code) && LingMouWxPayConstant.LINGMOUWXPAY_RETURN_SUCCESS.equals(result_code)) {
			// return_code=通信标识
			// result_code=交易标识
			// 只有当returnCode与resultCode均返回“success”，才代表微信支付统一下单成功
			prepayId = "prepay_id=" + resultMap.get("prepay_id");
		}
		return prepayId;
	}

	public String LingMouWxPayRefund() {
		LingMouWxPaySendData mLingMouWxPaySendData = new LingMouWxPaySendData();
		// 构建微信支付请求参数集合
		mLingMouWxPaySendData.setAppId(LingMouWxPayConstant.LINGMOUWXPAY_APP_ID);
		mLingMouWxPaySendData.setMch_id(LingMouWxPayConstant.LINGMOUWXPAY_MCH_ID);
		mLingMouWxPaySendData.setNonce_str(LingMouWxPayUtil.getRandomStr(32));
		// paySendData.setTransaction_id(WeChatUtils.getRandomStr(32));
		// Out_trade_no与Transaction_id二选一
		mLingMouWxPaySendData.setOut_trade_no("jtVFcgbM");
		mLingMouWxPaySendData.setOut_refund_no("jtVFcgbM");
		mLingMouWxPaySendData.setTotal_fee(1);
		mLingMouWxPaySendData.setRefund_fee(1);
		mLingMouWxPaySendData.setOp_user_id(LingMouWxPayConstant.LINGMOUWXPAY_MCH_ID);
		// 将参数拼成map,生产签名
		mLingMouWxPaySendData.setSign(LingMouWxPayUtil.getSign(buildParamMap(mLingMouWxPaySendData)));
		// 将请求参数对象转换成xml
		String reqXml = LingMouWxPayUtil.sendDataToXml(mLingMouWxPaySendData);
		try {
			// 发送请求
			CloseableHttpResponse response = HttpUtil.Post(LingMouWxPayConstant.LINGMOUWXPAY_UNIFIED_ORDER_URL, reqXml, true);
			try {
				// 关闭流
				EntityUtils.consume(response.getEntity());
			} finally {
				response.close();
			}
		} catch (Exception e) {
			System.out.println("微信退款异常");
		}
		return null;
	}

	/**
	 * 构建统一下单参数map 用于生成签名
	 * 
	 * @param data
	 * @return SortedMap<String,Object>
	 */
	private SortedMap<String, Object> buildParamMap(LingMouWxPaySendData data) {
		SortedMap<String, Object> paramters = new TreeMap<String, Object>();
		Field[] fields = data.getClass().getDeclaredFields();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				if (null != field.get(data)) {
					paramters.put(field.getName().toLowerCase(), field.get(data).toString());
				}
			}
		} catch (Exception e) {
			System.out.print("构建签名map错误: ");
			e.printStackTrace();
		}

		return paramters;
	}

}
