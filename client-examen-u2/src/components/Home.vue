<template>
  <div class="container-fluid p-2">
    <!-- Carousel -->
    <div id="carousel-shower" v-show="showCarousel">
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
                :caption="book.title"
                :img-src="book.image"
                :img-alt="book.title"
            >
              <p>{{ book.author }}</p>
            </b-carousel-slide>
          </b-carousel>
        </b-col>
      </b-row>

      <!-- Controls -->
      <b-row no-gutters class="controls-container">
        <b-col cols="12" lg="3" class="px-2 mb-2">
          <b-button variant="primary" block>Ordenar por autor</b-button>
        </b-col>
        <b-col cols="12" lg="3" class="px-2 mb-2">
          <b-button variant="primary" block>Ordenar por fecha</b-button>
        </b-col>
        <b-col cols="12" lg="3" class="px-2 mb-2">
          <b-button variant="primary" block>Mostrar si tiene imagen</b-button>
        </b-col>
        <b-col cols="12" lg="3" class="px-2 mb-2">
          <b-button v-b-modal.modal-create-book variant="info" block>
            <b-icon icon="plus-circle-fill" class="mr-2"></b-icon>
          </b-button>
          <ModalCreateBook/>
          <ModalEditBook :book="draggedBook"/>
        </b-col>
      </b-row>

      <!-- Books -->
      <b-row no-gutters class="books-container my-3">
        <b-col cols="12" lg="8">
          <b-row no-gutters>
            <b-col cols="12" md="6" lg="4" class="px-2 mb-2" v-for="(book, index) in books" :key="index">
              <b-card
                  :title="book.title"
                  :img-src="book.image"
                  img-alt="Image"
                  img-top
                  tag="article"
                  draggable="true"
                  @dragstart="handleDragStart(book, $event)"
              >
                <b-card-text>{{ book.author }}</b-card-text>
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
  </div>
</template>

<script>
import Vue from "vue";
import ModalCreateBook from "@/components/ModalCreateBook.vue";
import ModalEditBook from "@/components/ModalEditBook.vue";

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

      // Books
      draggedBook: null,
      books: []
    };
  },

  methods: {
    handleScroll() {
      let scroll = window.scrollY;
      let carouselTop = document.getElementById('carousel-shower').offsetTop;
      let carouselHeight = document.getElementById('carousel-shower').offsetHeight;
      let carouselBottom = carouselTop + carouselHeight;

      this.showCarousel = scroll <= carouselBottom;
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

    handleDropOnDeleteZone(event) {
      event.preventDefault();
      const bookId = event.dataTransfer.getData("bookId");
      const bookToDelete = this.books.find(book => book.id === bookId) || this.draggedBook;
      if (bookToDelete) {
        this.books = this.books.filter(book => book.id !== bookToDelete.id);
      }
    },

    openEditModal(book) {
      this.$bvModal.show('modal-edit-book');
    }
  },

  mounted() {
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