package com.sky.env.service.impl;

import com.sky.env.service.AbstractPropertySourceDetector;
import com.sky.env.service.PropertySourceDetector;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 3.子类的全部PropertySource都是MapPropertySource，name为文件的名称，所有PropertySource都
 * 用addBefore方法插入到systemProperties的前面，主要是为了提高匹配属性的优先级。接着需要定
 * 义一个属性探索者的合成类用来装载所有的子类：
 */
public class PropertySourceDetectorComposite implements PropertySourceDetector {

    private static final String DEFAULT_SUFFIX = "properties";
    private final List<AbstractPropertySourceDetector> propertySourceDetectors = new ArrayList<>();

    public void addPropertySourceDetector(AbstractPropertySourceDetector sourceDetector) {
        propertySourceDetectors.add(sourceDetector);
    }

    public void addPropertySourceDetectors(List<AbstractPropertySourceDetector> sourceDetectors) {
        propertySourceDetectors.addAll(sourceDetectors);
    }

    public List<AbstractPropertySourceDetector> getPropertySourceDetectors() {
        return Collections.unmodifiableList(propertySourceDetectors);
    }

    @Override
    public String[] getFileExtensions() {
        List<String> fileExtensions = new ArrayList<>(8);
        for (AbstractPropertySourceDetector propertySourceDetector : propertySourceDetectors) {
            fileExtensions.addAll(Arrays.asList(propertySourceDetector.getFileExtensions()));
        }
        return fileExtensions.toArray(new String[0]);
    }

    @Override
    public void load(ConfigurableEnvironment environment, String name, Resource resource) throws IOException {
        if (resource.isFile()) {
            String fileName = resource.getFile().getName();
            int index = fileName.lastIndexOf(".");
            String suffix;
            if (-1 == index) {
                //如果文件没有后缀,当作properties处理
                suffix = DEFAULT_SUFFIX;
            } else {
                suffix = fileName.substring(index + 1);
            }
            for (AbstractPropertySourceDetector propertySourceDetector : propertySourceDetectors) {
                if (propertySourceDetector.support(suffix)) {
                    propertySourceDetector.load(environment, name, resource);
                    return;
                }
            }
        }
    }
}