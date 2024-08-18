package co.id.nds.catalogue.catalogue.controller;

import co.id.nds.catalogue.catalogue.entities.UserEntity;
import co.id.nds.catalogue.catalogue.exceptions.ClientException;
import co.id.nds.catalogue.catalogue.exceptions.NotFoundException;
import co.id.nds.catalogue.catalogue.models.ResponseModel;
import co.id.nds.catalogue.catalogue.models.UserModel;
import co.id.nds.catalogue.catalogue.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/doInsert")
    public ResponseEntity<ResponseModel> doInsertUserController(@Valid @RequestBody UserModel userModel) {

        try {
            UserEntity user = userService.doInsert(userModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("New user is successfully added");
            response.setData(user);
            return ResponseEntity.ok(response);

        } catch (ClientException e){
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(responseModel);
        } catch (Exception e) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMsg("Sorry, there is a failure on our server");
            e.printStackTrace();
            return  ResponseEntity.internalServerError().body(responseModel);
        }
    }

    @GetMapping(value = "/doGetAllUsers")
    public ResponseEntity<ResponseModel> doGetAllUsersController() {
        try {
            List<UserEntity> users = userService.doGetAllUsers();

            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(users);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMsg("Sorry, there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(responseModel);
        }
    }

    @GetMapping(value = "/doSearchUser")
    public ResponseEntity<ResponseModel> doSearchUserController(@RequestBody UserModel userModel) {
        try {
            List<UserEntity> products = userService.doSearchByCriteria(userModel);

            ResponseModel responseModel = new ResponseModel();
            responseModel.setMsg("Request successfully");
            responseModel.setData(products);
            return ResponseEntity.ok(responseModel);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping(value = "/doGetDetailUser/{id}")
    public ResponseEntity<ResponseModel> doGetDetailUserController(@PathVariable Integer id) {

        try {
            UserEntity user = userService.findById(id);

            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(user);
            return ResponseEntity.ok(response);

        } catch (ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }

    }

    @PutMapping(value = "/doUpdate")
    public ResponseEntity<ResponseModel> doUpdateUserController(@RequestBody UserModel userModel) {
        try {
            UserEntity user = userService.doUpdate(userModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("User is successfully updated");
            response.setData(user);
            return ResponseEntity.ok(response);

        } catch (ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }

    }

    @DeleteMapping(value = "/doDelete")
    public ResponseEntity<ResponseModel> doDeleteUserController(@RequestBody UserModel userModel) {

        try {
            UserEntity user = userService.doDelete(userModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("User is successfully deleted");
            response.setData(user);
            return ResponseEntity.ok(response);

        }catch (ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }catch (NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }catch (Exception e){
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
