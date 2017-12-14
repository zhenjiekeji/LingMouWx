package LingMouWxPay.LingMouWxPayController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import LingMouWxPay.LingMouWxPayconfig.LingMouWxPayConstant;

/**
 * 微信支付首页面控制器
 * Created by fanxin on 2017/12/1.
 */
@Controller
@RequestMapping(value = "/")
public class LingMouWxPayIndexController {
	@RequestMapping(value = "/")
	public String index1(Model model) {
		model.addAttribute("payURL", LingMouWxPayConstant.LINGMOUWXPAY_PAY_URL);
		return "index";
	}
}
