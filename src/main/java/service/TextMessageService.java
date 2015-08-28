package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mou01.core.domain.wx.WxMessage;
import com.mou01.core.domain.wx.WxMessageType;
import com.mou01.core.domain.wx.normal.WxTextMessage;
import com.mou01.core.domain.wx.toweixin.News;
import com.mou01.core.domain.wx.toweixin.NewsMessage;
import com.mou01.core.util.WxMessageUtil;

@Service("textMessageService")
public class TextMessageService implements ITextMessageService {

	@Override
	public String handleTextMessage(String content) {

		String rtnContent = null;
		if (content.equals("1")) {
			rtnContent = "环境光临";
		} else if (content.equals("2")) {
			WxMessage message = createNewsMessage();
			rtnContent = message.toXML();
		} else if (content.equals("?") || content.equals("？")) {
			StringBuffer sb = new StringBuffer();
			sb.append("请参考如下命令列表:").append("\n");
			sb.append("1:欢迎").append("\n");
			sb.append("2:图文消息").append("\n");
			rtnContent = sb.toString();
		} else {
			rtnContent = "如有问题，请输入?";
		}

		return rtnContent;
	}

	private News createNews() {
		News news = new News();
		news.setTitle("慕课网介绍");
		news.setPicUrl("http://img1.imgtn.bdimg.com/it/u=4061686576,439297045&fm=21&gp=0.jpg");
		news.setUrl("http://www.imooc.com/");
		news.setDescription("专注做好IT技能教育的MOOC，符合互联网发展潮流接地气儿的MOOC。我们免费，我们只教有用的，我们专心做教育。");
		return news;
	}

	private NewsMessage createNewsMessage() {
		NewsMessage newsMessage = new NewsMessage();

		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(WxMessageType.MSGTYPE_NEWS);

		List<News> newsList = new ArrayList<News>();
		newsList.add(createNews());

		newsMessage.setArticles(newsList);

		return newsMessage;
	}

	@Override
	public String handleNormalMessage(Map<String, String> paramsMap) {

		String ToUserName = paramsMap.get("ToUserName");// 开发者微信号
		String FromUserName = paramsMap.get("FromUserName");// 发送方帐号（一个OpenID）
		String content = paramsMap.get("Content");// 文本消息内容

		String rebackContent = null;
		if (content.equals("1")) {
			rebackContent = "环境光临";
		} else if (content.equals("2")) {
			WxMessage message = createNewsMessage();
			message.setFromUserName(ToUserName);
			message.setToUserName(FromUserName);
			return message.toXML();
		} else if (content.equals("?") || content.equals("？")) {
			StringBuffer sb = new StringBuffer();
			sb.append("请参考如下命令列表:").append("\n");
			sb.append("1:欢迎").append("\n");
			sb.append("2:图文消息").append("\n");
			rebackContent = sb.toString();
		} else {
			rebackContent = "如有问题，请输入?";
		}

		WxTextMessage wxTextMessage = new WxTextMessage();
		wxTextMessage.setFromUserName(ToUserName);
		wxTextMessage.setToUserName(FromUserName);
		wxTextMessage.setMsgType("text");
		wxTextMessage.setCreateTime(new Date().getTime());
		String contentResponse = rebackContent;
		wxTextMessage.setContent(contentResponse);

		return WxMessageUtil.WxMessage2Xml(wxTextMessage);
	}
}
