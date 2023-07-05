<template>
  <div class="container">
    <h1>¡Descarga nuestra aplicación móvil!</h1>
    <p>Haz clic en el botón de abajo para descargar la aplicación.</p>
    <download-button id="download-button" apk-url="/app-debug.apk">Descargar</download-button>
  </div>
  <div class="image-container">
    <img src="./assets/reference_image.png" alt="App reference image" class="image" />
  </div>
</template>

<script>
import DownloadButton from "./components/DownloadButton.vue";

export default {
  components: {
    DownloadButton,
  },
};
</script>

<style>
.container {
  max-width: 600px;
  margin: 0 auto;
  padding: 40px;
  text-align: center;
}

.image-container {
  width: 100%;
  text-align: center;
  margin-top: 40px;
}

.image {
  max-width: 100%;
  margin: 0 auto;
  display: block;
}
</style>
