package util.generator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import util.common.json.JsonParser;
import util.common.json.JsonTypeWrapper;

public class JsonParserTest {

    @Test
    public void testParseNumber() {
        Integer value = JsonParser.parseJson("1", Integer.class);
        Assert.assertEquals(Integer.valueOf(1), value);
    }

    @Test
    public void testParseString() {
        String value = JsonParser.parseJson("\"1\"", String.class);
        Assert.assertEquals("1", value);
    }

    @Test
    public void testParseBoolean() {
        Boolean value = JsonParser.parseJson("true", Boolean.class);
        Assert.assertEquals(Boolean.TRUE, value);
    }

    @Test
    public void testParseNull() {
        Object value = JsonParser.parseJson("", Object.class);
        Assert.assertNull(value);
    }

    @Test
    public void testParseMap1() {
        Map<String, Object> value = JsonParser.parseJson("{\"key\": 1}", HashMap.class);
        Assert.assertNotNull(value);
        Assert.assertEquals(1, value.size());
        Assert.assertEquals(1L, value.get("key"));
    }

    @Test
    public void testParseMap2() {
        Map<String, Integer> value = JsonParser.parseJson("{\"key\": 1}", new JsonTypeWrapper<>() {});
        Assert.assertNotNull(value);
        Assert.assertEquals(1, value.size());
        Assert.assertEquals(Integer.valueOf(1), value.get("key"));
    }

    @Test
    public void testParseArray() {
        int[][] arr = JsonParser.parseJson("[[1, 2], [3, 4]]", int[][].class);
        Assert.assertNotNull(arr);
        Assert.assertEquals(1, arr[0][0]);
        Assert.assertEquals(2, arr[0][1]);
        Assert.assertEquals(3, arr[1][0]);
        Assert.assertEquals(4, arr[1][1]);
    }

    @Test
    public void testParseArray2() {
        List<Object> value = JsonParser.parseJson("[1,\"2\"]", new JsonTypeWrapper<>() {});
        Assert.assertNotNull(value);
        Assert.assertEquals(2, value.size());
        Assert.assertEquals(1L, value.get(0));
        Assert.assertEquals("2", value.get(1));
    }

    @Test
    public void testParseMapArray() {
        Map<Integer, int[]> value = JsonParser.parseJson("{1:[11, 12], 2: null}", new JsonTypeWrapper<>() {});
        Assert.assertNotNull(value);
        Assert.assertEquals(2, value.size());
        Assert.assertNull(value.get(2));
        Assert.assertEquals(11, value.get(1)[0]);
        Assert.assertEquals(12, value.get(1)[1]);
    }

}
