package com.fudan.se.community;

//import io.github.swagger2markup.Swagger2MarkupConfig;
//import io.github.swagger2markup.Swagger2MarkupConverter;
//import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
//import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AppPDFTest {
//    @Test
//    public void generateMarkdownFile() throws Exception {
//        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
//                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
//                .build();
//
//        URL apiUrl = new URL("http://localhost:8081/v2/api-docs");
//        // 指定文件名称
//        String markdownFileName = "src/docs/markdown/generated/Community_API";
//        Swagger2MarkupConverter.from(apiUrl)
//                .withConfig(config)
//                .build()
//                //指定生成目录下生成指定文件
//                .toFile(Paths.get(markdownFileName));
//    }
}

