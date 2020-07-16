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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.script.ScriptException;

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
            if (question.paidOnly) {
                logger.println(question.title + " is paid only!");
                return;
            }
            if (question.code == null || question.code.isEmpty()) {
                logger.println(question.title + " has no Java solution.");
                return;
            }
            logger.println("Question " + question.no + " : " + question.title);

            GeneratorQuestionClass questionClass = generator.getQuestionClass(question);
            boolean generated = generator.generateFiles(question, questionClass);
            if (generated) {
                logger.println(question.title + " generated.");
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    private static final PrintStream logger = System.out;

    public static class GeneratorQuestion {

        long no;
        String title;
        String titleSlug;
        String url;
        String difficulty;
        boolean paidOnly;
        String content;
        String code;

        public long getNo() {
            return no;
        }

        public void setNo(long no) {
            this.no = no;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitleSlug() {
            return titleSlug;
        }

        public void setTitleSlug(String titleSlug) {
            this.titleSlug = titleSlug;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(String difficulty) {
            this.difficulty = difficulty;
        }

        public boolean isPaidOnly() {
            return paidOnly;
        }

        public void setPaidOnly(boolean paidOnly) {
            this.paidOnly = paidOnly;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public static class GeneratorQuestionClass {

        public String packageName;
        public String className;
        public String directory;
        public boolean isMethodQuestion;
        public String decodedContent;
        public String decodedCode;
        public List<GeneratorQuestionMethod> methods = new ArrayList<>();

        String testedClassName;
        int methodArgumentCount;
        int exampleCount;
        String methodCode;

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getDirectory() {
            return directory;
        }

        public void setDirectory(String directory) {
            this.directory = directory;
        }

        public boolean isMethodQuestion() {
            return isMethodQuestion;
        }

        public void setMethodQuestion(boolean methodQuestion) {
            isMethodQuestion = methodQuestion;
        }

        public String getDecodedContent() {
            return decodedContent;
        }

        public void setDecodedContent(String decodedContent) {
            this.decodedContent = decodedContent;
        }

        public String getDecodedCode() {
            return decodedCode;
        }

        public void setDecodedCode(String decodedCode) {
            this.decodedCode = decodedCode;
        }

        public List<GeneratorQuestionMethod> getMethods() {
            return methods;
        }

        public void setMethods(List<GeneratorQuestionMethod> methods) {
            this.methods = methods;
        }

        public String getTestedClassName() {
            return testedClassName;
        }

        public void setTestedClassName(String testedClassName) {
            this.testedClassName = testedClassName;
        }

        public int getMethodArgumentCount() {
            return methodArgumentCount;
        }

        public void setMethodArgumentCount(int methodArgumentCount) {
            this.methodArgumentCount = methodArgumentCount;
        }

        public int getExampleCount() {
            return exampleCount;
        }

        public void setExampleCount(int exampleCount) {
            this.exampleCount = exampleCount;
        }
    }

    public static class GeneratorQuestionMethod {

        String raw;
        String methodName;
        String returnType;
        String callerType;
        List<GeneratorQuestionArgument> arguments = new ArrayList<>();

        public String getRaw() {
            return raw;
        }

        public void setRaw(String raw) {
            this.raw = raw;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public String getReturnType() {
            return returnType;
        }

        public void setReturnType(String returnType) {
            this.returnType = returnType;
        }

        public String getCallerType() {
            return callerType;
        }

        public void setCallerType(String callerType) {
            this.callerType = callerType;
        }

        public List<GeneratorQuestionArgument> getArguments() {
            return arguments;
        }

        public void setArguments(List<GeneratorQuestionArgument> arguments) {
            this.arguments = arguments;
        }
    }

    public static class GeneratorQuestionArgument {

        private String type, name;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

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
        res.no = no;
        res.paidOnly = (boolean) statStatusPair.get("paid_only");
        Map<String, Object> stat = getMapVal(statStatusPair, "stat");
        res.title = (String) stat.get("question__title");
        res.titleSlug = (String) stat.get("question__title_slug");
        res.url = "https://leetcode.com/problems/" + res.titleSlug + "/";
        long difficultyLevel = getMapVal(statStatusPair, "difficulty", "level");
        res.difficulty = new String[]{"", "Easy", "Medium", "Hard"}[(int) difficultyLevel];
        if (res.paidOnly) {
            return res;
        }

        Map<String, Object> questionDetail = getMapVal(postForLeetCodeDetail(res.titleSlug), "data", "question");
        res.content = getMapVal(questionDetail, "content");

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
        res.code = getMapVal(codeSnippet, "code");

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
        URL url = new URL("https://leetcode.com/api/problems/algorithms/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream input = connection.getInputStream();
        String text = new BufferedReader(new InputStreamReader(input))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        return JsonParser.parseJsonToMap(text);
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
        return JsonParser.parseJsonToMap(text);
    }

    private GeneratorQuestionClass getQuestionClass(GeneratorQuestion q) {
        GeneratorQuestionClass res = new GeneratorQuestionClass();
        res.packageName = String.format("q%03d", q.no / 50 * 50 + 50);
        res.directory = "src/main/java/" + res.packageName;
        res.className = String.format("Q%03d_%s", q.no, dashToCamel(q.titleSlug));

        res.decodedCode = q.code;
        res.decodedCode = res.decodedCode
                .replaceAll("/\\*[\\w\\W]*?\\*/", "")
                .replaceAll("//.+\\n", "")
                .replaceAll("^[ \t\r\n]+", "")
                .replaceAll("[ \t\r\n]+$", "")
                .replaceAll("(?:\r?\n){3,}", "\n\n");

        Matcher classNameMatcher = Pattern.compile("^class ([^ ]+) \\{").matcher(res.decodedCode);
        if (classNameMatcher.find()) {
            res.testedClassName = classNameMatcher.group(1);
        } else {
            throw new GeneratorException("Cannot find class info");
        }
        res.isMethodQuestion = "Solution".equals(res.testedClassName);

        Matcher methodMatcher = Pattern.compile("public(?: ([^ ]+)) ([_a-zA-Z0-9]+)\\(([^)]+)\\) \\{")
                .matcher(res.decodedCode);
        Pattern argumentPattern = Pattern.compile("([^ ]+) ([^, ]+)(?:,|$)");
        while (methodMatcher.find()) {
            GeneratorQuestionMethod method = new GeneratorQuestionMethod();
            method.returnType = methodMatcher.group(1);
            method.methodName = methodMatcher.group(2);
            if (methodMatcher.group(3) != null) {
                Matcher argumentMatcher = argumentPattern.matcher(methodMatcher.group(3));
                while (argumentMatcher.find()) {
                    GeneratorQuestionArgument argument = new GeneratorQuestionArgument();
                    argument.type = argumentMatcher.group(1);
                    argument.name = argumentMatcher.group(2);
                    method.arguments.add(argument);
                }
            }
            res.methods.add(method);
        }
        if (res.methods.isEmpty()) {
            throw new GeneratorException("Cannot find method.");
        }

        if (res.isMethodQuestion) {
            res.methodArgumentCount = res.methods.get(0).arguments.size();
            res.methodCode = res.decodedCode.substring(res.decodedCode.indexOf("\n") + 1,
                    res.decodedCode.lastIndexOf("\n"));
            res.methodCode = res.methodCode.replaceAll("\n[ \t]+", "\n").trim();
        }

        res.decodedContent = q.content;
        HtmlCharacterDecoder decoder = new HtmlCharacterDecoder();
        for (int i = 0; i < 2; i++) {
            res.decodedContent = decoder.decode(res.decodedContent);
            res.decodedContent = res.decodedContent
                    .replaceAll("<(?:br|hr)\\s*/?>", "\n")
                    .replaceAll("</?\\s*[a-zA-Z]+(?: [a-zA-Z_]+=\"[^\"]*\")*\\s*/?>", "");
        }
        res.decodedContent = res.decodedContent
                .replaceAll("\r?\n[ \t]+\r?\n", "\n\n")
                .replaceAll("^[ \t\r\n]+", "")
                .replaceAll("[ \t\r\n]+$", "")
                .replaceAll("(?:\r?\n){3,}", "\n\n");

        Matcher exampleMatcher = Pattern.compile("Example\\s*(?:\\d*)\\s*:").matcher(res.decodedContent);
        while (exampleMatcher.find()) {
            res.exampleCount++;
        }
        if (res.exampleCount == 0) {
            Matcher inputMatcher = Pattern.compile("Input:").matcher(res.decodedContent);
            while (inputMatcher.find()) {
                res.exampleCount++;
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

    private boolean generateFiles(GeneratorQuestion q, GeneratorQuestionClass qc) throws IOException, ScriptException {
        File dir = new File(qc.directory);
        dir.mkdirs();
        File classFile = new File(dir.getPath() + "/" + qc.className + ".java");
        if (classFile.exists()) {
            logger.println("File " + qc.className + " already exist, skipped.");
            return false;
        }

        TemplateEngine templateEngine = new TemplateEngine();

        Map<String, Object> variables = new HashMap<>();
        String code;
        if (qc.isMethodQuestion) {
            code = "    " + qc.methodCode.replaceAll("\n", "\n    ");
        } else {
            code = "    private static " + q.code.replaceAll("\n", "\n    ");
        }
        variables.put("code", code);
        variables.put("desc", " * " + qc.decodedContent.replaceAll("\n", "\n * "));
        variables.put("q", q);
        variables.put("qc", qc);
        templateEngine.setVariables(variables);

        String templateName = qc.isMethodQuestion ? "method" : "class";
        templateEngine.setTemplateFile("util/generator/template_" + templateName + ".data.txt");

        classFile.createNewFile();
        try (FileOutputStream outputStream = new FileOutputStream(classFile)) {
            templateEngine.execute(outputStream);
        }
        return true;
    }

}
