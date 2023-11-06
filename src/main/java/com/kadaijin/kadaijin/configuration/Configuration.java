package com.kadaijin.kadaijin.configuration;

import com.kadaijin.kadaijin.model.DAO.LogModel;
import com.kadaijin.kadaijin.utils.Paging;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

import java.util.List;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Paging page(){
        return new Paging();
    }



}
