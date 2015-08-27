package service;

import java.util.Map;

/****
 * 文本消息处理
 * 
 * @author NBQ
 *
 */
public interface IEventMessageService {

	/****
	 * 处理文本消息
	 * 
	 * @param content
	 * @return
	 */
	String handleEventMessage(Map<String,String> paramsMap);

}
