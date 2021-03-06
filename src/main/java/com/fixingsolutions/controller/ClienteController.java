package com.fixingsolutions.controller;

import com.fixingsolutions.bean.ClienteDao;
import com.fixingsolutions.domain.AjaxResponseBody;
import com.fixingsolutions.domain.Cliente;
import com.fixingsolutions.service.InputValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ClienteController {

    @GetMapping("/buscarClientes")
    public ResponseEntity<?> buscarClientes(){

        AjaxResponseBody resposta = new AjaxResponseBody();
        ClienteDao dao = new ClienteDao();
        try {
            List<Cliente> clientes = dao.getAll();
            if (clientes.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Clientes não encontrados");
            }

            resposta.setResult(clientes);

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

    @PostMapping(value = "/criarCliente",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> criarCliente(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {

            String nome = (String) params.get("nome");
            if(!InputValidationService.isValidStringInput(nome)){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Nome inválido");
            }

            String email = (String) params.get("email");
            if(!InputValidationService.isValidStringInput(email) || !email.contains("@") || !email.contains("mail")){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Email inválido");
            }

            String cpf = (String) params.get("cpf");
            if(!InputValidationService.isValidStringInput(cpf) || cpf.length()<14){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("CPF inválido");
            }

            String telefone = (String) params.get("telefone");
            if(!InputValidationService.isValidStringInput(telefone) || telefone.length()<15){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Telefone inválido");
            }

            Cliente cliente = new Cliente();
            cliente.setNome(nome);
            cliente.setEmail(email);
            cliente.setTelefone(telefone);
            cliente.setCpf(cpf);

            ClienteDao dao = new ClienteDao();
            dao.save(cliente);


        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

    @PutMapping(value = "/editarCliente",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> editarCliente(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {

            String nome = (String) params.get("nome");
            if(!InputValidationService.isValidStringInput(nome)){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Nome inválido");
            }

            String email = (String) params.get("email");
            if(!InputValidationService.isValidStringInput(email) || !email.contains("@") || !email.contains("mail")){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Email inválido");
            }

            String cpf = (String) params.get("cpf");
            if(!InputValidationService.isValidStringInput(cpf) || cpf.length()<14){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("CPF inválido");
            }

            String telefone = (String) params.get("telefone");
            if(!InputValidationService.isValidStringInput(telefone) || telefone.length()<15){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Telefone inválido");
            }

            Integer id = (Integer) params.get("id");
            if(id==null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Id inválido");
            }

            Cliente cliente = new Cliente();

            cliente.setId(id);
            cliente.setNome(nome);
            cliente.setEmail(email);
            cliente.setTelefone(telefone);
            cliente.setCpf(cpf);

            ClienteDao dao = new ClienteDao();
            dao.update(cliente);


        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

    @DeleteMapping(value = "/deletarCliente",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deletarCliente(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {

            Integer id = (Integer) params.get("id");

            ClienteDao dao = new ClienteDao();
            dao.delete(id);

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

}
