package com.zhong.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;


/**
 * Created by cc on 2021/3/28
 */
public class MarkdownUtils {

    /**
     * markdown 格式转换成 html 格式
     * @param markdown
     * @return
     */
    public static String markdownToHtml(String markdown){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        return renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
    }

    /**
     * 增加扩展【标题锚点，表格生成】
     * markdown 转成 html
     * @param markdown
     * @return
     */
    public static String markdownToHtmlExtensions(String markdown){
        //h标题生成id
        Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
        //转换table的html
        List<Extension> tableExtensions = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder()
                .extensions(tableExtensions)
                .build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(headingAnchorExtensions)
                .extensions(tableExtensions)
                .attributeProviderFactory(new AttributeProviderFactory() {
                    @Override
                    public AttributeProvider create(AttributeProviderContext context) {
                        return new CustomAttributeProvider();
                    }
                })
                .build();
        return renderer.render(document);
    }

    /**
     * 处理标签属性
     */
    static class CustomAttributeProvider implements AttributeProvider{

        @Override
        public void setAttributes(Node node, String s, Map<String, String> map) {
            //改变a标签的target属性为_black
            if (node instanceof Link){//link链接
                map.put("target","_blank");
            }
            if (node instanceof TableBlock){
                map.put("class","ui celled table");
            }
        }
    }











}
