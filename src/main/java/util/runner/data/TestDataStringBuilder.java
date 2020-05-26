package util.runner.data;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import util.runner.data.TestDataString.TestDataStringType;

public class TestDataStringBuilder {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[+-]?\\d+(?:\\.\\d+)?$");

    public static TestDataString parseString(String raw) {
        List<Term> terms = parseStringToTerm(raw);
        return parseTerms(terms, raw);
    }

    private static List<Term> parseStringToTerm(String raw) {
        List<Term> terms = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        // status:
        // 0: normal, 1: in string (wrapped with "), 2: transfered meaning (/ in string)
        int status = 0;
        for (int i = 0, len = raw.codePointCount(0, raw.length()); i <= len; i++) {
            int c = i == len ? ',' : raw.codePointAt(i);
            switch (status) {
                case 1:
                    switch (c) {
                        case '\"':
                            terms.add(new Term(TermType.STRING, sb.toString()));
                            sb.setLength(0);
                            status = 0;
                            break;
                        case '\\':
                            status = 2;
                            break;
                        default:
                            sb.appendCodePoint(c);
                    }
                    break;
                case 2:
                    sb.appendCodePoint(c);
                    status = 1;
                    break;
                default:
                    // status == 0
                    if (c == '\"') {
                        if (!sb.toString().trim().isEmpty()) {
                            throw new TestDataException(
                                    "Invalid term before string: " + sb + ", in string:\n" + raw);
                        }
                        status = 1;
                    } else {
                        Term stopTerm = Term.KEY_TERMS.get(c);
                        if (stopTerm == null) {
                            sb.appendCodePoint(c);
                        } else {
                            String value = sb.toString().trim();
                            if (!value.isEmpty()) {
                                if (Term.CONST_TERMS.containsKey(value)) {
                                    terms.add(Term.CONST_TERMS.get(value));
                                } else if (NUMBER_PATTERN.matcher(value).find()) {
                                    terms.add(new Term(TermType.NUMBER, value));
                                } else {
                                    throw new TestDataException(
                                            "Unknown test data term: " + value + ", in string:\n" + raw);
                                }
                                sb.setLength(0);
                            }
                            terms.add(stopTerm);
                        }
                    }
            }
        }

        if (status != 0) {
            throw new TestDataException("String term not end in string:\n" + raw);
        }
        terms.remove(terms.size() - 1);
        return terms;
    }

    private static TestDataString parseTerms(List<Term> terms, String raw) {
        Deque<TestDataString> stack = new ArrayDeque<>();
        TestDataString dummy = new TestDataString(Collections.emptyList());
        TestDataString curr, parent = dummy;
        for (int i = 0; i < terms.size(); i++) {
            Term term = terms.get(i);
            switch (term.type) {
                case STRING:
                    curr = new TestDataString(TestDataStringType.STRING, term.value);
                    parent.getChildren().add(curr);
                    break;
                case NUMBER:
                    curr = new TestDataString(TestDataStringType.NUMBER, term.value);
                    parent.getChildren().add(curr);
                    break;
                case TRUE:
                    parent.getChildren().add(TestDataString.TRUE);
                    break;
                case FALSE:
                    parent.getChildren().add(TestDataString.FALSE);
                    break;
                case NULL:
                    parent.getChildren().add(TestDataString.NULL);
                    break;
                case LEFT_BRACKET:
                    curr = new TestDataString(Collections.emptyList());
                    parent.getChildren().add(curr);
                    stack.push(parent);
                    parent = curr;
                    break;
                case RIGHT_BRACKET:
                    if (stack.isEmpty()) {
                        throw parseStringException("array closed too early", raw);
                    }
                    parent = stack.pop();
                    break;
                case COMMA:
                    if (i == terms.size() - 1
                            || terms.get(i + 1).type == TermType.RIGHT_BRACKET) {
                        throw parseStringException("comma should not at the end of array", raw);
                    }
                    break;
                default:
                    throw new TestDataException("should not run to here");
            }
        }
        if (!stack.isEmpty()) {
            throw parseStringException("array not closed", raw);
        }
        switch (dummy.getChildren().size()) {
            case 0:
                return null;
            case 1:
                return dummy.getChildren().get(0);
            default:
                return dummy;
        }
    }

    private static TestDataException parseStringException(String reason, String raw) {
        return new TestDataException("Error while parse test data: [" + reason + "] in string:\n" + raw);
    }

    /**
     * type of {@link Term}
     */
    private enum TermType {
        STRING,
        NUMBER,
        TRUE,
        FALSE,
        NULL,
        LEFT_BRACKET,
        RIGHT_BRACKET,
        COMMA
    }

    private static class Term {

        private final TermType type;

        private final String value;

        private Term(TermType type, String value) {
            this.type = type;
            this.value = value;
        }

        private static final Map<Integer, Term> KEY_TERMS = new HashMap<>();
        private static final Map<String, Term> CONST_TERMS = new HashMap<>();

        static {
            KEY_TERMS.put((int) '[', new Term(TermType.LEFT_BRACKET, "["));
            KEY_TERMS.put((int) ']', new Term(TermType.RIGHT_BRACKET, "]"));
            KEY_TERMS.put((int) ',', new Term(TermType.COMMA, ","));

            CONST_TERMS.put("null", new Term(TermType.NULL, "null"));
            CONST_TERMS.put("true", new Term(TermType.TRUE, "true"));
            CONST_TERMS.put("false", new Term(TermType.FALSE, "false"));
        }
    }
}
