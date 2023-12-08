package com.youdao.aicloud.translate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TranslationDemo extends JFrame {
    private JTextField chineseTextField, baiduTextField, youdaoTextField, commonTextField;
    private JButton translateButton;

    public TranslationDemo() {
        super("Translation Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        chineseTextField = new JTextField(20);
        baiduTextField = new JTextField(20);
        baiduTextField.setEditable(false);
        youdaoTextField = new JTextField(20);
        youdaoTextField.setEditable(false);
        commonTextField = new JTextField(20);
        commonTextField.setEditable(false);

        translateButton = new JButton("Translate");

        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    translate();
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        add(new JLabel("Chinese:"));
        add(chineseTextField);
        add(new JLabel("Baidu Translation:"));
        add(baiduTextField);
        add(new JLabel("Youdao Translation:"));
        add(youdaoTextField);
        add(new JLabel("Common:"));
        add(commonTextField);
        add(translateButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void translate() throws NoSuchAlgorithmException {
        String chineseText = chineseTextField.getText();

        // Replace YOUR_BAIDU_API_KEY with your actual Baidu API key
        String baiduTranslation = BaiduTranslateAPI.translate(chineseText, "auto", "en");
        baiduTextField.setText(baiduTranslation);

        // Replace YOUR_YOUDAO_API_KEY with your actual Youdao API key
        String youdaoTranslation = YoudaoTranslateAPI.translate(chineseText, "auto", "en");
        youdaoTextField.setText(youdaoTranslation);

        // Find common parts
        String common = findCommon(baiduTranslation, youdaoTranslation);
        commonTextField.setText(common);
    }

    private String findCommon(String str1, String str2) {
        List<String> words1 = new ArrayList<>();
        List<String> commonWords = new ArrayList<>();

        // 将两个字符串分割为单词
        String[] str1Words = str1.split("\\s+");
        String[] str2Words = str2.split("\\s+");

        // 将第一个字符串的单词加入到列表中
        for (String word : str1Words) {
            words1.add(word);
        }

        // 遍历第二个字符串的单词，如果在第一个字符串中存在，就是共同的单词
        for (String word : str2Words) {
            if (words1.contains(word)) {
                commonWords.add(word);
            }
        }
        String temp=new String();
        for(String word:commonWords){
            temp=temp+word+" ";
        }
        return temp;

    }


    public static void main(String[] args)throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TranslationDemo();
            }
        });
    }
}