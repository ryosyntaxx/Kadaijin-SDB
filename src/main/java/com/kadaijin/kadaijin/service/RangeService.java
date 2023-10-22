package com.kadaijin.kadaijin.service;

import java.sql.Timestamp;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kadaijin.kadaijin.DTO.fiture.ConvertUserDTO;
import com.kadaijin.kadaijin.DTO.log.UserDTO;
import com.kadaijin.kadaijin.configuration.SetUserDTO;
import com.kadaijin.kadaijin.model.log.LogModel;
import com.kadaijin.kadaijin.model.log.UserModel;
import com.kadaijin.kadaijin.repository.log.LogRepository;
import com.kadaijin.kadaijin.repository.log.UserRepository;

@Service
public class RangeService {

    @Autowired
    SetUserDTO setUserDTO;

    @Autowired
    ConvertUserDTO convertUserDTO;

    @Autowired
    LogRepository logRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    public UserDTO customize(String email, String starts, String finish) {

        UserModel userModel = new UserModel(userRepository.findIdByUsername(email));
        Timestamp start = Timestamp.valueOf(starts.concat(" 00:00:00"));
        Timestamp end = Timestamp.valueOf(finish.concat(" 23:59:59"));
        List<LogModel> logModels = logRepository
                .findLogsBetweenTimestampsForUsers(start, end, userModel);

        UserDTO userDTO = setUserDTO.setUserDTO(email, logModels);
        return userDTO;
    }

    public UserDTO restoreAll(String email) {
        UserDTO userDTO = new UserDTO();
        UserModel userModel = new UserModel(userRepository.findIdByUsername(email));
        List<LogModel> logModel = logRepository.findAllByuserId(userModel);

        userDTO.setUserName(email);
        userDTO.setLog(convertUserDTO.listModelToLogDTO(logModel));
        userDTO.setTotalLogin(logRepository.countByCustomValue(userRepository.findIdByUsername(email)));
        return userDTO;
    }

    public UserDTO range(String email, String dates) {

        UserModel userModel = new UserModel(userRepository.findIdByUsername(email));
        Timestamp start = Timestamp.valueOf(dates.concat(" 00:00:00"));
        Timestamp end = Timestamp.valueOf(dates.concat(" 23:59:59"));
        List<LogModel> logModels = logRepository
                .findLogsBetweenTimestampsForUsers(start, end, userModel);

        UserDTO userDTO = setUserDTO.setUserDTO(email, logModels);

        return userDTO;

    }

    public UserDTO range(String email, String dates, String years) {

        UserModel userModel = new UserModel(userRepository.findIdByUsername(email));
        Timestamp start = Timestamp.valueOf(years + "-" + dates.concat("-01 00:00:00"));
        Timestamp end = Timestamp.valueOf(years + "-" + dates.concat("-31 23:59:59"));
        List<LogModel> logModels = logRepository
                .findLogsBetweenTimestampsForUsers(start, end, userModel);

        UserDTO userDTO = setUserDTO.setUserDTO(email, logModels);

        return userDTO;
    }

    public UserDTO rangeYears(String email, String years) {

        UserModel userModel = new UserModel(userRepository.findIdByUsername(email));
        Timestamp start = Timestamp.valueOf(years + "-".concat("01-01 00:00:00"));
        Timestamp end = Timestamp.valueOf(years + "-".concat("12-31 23:59:59"));
        List<LogModel> logModels = logRepository
                .findLogsBetweenTimestampsForUsers(start, end, userModel);

        UserDTO userDTO = setUserDTO.setUserDTO(email, logModels);

        return userDTO;
    }

}
