package sudexpert.gov.by.workproject.service;

import sudexpert.gov.by.workproject.dto.ECCDTO;

import java.util.List;

public interface ECCService {

    ECCDTO createEcc(ECCDTO eccdto);

    ECCDTO updateEcc(Long id, ECCDTO eccdto);

    ECCDTO getEccById(Long id);

    void deleteEcc(Long id);

    List<ECCDTO> getAllEcc();

    List<ECCDTO> getEccsByWorkerId(Long workerId);

}
