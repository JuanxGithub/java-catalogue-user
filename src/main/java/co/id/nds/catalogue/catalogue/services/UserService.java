package co.id.nds.catalogue.catalogue.services;

import co.id.nds.catalogue.catalogue.entities.UserEntity;
import co.id.nds.catalogue.catalogue.exceptions.ClientException;
import co.id.nds.catalogue.catalogue.exceptions.NotFoundException;
import co.id.nds.catalogue.catalogue.globals.GlobalConstant;
import co.id.nds.catalogue.catalogue.models.UserModel;
import co.id.nds.catalogue.catalogue.repository.UserRepository;
import co.id.nds.catalogue.catalogue.repository.specification.UserSpec;
import co.id.nds.catalogue.catalogue.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements Serializable {

    @Autowired
    private UserRepository userRepository;

    UserValidator userValidator = new UserValidator();

    public UserEntity doInsert(UserModel userModel) throws ClientException {

        userValidator.notNullCheckUserId(userModel.getId());
        userValidator.nullCheckFullName(userModel.getFullName());
        userValidator.validateFullName(userModel.getFullName());
        userValidator.nullCheckRoleId(userModel.getRoleId());
        userValidator.validateRoleId(userModel.getRoleId());
        userValidator.nullCheckCallNumberId(userModel.getCallNumber());
        userValidator.validateCallNumberId(userModel.getCallNumber());

        Long count = userRepository.countByFullName(userModel.getFullName());
        if (count > 0) {
            throw new ClientException("User name is already existed");
        }

        UserEntity user = new UserEntity();
        user.setFullName(userModel.getFullName());
        user.setRoleId(userModel.getRoleId());
        user.setCallNumber(userModel.getCallNumber());
        user.setRecStatus(GlobalConstant.REC_STATUS_ACTIVE);
        user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        user.setCreatorId(userModel.getActorId() == null ? 0 : userModel.getActorId());

        return userRepository.save(user);
    }

    public List<UserEntity> doGetAllUsers() {
        List<UserEntity> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);

        return users;
    }

    public List<UserEntity> doSearchByCriteria(UserModel userModel){
        List<UserEntity> productEntities = new ArrayList<>();
        UserSpec spec = new UserSpec(userModel);
        userRepository.findAll(spec).forEach(productEntities::add);

        return productEntities;
    }

    public UserEntity findById(Integer id) throws ClientException, NotFoundException {

        userValidator.nullCheckUserId(id);
        userValidator.validateUserId(id);

        UserEntity user = userRepository.findById(id).orElse(null);
        userValidator.nullCheckObject(user);

        return user;
    }

    public UserEntity doUpdate(UserModel userModel) throws ClientException, NotFoundException{

        userValidator.nullCheckUserId(userModel.getId());
        userValidator.validateUserId(userModel.getId());

        Optional<UserEntity> optionalUser = userRepository.findById(userModel.getId());
        UserEntity user = optionalUser.orElseThrow(() -> new NotFoundException("Cannot find user with id: " + userModel.getId()));

        if(userModel.getFullName() != null) {
            userValidator.validateFullName(userModel.getFullName());

            Long count = userRepository.countByFullName(userModel.getFullName());
            if(count > 0) {
                throw new ClientException("User name is already existed");
            }

            user.setFullName(userModel.getFullName());
        }

        if (userModel.getCallNumber() != null) {
            userValidator.validateCallNumberId(userModel.getCallNumber());
            user.setCallNumber(userModel.getCallNumber());
        }

        if(userModel.getRoleId() != null) {
            userValidator.validateRoleId(userModel.getRoleId());
            user.setRoleId(userModel.getRoleId());
        }

        user.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        user.setUpdaterId(userModel.getActorId() == null ? 0 : userModel.getActorId());

        return userRepository.save(user);
    }

    public UserEntity doDelete(UserModel userModel) throws ClientException, NotFoundException {
        userValidator.nullCheckUserId(userModel.getId());
        userValidator.validateUserId(userModel.getId());

        Optional<UserEntity> optionalUser = userRepository.findById(userModel.getId());
        UserEntity user = optionalUser.orElseThrow(() -> new NotFoundException("Cannot find user with id: " + userModel.getId()));

        if(user.getRecStatus().equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE)) {
            throw new ClientException(
                    "User id (" + userModel.getId() + ") is already been deleted");

        }

        user.setRecStatus(GlobalConstant.REC_STATUS_NON_ACTIVE);
        user.setDeletedDate(new Timestamp(System.currentTimeMillis()));
        user.setDeleterId(userModel.getActorId() == null ? 0 : userModel.getActorId());

        userRepository.doDelete(user.getId(), user.getDeleterId());
        return user;

    }
}
