package com.kadaijin.kadaijin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kadaijin.kadaijin.model.KadaijinModel;

public interface KadaijinRepository extends JpaRepository<KadaijinModel, Integer> {

    KadaijinModel findByUsername(String username);

    KadaijinModel findByPassword(String password);

    boolean existsByUsernameAndPassword(String username, String password);

    // KadaijinModel findByPage(Integer limit, Integer offset);

}
