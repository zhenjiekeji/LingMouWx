package LingMouWxPay.LingMouWxPayconfig;

/**
 * 灵眸微信支付常量定义
 * Created by fanxin on 2017/12/1.
 */
public class LingMouWxPayConstant {
	/**
	 * 公众号AppId
	 */
	public static final String LINGMOUWXPAY_APP_ID = "wxfec2eaedb40a2ffa";

	/**
	 * 公众号AppSecret
	 */
	public static final String LINGMOUWXPAY_APP_SECRET = "15fe1c89dc3bdc0dec467c3e887f47a5";

	/**
	 * 微信支付商户号
	 */
	public static final String LINGMOUWXPAY_MCH_ID = "1493303292";

	/**
	 * 微信支付API秘钥
	 */
	public static final String LINGMOUWXPAY_KEY = "QBmgXTJP7QDTudt42qN4Jy2zuc2BvfYh";

	/**
	 * 微信交易类型:公众号支付
	 */
	public static final String LINGMOUWXPAY_TRADE_TYPE_JSAPI = "JSAPI";

	/**
	 * WEB
	 */
	public static final String LINGMOUWXPAY_WEB = "WEB";

	/**
	 * 返回成功字符串
	 */
	public static final String LINGMOUWXPAY_RETURN_SUCCESS = "SUCCESS";

	/**
	 * 支付地址(包涵回调地址)
	 */
	public static final String LINGMOUWXPAY_PAY_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2bef02f0ed84edfc&redirect_uri=http%3a%2f%2flingmouwxpay.ailingmou.com%2flingmouwxpay%2fm%2fweChat%2funifiedOrder&response_type=code&scope=snsapi_base#wechat_redirect";

	/**
	 * 微信统一下单url
	 */
	public static final String LINGMOUWXPAY_UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	/**
	 * 微信申请退款url
	 */
	public static final String LINGMOUWXPAY_REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

	/**
	 * 微信支付通知url
	 */
	public static final String LINGMOUWXPAY_NOTIFY_URL = "http://lingmouwxpay.ailingmou.com/lingmouwxpay/";
	
	/**
	 * 证书位置
	 */
	public static final String LINGMOUWXPAY_CERT_PATH = "../../../webapp/cert/apiclient_cert.p12";

	/**
	 * 通过code获取授权access_token的URL
	 */
	public static String Authtoken_URL(String code) {
		StringBuffer url = new StringBuffer();
		url.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=");
		url.append(LingMouWxPayConstant.LINGMOUWXPAY_APP_ID);
		url.append("&secret=");
		url.append(LingMouWxPayConstant.LINGMOUWXPAY_APP_SECRET);
		url.append("&code=");
		url.append(code);
		url.append("&grant_type=authorization_code");
		return url.toString();
	}
}
