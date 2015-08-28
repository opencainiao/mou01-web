package controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mou.common.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mou01.core.domain.wx.normal.WxTextMessage;
import com.mou01.core.util.CheckUtilWx;
import com.mou01.core.util.HttpServletRequestUtil;
import com.mou01.core.util.WxMessageUtil;

import service.IEventMessageService;
import service.ITextMessageService;

/****
 * 
 * @author NBQ
 *
 */
@Controller
@RequestMapping("/wx")
public class WxCheckDevelopController {

	private static final Logger logger = LogManager.getLogger(WxCheckDevelopController.class);

	@Resource(name = "textMessageService")
	private ITextMessageService textMessageService;

	@Resource(name = "eventMessageService")
	private IEventMessageService eventMessageService;

	/****
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	@ResponseBody
	public Object homeEntrance(Model model, HttpServletRequest request) {
		String params = HttpServletRequestUtil.getParams(request);
		logger.debug(params);
		try {

			String signature = HttpServletRequestUtil.getTrimParameter(request, "signature");
			String timestamp = HttpServletRequestUtil.getTrimParameter(request, "timestamp");
			String nonce = HttpServletRequestUtil.getTrimParameter(request, "nonce");
			String echostr = HttpServletRequestUtil.getTrimParameter(request, "echostr");

			if (CheckUtilWx.checkSignature(signature, timestamp, nonce)) {
				return echostr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/****
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/home", method = RequestMethod.POST)
	@ResponseBody
	public Object homeBusiness(Model model, HttpServletRequest request) {
		String params = HttpServletRequestUtil.getParams(request);
		logger.debug(params);
		try {

			Map<String, String> paramsMap = WxMessageUtil.xml2Map(request);
			logger.debug("请求参数\n{}", JsonUtil.getPrettyJsonStr(paramsMap));

			String ToUserName = paramsMap.get("ToUserName");// 开发者微信号
			String FromUserName = paramsMap.get("FromUserName");// 发送方帐号（一个OpenID）
			String CreateTime = paramsMap.get("CreateTime");// 消息创建时间 （整型）
			String MsgType = paramsMap.get("MsgType");// 开发者微信号
			String Content = paramsMap.get("Content");// 文本消息内容
			String MsgId = paramsMap.get("MsgId");// 消息id，64位整型

			String message = null;
			if ("event".equals(MsgType)) {
				message = this.eventMessageService.handleEventMessage(paramsMap);
			} else {
				message = textMessageService.handleNormalMessage(paramsMap);
				logger.debug("后台服务器返回给微信的消息\n{}", message);
			}

			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
