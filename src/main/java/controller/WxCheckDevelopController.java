package controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mou01.core.util.CheckUtilWx;
import com.mou01.core.util.HttpServletRequestUtil;

/****
 * 
 * @author NBQ
 *
 */
@Controller
@RequestMapping("/wxcheck")
public class WxCheckDevelopController {

	private static final Logger logger = LogManager
			.getLogger(WxCheckDevelopController.class);
	/****
	 * 查询系统城市信息（按照父节点id）
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public Object check(Model model, HttpServletRequest request) {
		String params = HttpServletRequestUtil.getParams(request);
		logger.debug(params);
		try {

			String signature = HttpServletRequestUtil.getTrimParameter(request,
					"signature");
			String timestamp = HttpServletRequestUtil.getTrimParameter(request,
					"timestamp");
			String nonce = HttpServletRequestUtil.getTrimParameter(request,
					"nonce");
			String echostr = HttpServletRequestUtil.getTrimParameter(request,
					"echostr");

			if (CheckUtilWx.checkSignature(signature, timestamp, nonce)) {
				return echostr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
