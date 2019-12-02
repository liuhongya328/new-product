package springboot.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class StringUtil {
	
	public static final String DEF_REGEX="\\#(.+?)\\#";
	
    /**
     * 判断对象（也可能是字符串）是否为空
     *
     * @param obj 要判断的对象
     * @return true-是；false-否
     */
    public static boolean isNullorEmpty(Object obj) {
        if (obj == null || "".equals(obj.toString().trim())) {
            return true;
        }
        return false;
    }

    public static boolean isNULL(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断对象（也可能是字符串）是否不为空
     *
     * @param obj 要判断的对象
     * @return true-是；false-否
     */
    public static boolean notNullorEmpty(Object obj) {
        if (obj != null && !"".equals(obj.toString().trim())) {
            return true;
        }
        return false;
    }

    /**
     * 获得UUID信息
     *
     * @return UUID
     */
    public static String UID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
              * 获取6位注册码
     Title: VerifyCode
     *@author QiuSheng Lv
     *@date 2019年8月20日
     */
    public static String VerifyCode() {
    	 String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        return verifyCode;
    }
   
    
    /**
     * 获得字符串的MD5格式
     *
     * @param source 源字符串
     * @return MD5后的字符串
     */
    public static String md5(String source) {
        return DigestUtils.md5Hex(source);
    }

    public static String getSubString(String str, int start, int end) {
        return str.substring(start, Math.min(end, str.length()));
    }

    public static String percent2String(float obj) {
        if (notNullorEmpty(obj)) {
            NumberFormat nf = NumberFormat.getPercentInstance();
            nf.setMinimumFractionDigits(2);
            return nf.format(obj);
        }
        throw new RuntimeException("格式转换错误");
    }

    public static String isNullToStr(String obj) {
        if (obj == null) {
            obj = "";
        }
        return obj;
    }

    /**
     * 将浮点型数字小数点保留两位（如小数点后超出两位进行四舍五入）转化为中文大写钱数字符串 <p>
     * <p>
     * 示例：<p>
     * 23.45将得到”贰拾叁元肆角伍分“<br>
     *
     * @param doubleNum 录入浮点型数字
     * @return 将浮点型数字小数点保留两位（如小数点后超出两位进行四舍五入）转化为中文大写钱数字符串
     */
    public static String numberToChinese(double doubleNum) {
        // 转化成标准格式
        DecimalFormat df = new DecimalFormat("############0.00");
        String sNum = df.format(doubleNum);

        // 如果末尾为.00，将.00去掉
        int pointPos = sNum.indexOf(".");
        if (sNum.substring(pointPos).compareTo(".00") == 0) {
            sNum = sNum.substring(0, pointPos);
        }

        String temp = "";
        String[] sBIT = new String[4];
        String[] sUNIT = new String[4];
        String[] sCents = new String[2];
        String sIntD = "";
        String sDecD = "";
        String NtoC = "";
        int iCount = 0;
        int lStartPos = 0;
        int iLength = 0;

        sBIT[0] = "";
        sBIT[1] = "拾";
        sBIT[2] = "佰";
        sBIT[3] = "仟";
        sUNIT[0] = "";
        sUNIT[1] = "万";
        sUNIT[2] = "亿";
        sUNIT[3] = "万";
        sCents[0] = "分";
        sCents[1] = "角";

        // 校验输入是否为0元
        if (sNum.compareTo("0") == 0 || sNum.compareTo("0.0") == 0 || sNum.compareTo("0.00") == 0) {
            NtoC = "零元整";
            return NtoC;
        }

        if (sNum.indexOf(".") > 0) {
            temp = sNum.substring(0, sNum.indexOf("."));
        } else {
            temp = sNum;
        }
        iCount = temp.length() % 4 != 0 ? temp.length() / 4 + 1 : temp.length() / 4;

        // 整数部分
        for (int i = iCount; i >= 1; i--) {
            if (i == iCount && temp.length() % 4 != 0) {
                iLength = temp.length() % 4;
            } else {
                iLength = 4;
            }
            sIntD = temp.substring(lStartPos, lStartPos + iLength);
            for (int j = 0; j < sIntD.length(); j++) {
                if (Integer.parseInt(sIntD.substring(j, j + 1)) != 0) {
                    switch (Integer.parseInt(sIntD.substring(j, j + 1))) {
                        case 1:
                            NtoC = NtoC + "壹" + sBIT[sIntD.length() - j - 1];
                            break;
                        case 2:
                            NtoC = NtoC + "贰" + sBIT[sIntD.length() - j - 1];
                            break;
                        case 3:
                            NtoC = NtoC + "叁" + sBIT[sIntD.length() - j - 1];
                            break;
                        case 4:
                            NtoC = NtoC + "肆" + sBIT[sIntD.length() - j - 1];
                            break;
                        case 5:
                            NtoC = NtoC + "伍" + sBIT[sIntD.length() - j - 1];
                            break;
                        case 6:
                            NtoC = NtoC + "陆" + sBIT[sIntD.length() - j - 1];
                            break;
                        case 7:
                            NtoC = NtoC + "柒" + sBIT[sIntD.length() - j - 1];
                            break;
                        case 8:
                            NtoC = NtoC + "捌" + sBIT[sIntD.length() - j - 1];
                            break;
                        case 9:
                            NtoC = NtoC + "玖" + sBIT[sIntD.length() - j - 1];
                            break;
                    }
                } else if (j + 1 < sIntD.length() && sIntD.charAt(j + 1) != '0') {
                    NtoC = NtoC + "零";
                }
            }
            lStartPos = lStartPos + iLength;
            if (i < iCount) {
                if (Integer.parseInt(sIntD.substring(sIntD.length() - 1, sIntD.length())) != 0
                        || Integer.parseInt(sIntD.substring(sIntD.length() - 2, sIntD.length() - 1)) != 0
                        || Integer.parseInt(sIntD.substring(sIntD.length() - 3, sIntD.length() - 2)) != 0
                        || Integer.parseInt(sIntD.substring(sIntD.length() - 4, sIntD.length() - 3)) != 0) {
                    NtoC = NtoC + sUNIT[i - 1];
                }
            } else {
                NtoC = NtoC + sUNIT[i - 1];
            }
        }
        if (NtoC.length() > 0) {
            NtoC = NtoC + "元";
        }

        // 小数处理部分
        if (sNum.indexOf(".") > 0) {
            sDecD = sNum.substring(sNum.indexOf(".") + 1);
            for (int i = 0; i < 2; i++) {
                if (Integer.parseInt(sDecD.substring(i, i + 1)) != 0) {
                    switch (Integer.parseInt(sDecD.substring(i, i + 1))) {
                        case 1:
                            NtoC = NtoC + "壹" + sCents[1 - i];
                            break;
                        case 2:
                            NtoC = NtoC + "贰" + sCents[1 - i];
                            break;
                        case 3:
                            NtoC = NtoC + "叁" + sCents[1 - i];
                            break;
                        case 4:
                            NtoC = NtoC + "肆" + sCents[1 - i];
                            break;
                        case 5:
                            NtoC = NtoC + "伍" + sCents[1 - i];
                            break;
                        case 6:
                            NtoC = NtoC + "陆" + sCents[1 - i];
                            break;
                        case 7:
                            NtoC = NtoC + "柒" + sCents[1 - i];
                            break;
                        case 8:
                            NtoC = NtoC + "捌" + sCents[1 - i];
                            break;
                        case 9:
                            NtoC = NtoC + "玖" + sCents[1 - i];
                            break;
                    }
                } else if (NtoC.length() > 0) {
                    NtoC = NtoC + "零";
                }
            }
        } else {
            NtoC = NtoC + "整";
        }

        // 判断NtoC是否最后一位字符是否为"零",如是的话，删除"零"
        if (NtoC.substring(NtoC.length() - 1).compareTo("零") == 0) {
            NtoC = NtoC.substring(0, NtoC.length() - 1);
        }
        return NtoC;
    }

    public static String numberToChinese(BigDecimal bigdecimal) {
        if (bigdecimal == null) {
            bigdecimal = new BigDecimal("0");
        }
        return numberToChinese(bigdecimal.doubleValue());
    }

    /**
     * 将字符串有某种编码转变成另一种编码
     *
     * @param string        编码的字符串
     * @param originCharset 原始编码格式
     * @param targetCharset 目标编码格式
     * @return String 编码后的字符串
     */
    public static String encodeString(String string, Charset originCharset, Charset targetCharset) {
        return string = new String(string.getBytes(originCharset), targetCharset);
    }

    /**
     * URL编码
     *
     * @param string  编码字符串
     * @param charset 编码格式
     * @return String
     */
    @SuppressWarnings("deprecation")
    public static String encodeUrl(String string, String charset) {
        if (null != charset && !charset.isEmpty()) {
            try {
                return URLEncoder.encode(string, charset);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return URLEncoder.encode(string);
    }

    /**
     * URL编码
     *
     * @param string  解码字符串
     * @param charset 解码格式
     * @return String
     */
    @SuppressWarnings("deprecation")
    public static String decodeUrl(String string, String charset) {
        if (null != charset && !charset.isEmpty()) {
            try {
                return URLDecoder.decode(string, charset);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }
        return URLDecoder.decode(string);
    }

    /**
     * 判断字符串是否是空的
     * 方法摘自commons.lang
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * <p>判断字符串是否是""," ",null,注意和isEmpty的区别</p>
     * 方法摘自commons.lang
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static String getDateSlashToLine(String dateStr) {
        if (dateStr != null) {
            return dateStr.replace("/", "-");
        }
        return null;
    }

    public static Object[] remove(Object[] array, final Object objectToFind) {
        if (ArrayUtils.contains(array, objectToFind)) {
            array = ArrayUtils.remove(array, ArrayUtils.indexOf(array, objectToFind));
        }

        return array;
    }
    
    /**
     * 保留小数位数（四舍五入）
     * @param content 处理内容
     * @param scale  保留小数位数
     * @return
     */
    public static String decimalRounding(String content, int scale){
    	if(StringUtils.isBlank(content))
    		return "";
    	BigDecimal rtn = new BigDecimal(content.replace(" ", ""));
    	return rtn.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }
    
    /**
     * 
     * String去除\n 回车(\u000a)    \t 水平制表符(\u0009) 
     * 
     * */
    public static String parseToNeatString(String str) {
    	String dest = "";
    	if (str!=null) {
    	Pattern p = Pattern.compile("\\s*|\t|\r|\n");
    	Matcher m = p.matcher(str);
    	dest = m.replaceAll("");
    	}
    	return dest;
    }
    
    /**
     * 转为小写
     * @param str
     * @return
     */
    public static String toLower(String str) {
    	if (str ==null) {
    		return null;
    	}
    	return str.toLowerCase();
    }
    
    public static String render(String template, Map<String, Object> data) {
        return render(template,data,DEF_REGEX);
    }
    
    /**
     * 根据正则表达式替换文本内容
     * @param template
     * @param data
     * @param regex
     * @return
     */
    public static String render(String template, Map<String, Object> data,String regex) {
        if(StringUtils.isBlank(template)){
            return "";
        }
        if(StringUtils.isBlank(regex)){
            return template;
        }
        if(data == null || data.size() == 0){
            return template;
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(template);
        while (matcher.find()) {
        	String name = matcher.group(1); // 键名
        	matcher.appendReplacement(sb,isNullToStr(data.get(name)));
        }
        matcher.appendTail(sb);

        return sb.toString();  
    }
    
    public static String isNullToStr(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }
    
	public static String createString(List repeatStr, int repeatNum, String split) {
		if (repeatNum <= 0) {
			throw new IllegalArgumentException("the repeatNum must more then 0 ! repeatNum=" + repeatNum);
		}

		StringBuilder sb = new StringBuilder(128);

		if (split == null) {
			for (int i = 0; i < repeatNum; i++)
				sb.append(repeatStr.get(i));
		} else {
			for (int i = 0; i < repeatNum; i++) {
				sb.append(repeatStr.get(i)).append(split);
			}
			sb.delete(sb.length() - split.length(), sb.length());
		}

		return sb.toString();
	}
	
	  public static boolean isBlank(Object str)
	  {
	    if (str == null) {
	      return true;
	    }
	    if ((str instanceof CharSequence)) {
	      return StringUtils.isBlank((CharSequence)str);
	    }
	    return false;
	  }
	  
	  public static boolean isEmpty(Object obj)
	  {
	    if (obj == null)
	    {
	      return true;
	    }
	    if ((obj instanceof List))
	    {
	      return ((List) obj).size() == 0;
	    }
	    if ((obj instanceof String))
	    {
	      return ((String) obj).trim().equals("");
	    }
	    return false;
	  }
	   
	  /**
	   * 判断对象不为空
	   * 
	   * @param obj
	   *      对象名
	   * @return 是否不为空
	   */
	  public static boolean isNotEmpty(Object obj)
	  {
	    return !isEmpty(obj);
	  }
}
