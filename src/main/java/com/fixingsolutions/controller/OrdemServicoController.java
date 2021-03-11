package com.fixingsolutions.controller;

import com.fixingsolutions.bean.*;
import com.fixingsolutions.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
public class OrdemServicoController {

    @GetMapping("/buscarOs")
    public ResponseEntity<?> buscarOs(){

        AjaxResponseBody resposta = new AjaxResponseBody();
        OsDao dao = new OsDao();
        try{

            List<Os> ordemServico = dao.getAll();
            if (ordemServico.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Orcamentos não encontrados");
            }

            resposta.setResult(ordemServico);

        }catch (Exception e){

            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");

        }

        return ResponseEntity.ok(resposta);

    }

    @PostMapping(value = "/criarOs",consumes = {MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<?> criarOs(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {

            Integer idOrcamento = (Integer) params.get("orcamento");
            if(idOrcamento==null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Orcamento inválido");
            }

            String titulo = (String) params.get("titulo");
            if(titulo==null||titulo==""){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Titulo inválido");
            }

            OrcamentoDao orcamentoDao = new OrcamentoDao();
            Orcamento orcamento = orcamentoDao.get(idOrcamento);

            Os ordemServico = new Os();
            ordemServico.setOrcamento(orcamento);
            ordemServico.setTitulo(titulo
            );
            ordemServico.setStatus(1);
            ordemServico.setDataCriacao(new Date());
            ordemServico.setDataUltimaAtualizacao(new Date());

            OsDao ordemServicoDao = new OsDao();
            ordemServicoDao.save(ordemServico);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

    @PostMapping(value = "/alterarOrcamento",consumes = {MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<?> alterarOrcamento(@RequestBody Map<String,Object> params) {

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {

            List<?> paramsServicos = (ArrayList) params.get("servicos");

            if(!(paramsServicos instanceof List)){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Serviços inválidos");
            }

            Integer id = (Integer) params.get("id");
            if(id==null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Orçamento inválido");
            }

            Integer idCliente = (Integer) params.get("cliente");
            if(idCliente==null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Cliente inválido");
            }

            Integer idFuncionario = (Integer) params.get("responsavel");
            if(idFuncionario==null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Responsável inválido");
            }

            Object horasPrevistas = params.get("horasPrevistas");
            if(horasPrevistas==null || horasPrevistas.toString().isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Horas prevístas inválida");
            }

            ClienteDao clienteDao = new ClienteDao();
            Cliente cliente = clienteDao.get((Integer) params.get("cliente"));

            FuncionarioDao funcionarioDao = new FuncionarioDao();
            Funcionario funcionario = funcionarioDao.get((Integer) params.get("responsavel"));

            OrcamentoDao orcamentoDao = new OrcamentoDao();
            Orcamento orcamento = orcamentoDao.get(id);
            orcamento.setCliente(cliente);
            orcamento.setFuncionario(funcionario);
            orcamento.setId(id);
            orcamento.setHorasPrevistas(new Integer(horasPrevistas.toString()));

            orcamentoDao.update(orcamento);

            ServicoDao servicoDao = new ServicoDao();

            for(int i=0;i<paramsServicos.size();i++){

                Servico servico = new Servico();

                LinkedHashMap ob = (LinkedHashMap) paramsServicos.get(i);

                String descricao = (String) ob.get("descricao");

                if(descricao==null || descricao.isEmpty()){
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Descrição do serviço Nº"+(i+1)+" inválido");
                }

                Object paramValor = ob.get("valor");
                if(paramValor==null){
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Valor do serviço Nº"+i+" inválido");
                }

                servico.setDescricao(descricao);
                BigDecimal valorServico = new BigDecimal(paramValor.toString());
                if(valorServico.compareTo(new BigDecimal("0")) <= 0){
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Valor do serviço Nº"+(i+1)+" inválido");
                }

                servico.setValor(valorServico);
                servico.setId((Integer) ob.get("id"));

                if(servico.getId()!=null){

                    servicoDao.update(servico);

                }else{

                    servicoDao.save(servico,(Integer) params.get("id"));

                }

            }

            Os.recalcularValorOrcamento((Integer) params.get("id"));

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }

        return ResponseEntity.ok(resposta);

    }

}
