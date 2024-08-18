package co.id.nds.catalogue.catalogue.repository.specification;

import co.id.nds.catalogue.catalogue.entities.UserEntity;
import co.id.nds.catalogue.catalogue.globals.GlobalConstant;
import co.id.nds.catalogue.catalogue.models.UserModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class UserSpec implements Specification<UserEntity> {
    private UserModel userModel;

    public UserSpec(UserModel userModel){
        super();
        this.userModel = userModel;
    }

    @Override
    public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        Predicate p = criteriaBuilder.and();

        if (userModel.getId() != null && userModel.getId() != 0) {
            p.getExpressions().add((criteriaBuilder.equal(root.get("id"), userModel.getId())));
        }

        if (userModel.getFullName() != null && userModel.getFullName().isEmpty()) {
            p.getExpressions().add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")),
                    "%" + userModel.getFullName().toLowerCase() + "%"));
        }

        if(userModel.getRoleId() != null){
            p.getExpressions().add(criteriaBuilder.equal(root.get("roleId"), userModel.getRoleId()));
        }

        if(userModel.getCallNumber() != null && userModel.getCallNumber().isEmpty()){
            p.getExpressions().add(criteriaBuilder.like(root.get("callNumber"),
                    "%" + userModel.getCallNumber()+ "%"));
        }

        if (userModel.getRecStatus() != null && (userModel.getRecStatus().trim()
                .equalsIgnoreCase(GlobalConstant.REC_STATUS_ACTIVE)
                || userModel.getRecStatus().trim().equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE))) {
            p.getExpressions().add(criteriaBuilder.equal(criteriaBuilder.upper(root.get("recStatus")),
                    userModel.getRecStatus().toUpperCase()));
        }

        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));

        return p;
    }

}

