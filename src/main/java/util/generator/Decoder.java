package util.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.PrimitiveIterator;

public class Decoder {

    private static class Dict {

        boolean isCharacter;

        int len, escapedCodePoint;

        Map<Integer, Dict> children = new HashMap<>();
    }

    private Dict root = new Dict();

    public void addEscape(String str, int escapedCodePoint) {
        int[] codePoints = str.codePoints().toArray();
        Dict dict = root;
        for (int i = 0; i < codePoints.length; i++) {
            final int cp = codePoints[i];
            if (dict.isCharacter) {
                throw new GeneratorException(
                        "Error while define escape character [%s], %s is already a escaped character!",
                        str, str.substring(0, i));
            }
            dict = dict.children.computeIfAbsent(cp, k -> new Dict());
            dict.len = i + 1;
        }
        dict.isCharacter = true;
        dict.escapedCodePoint = escapedCodePoint;
    }

    public String decode(String raw) {
        StringBuilder sb = new StringBuilder(raw.length());
        Dict dict = root;
        PrimitiveIterator.OfInt it = raw.codePoints().iterator();
        while (it.hasNext()) {
            int cp = it.nextInt();
            sb.appendCodePoint(cp);
            dict = dict.children.getOrDefault(cp, root);
            if (dict.isCharacter) {
                sb.setLength(sb.length() - dict.len);
                sb.appendCodePoint(dict.escapedCodePoint);
                dict = root;
            }
        }
        return sb.toString();
    }

}
