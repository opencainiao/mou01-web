package service;

import java.util.Map;

/****
 * 文本消息处理
 * 
 * @author NBQ
 *
 */
public interface ITextMessageService {
	
	public String handleNormalMessage(Map<String,String >paramsMap);

	/****
	 * 处理文本消息
	 * 
	 * @param content
	 * @return
	 */
	String handleTextMessage(String content);

}
