<template>
<div class="main">
    <div class="row">
        <div class="col d-flex justify-content-center" v-for="photo in photos" 
                                :key="photo.id" 
                                :class="photo.visible ? ' ' : 'd-none'">
            <div class="card m-5 p-3">
                <div class="card-img d-flex justify-content-center">
                    <img  :src="photo.url" class="card-img-top img-fluid"> 
                </div>
                <div class="card-body">
                    <h3>{{photo.title}}</h3>
                    <div>
                        <p class="card-text text-muted" v-if="photo.description == null">Nessuna descrizione</p>
                        <p class="card-text" v-else>{{photo.description}}</p>
                        <h5>tag: {{photo.tag}}</h5>
                    </div>
                </div>
                <div v-if="photo.categories">
                    <div v-if="photo.categories.length > 0" >
                        <h6 class="">Categorie: </h6>
                        <p class="text-uppercase" v-for="category in photo.categories" :key="category.id">{{category.name}} </p>
                    </div>
                    <div v-else>Nessuna Categoria</div>
                </div>
                <button v-else @click="getPhotoCategories(photo.id)" class="btn btn-primary btn-sm text-uppercase font-monospace fw-semibold">show category</button>        
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
            const index = this.getPhotoIndexById( photoId);
            const photo = this.photos[index];
            photo.categories = categories; 
            this.photos.splice(index, 1 , photo);
            console.log(this.photos[index].categories);
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