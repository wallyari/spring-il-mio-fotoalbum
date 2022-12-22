<template>
<div class="main">
    <div class="row">
        <div class="col-12 d-flex justify-content-center">
            <input type="search" 
                    class="form-control mr-sm-2 me-1" 
                    style="width: 18rem;" 
                    placeholder="search" 
                    v-model="searchValue">
            <button 
                    type="submit" 
                    class="btn btn-primary" 
                    @click="searchPhoto()">Search
            </button>
        </div>
        <div class="col d-flex justify-content-center" v-for="photo in photos" 
                                :key="photo.id" 
                                :class="photo.visible ? ' ' : 'd-none'">
            <div class="card m-5 p-3 justify-content-center">
                <div class="card-img d-flex">
                    <img :src="photo.url" class="card-img-top img-fluid"> 
                </div>
                <div class="card-body">
                    <h3>{{photo.title}}</h3>
                    <div class="mb-5">
                        <p class="card-text text-muted" v-if="photo.description == null">Nessuna descrizione</p>
                        <p class="card-text" v-else>{{photo.description}}</p>
                        <h5 class="mb-2 bg-light text-dark rounded p-2">tag: #{{photo.tag}}</h5>
                    </div>
                    <div>
                        <h4>Commenta:</h4>
                        <div class="d-flex mb-2">
                            <input type="text" class="form-control me-2">
                            <button class="btn btn-primary font-monospace fw-semibold">SEND</button>
                        </div>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item bg-light" 
                                v-for="comment in photo.comments" 
                                :key="comment.id">
                                <span>{{comment.text}}</span>
                            </li>
                        </ul>
                    </div>
                </div>
                <div v-if="photo.categories">
                    <div v-if="photo.categories.length > 0" >
                        <h6>Categorie: </h6>
                        <p class="text-uppercase" v-for="category in photo.categories" :key="category.id">{{category.name}} </p>
                    </div>
                    <div v-else>Nessuna Categoria</div>
                </div>
                <button v-else @click="getPhotoCategories(photo.id)" class="btn btn-primary btn-sm text-uppercase font-monospace fw-semibold col-2">show category</button>
            </div>
        </div>
    </div>
</div>
</template>

<script>
    import axios from 'axios';

    const API_URL = 'http://localhost:8090/api/1/';
    const ACTIVE_INDEX  = -1;

    export default {
    name: 'MyMain',

    data() {
        return {
        photos: [ ],
        activePhotoIndex: ACTIVE_INDEX,
        searchValue: '',

        }
    },
    methods: {
        getPhotos() {
        axios.get(API_URL + 'photos')
            .then(response => {
            this.photos = response.data;
            })
            .catch(error => {
            console.log(error);
            });
        },
        searchPhoto(){
            console.log('hai scritto: ',this.searchValue)
            if(this.searchValue === '') return this.getPhotos();
            axios.get(API_URL + 'photos/search/' + this.searchValue)
            .then(response => {
            this.photos = response.data;
            })
            .catch(error => {
            console.log(error);
            });
    },
    getPhotoIndexById(id) {
        for (let i=0; i<this.photos.length; i++) {
            const photo = this.photos[i];
            if (photo.id == id){
                return i;
            }
        }
        return -1;
    },
        getPhotoCategories(photoId) {
        axios.get(API_URL + "category/by/photo/" + photoId)
        .then(response => {
            const categories = response.data
            if (categories == null) return
            const index = this.getPhotoIndexById(photoId);
            const photo = this.photos[index];
            photo.categories = categories; 
            this.photos.splice(index, 1 , photo);
            console.log(this.photos[index].categories);
        })
        .catch(error => {
        console.log(error)
        })
    }, 
        getPhotoComments(photoId) {
        axios.get(API_URL + "/comments/by/photo/" + photoId)
        .then(response => {
            const photoComments = response.data
            if (photoComments == null) return
            const index = this.getPhotoIndexById(photoId);
            const photo = this.photos[index];
            photo.photoComments = photoComments; 
            this.photos.splice(index, 1 , photo);
            console.log(this.photos[index]);
        })
        .catch(error => {
        console.log(error)
        })
    }, 
},
mounted() {
    this.getPhotos();
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>