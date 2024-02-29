<template>
  <div class="container-fluid p-2">
    <!-- Carousel -->
    <div id="carousel-shower" v-if="showCarousel">

      <b-row no-gutters class="carousel-container mb-4">
        <b-col cols="12">
          <b-carousel
              id="carousel-1"
              class="carousel-fade"
              v-model="slide"
              :interval="4000"
              controls
              indicators
              background="#ababab"
              img-width="1024"
              img-height="480"
              style="text-shadow: 1px 1px 2px #333;"
          >
            <b-carousel-slide
                v-for="(book, index) in books"
                :key="index"
                :caption="book.titulo"
                :img-src="book.portada || 'https://via.placeholder.com/420x640.png?text=Sin%20Imagen'"
                :img-alt="book.titulo"
            >
              <p>{{ book.autor }}</p>
            </b-carousel-slide>
          </b-carousel>
        </b-col>
      </b-row>
    </div>


    <!-- Controls -->
    <b-row no-gutters class="controls-container">
      <b-col cols="12" lg="3" class="px-2 mb-2">
        <b-button @click="getAllBooksBy('autor')" variant="primary" block>Ordenar por autor</b-button>
      </b-col>
      <b-col cols="12" lg="3" class="px-2 mb-2">
        <b-button @click="getAllBooksBy('fecha')" variant="primary" block>Ordenar por fecha</b-button>
      </b-col>
      <b-col cols="12" lg="3" class="px-2 mb-2">
        <b-button @click="getAllBooksBy('imagen')" variant="primary" block>Mostrar si tiene imagen</b-button>
      </b-col>
      <b-col cols="12" lg="3" class="px-2 mb-2">
        <b-button v-b-modal.modal-create-book variant="info" block>
          <b-icon icon="plus-circle-fill" class="mr-2"></b-icon>
        </b-button>
        <ModalCreateBook @book-created="getAllBooks"/>
        <ModalEditBook @book-created="getAllBooks" :book="draggedBook"/>
      </b-col>
    </b-row>

    <!-- Books -->
    <b-row no-gutters class="books-container my-3">
      <b-col cols="12" lg="8">
        <b-row no-gutters>
          <b-col cols="12" md="6" lg="4" class="px-2 mb-2" v-for="(book, index) in books" :key="index">
            <b-card
                :title="book.titulo"
                :img-src="book.portada || 'https://via.placeholder.com/420x640.png?text=Sin%20Imagen'"
                img-alt="Image"
                img-top
                tag="article"
                draggable="true"
                @dragstart="handleDragStart(book, $event)"
            >
              <b-card-text>{{ book.autor }}</b-card-text>
              <b-card-text>
                <small class="text-muted
                  ">Fecha de publicación: {{ book.fechaPublicacion }}</small>
              </b-card-text>
            </b-card>
          </b-col>
        </b-row>
      </b-col>

      <b-col cols="12" lg="4">
        <b-row no-gutters>
          <b-col cols="12">
            <div class="edit-zone"
                 @dragover.prevent
                 @drop="handleDropOnEditZone"
            >
              <b-icon icon="pencil-fill" class="mr-2"></b-icon>
            </div>
          </b-col>
          <b-col cols="12">
            <div class="delete-zone"
                 @dragover.prevent
                 @drop="handleDropOnDeleteZone"
            >
              <b-icon icon="trash-fill" class="mr-2"></b-icon>
            </div>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import Vue from "vue";
import ModalCreateBook from "@/components/ModalCreateBook.vue";
import ModalEditBook from "@/components/ModalEditBook.vue";
import BookService from "@/services/BookService.js";

export default Vue.extend({
  name: "Home",
  components: {
    ModalCreateBook,
    ModalEditBook,
  },
  data() {
    return {
      // Carousel
      slide: 0,
      showCarousel: true,
      carouselHidden: false,

      // Books
      draggedBook: null,
      books: [
        {
          id: 1,
          title: "El principito",
          author: "Antoine de Saint-Exupéry",
          image: `https://picsum.photos/1024/480?random=${Math.random() * 100}`
        }
      ],

      // Paginator
      sortBy: "title",
      sortDesc: false,
      perPage: 20,
      currentPage: 1,
    };
  },

  methods: {
    async getAllBooks() {
      this.books = [];
      try {
        const data = await BookService.getAllBooks(
            parseInt(this.currentPage) - 1,
            parseInt(this.perPage),
            this.sortBy,
        );
        this.books = data.content;
      } catch (error) {
        console.error(error);
      }
    },

    async getAllBooksBy(sort) {
      switch (sort) {
        case 'autor':
          const dataByAuthor = await BookService.getBookByAutor();
          this.books = dataByAuthor.content;
          break;
        case 'fecha':
          const dataByDate = await BookService.getBookByFecha();
          this.books = dataByDate.content;
          break;
        case 'imagen':
          const dataByImage = await BookService.getBookByImagen();
          this.books = dataByImage.content;
          break;
      }
    },

    handleScroll() {
      let scroll = window.scrollY;

      console.log(scroll);

      // Verifica si el carrusel ya se ha ocultado y el scroll es mayor a 500 para no hacer nada
      if (this.carouselHidden && scroll > 500) return;

      // Si el scroll es menor o igual a 500 y el carrusel estaba oculto, permite que se muestre
      if (scroll <= 500) {
        this.showCarousel = true;
        this.carouselHidden = false; // Restablece la bandera cuando el usuario sube
      } else {
        // Si el scroll es mayor a 500, oculta el carrusel y establece la bandera
        this.showCarousel = false;
        this.carouselHidden = true; // Marca que el carrusel se ha ocultado
      }
    },

    handleDragStart(book, event) {
      this.draggedBook = book;
      event.dataTransfer.setData('text/plain', book.title);
    },

    handleDropOnEditZone(event) {
      event.preventDefault();
      const bookId = event.dataTransfer.getData("bookId");
      const bookToEdit = this.books.find(book => book.id === bookId) || this.draggedBook;
      if (bookToEdit) {
        this.openEditModal(bookToEdit);
      }
    },

    async handleDropOnDeleteZone(event) {
      event.preventDefault();
      const bookId = event.dataTransfer.getData("bookId");
      const bookToDelete = this.books.find(book => book.id === bookId) || this.draggedBook;
      if (bookToDelete) {
        const data = await BookService.deleteBook(bookToDelete);
        if (data) {
          await this.getAllBooks();
        }
      }
    },

    openEditModal(book) {
      this.$bvModal.show('modal-edit-book');
    }
  },

  mounted() {
    this.getAllBooks();
    window.addEventListener('scroll', this.handleScroll);
  },

  beforeDestroy() {
    window.removeEventListener('scroll', this.handleScroll);
  }
});

</script>

<style scoped>
.carousel-container {
  margin: 0 auto;
  max-width: 1024px;
}

.controls-container {
  margin-top: 50px;
}

.card {
  height: 100%;
}

.hidden {
  display: none;
}

.edit-zone {
  background-color: #d6d8d9;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 10px;
  border-radius: 5px;
  margin-bottom: 10px;
  height: 200px;
}

.delete-zone {
  background-color: #f8d7da;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 10px;
  border-radius: 5px;
  margin-bottom: 10px;
  height: 200px;
}
</style>