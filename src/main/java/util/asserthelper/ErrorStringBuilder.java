package util.asserthelper;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.function.Function;

public class ErrorStringBuilder {

    public ErrorStringBuilder() {}

    public ErrorStringBuilder(Object value) {
        append(value);
    }

    private StringBuilder sb = new StringBuilder();

    private String rangeStart = "[";

    private String rangeEnd = "]";

    private String itemInterval = ", ";

    private Function<Object, String> itemToString;

    public ErrorStringBuilder setRangeStart(String rangeStart) {
        this.rangeStart = rangeStart;
        return this;
    }

    public ErrorStringBuilder setRangeEnd(String rangeEnd) {
        this.rangeEnd = rangeEnd;
        return this;
    }

    public ErrorStringBuilder setItemInterval(String itemInterval) {
        this.itemInterval = itemInterval;
        return this;
    }

    public ErrorStringBuilder setItemToString(Function<Object, String> itemToString) {
        this.itemToString = itemToString;
        return this;
    }

    public ErrorStringBuilder append(Object value) {
        if (isArray(value)) {
            append(convertArray(value));
        } else {
            sb.append(value);
        }
        return this;
    }

    private boolean isArray(Object value) {
        if (value != null) {
            Class clazz = value.getClass();
            return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
        }
        return false;
    }

    private Object[] convertArray(Object obj) {
        Class clazz = obj.getClass();
        if (clazz.isArray()) {
            Object[] newArr = new Object[Array.getLength(obj)];
            for (int i = 0; i < newArr.length; i++) {
                newArr[i] = Array.get(obj, i);
            }
            return newArr;
        } else {
            Collection col = (Collection) obj;
            Object[] newArr = new Object[col.size()];
            int i = 0;
            for (Object o : col) {
                newArr[i++] = o;
            }
            return newArr;
        }
    }

    public ErrorStringBuilder append(String format, Object... paras) {
        if (paras.length > 0) {
            format = String.format(format, paras);
        }
        sb.append(format);
        return this;
    }

    public ErrorStringBuilder append(Object[] values) {
        if (values == null) {
            sb.append("(null)");
        } else {
            addArray(values, "");
        }
        return this;
    }

    private void addArray(Object[] values, String indent) {
        sb.append(rangeStart);
        for (int i = 0; i < values.length; i++) {
            final Object val = values[i];
            if (val == null) {
                sb.append("null");
                if (i != values.length - 1) {
                    append(itemInterval);
                }
            } else if (isArray(val)) {
                // array
                sb.append("\n").append(indent + "  ");
                addArray(convertArray(val), indent + "  ");
                if (i != values.length - 1) {
                    append(itemInterval);
                }
                if (i == values.length - 1) {
                    sb.append("\n").append(indent);
                }
            } else {
                sb.append(convertItemToString(val));
                if (i != values.length - 1) {
                    append(itemInterval);
                }
            }
        }
        sb.append(rangeEnd);
    }

    private String convertItemToString(Object val) {
        if (itemToString != null) {
            return itemToString.apply(val);
        } else if (val instanceof String) {
            return "\"" + val + "\"";
        } else if (val instanceof Character) {
            return "'" + val + "'";
        } else if (Number.class.isAssignableFrom(val.getClass())) {
            return val.toString();
        } else {
            return "{" + val.toString() + "}";
        }
    }

    public ErrorStringBuilder newLine() {
        sb.append('\n');
        return this;
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
