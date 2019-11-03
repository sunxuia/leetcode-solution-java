package q150;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/word-ladder-ii/
 *
 * Given two words (beginWord and endWord), and a dictionary's word list, find all shortest transformation sequence
 * (s) from beginWord to endWord, such that:
 *
 * Only one letter can be changed at a time
 * Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
 *
 * Note:
 *
 * Return an empty list if there is no such transformation sequence.
 * All words have the same length.
 * All words contain only lowercase alphabetic characters.
 * You may assume no duplicates in the word list.
 * You may assume beginWord and endWord are non-empty and are not the same.
 *
 * Example 1:
 *
 * Input:
 * beginWord = "hit",
 * endWord = "cog",
 * wordList = ["hot","dot","dog","lot","log","cog"]
 *
 * Output:
 * [
 * ["hit","hot","dot","dog","cog"],
 * ["hit","hot","lot","log","cog"]
 * ]
 *
 * Example 2:
 *
 * Input:
 * beginWord = "hit"
 * endWord = "cog"
 * wordList = ["hot","dot","dog","lot","log"]
 *
 * Output: []
 *
 * Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.
 */
@RunWith(LeetCodeRunner.class)
public class Q126_WordLadderII {

    /**
     * 这题比较复杂, 要求路径, 所以使用dfs, 但是要求的还是最短路径(直接求会造成超时), 所以需要在此之前使用bfs 找出最短路径.
     */
    @Answer
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        endIndex = wordList.indexOf(endWord);
        if (endIndex == -1) {
            return Collections.emptyList();
        }
        startIndex = wordList.indexOf(beginWord);
        if (startIndex == -1) {
            startIndex = wordList.size();
            wordList = new ArrayList<>(wordList);
            wordList.add(beginWord);
        }
        this.wordList = wordList;
        length = wordList.size();
        link = getLink();
        shortest = getShortestPath();

        res = new ArrayList<>();
        dfs(new ArrayList<>(), startIndex, shortest[endIndex]);
        return res;
    }

    // 各个点之间的连通性
    private boolean[][] link;

    // 起点到各个点的最短距离
    private int[] shortest;

    // 起点和终点
    private int startIndex, endIndex;

    // wordList
    private List<String> wordList;

    // wordList 的长度
    private int length;

    // 结果
    private List<List<String>> res;

    private boolean[][] getLink() {
        boolean[][] res = new boolean[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                res[i][j] = res[j][i] = canLink(wordList.get(i), wordList.get(j));
            }
        }
        return res;
    }

    private boolean canLink(String a, String b) {
        int count = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                count++;
            }
        }
        return count == 1;
    }

    private int[] getShortestPath() {
        int[] res = new int[length];
        Arrays.fill(res, Integer.MAX_VALUE);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startIndex);
        for (int dist = 0; !queue.isEmpty(); dist++) {
            for (int i = queue.size(); i > 0; i--) {
                int p = queue.remove();
                if (res[p] == Integer.MAX_VALUE) {
                    res[p] = dist;
                    for (int j = 0; j < length; j++) {
                        if (link[p][j]) {
                            queue.add(j);
                        }
                    }
                }
            }
        }
        return res;
    }

    private void dfs(ArrayList<String> path, int cur, int remainCount) {
        path.add(wordList.get(cur));
        if (remainCount == 0) {
            if (cur == endIndex) {
                res.add((List<String>) path.clone());
            }
        } else {
            for (int i = 0; i < length; i++) {
                if (link[cur][i] && shortest[i] == shortest[cur] + 1) {
                    dfs(path, i, remainCount - 1);
                }
            }
        }
        path.remove(path.size() - 1);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("hit")
            .addArgument("cog")
            .addArgument(Arrays.asList("hot", "dot", "dog", "lot", "log", "cog"))
            .expect(new String[][]{
                    {"hit", "hot", "dot", "dog", "cog"},
                    {"hit", "hot", "lot", "log", "cog"}
            }).build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument("hit")
            .addArgument("cog")
            .addArgument(Arrays.asList("hot", "dot", "dog", "lot", "log"))
            .expect(Collections.emptyList()).build();

    @TestData
    public DataExpectation overTime() {
        return DataExpectation.builder()
                .addArgument("cet")
                .addArgument("ism")
                .addArgument(Arrays.asList(
                        "kid", "tag", "pup", "ail", "tun", "woo", "erg", "luz", "brr", "gay", "sip", "kay", "per",
                        "val", "mes", "ohs", "now", "boa", "cet", "pal", "bar", "die", "war", "hay", "eco", "pub",
                        "lob", "rue", "fry", "lit", "rex", "jan", "cot", "bid", "ali", "pay", "col", "gum", "ger",
                        "row", "won", "dan", "rum", "fad", "tut", "sag", "yip", "sui", "ark", "has", "zip", "fez",
                        "own", "ump", "dis", "ads", "max", "jaw", "out", "btu", "ana", "gap", "cry", "led", "abe",
                        "box", "ore", "pig", "fie", "toy", "fat", "cal", "lie", "noh", "sew", "ono", "tam", "flu",
                        "mgm", "ply", "awe", "pry", "tit", "tie", "yet", "too", "tax", "jim", "san", "pan", "map",
                        "ski", "ova", "wed", "non", "wac", "nut", "why", "bye", "lye", "oct", "old", "fin", "feb",
                        "chi", "sap", "owl", "log", "tod", "dot", "bow", "fob", "for", "joe", "ivy", "fan", "age",
                        "fax", "hip", "jib", "mel", "hus", "sob", "ifs", "tab", "ara", "dab", "jag", "jar", "arm",
                        "lot", "tom", "sax", "tex", "yum", "pei", "wen", "wry", "ire", "irk", "far", "mew", "wit",
                        "doe", "gas", "rte", "ian", "pot", "ask", "wag", "hag", "amy", "nag", "ron", "soy", "gin",
                        "don", "tug", "fay", "vic", "boo", "nam", "ave", "buy", "sop", "but", "orb", "fen", "paw",
                        "his", "sub", "bob", "yea", "oft", "inn", "rod", "yam", "pew", "web", "hod", "hun", "gyp",
                        "wei", "wis", "rob", "gad", "pie", "mon", "dog", "bib", "rub", "ere", "dig", "era", "cat",
                        "fox", "bee", "mod", "day", "apr", "vie", "nev", "jam", "pam", "new", "aye", "ani", "and",
                        "ibm", "yap", "can", "pyx", "tar", "kin", "fog", "hum", "pip", "cup", "dye", "lyx", "jog",
                        "nun", "par", "wan", "fey", "bus", "oak", "bad", "ats", "set", "qom", "vat", "eat", "pus",
                        "rev", "axe", "ion", "six", "ila", "lao", "mom", "mas", "pro", "few", "opt", "poe", "art",
                        "ash", "oar", "cap", "lop", "may", "shy", "rid", "bat", "sum", "rim", "fee", "bmw", "sky",
                        "maj", "hue", "thy", "ava", "rap", "den", "fla", "auk", "cox", "ibo", "hey", "saw", "vim",
                        "sec", "ltd", "you", "its", "tat", "dew", "eva", "tog", "ram", "let", "see", "zit", "maw",
                        "nix", "ate", "gig", "rep", "owe", "ind", "hog", "eve", "sam", "zoo", "any", "dow", "cod",
                        "bed", "vet", "ham", "sis", "hex", "via", "fir", "nod", "mao", "aug", "mum", "hoe", "bah",
                        "hal", "keg", "hew", "zed", "tow", "gog", "ass", "dem", "who", "bet", "gos", "son", "ear",
                        "spy", "kit", "boy", "due", "sen", "oaf", "mix", "hep", "fur", "ada", "bin", "nil", "mia",
                        "ewe", "hit", "fix", "sad", "rib", "eye", "hop", "haw", "wax", "mid", "tad", "ken", "wad",
                        "rye", "pap", "bog", "gut", "ito", "woe", "our", "ado", "sin", "mad", "ray", "hon", "roy",
                        "dip", "hen", "iva", "lug", "asp", "hui", "yak", "bay", "poi", "yep", "bun", "try", "lad",
                        "elm", "nat", "wyo", "gym", "dug", "toe", "dee", "wig", "sly", "rip", "geo", "cog", "pas",
                        "zen", "odd", "nan", "lay", "pod", "fit", "hem", "joy", "bum", "rio", "yon", "dec", "leg",
                        "put", "sue", "dim", "pet", "yaw", "nub", "bit", "bur", "sid", "sun", "oil", "red", "doc",
                        "moe", "caw", "eel", "dix", "cub", "end", "gem", "off", "yew", "hug", "pop", "tub", "sgt",
                        "lid", "pun", "ton", "sol", "din", "yup", "jab", "pea", "bug", "gag", "mil", "jig", "hub",
                        "low", "did", "tin", "get", "gte", "sox", "lei", "mig", "fig", "lon", "use", "ban", "flo",
                        "nov", "jut", "bag", "mir", "sty", "lap", "two", "ins", "con", "ant", "net", "tux", "ode",
                        "stu", "mug", "cad", "nap", "gun", "fop", "tot", "sow", "sal", "sic", "ted", "wot", "del",
                        "imp", "cob", "way", "ann", "tan", "mci", "job", "wet", "ism", "err", "him", "all", "pad",
                        "hah", "hie", "aim", "ike", "jed", "ego", "mac", "baa", "min", "com", "ill", "was", "cab",
                        "ago", "ina", "big", "ilk", "gal", "tap", "duh", "ola", "ran", "lab", "top", "gob", "hot",
                        "ora", "tia", "kip", "han", "met", "hut", "she", "sac", "fed", "goo", "tee", "ell", "not",
                        "act", "gil", "rut", "ala", "ape", "rig", "cid", "god", "duo", "lin", "aid", "gel", "awl",
                        "lag", "elf", "liz", "ref", "aha", "fib", "oho", "tho", "her", "nor", "ace", "adz", "fun",
                        "ned", "coo", "win", "tao", "coy", "van", "man", "pit", "guy", "foe", "hid", "mai", "sup",
                        "jay", "hob", "mow", "jot", "are", "pol", "arc", "lax", "aft", "alb", "len", "air", "pug",
                        "pox", "vow", "got", "meg", "zoe", "amp", "ale", "bud", "gee", "pin", "dun", "pat", "ten",
                        "mob"))
                .expect(new String[][]{
                        {"cet", "cot", "con", "ion", "inn", "ins", "its", "ito", "ibo", "ibm", "ism"},
                        {"cet", "cat", "can", "ian", "inn", "ins", "its", "ito", "ibo", "ibm", "ism"},
                        {"cet", "get", "gee", "gte", "ate", "ats", "its", "ito", "ibo", "ibm", "ism"}
                }).build();
    }

}
