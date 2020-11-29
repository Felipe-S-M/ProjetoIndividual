package com.fixingsolutions.controller;

import com.fixingsolutions.bean.FuncionarioDao;
import com.fixingsolutions.domain.AjaxResponseBody;
import com.fixingsolutions.domain.Cargo;
import com.fixingsolutions.domain.Funcionario;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.fixingsolutions.bean.CargoDao;

@RestController
public class FuncionarioController {

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/buscarCargos")
    public ResponseEntity<?> buscarCargos(){

        AjaxResponseBody resposta = new AjaxResponseBody();
        CargoDao dao = new CargoDao();
        try {
            List<Cargo> cargos = dao.getAll();
            if (cargos.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Cargos não encontrados");
            }

            resposta.setResult(cargos);
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping(value = "/criarConta",consumes = {MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<?> criarConta(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();
        Funcionario funcionario = new Funcionario();
        try {

            String email = (String) params.get("email");
            if(email == null || email.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Email inválido");
            }

            String senha = (String) params.get("senha");

            if(senha == null || senha.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Senha inválido");
            }
            String nome = (String) params.get("nome");
            if(nome == null || nome.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Nome inválido");
            }
            Integer cargo = (Integer) params.get("cargo");
            if(cargo == null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Cargo inválido");
            }

            funcionario.setEmail(email);
            funcionario.setPassword(senha);
            funcionario.setNome(nome);
            funcionario.setIdCargo(cargo);

            FuncionarioDao dao = new FuncionarioDao();
            dao.save(funcionario);

        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

}