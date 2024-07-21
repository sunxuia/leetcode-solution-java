package util.generator;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.script.ScriptException;
import util.common.json.JsonParser;
import util.common.json.JsonTypeWrapper;
import util.generator.model.GeneratorQuestion;
import util.generator.model.GeneratorQuestionArgument;
import util.generator.model.GeneratorQuestionClass;
import util.generator.model.GeneratorQuestionExample;
import util.generator.model.GeneratorQuestionMethod;

public class Generator {

    /**
     * 在项目的根目录 (leetcode-solution 目录) 运行这个程序
     * 示例: java -cp target/classes util.generator.Generator 860
     * -cp target/classes 指定了class 文件的生成目录, 860 是要生成的题目的编号(第860题)
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            logger.println("Please specific question no in argument!");
            return;
        }
        try {
            long no = Long.parseLong(args[0]);
            Generator generator = new Generator();
            GeneratorQuestion question = generator.getQuestion(no);
            if (question == null) {
                logger.println("Question " + no + " not found!");
                return;
            }
            String title = question.getTitle();
            if (question.isPaidOnly()) {
                logger.println(title + " is paid only!");
                return;
            }
            if (question.getCode() == null || question.getCode().isEmpty()) {
                logger.println(title + " has no Java solution.");
                return;
            }
            logger.println("Question " + question.getNo() + " : " + title);

            GeneratorQuestionClass questionClass = generator.getQuestionClass(question);
            boolean generated = generator.generateFiles(question, questionClass);
            if (generated) {
                logger.println(title + " generated.");
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    private static final PrintStream logger = System.out;

    private GeneratorQuestion getQuestion(long no) throws Exception {
        Map<String, Object> algorithms = getForLeetCodeAlgorithms();
        List<Map<String, Object>> statStatusPairs = getMapVal(algorithms, "stat_status_pairs");
        Map<String, Object> statStatusPair = null;
        for (Map<String, Object> pair : statStatusPairs) {
            long questionId = getMapVal(pair, "stat", "frontend_question_id");
            if (questionId == no) {
                statStatusPair = pair;
                break;
            }
        }
        if (statStatusPair == null) {
            return null;
        }

        GeneratorQuestion res = new GeneratorQuestion();
        res.setNo(no);
        res.setPaidOnly(Long.valueOf(1L).equals(statStatusPair.get("paid_only")));
        Map<String, Object> stat = getMapVal(statStatusPair, "stat");
        res.setTitle((String) stat.get("question__title"));
        res.setTitleSlug((String) stat.get("question__title_slug"));
        res.setUrl("https://leetcode.com/problems/" + res.getTitleSlug() + "/");
        long difficultyLevel = getMapVal(statStatusPair, "difficulty", "level");
        res.setDifficulty(new String[]{"", "Easy", "Medium", "Hard"}[(int) difficultyLevel]);
        if (res.isPaidOnly()) {
            return res;
        }

        Map<String, Object> questionDetail = getMapVal(postForLeetCodeDetail(res.getTitleSlug()), "data", "question");
        res.setContent(getMapVal(questionDetail, "content"));

        Map<String, Object> codeSnippet = null;
        List<Map<String, Object>> codeSnippets = getMapVal(questionDetail, "codeSnippets");
        for (Map<String, Object> cs : codeSnippets) {
            if ("Java".equals(getMapVal(cs, "lang"))) {
                codeSnippet = cs;
                break;
            }
        }
        if (codeSnippet == null) {
            return res;
        }
        res.setCode(getMapVal(codeSnippet, "code"));

        return res;
    }

    @SuppressWarnings("unchecked")
    private <T> T getMapVal(Object map, String... keys) {
        Object val = map;
        for (int i = 0; i < keys.length && val != null; i++) {
            val = ((Map<String, Object>) val).get(keys[i]);
        }
        return (T) val;
    }

    private Map<String, Object> getForLeetCodeAlgorithms() throws IOException {
        // 设置系统代理
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "7890");

        URL url = new URL("https://leetcode.com/api/problems/algorithms/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream input = connection.getInputStream();
        String text = new BufferedReader(new InputStreamReader(input))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        return JsonParser.parseJson(text, JsonTypeWrapper.DEFAULT_MAP);
    }

    private Map<String, Object> postForLeetCodeDetail(String titleSlug) throws IOException {
        String requestBody = "{ \"operationName\": \"questionData\", \"variables\": { \"titleSlug\": "
                + "\"" + titleSlug + "\" }, \"query\": \"query questionData($titleSlug: String!) {\\n question"
                + "(titleSlug: $titleSlug) {\\n questionId\\n questionFrontendId\\n boundTopicId\\n title\\n "
                + "titleSlug\\n content\\n translatedTitle\\n translatedContent\\n isPaidOnly\\n difficulty\\n "
                + "likes\\n dislikes\\n isLiked\\n similarQuestions\\n contributors {\\n username\\n profileUrl\\n "
                + "avatarUrl\\n __typename\\n }\\n langToValidPlayground\\n topicTags {\\n name\\n slug\\n "
                + "translatedName\\n __typename\\n }\\n companyTagStats\\n codeSnippets {\\n lang\\n langSlug\\n "
                + "code\\n __typename\\n }\\n stats\\n hints\\n solution {\\n id\\n canSeeDetail\\n paidOnly\\n "
                + "__typename\\n }\\n status\\n sampleTestCase\\n metaData\\n judgerAvailable\\n judgeType\\n "
                + "mysqlSchemas\\n enableRunCode\\n enableTestMode\\n enableDebugger\\n envInfo\\n libraryUrl\\n "
                + "adminUrl\\n __typename\\n }\\n}\\n\"}";
        byte[] body = requestBody.getBytes();
        URL url = new URL("https://leetcode.com/graphql/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Content-Length", Integer.toString(body.length));

        connection.setDoOutput(true);
        DataOutputStream output = new DataOutputStream(connection.getOutputStream());
        output.write(body);
        output.close();

        InputStream input = connection.getInputStream();
        String text = new BufferedReader(new InputStreamReader(input))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        return JsonParser.parseJson(text, JsonTypeWrapper.DEFAULT_MAP);
    }

    private GeneratorQuestionClass getQuestionClass(GeneratorQuestion q) {
        GeneratorQuestionClass res = new GeneratorQuestionClass();
        res.setPackageName(String.format("q%03d", (q.getNo() - 1) / 50 * 50 + 50));
        res.setDirectory("src/main/java/" + res.getPackageName());
        res.setClassName(String.format("Q%03d_%s", q.getNo(), dashToCamel(q.getTitleSlug())));

        String decodedCode = q.getCode();
        decodedCode = decodedCode
                .replaceAll("/\\*[\\w\\W]*?\\*/", "")
                .replaceAll("//.+\\n", "")
                .replaceAll("^[ \t\r\n]+", "")
                .replaceAll("[ \t\r\n]+$", "")
                .replaceAll("(?:\r?\n){3,}", "\n\n");
        res.setDecodedCode(decodedCode);

        Matcher classNameMatcher = Pattern.compile("^class ([^ ]+) \\{").matcher(res.getDecodedCode());
        if (classNameMatcher.find()) {
            res.setTestedClassName(classNameMatcher.group(1));
        } else {
            throw new GeneratorException("Cannot find class info");
        }
        res.setMethodQuestion("Solution".equals(res.getTestedClassName()));

        Matcher methodMatcher = Pattern.compile("public(?: ([^ ]+)) ([_a-zA-Z0-9]+) *\\(([^)]+)\\) \\{")
                .matcher(res.getDecodedCode());
        Pattern argumentPattern = Pattern.compile("([^ ]+) ([^, ]+)(?:,|$)");
        while (methodMatcher.find()) {
            GeneratorQuestionMethod method = new GeneratorQuestionMethod();
            method.setReturnType(methodMatcher.group(1));
            method.setMethodName(methodMatcher.group(2));
            if (methodMatcher.group(3) != null) {
                Matcher argumentMatcher = argumentPattern.matcher(methodMatcher.group(3));
                while (argumentMatcher.find()) {
                    GeneratorQuestionArgument argument = new GeneratorQuestionArgument();
                    argument.setType(argumentMatcher.group(1));
                    argument.setName(argumentMatcher.group(2));
                    method.getArguments().add(argument);
                }
            }
            res.getMethods().add(method);
        }
        if (res.getMethods().isEmpty()) {
            throw new GeneratorException("Cannot find method.");
        }

        if (res.isMethodQuestion()) {
            res.setMethodArgumentCount(res.getMethods().get(0).getArguments().size());
            String methodCode = decodedCode.substring(decodedCode.indexOf("\n") + 1, decodedCode.lastIndexOf("\n"));
            methodCode = methodCode.replaceAll("\n[ \t]+", "\n").trim();
            res.setMethodCode(methodCode);
        }
        res.setHtmlContent(q.getContent());

        String decodedContent = q.getContent();
        HtmlCharacterDecoder decoder = new HtmlCharacterDecoder();
        for (int i = 0; i < 2; i++) {
            decodedContent = decoder.decode(decodedContent);
            decodedContent = decodedContent
                    .replaceAll("<(?:br|hr)\\s*/?>", "\n")
                    .replaceAll("<sup>(\\d+)</sup>", "^$1")
                    .replaceAll("</?\\s*[a-zA-Z]+(?: [a-zA-Z_]+=\"[^\"]*\")*\\s*/?>", "");
        }
        decodedContent = decodedContent
                .replaceAll("\r?\n[ \t]+\r?\n", "\n\n")
                .replaceAll("^[ \t\r\n]+", "")
                .replaceAll("[ \t\r\n]+$", "")
                .replaceAll("(?:\r?\n){3,}", "\n\n");

        Matcher exampleMatcher = Pattern.compile("Example\\s*(?:\\d*)\\s*:").matcher(decodedContent);
        int exampleCount = 0;
        while (exampleMatcher.find()) {
            exampleCount++;
        }
        if (exampleCount == 0) {
            Matcher inputMatcher = Pattern.compile("Input:").matcher(decodedContent);
            while (inputMatcher.find()) {
                exampleCount++;
            }
        }
        res.setExampleCount(exampleCount);

        if (res.isMethodQuestion()) {
            List<GeneratorQuestionExample> examples = res.getExamples();
            GeneratorQuestionMethod method = res.getMethods().get(0);
            Matcher ioMatcher = Pattern.compile("Input:([\\s\\S]+?)(?:\\s+)Output:(.+)").matcher(decodedContent);
            Pattern argPattern = Pattern.compile("([^ ]+)\\s*=\\s*([^ ]+)(?:,|\r?\n|$)");
            while (ioMatcher.find()) {
                String input = ioMatcher.group(1), output = ioMatcher.group(2);
                GeneratorQuestionExample example = new GeneratorQuestionExample();
                examples.add(example);
                if (method.getArguments().size() == 1) {
                    Matcher argMatcher = argPattern.matcher(input);
                    if (argMatcher.find()) {
                        example.getInputs().add(argMatcher.group(2));
                    } else {
                        example.getInputs().add(input);
                    }
                } else {
                    Matcher argMatcher = argPattern.matcher(input);
                    while (argMatcher.find()) {
                        example.getInputs().add(argMatcher.group(2));
                    }
                }
                for (int i = 0; i < example.getInputs().size(); i++) {
                    String type = method.getArguments().get(i).getType();
                    String arg = example.getInputs().get(i);
                    example.getInputs().set(i, convertType(type, arg));
                }

                example.setOutput(convertType(method.getReturnType(), output));
            }
        }

        return res;
    }

    private String dashToCamel(String text) {
        StringBuilder sb = new StringBuilder();
        boolean dashBefore = true;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '-') {
                dashBefore = true;
            } else if (dashBefore) {
                sb.append(Character.toUpperCase(c));
                dashBefore = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private String convertType(String type, String raw) {
        raw = raw.trim();
        if (type.contains("List")) {
            raw = raw.replaceAll("\\[", "List.of(")
                    .replaceAll("]", ")");
            if (type.contains("char")) {
                raw = raw.replaceAll("\"", "'");
            }
        } else if (type.contains("TreeNode")) {
            raw = raw.replaceAll("\\[", "TreeNode.createByLevel(")
                    .replaceAll("]", ")");
        } else if (type.contains("ListNode")) {
            raw = raw.replaceAll("\\[", "ListNode.createListNode(")
                    .replaceAll("]", ")");
        }
        if (type.contains("[")) {
            raw = raw.replaceAll("\\[", "{")
                    .replaceAll("]", "}");
            if (type.contains("char")) {
                raw = raw.replaceAll("\"", "'");
            }
            raw = "new " + type + raw;
        }
        return raw;
    }

    private boolean generateFiles(GeneratorQuestion q, GeneratorQuestionClass qc) throws IOException, ScriptException {
        File dir = new File(qc.getDirectory());
        dir.mkdirs();
        File classFile = new File(dir.getPath() + "/" + qc.getClassName() + ".java");
        if (classFile.exists()) {
            logger.println("File " + qc.getClassName() + " already exist, skipped.");
            return false;
        }

        TemplateEngine templateEngine = new TemplateEngine();

        Map<String, Object> variables = new HashMap<>();
        String code;
        if (qc.isMethodQuestion()) {
            code = "    " + qc.getMethodCode().replaceAll("\n", "\n    ");
        } else {
            code = "    private static " + q.getCode().replaceAll("\n", "\n    ");
        }
        variables.put("code", code);
        variables.put("desc", " * " + qc.getHtmlContent().replaceAll("\n", "\n * "));
        variables.put("q", q);
        variables.put("qc", qc);
        templateEngine.setVariables(variables);

        String templateName = qc.isMethodQuestion() ? "method" : "class";
        templateEngine.setTemplateFile("util/generator/template_" + templateName + ".data.txt");

        classFile.createNewFile();
        try (FileOutputStream outputStream = new FileOutputStream(classFile)) {
            templateEngine.execute(outputStream);
        }
        return true;
    }

}
