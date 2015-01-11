package com.fsck.ertebat.helper;

import junit.framework.TestCase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class HtmlConverterTest extends TestCase {
    // Useful if you want to write stuff to a file for debugging in a browser.
    private static final boolean WRITE_TO_FILE = Boolean.parseBoolean(System.getProperty("ertebat.htmlConverterTest.writeToFile", "false"));
    private static final String OUTPUT_FILE = "C:/temp/parse.html";

    public void testTextQuoteToHtmlBlockquote() {
        String message = "Panama!\r\n" +
            "\r\n" +
            "Bob Barker <bob@aol.com> wrote:\r\n" +
            "> a canal\r\n" +
            ">\r\n" +
            "> Dorothy Jo Gideon <dorothy@aol.com> espoused:\r\n" +
            "> >A man, a plan...\r\n" +
            "> Too easy!\r\n" +
            "\r\n" +
            "Nice job :)\r\n" +
            ">> Guess!";
        String result = HtmlConverter.textToHtml(message);
        writeToFile(result);
        assertEquals("<pre class=\"ertebatmail\">"
            + "Panama!<br />"
            + "<br />"
            + "Bob Barker &lt;bob@aol.com&gt; wrote:<br />"
            + "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #729fcf; padding-left: 1ex;\">"
            +   " a canal<br />"
            +   "<br />"
            +   " Dorothy Jo Gideon &lt;dorothy@aol.com&gt; espoused:<br />"
            +   "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #ad7fa8; padding-left: 1ex;\">"
            +     "A man, a plan...<br />"
            +   "</blockquote>"
            +     " Too easy!<br />"
            +   "</blockquote>"
            +   "<br />"
            +   "Nice job :)<br />"
            +   "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #729fcf; padding-left: 1ex;\">"
            +     "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #ad7fa8; padding-left: 1ex;\">"
            +     " Guess!"
            +   "</blockquote>"
            + "</blockquote>"
            + "</pre>", result);
    }

    public void testTextQuoteToHtmlBlockquoteIndented() {
        String message = "*facepalm*\r\n" +
            "\r\n" +
            "Bob Barker <bob@aol.com> wrote:\r\n" +
            "> A wise man once said...\r\n" +
            ">\r\n" +
            ">     LOL F1RST!!!!!\r\n" +
            ">\r\n" +
            "> :)";
        String result = HtmlConverter.textToHtml(message);
        writeToFile(result);
        assertEquals("<pre class=\"ertebatmail\">"
                + "*facepalm*<br />"
                + "<br />"
                + "Bob Barker &lt;bob@aol.com&gt; wrote:<br />"
                + "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #729fcf; padding-left: 1ex;\">"
                +   " A wise man once said...<br />"
                +   "<br />"
                +   "     LOL F1RST!!!!!<br />"
                +   "<br />"
                +   " :)"
                + "</blockquote></pre>", result);

    }

    public void testQuoteDepthColor() {
        assertEquals(HtmlConverter.getQuoteColor(1), HtmlConverter.QUOTE_COLOR_LEVEL_1);
        assertEquals(HtmlConverter.getQuoteColor(2), HtmlConverter.QUOTE_COLOR_LEVEL_2);
        assertEquals(HtmlConverter.getQuoteColor(3), HtmlConverter.QUOTE_COLOR_LEVEL_3);
        assertEquals(HtmlConverter.getQuoteColor(4), HtmlConverter.QUOTE_COLOR_LEVEL_4);
        assertEquals(HtmlConverter.getQuoteColor(5), HtmlConverter.QUOTE_COLOR_LEVEL_5);

        assertEquals(HtmlConverter.getQuoteColor(-1), HtmlConverter.QUOTE_COLOR_DEFAULT);
        assertEquals(HtmlConverter.getQuoteColor(0), HtmlConverter.QUOTE_COLOR_DEFAULT);
        assertEquals(HtmlConverter.getQuoteColor(6), HtmlConverter.QUOTE_COLOR_DEFAULT);

        String message = "zero\r\n" +
            "> one\r\n" +
            ">> two\r\n" +
            ">>> three\r\n" +
            ">>>> four\r\n" +
            ">>>>> five\r\n" +
            ">>>>>> six";
        String result = HtmlConverter.textToHtml(message);
        writeToFile(result);
        assertEquals("<pre class=\"ertebatmail\">"
                + "zero<br />"
                + "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #729fcf; padding-left: 1ex;\">"
                +   " one<br />"
                +   "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #ad7fa8; padding-left: 1ex;\">"
                +     " two<br />"
                +     "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #8ae234; padding-left: 1ex;\">"
                +       " three<br />"
                +       "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #fcaf3e; padding-left: 1ex;\">"
                +         " four<br />"
                +         "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #e9b96e; padding-left: 1ex;\">"
                +           " five<br />"
                +           "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #ccc; padding-left: 1ex;\">"
                +             " six"
                +           "</blockquote>"
                +         "</blockquote>"
                +       "</blockquote>"
                +     "</blockquote>"
                +   "</blockquote>"
                + "</blockquote>"
                + "</pre>", result);
    }

    private void writeToFile(final String content) {
        if (!WRITE_TO_FILE) {
            return;
        }
        try {
            System.err.println(content);

            File f = new File(OUTPUT_FILE);
            f.delete();

            FileWriter fstream = new FileWriter(OUTPUT_FILE);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(content);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testPreserveSpacesAtFirst() {
        String message = "foo\r\n"
                + " bar\r\n"
                + "  baz\r\n";
        String result = HtmlConverter.textToHtml(message);
        writeToFile(result);
        assertEquals("<pre class=\"ertebatmail\">"
                + "foo<br />"
                + " bar<br />"
                + "  baz<br />"
                + "</pre>", result);
    }

    public void testPreserveSpacesAtFirstForSpecialCharacters() {
        String message =
                  " \r\n"
                + "  &\r\n"
                + "    \n"
                + "   <\r\n"
                + "  > \r\n";
        String result = HtmlConverter.textToHtml(message);
        writeToFile(result);
        assertEquals("<pre class=\"ertebatmail\">"
                + " <br />"
                + "  &amp;<br />"
                + "    <br />"
                + "   &lt;<br />"
                + "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #729fcf; padding-left: 1ex;\">"
                + " <br />"
                + "</blockquote>"
                + "</pre>", result);
    }

    public void testLinkifyBitcoinAndHttpUri() {
        String text = "bitcoin:19W6QZkx8SYPG7BBCS7odmWGRxqRph5jFU http://example.com/";

        StringBuffer outputBuffer = new StringBuffer();
        HtmlConverter.linkifyText(text, outputBuffer);

        assertEquals("<a href=\"bitcoin:19W6QZkx8SYPG7BBCS7odmWGRxqRph5jFU\">" +
                "bitcoin:19W6QZkx8SYPG7BBCS7odmWGRxqRph5jFU" +
                "</a> " +
                "<a href=\"http://example.com/\">" +
                "http://example.com/" +
                "</a>", outputBuffer.toString());
    }
}
