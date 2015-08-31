package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.mou.common.StringUtil;
import org.springframework.stereotype.Service;

import com.mou01.core.domain.wx.message.WxMessage;
import com.mou01.core.domain.wx.message.WxMessageType;
import com.mou01.core.domain.wx.message.normal.WxTextMessage;
import com.mou01.core.domain.wx.message.toweixin.Image;
import com.mou01.core.domain.wx.message.toweixin.ImageMessage;
import com.mou01.core.domain.wx.message.toweixin.Music;
import com.mou01.core.domain.wx.message.toweixin.MusicMessage;
import com.mou01.core.domain.wx.message.toweixin.News;
import com.mou01.core.domain.wx.message.toweixin.NewsMessage;

@Service("textMessageService")
public class TextMessageService implements ITextMessageService {

	@Override
	public String handleNormalMessage(Map<String, String> paramsMap) {

		String ToUserName = paramsMap.get("ToUserName");// 开发者微信号
		String FromUserName = paramsMap.get("FromUserName");// 发送方帐号（一个OpenID）
		String contentIn = paramsMap.get("Content");// 文本消息内容

		String content = null;
		if (StringUtil.isEmpty(contentIn)) {
			StringBuffer sb = new StringBuffer();
			sb.append("请参考如下命令列表:").append("\n");
			sb.append("1:欢迎").append("\n");
			sb.append("2:图文消息").append("\n");
			sb.append("3:图片消息").append("\n");
			sb.append("4:歌曲《小苹果》").append("\n");
			content = sb.toString();
		} else if (contentIn.equals("1")) {
			content = "环境光临";
		} else if (contentIn.equals("2")) {
			WxMessage message = createNewsMessage(FromUserName, ToUserName);
			return message.toXML();
		} else if (contentIn.equals("3")) {
			WxMessage message = createImageMessage(FromUserName, ToUserName);
			return message.toXML();
		} else if (contentIn.equals("4")) {
			WxMessage message = createMusicMessage(FromUserName, ToUserName);
			return message.toXML();
		} else if (contentIn.equals("?") || contentIn.equals("？")) {
			StringBuffer sb = new StringBuffer();
			sb.append("请参考如下命令列表:").append("\n");
			sb.append("1:欢迎").append("\n");
			sb.append("2:图文消息").append("\n");
			sb.append("3:图片消息").append("\n");
			sb.append("4:歌曲《小苹果》").append("\n");
			content = sb.toString();
		} else {
			content = "如有问题，请输入?";

		}

		WxMessage message = createTextMessage(FromUserName, ToUserName, content);

		return message.toXML();
	}

	private WxMessage createImageMessage(String ToUserName, String FromUserName) {

		ImageMessage im = new ImageMessage();

		im.setMsgType(WxMessageType.MSGTYPE_IMAGE);
		Image image = new Image();
		image.setMediaId("CcRr44pNlMmeQawJIM7rdEDwXVgQcIp22EyNrAlt-ngHRK48shqi7jv3V9Y1Yvvz");
		im.setImage(image);
		im.setCreateTime(new Date().getTime());
		im.setFromUserName(ToUserName);
		im.setToUserName(FromUserName);
		return im;
	}

	private WxMessage createMusicMessage(String ToUserName, String FromUserName) {

		String music_url = "http://yinyueshiting.baidu.com/data2/music/123297884/120125029144090726164.mp3?xcode=d86b9f1baf0f757323407a79428c3f0f";

		Music music = new Music();
		music.setThumbMediaId("CcRr44pNlMmeQawJIM7rdEDwXVgQcIp22EyNrAlt-ngHRK48shqi7jv3V9Y1Yvvz");
		music.setTitle("小苹果");
		music.setDescription("筷子兄弟-小苹果");
		music.setMusicUrl(music_url);
		music.setHQMusicUrl(music_url);

		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setMsgType(WxMessageType.MSGTYPE_MUSIC);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMusic(music);
		musicMessage.setFromUserName(ToUserName);
		musicMessage.setToUserName(FromUserName);

		return musicMessage;
	}

	private WxMessage createTextMessage(String ToUserName, String FromUserName,
			String content) {

		WxTextMessage wxTextMessage = new WxTextMessage();
		wxTextMessage.setFromUserName(ToUserName);
		wxTextMessage.setToUserName(FromUserName);
		wxTextMessage.setMsgType(WxMessageType.MSGTYPE_TEXT);
		wxTextMessage.setCreateTime(new Date().getTime());
		wxTextMessage.setContent(content);

		return wxTextMessage;
	}

	private News createNews() {
		News news = new News();
		news.setTitle("慕课网介绍");
		news.setPicUrl("http://img1.imgtn.bdimg.com/it/u=4061686576,439297045&fm=21&gp=0.jpg");
		news.setUrl("http://www.imooc.com/");
		news.setDescription("专注做好IT技能教育的MOOC，符合互联网发展潮流接地气儿的MOOC。我们免费，我们只教有用的，我们专心做教育。");
		return news;
	}

	private NewsMessage createNewsMessage(String ToUserName, String FromUserName) {
		NewsMessage newsMessage = new NewsMessage();

		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(WxMessageType.MSGTYPE_NEWS);

		List<News> newsList = new ArrayList<News>();
		newsList.add(createNews());

		newsMessage.setArticles(newsList);
		newsMessage.setFromUserName(ToUserName);
		newsMessage.setToUserName(FromUserName);

		return newsMessage;
	}
}
