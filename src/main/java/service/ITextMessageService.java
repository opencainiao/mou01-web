package service;

/****
 * 文本消息处理
 * 
 * @author NBQ
 *
 */
public interface ITextMessageService {

	/****
	 * 处理文本消息
	 * 
	 * @param content
	 * @return
	 */
	String handleTextMessage(String content);

}
