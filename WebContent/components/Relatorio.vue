<template>
  <div class="d-flex justify-content-center">
    <transition name="slide-fade">
      <div style="border:1px;border-style:inset;border-color:#ff0000;border-radius: 3px;height: 30%;width: 380px;display: block;" class="d-flex align-self-center justify-content-center rounded">
        <form class="col-12 form-inline align-self-center">

          <div class="form-group">
            <label for="dataIniPeriodo">Data início do periodo</label>
            <input type="date" id="dataIniPeriodo" v-model="dataIniPeriodo" class="form-control" style="margin-left:10px;">
          </div>

          <div class="form-group" style="margin-top: 20px;">
            <label>Data fim do periodo</label>
            <input style="margin-left:25px;" type="date" v-model="dataFimPeriodo" class="form-control">
          </div>

          <button type="button" @click="gerarRelatorio" class="btn btn-primary" style="margin-top:15px;margin-left: 220px;">Gerar relatório</button>

        </form>
      </div>
    </transition>
  </div>
</template>

<script>
export default {
  name: "Relatorio",
  data: function(){
    return {
      dataFimPeriodo:null,
      dataIniPeriodo:null
    }
  },
  methods: {
    gerarRelatorio(){

      var ref = this

      window.$.ajax({
        method: "POST",
        url: "http://localhost:8080/relatorio",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({dataFimPeriodo:ref.dataFimPeriodo,dataIniPeriodo:ref.dataIniPeriodo}),
        success: function (result) {

          window.open(require('../static/'+result.result[0].fileName), '_blank')

        },
        error: function (result) {

          alert(result.responseText)

        }
      });

    }
  }
}
</script>

<style scoped>
.slide-fade-enter-active {
  transition: all .3s ease;
}
.slide-fade-enter, .slide-fade-leave-to {
  transform: translateX(10px);
  opacity: 0;
}
</style>