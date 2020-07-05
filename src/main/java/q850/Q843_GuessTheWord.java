package q850;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/guess-the-word/
 *
 * This problem is an interactive problem new to the LeetCode platform.
 *
 * We are given a word list of unique words, each word is 6 letters long, and one word in this list is chosen as secret.
 *
 * You may call master.guess(word) to guess a word.  The guessed word should have type string and must be from the
 * original list with 6 lowercase letters.
 *
 * This function returns an integer type, representing the number of exact matches (value and position) of your guess
 * to the secret word.  Also, if your guess is not in the given wordlist, it will return -1 instead.
 *
 * For each test case, you have 10 guesses to guess the word. At the end of any number of calls, if you have made 10
 * or less calls to master.guess and at least one of these guesses was the secret, you pass the testcase.
 *
 * Besides the example test case below, there will be 5 additional test cases, each with 100 words in the word list.
 * The letters of each word in those testcases were chosen independently at random from 'a' to 'z', such that every
 * word in the given word lists is unique.
 *
 * Example 1:
 * Input: secret = "acckzz", wordlist = ["acckzz","ccbazz","eiowzz","abcczz"]
 *
 * Explanation:
 *
 * master.guess("aaaaaa") returns -1, because "aaaaaa" is not in wordlist.
 * master.guess("acckzz") returns 6, because "acckzz" is secret and has all 6 matches.
 * master.guess("ccbazz") returns 3, because "ccbazz" has 3 matches.
 * master.guess("eiowzz") returns 2, because "eiowzz" has 2 matches.
 * master.guess("abcczz") returns 4, because "abcczz" has 4 matches.
 *
 * We made 5 calls to master.guess and one of them was the secret, so we pass the test case.
 *
 * Note:  Any solutions that attempt to circumvent the judge will result in disqualification.
 */
@RunWith(LeetCodeRunner.class)
public class Q843_GuessTheWord {

    // 没有思路, 参考 https://www.cnblogs.com/grandyang/p/11449244.html
    // 思路是随机检测一个字符串, 根据返回的结果(匹配数量) 来过滤未匹配的字符串.
    // 假设此次猜测的是 g, 返回值是2, 则说明密码和 g 相同的点有2个, 因为密码肯定在候选的wordlist 中, 所以按照这个去筛选即可.
    @Answer
    public void findSecretWord(String[] wordlist, Master master) {
        final int n = wordlist.length;
        int[][] sames = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int same = 0;
                for (int k = 0; k < 6; k++) {
                    same += wordlist[i].charAt(k) == wordlist[j].charAt(k) ? 1 : 0;
                }
                sames[i][j] = sames[j][i] = same;
            }
        }
        List<Integer> remains = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            remains.add(i);
        }

        Random random = new Random();
        for (int times = 0; times < 10; times++) {
            // 随机选择一个单词, 必须随机选择而不是选择开头/结尾, OJ 上有针对性的测试用例
            int picked = remains.remove(random.nextInt(remains.size()));
            int count = master.guess(wordlist[picked]);
            if (count == 6) {
                return;
            }

            // 筛选单词
            for (int i = remains.size() - 1; i >= 0; i--) {
                if (sames[picked][remains.get(i)] != count) {
                    remains.remove(i);
                }
            }
        }
    }

    private interface Master {

        int guess(String word);
    }


    @TestData
    public DataExpectation example = createTestData("acckzz", new String[]{
            "acckzz", "ccbazz", "eiowzz", "abcczz"});

    @TestData
    public DataExpectation normal1 = createTestData("hbaczn", new String[]{
            "gaxckt", "trlccr", "jxwhkz", "ycbfps", "peayuf", "yiejjw", "ldzccp", "nqsjoa", "qrjasy", "pcldos",
            "acrtag", "buyeia", "ubmtpj", "drtclz", "zqderp", "snywek", "caoztp", "ibpghw", "evtkhl", "bhpfla",
            "ymqhxk", "qkvipb", "tvmued", "rvbass", "axeasm", "qolsjg", "roswcb", "vdjgxx", "bugbyv", "zipjpc",
            "tamszl", "osdifo", "dvxlxm", "iwmyfb", "wmnwhe", "hslnop", "nkrfwn", "puvgve", "rqsqpq", "jwoswl",
            "tittgf", "evqsqe", "aishiv", "pmwovj", "sorbte", "hbaczn", "coifed", "hrctvp", "vkytbw", "dizcxz",
            "arabol", "uywurk", "ppywdo", "resfls", "tmoliy", "etriev", "oanvlx", "wcsnzy", "loufkw", "onnwcy",
            "novblw", "mtxgwe", "rgrdbt", "ckolob", "kxnflb", "phonmg", "egcdab", "cykndr", "lkzobv", "ifwmwp",
            "jqmbib", "mypnvf", "lnrgnj", "clijwa", "kiioqr", "syzebr", "rqsmhg", "sczjmz", "hsdjfp", "mjcgvm",
            "ajotcx", "olgnfv", "mjyjxj", "wzgbmg", "lpcnbj", "yjjlwn", "blrogv", "bdplzs", "oxblph", "twejel",
            "rupapy", "euwrrz", "apiqzu", "ydcroj", "ldvzgq", "zailgu", "xgqpsr", "wxdyho", "alrplq", "brklfk"});

    @TestData
    public DataExpectation normal2 = createTestData("cymplm", new String[]{
            "eykdft", "gjeixr", "eksbjm", "mxqhpk", "tjplhf", "ejgdra", "npkysm", "jsrsid", "cymplm", "vegdgt",
            "jnhdvb", "jdhlzb", "sgrghh", "jvydne", "laxvnm", "xbcliw", "emnfcw", "pyzdnq", "vzqbuk", "gznrnn",
            "robxqx", "oadnrt", "kzwyuf", "ahlfab", "zawvdf", "edhumz", "gkgiml", "wqqtla", "csamxn", "bisxbn",
            "zwxbql", "euzpol", "mckltw", "bbnpsg", "ynqeqw", "uwvqcg", "hegrnc", "rrqhbp", "tpfmlh", "wfgfbe",
            "tpvftd", "phspjr", "apbhwb", "yjihwh", "zgspss", "pesnwj", "dchpxq", "axduwd", "ropxqf", "gahkbq",
            "yxudiu", "dsvwry", "ecfkxn", "hmgflc", "fdaowp", "hrixpl", "czkgyp", "mmqfao", "qkkqnz", "lkzaxu",
            "cngmyn", "nmckcy", "alpcyy", "plcmts", "proitu", "tpzbok", "vixjqn", "suwhab", "dqqkxg", "ynatlx",
            "wmbjxe", "hynjdf", "xtcavp", "avjjjj", "fmclkd", "ngxcal", "neyvpq", "cwcdhi", "cfanhh", "ruvdsa",
            "pvzfyx", "hmdmtx", "pepbsy", "tgpnql", "zhuqlj", "tdrsfx", "xxxyle", "zqwazc", "hsukcb", "aqtdvn",
            "zxbxps", "wziidg", "tsuxvr", "florrj", "rpuorf", "jzckev", "qecnsc", "rrjdyh", "zjtdaw", "dknezk"});

    @TestData
    public DataExpectation normal3 = createTestData("ccoyyo", new String[]{
            "wichbx", "oahwep", "tpulot", "eqznzs", "vvmplb", "eywinm", "dqefpt", "kmjmxr", "ihkovg", "trbzyb",
            "xqulhc", "bcsbfw", "rwzslk", "abpjhw", "mpubps", "viyzbc", "kodlta", "ckfzjh", "phuepp", "rokoro",
            "nxcwmo", "awvqlr", "uooeon", "hhfuzz", "sajxgr", "oxgaix", "fnugyu", "lkxwru", "mhtrvb", "xxonmg",
            "tqxlbr", "euxtzg", "tjwvad", "uslult", "rtjosi", "hsygda", "vyuica", "mbnagm", "uinqur", "pikenp",
            "szgupv", "qpxmsw", "vunxdn", "jahhfn", "kmbeok", "biywow", "yvgwho", "hwzodo", "loffxk", "xavzqd",
            "vwzpfe", "uairjw", "itufkt", "kaklud", "jjinfa", "kqbttl", "zocgux", "ucwjig", "meesxb", "uysfyc",
            "kdfvtw", "vizxrv", "rpbdjh", "wynohw", "lhqxvx", "kaadty", "dxxwut", "vjtskm", "yrdswc", "byzjxm",
            "jeomdc", "saevda", "himevi", "ydltnu", "wrrpoc", "khuopg", "ooxarg", "vcvfry", "thaawc", "bssybb",
            "ccoyyo", "ajcwbj", "arwfnl", "nafmtm", "xoaumd", "vbejda", "kaefne", "swcrkh", "reeyhj", "vmcwaf",
            "chxitv", "qkwjna", "vklpkp", "xfnayl", "ktgmfn", "xrmzzm", "fgtuki", "zcffuv", "srxuus", "pydgmq"});

    private DataExpectation createTestData(String secret, String[] wordlist) {
        MasterImpl master = new MasterImpl(secret, wordlist);
        return DataExpectation.builder()
                .addArgument(wordlist)
                .addArgument(master)
                .assertMethod(() -> Assert.assertTrue(master.pass))
                .build();
    }

    private static class MasterImpl implements Master {

        final String secret;

        final Set<String> wordlist;

        int times;

        boolean pass;

        MasterImpl(String secret, String[] wordlist) {
            this.secret = secret;
            this.wordlist = new HashSet<>(Arrays.asList(wordlist));
        }


        @Override
        public int guess(String word) {
            if (++times > 10) {
                throw new RuntimeException("For each test case, you have 10 guesses to guess the word.");
            }
            if (!wordlist.contains(word)) {
                return -1;
            }
            if (secret.equals(word)) {
                pass = true;
                return secret.length();
            }
            int res = 0;
            for (int i = 0; i < secret.length(); i++) {
                if (secret.charAt(i) == word.charAt(i)) {
                    res++;
                }
            }
            return res;
        }
    }

}
