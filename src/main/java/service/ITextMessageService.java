package service;

import java.util.Map;

/****
 * 文本消息处理
 * 
 * @author NBQ
 *
 */
public interface ITextMessageService {

	/****
	 * 处理普通消息
	 * 
	 * @param paramsMap
	 * @return
	 */
	public String handleNormalMessage(Map<String, String> paramsMap);

}
