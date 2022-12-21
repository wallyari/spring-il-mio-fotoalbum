<template>
  <div class="header">
  <header th:fragment="header-nav">
    <nav class="navbar navbar-light bg-light justify-content-between font-monospace fw-semibold">
        <a class="navbar-brand ms-3">Spring ALBUM</a>
      <div class="navbar-nav">
        <a class="btn btn-danger btn-sm me-5 fw-semibold text-center" href="http://localhost:8090/photo">Admin Area</a>
      </div>
    </nav>
  </header>
  </div>
</template>

<script>
export default {
  name: 'MyHeader',
  data() {
        return {     
        }
    }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>