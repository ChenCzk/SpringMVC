package com.SSM.Converter;

import com.SSM.POJO.Czk;
import org.springframework.core.convert.converter.Converter;

public class CzkConverter implements Converter<String,Czk> {


    public Czk convert(String source) {
        if (source.indexOf("-") == -1){
            return null;  //不包括指定字符，放生
        }
        String[] split = source.split("-");
        Czk czk = new Czk();
        czk.setName(split[0]);
        czk.setNote(split[1]);
        return czk;
    }
}
