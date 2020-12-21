package springboot.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class MD5Util {
    public static String md5(String data) throws UnsupportedEncodingException {
        return md5(data, Charset.forName("UTF-8"));
    }

    public static String md5(String data, Charset encoding) throws UnsupportedEncodingException {
        return md5(data.getBytes(encoding));
    }

    public static String md5(byte[] data) {
        return DigestUtils.md5Hex(data);
    }

    public static boolean verify(String data, String md5) throws UnsupportedEncodingException {
        return verify(data, md5, Charset.forName("UTF-8"));
    }

    public static boolean verify(String data, String md5, Charset encoding) throws UnsupportedEncodingException {
        return md5(data, encoding).equals(md5);
    }
    
    public static String md5WithSalt(String source) throws Exception {
        // 16位随机数盐生成
        Random random = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(random.nextInt(99999999)).append(random.nextInt(99999999));
        int len = sb.length();
        // 不够16位，补位
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append(random.nextInt(9));
            }
        }
        String salt = sb.toString();
        // 获取加盐后的文本十六进制字符串形式的MD5摘要, 依赖commons-codec包
        String md5 = DigestUtils.md5Hex(source + salt);

        char[] cs = new char[48];
        // 将盐均匀撒入md5值中（○●○●○●○●○●○●○●○●○●○，其中实心点位置为盐）
        for (int i = 0; i < 48; i += 3) {
            cs[i] = md5.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = md5.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }

    public static String sign(Map<String, String> params, String secretKey) throws Exception {
        // 第一步：参数排序(按ASCII顺序排序)
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起(前后加上secretKey)
        StringBuilder query = new StringBuilder();
        query.append(secretKey);
        for (String key : keys) {
            if ("sign".equals(key)) {
                continue;
            }
            String value = params.get(key);
            if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                query.append(key).append(value);
            }
        }
        query.append(secretKey);
        // 第三步：加盐MD5处理，并转换为大写
        return md5WithSalt(query.toString()).toUpperCase();
    }


}
