<template>
  <b-modal
      id="modal-create-book"
      title="Registrar libro"
      @ok="handleOk"
      @cancel="handleClose"
      @hide="handleClose"
      @close="handleClose"
      ok-title="Registrar"
      cancel-title="Cancelar"
      no-stacking
      centered
  >
    <b-form>
      <b-form-group
          label="Título"
          label-for="title"
      >
        <b-form-input
            id="title"
            v-model="title"
            required
        />
      </b-form-group>
      <b-form-group
          label="Autor"
          label-for="author"
      >
        <b-form-input
            id="author"
            v-model="author"
            required
        />
      </b-form-group>
      <b-row no-gutters>
        <b-col cols="4">
          <b-form-group
              label="Año"
              label-for="year"
          >
            <b-form-input
                id="year"
                type="number"
                placeholder="yyyy"
                v-model="year"
                required
            ></b-form-input>
          </b-form-group>
        </b-col>
        <b-col cols="4">
          <b-form-group
              label="Mes"
              label-for="month"
          >
            <b-form-input
                id="month"
                type="number"
                placeholder="mm"
                v-model="month"
                required
            ></b-form-input>
          </b-form-group>
        </b-col>
        <b-col cols="4">
          <b-form-group
              label="Día"
              label-for="day"
          >
            <b-form-input
                id="day"
                type="number"
                placeholder="dd"
                v-model="day"
                required
            ></b-form-input>
          </b-form-group>
        </b-col>
      </b-row>

      <b-form-group
          label="Imagen"
          label-for="image"
      >
        <b-form-input
            id="image"
            v-model="image"
            required
        />
      </b-form-group>
    </b-form>
  </b-modal>
</template>

<script>
import Vue from 'vue'

export default Vue.extend({
  name: 'ModalCreateBook',
  data() {
    return {
      title: '',
      author: '',
      image: '',

      year: '',
      month: '',
      day: '',
    }
  },
  methods: {
    async handleOk() {
      const publicationDate = `${this.year}-${this.month}-${this.day}`
      const book = {
        titulo: this.title,
        autor: this.author,
        fechaPublicacion: publicationDate,
        portada: this.image
      }
    },
    handleClose
        () {
      this.title = ''
      this.author = ''
      this.year = ''
      this.month = ''
      this.day = ''
      this.image = ''
    }
    ,
  }
})
</script>

<style scoped>

</style>