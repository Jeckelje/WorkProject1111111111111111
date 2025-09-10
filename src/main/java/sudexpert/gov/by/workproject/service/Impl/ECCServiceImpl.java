package sudexpert.gov.by.workproject.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import sudexpert.gov.by.workproject.dto.ECCDTO;
import sudexpert.gov.by.workproject.error.ErrorMessages;
import sudexpert.gov.by.workproject.exception.ResourceNotFoundException;
import sudexpert.gov.by.workproject.mapper.ECCMapper;
import sudexpert.gov.by.workproject.model.ECC;
import sudexpert.gov.by.workproject.model.Train;
import sudexpert.gov.by.workproject.repository.ECCRepository;
import sudexpert.gov.by.workproject.service.ECCService;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ECCServiceImpl implements ECCService {

    ECCRepository eccRepository;
    ECCMapper eccMapper;


    @Override
    public ECCDTO createEcc(ECCDTO eccdto) {
        return eccMapper.toDTO(eccRepository.save(eccMapper.toEntity(eccdto)));
    }

    @Override
    public ECCDTO updateEcc(Long id, ECCDTO eccdto) {
        ECC existingECC = findECCByIdOrThrow(id);
        eccMapper.updateEССFromRequest(eccdto, existingECC);

        ECC updatedECC = eccRepository.save(existingECC);
        return eccMapper.toDTO(updatedECC);
    }

    @Override
    public ECCDTO getEccById(Long id) {
        ECC ecc = findECCByIdOrThrow(id);
        return eccMapper.toDTO(ecc);
    }

    @Override
    public void deleteEcc(Long id) {
        ECC ecc = findECCByIdOrThrow(id);
        eccRepository.deleteById(id);
    }

    @Override
    public List<ECCDTO> getAllEcc() {
        return eccRepository.findAll().stream()
                .map(eccMapper::toDTO)
                .toList();
    }

    @Override
    public List<ECCDTO> getEccsByWorkerId(Long workerId) {
        List<ECC> eccs = findECCSByWorkerIdOrThrow(workerId);
        return eccs.stream()
                .map(eccMapper::toDTO).toList();
    }

    //---------------------------------------------------------------------------------------------------------
    private ECC findECCByIdOrThrow(Long id) {
        return eccRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessages.RESOURCE_NOT_FOUND_BY_ID_MESSAGE, "ECC", id)));
    }

    private List<ECC> findECCSByWorkerIdOrThrow(Long workerId) {
        List<ECC> eccs = eccRepository.findECCSByWorkerId(workerId);
        if (eccs.isEmpty()) {
            throw new ResourceNotFoundException(
                    String.format(ErrorMessages.RESOURCE_NOT_FOUND_BY_CUSTOMER_NAME_MESSAGE, "ECC for worker", workerId)
            );
        }
        return eccs;
    }
}
