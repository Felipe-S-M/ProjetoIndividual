<template>
  <div class="align-self-center">
    <button @click="showModal('criarFuncionario')" style="height: 45px;" class="btn-default btn-primary rounded">Criar Funcionario</button>

    <div style="height: auto;width: auto;display: block;margin-top: 15px;text-align: center;" class="rounded">
      <div style="background-color: white;height: 86%;width: 100%;display: block;" class="align-self-center rounded">
        <Tabela :url="'http://localhost:8080/buscarFuncionario'" ref="tabelaAjax"></Tabela>
      </div>
      <b-modal ref="criarFuncionario" @hide="reload()" hide-footer title="Criar Funcionario">

        <template #modal-header>
          <div class="mx-auto">
            <h5>Criar Funcionario</h5>
          </div>
        </template>

        <form class="col-12">
          <div class="card-body">
            <label>Nome</label>
            <input v-model="nome" class="form-control">
          </div>
          <div class="card-body">
            <label>Email</label>
            <input v-model="email" placeholder="seuemail@emailless.com" class="form-control">
          </div>
          <div class="card-body">
            <label>Senha</label>
            <input v-model="senha" :type="'password'" class="form-control">
          </div>
          <div class="card-body">
            <label>Cargo</label>
            <select v-model="cargoSelecionado" class="form-control">
              <option v-for="cargo in cargos" :value="cargo" :key="cargo.id">
                {{ cargo.descricao }}
              </option>
            </select>
          </div>
          <div class="card-body">
            <button @click="hideModal('criarFuncionario')" type="button" class="btn btn-secondary float-left">Fechar</button><button @click="criarFuncionario()" type="button" class="btn btn-success float-right">Criar</button>
          </div>
        </form>
      </b-modal>
      <b-modal ref="editar" hide-footer onclose="reload()" title="Editar Funcionario">

        <template #modal-header>
          <div class="mx-auto">
            <h5>Editar Funcionario</h5>
          </div>
        </template>

        <form class="col-12">
          <div class="card-body">
            <label>Nome</label>
            <input v-model="nome" class="form-control">
          </div>
          <div class="card-body">
            <label>Email</label>
            <input v-model="email" placeholder="seuemail@emailless.com" class="form-control">
          </div>
          <div class="card-body">
            <label>Senha</label>
            <input v-model="senha" :type="'password'" class="form-control">
          </div>
          <div class="card-body">
            <label>Cargo</label>
            <select v-model="cargoSelecionado" class="form-control">
              <option v-for="cargo in cargos" :value="cargo" :key="cargo.id">
                {{ cargo.descricao }}
              </option>
            </select>
          </div>
          <div class="card-body">
            <button @click="hideModal('editar')" type="button" class="btn btn-secondary float-left">Fechar</button><button @click="editar()" type="button" class="btn btn-success float-right">Salvar</button>
          </div>
        </form>
      </b-modal>
    </div>

    <b-modal ref="confirmacaoExclusaoFuncionario" hide-footer>

      <template #modal-header>
        <div class="mx-auto">
          <h5>Confirmar</h5>
        </div>
      </template>

      <form class="col-12">

        <div class="card-body">
          Tem certeza que deseja excluir o funcionario?
        </div>

        <div class="card-body">
          <button @click="hideModal('confirmacaoExclusaoFuncionario')" type="button" class="btn btn-secondary float-left">Fechar</button><button @click="excluirFuncionario()" type="button" class="btn btn-success float-right">Confirmar</button>
        </div>

      </form>
    </b-modal>

  </div>
</template>

<script>
import Tabela from "./Tabela";
export default {
  name: "Funcionario",
  components: {Tabela},
  data: function(){
    return {
      id:null,
      email: null,
      senha: null,
      nome: null,
      cargoSelecionado:null,
      cargos: []
    }
  },
  methods:{
    criarFuncionario() {
      var ref = this

      window.$.ajax({
        method: "POST",
        url: "http://localhost:8080/criarConta",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({email:ref.email,senha:ref.senha,nome:ref.nome,cargo:ref.cargoSelecionado?.id}),
        success: function (result) {

          alert("Funcionario criado com sucesso!")
          ref.hideModal('criarFuncionario')
          ref.reload();

        },
        error: function (result){

          alert(result.responseText)

        }
      });

    },
    editar(){

      var ref = this

      window.$.ajax({
        method: "PUT",
        url: "http://localhost:8080/editarFuncionario",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({id:ref.id,email:ref.email,senha:ref.senha,nome:ref.nome,cargo:ref.cargoSelecionado?.id}),
        success: function (result) {

          alert("Funcionario atualizado com sucesso!")
          ref.hideModal('editar')
          ref.reload();

        },
        error: function (result){

          alert(result.responseText)

        }
      });

    },
    excluir(id) {

      var ref = this
      ref.id = id
      ref.showModal('confirmacaoExclusaoFuncionario')
    },
    showModal(modaiId,acao) {
      var ref = this
      if(acao=='reload'){
        ref.reload();
      }
      ref.$refs[modaiId].show()

    },
    hideModal: function(modaiId) {

      this.$refs[modaiId].hide()

    },
    abrirPopupEditar(funcionario){

      var ref = this
      ref.id = funcionario.id
      ref.senha = funcionario.senha
      ref.nome = funcionario.nome
      ref.email = funcionario.email
      ref.cargoSelecionado = funcionario.cargo
      ref.showModal('editar');

    },
    reload(){

      var ref = this;
      ref.$refs.tabelaAjax.request();
      ref.id = null
      ref.senha = null
      ref.nome = null
      ref.email = null
      ref.cargoSelecionado = null

    },
    buscarCargos(){
      var ref = this

      window.$.ajax({
        method: "GET",
        url: "http://localhost:8080/buscarCargos",
        contentType: "application/json",
        success: function (result) {

          ref.cargos = result.result

        },
        error: function (result){

          alert(result.responseText)

        }
      });
    },

    excluirFuncionario() {

      var ref = this

      window.$.ajax({
        method: "DELETE",
        url: "http://localhost:8080/deletarFuncionario",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({id: ref.id}),
        success: function (result) {

          alert("Funcionario excluido com sucesso!")
          ref.hideModal('editar')
          ref.hideModal('confirmacaoExclusaoFuncionario')
          ref.reload();

        },
        error: function (result) {

          alert(result.responseText)

        }
      });
    },

  },
  mounted() {
    this.buscarCargos();
  }
}
</script>
