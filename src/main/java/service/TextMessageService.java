package service;

import org.springframework.stereotype.Service;

@Service("textMessageService")
public class TextMessageService implements ITextMessageService {

	@Override
	public String handleTextMessage(String content) {

		String rtnContent = null;
		if (content.equals("1")) {
			rtnContent = "环境光临";
		} else if (content.equals("2")) {
			rtnContent = "如有问题，请输入?";
		} else if (content.equals("?") || content.equals("？")) {
			rtnContent = "你好，请问有什么可以帮您 :)";
		} else {
			rtnContent = "如有问题，请输入?";
		}

		return rtnContent;
	}
}
