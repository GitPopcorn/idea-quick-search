package top.nobodycares.quicksearch.utils;

/**
 * String Utilities
 * @author ZhouYi
 * @date 2021/11/12 16:28
 * @description description
 * @note note
 */
public class StringUtils {
	
	// ===== ===== ===== ===== [Static Utility Methods] ===== ===== ===== ===== //
	
	public static boolean isEmpty(CharSequence cs) {
		return (cs == null) || (cs.length() == 0);
		
	}
	
	public static boolean isNotEmpty(CharSequence cs) {
		return (cs != null) && (cs.length() > 0);
		
	}
	
}
