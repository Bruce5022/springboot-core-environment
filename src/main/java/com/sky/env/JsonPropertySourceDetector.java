package com.sky.env;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.boot.json.YamlJsonParser;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

//Json
@Component
public class JsonPropertySourceDetector extends AbstractPropertySourceDetector {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    private static final JsonParser JSON_PARSER = JsonParserFactory.getJsonParser();

    @Override
    public String[] getFileExtensions() {
        return new String[]{"json"};
    }

    @Override
    public void load(ConfigurableEnvironment environment, String name, Resource resource) throws IOException {
        try {
            Map<String, Object> map = JSON_PARSER.parseMap(getContentStringFromResource(resource));
            Map<String, Object> target = flatten(map);
            addPropertySource(environment, new MapPropertySource(name, target));
        } catch (Exception e) {
            log.warn("加载Json文件属性到环境变量失败,name = {},resource = {}", name, resource);
        }
    }
}



