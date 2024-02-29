<template>
  <b-modal
      id="modal-edit-book"
      title="Editar libro"
      ok-title="Guardar"
      cancel-title="Cancelar"
      @ok="handleOk"
      @cancel="handleCancel"
      @shown="handleShow"
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
  name: 'ModalEditBook',
  props: {
    book: {
      type: Object,
    }
  },
  data() {
    return {
      title: '',
      author: '',
      year: '',
      month: '',
      day: '',
      image: ''
    }
  },
  methods: {
    handleShow() {
      this.title = this.book.titulo
      this.author = this.book.autor
      this.image = this.book.portada,
          this.year = this.book.fechaPublicacion.split('-')[0]
      this.month = this.book.fechaPublicacion.split('-')[1]
      this.day = this.book.fechaPublicacion.split('-')[2]
    },
    async handleOk() {
      const publicationDate = `${this.year}-${this.month}-${this.day}`
      const book = {
        titulo: this.title,
        autor: this.author,
        fechaPublicacion: publicationDate,
        portada: this.image
      };
    },
    handleCancel() {
      this.title = ''
      this.author = ''
      this.publicationDate = ''
      this.image = ''
    }
  },
})
</script>

<style scoped>

</style>