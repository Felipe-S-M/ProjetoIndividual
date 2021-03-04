package com.fixingsolutions.bean;

import com.fixingsolutions.domain.*;
import com.fixingsolutions.repository.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoDao implements Dao<Orcamento>{

    private Conexao conexao = new Conexao();

    @Override
    public Orcamento get(int id) throws SQLException {
        String comando = "select * from orcamento orc join funcionario fun on fun.id = orc.idFuncionario join cargo on fun.idCargo = cargo.id join cliente cli on orc.idCliente = cli.id where orc.id = ?";
        Orcamento orcamento = new Orcamento();

        Connection dbConenection = conexao.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setInt(1,id);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()){

            orcamento.setId(rs.getInt("orc.id"));
            orcamento.setData(rs.getDate("orc.data"));
            orcamento.setValor(rs.getBigDecimal("orc.valor"));
            orcamento.setHorasPrevistas(rs.getInt("orc.horasPrevistas"));

            Funcionario funcionario = new Funcionario();
            funcionario.setId(rs.getInt("fun.id"));
            funcionario.setEmail(rs.getString("fun.email"));
            funcionario.setSenha(rs.getString("fun.password"));
            funcionario.setNome(rs.getString("fun.nome"));

            Cargo cargo = new Cargo();
            cargo.setId(rs.getInt("cargo.id"));
            cargo.setDescricao(rs.getString("cargo.descricao"));

            funcionario.setCargo(cargo);

            Cliente cliente = new Cliente();
            cliente.setId(rs.getInt("cli.id"));
            cliente.setEmail(rs.getString("cli.email"));
            cliente.setCpf(rs.getString("cli.cpf"));
            cliente.setNome(rs.getString("cli.nome"));
            cliente.setTelefone(rs.getString("cli.telefone"));

            orcamento.setFuncionario(funcionario);
            orcamento.setCliente(cliente);

        }

        dbConenection.close();

        return orcamento;
    }

    @Override
    public List<Orcamento> getAll() throws SQLException {

        List<Orcamento> orcamentos = new ArrayList<Orcamento>();
        String comando = "select * from orcamento orc join funcionario fun on fun.id = orc.idFuncionario join cargo on fun.idCargo = cargo.id join cliente cli on orc.idCliente = cli.id";

        Connection dbConenection = conexao.abrirConexao();
        Statement stmt = dbConenection.createStatement();
        ResultSet rs = stmt.executeQuery(comando);

        while(rs.next()) {
            Orcamento orcamento = new Orcamento();
            orcamento.setId(rs.getInt("orc.id"));
            orcamento.setData(rs.getDate("orc.data"));
            orcamento.setValor(rs.getBigDecimal("orc.valor"));
            orcamento.setHorasPrevistas(rs.getInt("orc.horasPrevistas"));

            Funcionario funcionario = new Funcionario();
            funcionario.setId(rs.getInt("fun.id"));
            funcionario.setEmail(rs.getString("fun.email"));
            funcionario.setSenha(rs.getString("fun.password"));
            funcionario.setNome(rs.getString("fun.nome"));

            Cargo cargo = new Cargo();
            cargo.setId(rs.getInt("cargo.id"));
            cargo.setDescricao(rs.getString("cargo.descricao"));

            funcionario.setCargo(cargo);

            Cliente cliente = new Cliente();
            cliente.setId(rs.getInt("cli.id"));
            cliente.setEmail(rs.getString("cli.email"));
            cliente.setCpf(rs.getString("cli.cpf"));
            cliente.setNome(rs.getString("cli.nome"));
            cliente.setTelefone(rs.getString("cli.telefone"));

            orcamento.setFuncionario(funcionario);
            orcamento.setCliente(cliente);

            orcamentos.add(orcamento);

        }

        dbConenection.close();

        return orcamentos;

    }

    @Override
    public void save(Orcamento orcamento) throws SQLException{

        String comando = "insert into orcamento(data,valor,horasPrevistas,idFuncionario,idCliente) values (?,?,?,?,?)";

        Connection dbConenection = conexao.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setDate(1,new java.sql.Date(orcamento.getData().getTime()));
        preparedStatement.setBigDecimal(2,orcamento.getValor());
        preparedStatement.setInt(3,orcamento.getHorasPrevistas());
        preparedStatement.setInt(4,orcamento.getFuncionario().getId());
        preparedStatement.setInt(5,orcamento.getCliente().getId());

        int rs = preparedStatement.executeUpdate();



        if(orcamento.getServicos() != null){

            ServicoDao servicoDao = new ServicoDao();

            for(Object ob : orcamento.getServicos()){

                Servico servico = (Servico) ob;

                if(servico.getId() != null){

                    servicoDao.update(servico);

                }else{

                    servicoDao.save(servico,findIdByObject(orcamento));

                }

            }

        }

        dbConenection.close();

    }

    @Override
    public void update(Orcamento orcamento) throws SQLException{
        String comando = "update orcamento set valor = ?,horasPrevistas = ?,idFuncionario = ?,idCliente = ?  where id = ?";

        Connection dbConenection = conexao.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setBigDecimal(1,orcamento.getValor());
        preparedStatement.setInt(2,orcamento.getHorasPrevistas());
        preparedStatement.setInt(3,orcamento.getFuncionario().getId());
        preparedStatement.setInt(4,orcamento.getCliente().getId());
        preparedStatement.setInt(5,orcamento.getId());

        Integer rs = preparedStatement.executeUpdate();

        dbConenection.close();

    }

    @Override
    public void delete(Integer id) throws SQLException{
        String comando = "delete from orcamento where id = ?";

        deleteFromMm(id);

        Connection dbConenection = conexao.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setInt(1,id);

        int rs = preparedStatement.executeUpdate();

        dbConenection.close();

    }

    public Integer findIdByObject(Orcamento orcamento) throws SQLException {

        String comando = "select * from orcamento where data = ? and valor = ? and horasPrevistas = ? and idFuncionario = ? and idCliente = ?";

        Connection dbConenection = conexao.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setDate(1, new java.sql.Date(orcamento.getData().getTime()));
        preparedStatement.setBigDecimal(2, orcamento.getValor());
        preparedStatement.setInt(3, orcamento.getHorasPrevistas());
        preparedStatement.setInt(4, orcamento.getFuncionario().getId());
        preparedStatement.setInt(5, orcamento.getCliente().getId());
        ResultSet rs = preparedStatement.executeQuery();

        Integer id = null;

        if(rs.next()){

            id = rs.getInt("id");

        }

        dbConenection.close();

        return id;

    }

    public void deleteFromMm(Integer id) throws SQLException{
        String comando = "delete from tiposervico_orcamento where idOrcamento = ?";

        Connection dbConenection = conexao.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setInt(1,id);

        int rs = preparedStatement.executeUpdate();
        dbConenection.close();

    }

}
