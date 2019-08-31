package com.sky.env.service.impl;

import com.sky.env.service.AbstractPropertySourceDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.YamlJsonParser;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Map;

//Yaml
public class YamlPropertySourceDetector extends AbstractPropertySourceDetector {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    private static final JsonParser YAML_PARSER = new YamlJsonParser();

    @Override
    public String[] getFileExtensions() {
        return new String[]{"yaml", "yml"};
    }

    @Override
    public void load(ConfigurableEnvironment environment, String name, Resource resource) throws IOException {
        try {
            Map<String, Object> map = YAML_PARSER.parseMap(getContentStringFromResource(resource));
            Map<String, Object> target = flatten(map);
            addPropertySource(environment, new MapPropertySource(name, target));
        } catch (Exception e) {
            log.warn("加载Yaml文件属性到环境变量失败,name = {},resource = {}", name, resource);
        }
    }
}