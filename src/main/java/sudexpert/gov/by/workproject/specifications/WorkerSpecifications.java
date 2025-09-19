package sudexpert.gov.by.workproject.specifications;

import org.springframework.data.jpa.domain.Specification;
import sudexpert.gov.by.workproject.model.WorkerEntity;

public class WorkerSpecifications {
    public static Specification<WorkerEntity> hasNameLike(String search) {
        return (root, query, cb) -> search == null || search.isBlank()
                ? cb.conjunction()
                : cb.like(cb.lower(root.get("surname")), "%" + search.toLowerCase() + "%");
    }

    public static Specification<WorkerEntity> hasJobTitle(String job) {
        return (root, query, cb) -> job == null || job.isBlank()
                ? cb.conjunction()
                : cb.equal(root.get("jobTitle"), job);
    }

    public static Specification<WorkerEntity> hasFlag(String flagName) {
        return (root, query, cb) -> cb.isTrue(root.get(flagName));
    }
}
