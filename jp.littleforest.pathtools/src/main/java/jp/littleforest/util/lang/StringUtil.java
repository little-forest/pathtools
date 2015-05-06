package jp.littleforest.util.lang;

import java.util.Collection;
import java.util.Iterator;

/**
 * 文字列に関するユーティリティクラスです。<br />
 * 
 * @author y-komori
 */
public class StringUtil {
    private StringUtil() {

    }

    /**
     * 文字列が {@code null} または空文字列なら {@code true} を返します。
     * 
     * @param text
     *            文字列
     * @return 文字列が {@code null} または空文字列なら {@code true}
     */
    public static final boolean isEmpty(final String text) {
        return text == null || text.length() == 0;
    }

    /**
     * 文字列が {@code null} または空文字列でないなら {@code true} を返します。
     * 
     * @param text
     *            文字列
     * @return 文字列が {@code null} または空文字列でないなら {@code true}
     */
    public static final boolean isNotEmpty(final String text) {
        return !isEmpty(text);
    }

    /**
     * 文字列を指定回数繰り返します。<br />
     * 
     * @param str
     *            文字列
     * @param times
     *            繰り返し回数(0以上)
     * @return 繰り返した文字列
     */
    public static String repeatChar(final String str, final int times) {
        if (str == null || times < 0) {
            return null;
        }

        StringBuffer buf = new StringBuffer(str.length() * times);
        for (int i = 0; i < times; i++) {
            buf.append(str);
        }

        return buf.toString();
    }

    /**
     * 文字列が {@code null} であった場合、空文字列を返します。<br />
     * 
     * @param str
     *            文字列
     * @return 変換後の文字列
     */
    public static String nullToEmpty(final String str) {
        return str == null ? "" : str;
    }

    /**
     * 改行を含む文字列を改行毎に分けて配列として返します。<br />
     * 
     * @param str
     *            文字列
     * @return 改行毎にわけた文字列の配列。元の文字列が {@code null} の場合は、要素 0 の配列
     */
    public static String[] splitToArray(String str) {
        if (str == null) {
            return new String[] {};
        }
        return str.split("(\r\n|\n)");
    }

    /**
     * 文字列中の改行を取り除きます。<br />
     * 
     * @param str
     *            文字列
     * @return 改行を取り除いた文字列
     */
    public static String eliminateLF(String str) {
        if (str != null) {
            str = str.replace("\n", "");
            return str.replace("\r", "");
        } else {
            return null;
        }
    }

    /**
     * 指定された二つの文字列をデリミタで結合します。<br />
     * 文字列1と文字列2の間には必ず1つのデリミタが含まれることを保証します。<br />
     * ただし、文字列1と文字列2の双方が {@code null} または空文字列の場合、結果は空文字列となります。<br />
     * 
     * @param str1
     *            文字列1({@code null} または空文字列でも構いません)
     * @param str2
     *            文字列2({@code null} または空文字列でも構いません)
     * @param delimiter
     *            デリミタ
     * @return 結合結果
     */
    public static String normalizeConcat(String str1, String str2, char delimiter) {
        str1 = str1 != null ? str1 : "";
        str2 = str2 != null ? str2 : "";
        str1 = chopRight(str1, delimiter);
        str2 = chopLeft(str2, delimiter);
        int len1 = str1.length();
        int len2 = str2.length();
        StringBuilder result = new StringBuilder(len1 + len2);
        if (len1 > 0 && len2 > 0) {
            result.append(str1);
            result.append(delimiter);
            result.append(str2);
        }
        return result.toString();
    }

    /**
     * 指定された文字列の先頭に指定されたデリミタが存在する限り、それらを削除します。<br />
     * 対象文字列が {@code null} や空文字列の場合、引数をそのまま返します。<br />
     * 
     * @param str
     *            対象文字列({@code null} または空文字列でも構いません)
     * @param delimiter
     *            デリミタ
     * @return 削除された文字列
     */
    public static String chopLeft(String str, char delimiter) {
        if (str == null || "".equals(str)) {
            return str;
        }
        int pos;
        int len = str.length();
        for (pos = 0; pos < len; pos++) {
            if (str.charAt(pos) != delimiter) {
                break;
            }
        }
        return str.substring(pos);
    }

    /**
     * 指定された文字列の最後に指定されたデリミタが存在する限り、それらを削除します。<br />
     * 対象文字列が {@code null} や空文字列の場合、引数をそのまま返します。<br />
     * 
     * @param str
     *            対象文字列({@code null} または空文字列でも構いません)
     * @param delimiter
     *            デリミタ
     * @return 削除された文字列
     */
    public static String chopRight(String str, char delimiter) {
        if (str == null || "".equals(str)) {
            return str;
        }
        int pos;
        for (pos = str.length() - 1; pos >= 0; pos--) {
            if (str.charAt(pos) != delimiter) {
                break;
            }
        }
        return str.substring(0, pos + 1);
    }

    /**
     * 指定された文字列が {@code prefix} で始まる場合、その文字列を削除して返します。<br />
     * 
     * @param str
     *            対象文字列
     * @param prefix
     *            削除対象文字列
     * @return 削除された文字列
     */
    public static String trimPrefix(String str, String prefix) {
        if (str == null) {
            return null;
        }
        if (prefix != null && str.startsWith(prefix)) {
            return str.substring(prefix.length(), str.length());
        } else {
            return str;
        }
    }

    /**
     * 指定された文字列が {@code suffix} で終わる場合、その文字列を削除して返します。<br />
     * 
     * @param str
     *            対象文字列
     * @param suffix
     *            削除対象文字列
     * @return 削除された文字列
     */
    public static String trimSuffix(String str, String suffix) {
        if (str == null) {
            return null;
        }
        if (suffix != null && str.endsWith(suffix)) {
            return str.substring(0, str.length() - suffix.length());
        } else {
            return str;
        }
    }

    /**
     * 指定されたコレクションの要素をイテレータで取得される順に取り出し、デリミタで繋げて返します。<br>
     * 
     * @param collection
     *            コレクション
     * @param delimiter
     *            デリミタ
     * @return 繋げた文字列。{@code collection} が {@code null} の場合は、{@code null}
     */
    public static <E> String concat(Collection<E> collection, String delimiter){
        if (collection == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        Iterator<E> itr = collection.iterator();
        while(itr.hasNext()){
            buf.append(itr.next().toString());
            if(itr.hasNext()){
                buf.append(delimiter);
            }
        }
        return buf.toString();
    }
}
