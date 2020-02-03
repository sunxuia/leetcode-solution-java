package util;

public class UtilPackageHelper {

    public static final String PACKAGE_NAME = UtilPackageHelper.class.getPackage().getName();

    private static final String PACKAGE_PREFIX = PACKAGE_NAME + ".";

    public static boolean isInPackage(String className) {
        return className.startsWith(PACKAGE_PREFIX);
    }

}
