package com.tfp.reg.studyreg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: ph
 * Date: 2020/6/17
 * Time: 2:16
 * Description:
 */
public class HtmlReg {

    public static  final String   REG_STR ="var\\s+ptoken\\s+=\\s+\"(?<poken>\\w+)\"";
    public static  final String   htmlPath ="var ptoken = \"C0AB49CDFF0528E9FCB09535BE9D5F8FFA46BBE4CEFD82402A71C690A8D5FDB3F2F78A59ED79A9E5C8EDF2D459A2028F\"";
    public static void main(String[] args) {

//        test();
        System.out.println("ending ------");

        Pattern pattern = Pattern.compile(REG_STR);
        Matcher matcher = pattern.matcher(htmlPath);
        System.out.println(htmlPath);
        System.out.println(matcher.pattern().pattern());
//        System.out.println(matcher.find());
//       System.out.println(matcher.matches());

//        System.out.println(matcher.group(1));
// 一定要注意:正则表达式中:matcher.matches()和matcher.find()不要一起使用，否则其中一个(后面的)后返回false。
        while(matcher.find()){
            System.out.println(matcher.group("poken"));
        }
    }


    public  static void  test(){
        String str = "<img  src='aaa.jpg' /><img src=bbb.png/><img src=\"ccc.png\"/>" +
                "<img src='ddd.exe'/><img src='eee.jpn'/>";
        // 这里我们考虑了一些不规范的 img 标签写法，比如：空格、引号
        Pattern pattern = Pattern.compile("<img\\s+src=(?:['\"])?(?<src>\\w+.(jpg|png))(?:['\"])?\\s*/>");
        System.out.println(str);
        System.out.println(pattern.pattern());
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.out.println(matcher.group("src"));
        }
    }


}
