<template>
  <div style="border-bottom:2px;border-bottom-style:solid;border-bottom-color:#ff0000;">
    <div class="sidebar">
      <div class="sidebar-backdrop" @click="closeSidebarPanel" v-if="isPanelOpen"></div>
      <transition name="slide">
        <div v-if="isPanelOpen" class="sidebar-panel bg-dark" style="color:white;">
          <ul class="nav sidebar-nav">
            <li><button @click="$parent.abrirHome()" class="list-group-item list-group-item-action bg-dark" style="color: white;">Home</button></li>
            <li><button @click="$parent.abrirCliente()" class="list-group-item list-group-item-action bg-dark" style="color: white;">Cliente</button></li>
            <li><button :disabled="!permissoesAdmin" @click="$parent.abrirFuncionario()" class="list-group-item list-group-item-action bg-dark" style="color: white;">Funcionario</button></li>
            <li><button @click="$parent.abrirOrcamento()" class="list-group-item list-group-item-action bg-dark" style="color: white;">Orçamento</button></li>
            <li><button @click="$parent.abrirOrdemServico()" class="list-group-item list-group-item-action bg-dark" style="color: white;">Os</button></li>
            <li><button :disabled="!permissoesAdmin" @click="$parent.abrirRelatorio()" class="list-group-item list-group-item-action bg-dark" style="color: white;">Relatório</button></li>
          </ul>
        </div>
      </transition>
    </div>

    <nav class="navbar navbar-dark" style="background-color:white;">

      <button class="navbar-toggler float-right bg-dark" type="button" @click="isPanelOpen=true"><span class="navbar-toggler-icon"></span></button>
      <h4 style="color: black;margin-right: 45%;">Fixing Solutions</h4>
    </nav>

  </div>

</template>

<script>
export default {
  name: "Header",
  props:{
    permissoesAdmin:null
  },
  data: function (){
    return{
      isPanelOpen: false
    }
  },
  methods:{
    closeSidebarPanel() {
      this.isPanelOpen = false
    }
  }
}
</script>

<style scoped>

.slide-enter-active,
.slide-leave-active
{
  transition: transform 0.2s ease;
}

.slide-enter,
.slide-leave-to {
  transform: translateX(-100%);
  transition: all 150ms ease-in 0s
}

.sidebar-backdrop {
  background-color: rgba(0,0,0,.5);
  width: 100vw;
  height: 100vh;
  position: fixed;
  top: 0;
  left: 0;
  cursor: pointer;
}

.sidebar-panel {
  overflow-y: auto;
  background-color: #130f40;
  position: fixed;
  left: 0;
  top: 0;
  height: 100vh;
  z-index: 999;
  padding: 3rem 20px 2rem 20px;
  width: 300px;
}
li{
  position: relative;
  line-height: 20px;
  display: inline-block;
  width: 100%;
}
</style>