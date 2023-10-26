package com.kadaijin.kadaijin.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kadaijin.kadaijin.model.DAO.AccountsModel;
import com.kadaijin.kadaijin.model.DAO.LogModel;
import com.kadaijin.kadaijin.model.DTO.AccountsDTO;
import com.kadaijin.kadaijin.model.DTO.RangeCustomDTO;
import com.kadaijin.kadaijin.model.converter.ConvertDTO;
import com.kadaijin.kadaijin.repository.AccountsRepository;
import com.kadaijin.kadaijin.repository.PersonalDataRepository;

import jakarta.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class LogsService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    PersonalDataRepository personalDataRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ConvertDTO convertDTO;

    // GET ALL ACCOUNTS AND DATA
    public List<AccountsDTO> getLog() {
        List<AccountsModel> AccountsModel = accountsRepository.findAll();
        return convertDTO.listAccountModelToDTO(AccountsModel);
    }

    // GET ONE ACCOUNT
    public AccountsDTO getOneName(String request) {
        AccountsModel AccountsModel = accountsRepository.findByEmails(request);
        return new AccountsDTO(AccountsModel);

    }

    // GET ACCOUNTS AND LOG WITH PAGE
    public List<AccountsDTO> getPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AccountsModel> pageResult = accountsRepository.findAll(pageable);
        return convertDTO.listEntityToDto(pageResult);
    }

    // GET ACCOUNTS WITH LOGS CUSTOMIZE
    public AccountsDTO customsize(RangeCustomDTO rangeCustomDTO) {
        List<LogModel> logModels = new ArrayList<>();
        AccountsModel accountsModel = accountsRepository.findByEmailAndLog(
                rangeCustomDTO.getEmail(),
                rangeCustomDTO.getStart(),
                rangeCustomDTO.getEnd());

        for (LogModel logModel : accountsModel.getLogs()) {
            if (logModel.getLogin().after(rangeCustomDTO.getStart())
                    &&
                    logModel.getLogin().before(rangeCustomDTO.getEnd())) {
                logModels.add(logModel);
            }
        }

        return new AccountsDTO(accountsModel, logModels);
    }

}
