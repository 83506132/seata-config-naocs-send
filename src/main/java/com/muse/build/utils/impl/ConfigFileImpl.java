package com.muse.build.utils.impl;

import com.muse.build.utils.ConfigFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConfigFileImpl implements ConfigFile {

    final File fileConfig;

    public ConfigFileImpl(File fileConfig) {
        if (fileConfig == null || !fileConfig.isFile()) {
            throw new RuntimeException("抱歉请正确传入文件");
        }
        this.fileConfig = fileConfig;
    }

    @Override
    public String[] config() {
        List<String> list = new ArrayList<>();
        try (FileReader fileReader = new FileReader(fileConfig); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                list.add(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.toArray(new String[0]);
    }
}
