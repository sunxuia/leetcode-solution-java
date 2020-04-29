package q550;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/encode-and-decode-tinyurl/
 *
 * Note: This is a companion problem to the System Design problem: Design TinyURL.
 *
 * TinyURL is a URL shortening service where you enter a URL such as https://leetcode.com/problems/design-tinyurl and
 * it returns a short URL such as http://tinyurl.com/4e9iAk.
 *
 * Design the encode and decode methods for the TinyURL service. There is no restriction on how your encode/decode
 * algorithm should work. You just need to ensure that a URL can be encoded to a tiny URL and the tiny URL can be
 * decoded to the original URL.
 */
public class Q535_EncodeAndDecodeTinyURL {

    // 这题的解法有很多, 因为条件很宽泛
    private static class Codec {

        private static final String PREFIX = "http://tinyurl.com/";

        private static final char[] CHARS =
                "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

        private Map<String, String> shortLongMap = new HashMap<>();

        private Map<String, String> longShortMap = new HashMap<>();

        public Codec() {
            shortLongMap.put(" ", " ");
            longShortMap.put(" ", " ");
        }

        // Encodes a URL to a shortened URL.
        public String encode(String longUrl) {
            if (longShortMap.containsKey(longUrl)) {
                return PREFIX + longShortMap.get(longUrl);
            }
            StringBuilder sb = new StringBuilder(" ");
            int hashCode = longUrl.hashCode() - 1;
            while (shortLongMap.containsKey(sb.toString().intern())) {
                sb.setLength(sb.length() - 1);
                hashCode = Math.abs(++hashCode);
                for (int i = hashCode; i != 0; i /= CHARS.length) {
                    sb.append(CHARS[i % CHARS.length]);
                }
            }
            String key = sb.toString().intern();
            shortLongMap.put(key, longUrl);
            longShortMap.put(longUrl, key);
            return PREFIX + key;
        }

        // Decodes a shortened URL to its original URL.
        public String decode(String shortUrl) {
            return shortLongMap.get(shortUrl.substring(PREFIX.length()));
        }
    }

    @Test
    public void testMethod() {
        Codec codec = new Codec();
        testCase(codec, "https://leetcode.com/problems/design-tinyurl");
    }

    private void testCase(Codec codec, String url) {
        String encode1 = codec.encode(url);
        Assert.assertNotEquals(url, encode1);

        String encode2 = codec.encode(url);
        Assert.assertEquals(encode1, encode2);

        String decode = codec.decode(encode1);
        Assert.assertEquals(url, decode);
    }

}
