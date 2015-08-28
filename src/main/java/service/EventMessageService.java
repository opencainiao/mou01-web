package service;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mou.common.DateUtil;
import org.springframework.stereotype.Service;

import com.mou.mongodb.base.springdb.dao.CommonDaoMongo;
import com.mou01.core.domain.wx.event.WxEvent;
import com.mou01.core.domain.wx.normal.WxTextMessage;
import com.mou01.core.util.WxMessageUtil;

/****
 * 事件消息处理器
 * 
 * @author NBQ
 *
 */
@Service("eventMessageService")
public class EventMessageService implements IEventMessageService {

	private static final Logger logger = LogManager.getLogger(EventMessageService.class);

	@Resource(name = "commonDaoMongo")
	private CommonDaoMongo commonDaoMongo;

	@Override
	public String handleEventMessage(Map<String, String> paramsMap) {

		if (paramsMap.get("Event").equals("subscribe")) {
			return handleSubscribe(paramsMap);
		} else if (paramsMap.get("Event").equals("unsubscribe")) {
			return handleUnSubscribe(paramsMap);
		}

		return handleNoneEvent(paramsMap);
	}

	/****
	 * 处理取消关注事件
	 * 
	 * @param paramsMap
	 * @return
	 */
	private String handleUnSubscribe(Map<String, String> paramsMap) {

		String FromUserName = paramsMap.get("FromUserName");// 发送方帐号（一个OpenID）

		logger.info("用户[{}]于[{}]取消关注", FromUserName, DateUtil.getCurrentTimsmp());

		WxEvent event = new WxEvent();
		event.SetInfoSubOrUnSub(paramsMap);

		this.commonDaoMongo.insertOne(event);

		return null;
	}

	/****
	 * 不支持的事件
	 * 
	 * @param paramsMap
	 * @return
	 */
	private String handleNoneEvent(Map<String, String> paramsMap) {
		WxTextMessage wxTextMessage = new WxTextMessage();

		String ToUserName = paramsMap.get("ToUserName");// 开发者微信号
		String FromUserName = paramsMap.get("FromUserName");// 发送方帐号（一个OpenID）

		wxTextMessage.setFromUserName(ToUserName);
		wxTextMessage.setToUserName(FromUserName);
		wxTextMessage.setMsgType("text");
		wxTextMessage.setCreateTime(new Date().getTime());
		String contentResponse = "不支持的事件";
		wxTextMessage.setContent(contentResponse);

		return WxMessageUtil.WxMessage2Xml(wxTextMessage);
	}

	/****
	 * 处理订阅事件
	 * 
	 * @param paramsMap
	 * @return
	 */
	private String handleSubscribe(Map<String, String> paramsMap) {

		// 登记数据库日志
		WxEvent event = new WxEvent();
		event.SetInfoSubOrUnSub(paramsMap);
		this.commonDaoMongo.insertOne(event);

		// 向关注用户返回信息
		WxTextMessage wxTextMessage = new WxTextMessage();

		String ToUserName = paramsMap.get("ToUserName");// 开发者微信号
		String FromUserName = paramsMap.get("FromUserName");// 发送方帐号（一个OpenID）

		wxTextMessage.setFromUserName(ToUserName);
		wxTextMessage.setToUserName(FromUserName);
		wxTextMessage.setMsgType("text");
		wxTextMessage.setCreateTime(new Date().getTime());
		String contentResponse = "欢迎您的关注，我们等你好久了，亲 :)";
		wxTextMessage.setContent(contentResponse);

		return WxMessageUtil.WxMessage2Xml(wxTextMessage);
	}

}
