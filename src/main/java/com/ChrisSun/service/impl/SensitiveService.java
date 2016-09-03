package com.ChrisSun.service.impl;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 2016/9/3.
 */
@Service
public class SensitiveService implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);
    private static final String DEFAULT_REPLACEMENT = "敏感词";

    /**
     * 根节点
     */
    private TrieNode rootNode = new TrieNode();


    /**
     * 判断是否是一个符号
     */
    private boolean isSymbol(char c) {
        int ic = (int) c;
        // 0x2E80-0x9FFF 东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }


    /**
     * 过滤敏感词
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text))
            return text;

        int begin = 0, position = 0;
        TrieNode tempNode = rootNode;
        StringBuilder result = new StringBuilder();

        while (position < text.length()) {
            char c = text.charAt(position);
            //乱七八糟的字符直接跳过
            if (isSymbol(c)) {
                if (tempNode == rootNode) {
                    result.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }
            if ((tempNode = tempNode.getSubNode(c)) == null){
                result.append(text.charAt(begin));
                position = ++begin;
//                position = begin + 1;
//                begin = position;
                tempNode = rootNode;
            }else if (tempNode.isKeyWordEnd()) {
                result.append(DEFAULT_REPLACEMENT);
                begin = ++position;
//                begin = position + 1;
//                position = begin;
                tempNode = rootNode;
            }else {
                ++position;
            }
        }
        result.append(text.substring(begin));
        return result.toString();
    }

    private void addWord(String lineTxt) {
        if (lineTxt == null || lineTxt.length() == 0){
            return;
        }

        TrieNode tempNode = rootNode;
        // 循环每个字节
        for (int i = 0; i < lineTxt.length(); i++) {
            Character c = lineTxt.charAt(i);
            //过滤乱七八糟的字符
            if (isSymbol(c)){
                continue;
            }
            TrieNode node = tempNode.getSubNode(c);
            if (node == null){//not initialize
                node = new TrieNode();
                tempNode.addSubNode(c, node);
            }
            tempNode = node;
            if (i == lineTxt.length() -1 ){
                //关键词结束，设置结束标志
                tempNode.setKeyWordEnd(true);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            String lineText;
            while ((lineText = bufferedReader.readLine()) != null) {
                lineText = lineText.trim();
                addWord(lineText);
            }
        }catch (Exception e){
            logger.error("failed to read SensitiveWords.txt");
        }finally {
            bufferedReader.close();
            reader.close();
            is.close();
        }
    }

    private class TrieNode {
        /**
         * true 关键词的终结 ； false 继续
         */
        private boolean end = false;
        /**
         * key下一个字符，value是对应的节点
         */
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        /**
         * 向指定位置添加节点树
         */
        public void addSubNode(Character c, TrieNode node) {
            subNodes.put(c, node);
        }
        /**
         * 获取下个节点
         */
        public TrieNode getSubNode(Character c) {
            return subNodes.get(c);
        }

        public boolean isKeyWordEnd() {
            return end;
        }

        public void setKeyWordEnd(boolean end) {
            this.end = end;
        }
        public int getSubNodesCount() {
            return subNodes.size();
        }
    }

    public static void main(String[] argv) {
        SensitiveService s = new SensitiveService();
        s.addWord("色情");
        s.addWord("好色");
        System.out.print(s.filter("你好X色**情XX"));
    }
}
