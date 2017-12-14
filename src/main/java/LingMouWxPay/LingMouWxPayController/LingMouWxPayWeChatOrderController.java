package LingMouWxPay.LingMouWxPayController;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import LingMouWxPay.LingMouWxPayconfig.LingMouWxPayConstant;
import LingMouWxPay.LingMouWxPayModel.LingMouWxPayAuthToken;
import LingMouWxPay.LingMouWxPayService.LingMouWeixinPayPayService;
import LingMouWxPay.LingMouWxPayUtils.LingMouWxPayUtil;

/**
 * 微信支付页面控制器
 * Created by fanxin on 2017/12/1.
 */

@Controller
@RequestMapping(value = "/m/weChat")
public class LingMouWxPayWeChatOrderController {

	@Autowired
	private LingMouWeixinPayPayService mLingMouWeixinPayPayService;

	/**
	 * 统一下单
	 */
	@RequestMapping(value = "/unifiedOrder")
	public String LingMouWxPayUnifiedOrder(HttpServletRequest pRequest, Model pModel) {
		// 用户同意授权，获得的code
		String LingMoucode = pRequest.getParameter("code");
		// 通过code获取网页授权access_token
		LingMouWxPayAuthToken mLingMouWxPayAuthToken = LingMouWxPayUtil.getTokenByAuthCode(LingMoucode);
		// 调用统一下单service
		String prepayId = mLingMouWeixinPayPayService.LingMouWxPayUnifiedOrder(mLingMouWxPayAuthToken,
				pRequest.getRemoteAddr());
		if (!LingMouWxPayUtil.isEmpty(prepayId)) {
			String timeStamp = LingMouWxPayUtil.getTimeStamp();// 当前时间戳
			String nonceStr = LingMouWxPayUtil.getRandomStr(20);// 不长于32位的随机字符串

			SortedMap<String, Object> signMap = new TreeMap<String, Object>();// 自然升序map
			signMap.put("appId", LingMouWxPayConstant.LINGMOUWXPAY_APP_ID);
			signMap.put("package", prepayId);
			signMap.put("timeStamp", timeStamp);
			signMap.put("nonceStr", nonceStr);
			signMap.put("signType", "MD5");

			pModel.addAttribute("appId", LingMouWxPayConstant.LINGMOUWXPAY_APP_ID);
			pModel.addAttribute("timeStamp", timeStamp);
			pModel.addAttribute("nonceStr", nonceStr);
			pModel.addAttribute("prepayId", prepayId);
			pModel.addAttribute("paySign", LingMouWxPayUtil.getSign(signMap));// 获取签名
		} else {
			System.out.println("微信统一下单失败,订单编号:失败原因");
			return "reorderlist:/";// 支付下单失败，重定向至订单列表
		}
		// 将支付需要参数返回至页面，采用h5方式调用支付接口
		return "h5Pay";
	}

	/**
	 * 申请退款
	 */
	@RequestMapping(value = "/refund")
	public String LingMouWxPayRefund(HttpServletRequest pRequest, Model pModel) {
		// 调用统一下单service
		mLingMouWeixinPayPayService.LingMouWxPayRefund();
		return "h5Pay";

	}
}