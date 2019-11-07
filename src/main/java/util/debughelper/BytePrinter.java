package util.debughelper;

public class BytePrinter {

    public static void printByte(int value) {
        printByte(value, true);
    }

    public static void printByte(int value, boolean printDigit) {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 31; i >= 0; i--) {
            sb1.append(String.format("%2d ", i));
            sb2.append(' ').append((value >>> i) & 1).append(' ');
        }
        if (printDigit) {
            System.out.println(sb1.toString());
        }
        System.out.println(sb2.toString());
    }

    public static String getByteString(int value) {
        StringBuilder sb = new StringBuilder(32);
        for (int i = 31; i >= 0; i--) {
            sb.append((value >>> i) & 1);
        }
        return sb.toString();
    }

}
