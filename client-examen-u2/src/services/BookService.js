import axios from 'axios';

let API_URL = 'http://localhost:8080/api/libro/';

const getAllBooks = async (page, size, sort) => {
    const params = {
        page,
        size,
        sort
    };
    try {
        const response = await axios.post(API_URL, params);
        return response.data;
    } catch (error) {
        throw error;
    }
}

const saveBook = async (book) => {
    try {
        const response = await axios.post(API_URL + "save", book);
        return response.data;
    } catch (error) {
        throw error;
    }
}

const deleteBook = async (book) => {
    try {
        const response = await axios.post(API_URL + "delete", book);
        return response.data;
    } catch (error) {
        throw error;
    }
}

const getBookByAutor = async () => {
    try {
        const response = await axios.get(API_URL + "autor");
        return response.data;
    } catch (error) {
        throw error;
    }
}

const getBookByFecha = async () => {
    try {
        const response = await axios.get(API_URL + "fecha");
        return response.data;
    } catch (error) {
        throw error;
    }
}

const getBookByImagen = async () => {
    try {
        const response = await axios.get(API_URL + "imagen");
        return response.data;
    } catch (error) {
        throw error;
    }
}

export default {
    getAllBooks,
    saveBook,
    deleteBook,
    getBookByAutor,
    getBookByFecha,
    getBookByImagen
}