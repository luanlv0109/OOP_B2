package com.example.da.service.impl;

import com.example.da.service.ManagerEntityService;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ManagerServiceImpl implements ManagerEntityService {

    @Override
    public List<String> getEntityNames(String packageName) {
        List<String> list = new ArrayList<>();
        try{
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            assert classLoader != null;
                String path = packageName.replace('.', '/');
            URL packageURL = classLoader.getResource(path);
            if(packageURL != null) {
                File directory = new File(packageURL.getFile());
                for(File file : directory.listFiles()) {
                    if(file.getName().endsWith(".class")) {
                        String className = packageName + '.' + file.getName().replace(".class", "");
                        Class<?> clazz = Class.forName(className);
                        if (clazz.isAnnotationPresent(Entity.class) || clazz.isAnnotationPresent(Table.class)) {
                            list.add(clazz.getSimpleName());
                            log.info("name: {}" , clazz.getSimpleName());
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
