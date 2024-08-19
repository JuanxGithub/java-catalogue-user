package co.id.nds.catalogue.catalogue.validator;

import co.id.nds.catalogue.catalogue.exceptions.ClientException;
import co.id.nds.catalogue.catalogue.exceptions.NotFoundException;
import co.id.nds.catalogue.catalogue.globals.GlobalConstant;

public class UserValidator {

    public void nullCheckUserId(Integer id) throws ClientException {
        if (id == null) {
            throw new ClientException("User id is required");
        }
    }

    public void notNullCheckUserId(Integer id) throws ClientException {
        if (id != null) {
            throw new ClientException("User id is auto generated, do not input id");
        }
    }

    public void nullCheckFullName(String name) throws ClientException {
        if (name == null) {
            throw new ClientException("User name is required");
        }
    }

    public void nullCheckRoleId(String roleId) throws ClientException {
        if(roleId == null) {
            throw new ClientException("User Role Id is required");
        }
    }

    public void nullCheckCallNumberId(String callNumber) throws ClientException {
        if (callNumber == null) {
            throw new ClientException("User Call Number id is required");
        }
    }

    public void nullCheckObject(Object o) throws NotFoundException {
        if (o == null) {
            throw new NotFoundException("User is not found");
        }
    }

    public void validateUserId(Integer id) throws ClientException {
        if (id <= 0){
            throw new ClientException("User id input is invalid");
        }
    }

    public void validateFullName(String name) throws ClientException {
        if (name.trim().equalsIgnoreCase("")) {
            throw new ClientException("User name is required");
        }
    }

    public void validateCallNumberId(String callNumber) throws ClientException {
        if (callNumber == null || callNumber.isEmpty()) {
            throw new ClientException("Call number cannot be empty");
        }

        if (!callNumber.startsWith("0") && !callNumber.startsWith("+62")) {
            throw new ClientException("Call number must start with 0 or +62");
        }

        String numericCallNumber = callNumber.replaceAll("\\D", "");

        if (numericCallNumber.length() < 9 || numericCallNumber.length() > 12) {
            throw new ClientException("Call number must be between 9 to 12 digits");
        }
    }

    public void validateRoleId(String roleId) throws ClientException {
        if(roleId.length() != 5 || !roleId.startsWith("R")) {
            throw new ClientException("User Role id contains five digit and start with 'R'");
        }
    }

    public void validateRecStatus(String id, String recStatus) throws ClientException {
        if (recStatus.equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE)) {
            throw new ClientException("User with id" + id + " is already been deleted.");
        }
    }
}
