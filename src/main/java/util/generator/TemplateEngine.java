package util.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

public class TemplateEngine {

    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("\\{\\{(.+?)}}");

    private static final Pattern ENDS_WITH_SPACE = Pattern.compile("\r?\n([ \t]*)$");

    private static final Map<Integer, String> ESCAPE_CHARACTERS = new HashMap<>();

    static {
        ESCAPE_CHARACTERS.put((int) '\r', "\\r");
        ESCAPE_CHARACTERS.put((int) '\n', "\\n");
        ESCAPE_CHARACTERS.put((int) '\\', "\\\\");
        ESCAPE_CHARACTERS.put((int) '\t', "\\t");
        ESCAPE_CHARACTERS.put((int) '\b', "\\b");
        ESCAPE_CHARACTERS.put((int) '\f', "\\f");
        ESCAPE_CHARACTERS.put((int) '&', "\\&");
        ESCAPE_CHARACTERS.put((int) '\'', "\\'");
        ESCAPE_CHARACTERS.put((int) '"', "\\\"");
    }

    private ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

    private String templateFile, script;

    private Map<String, Object> variables = Collections.emptyMap();

    public String getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(String templateFile) throws IOException {
        this.templateFile = templateFile;
        script = createScript();
    }

    private String createScript() throws IOException {
        String text = getTemplateText();
        String ls = System.lineSeparator();

        StringBuilder sb = new StringBuilder();
        int prev = 0;
        Matcher matcher = INSTRUCTION_PATTERN.matcher(text);
        while (matcher.find()) {
            String code = matcher.group(1);
            boolean isCommand = code.startsWith("-") && code.endsWith("-");

            String before = text.substring(prev, matcher.start());
            prev = matcher.end();
            Matcher m = ENDS_WITH_SPACE.matcher(before);
            if (isCommand && m.find() && text.startsWith(ls, matcher.end())) {
                before = before.substring(0, m.start(1));
                prev += ls.length();
            }
            sb.append("output(\"").append(toJsString(before)).append("\")\n");

            if (isCommand) {
                sb.append(code.substring(1, code.length() - 1).trim()).append("\n");
            } else {
                sb.append("output(").append(code.trim()).append(")\n");
            }
        }
        if (prev < text.length()) {
            sb.append("output(\"").append(toJsString(text.substring(prev))).append("\")\n");
        }
        return sb.toString();
    }

    private String getTemplateText() throws IOException {
        URL url = getClass().getClassLoader().getResource(templateFile);
        if (url == null) {
            throw new GeneratorException("template [%s] not exist.", templateFile);
        }
        File templateFile = new File(url.getFile());
        if (!templateFile.exists()) {
            throw new GeneratorException("Template file [%s] not exist.", templateFile.getAbsoluteFile());
        }
        String text;
        try (InputStream inputStream = new FileInputStream(templateFile)) {
            text = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining(System.lineSeparator()));
        }
        return text;
    }

    private String toJsString(String str) {
        StringBuilder sb = new StringBuilder();
        str.codePoints().forEachOrdered(c -> {
            if (ESCAPE_CHARACTERS.containsKey(c)) {
                sb.append(ESCAPE_CHARACTERS.get(c));
            } else {
                sb.appendCodePoint(c);
            }
        });
        return sb.toString();
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public void execute(OutputStream outputStream) throws ScriptException {
        SimpleScriptContext context = new SimpleScriptContext();
        Bindings bindings = context.getBindings(ScriptContext.ENGINE_SCOPE);
        bindings.putAll(variables);
        bindings.put("output", (Consumer<Object>) obj -> {
            String str = obj == null ? "" : obj.toString();
            try {
                outputStream.write(str.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        engine.eval(script, context);
    }

}
