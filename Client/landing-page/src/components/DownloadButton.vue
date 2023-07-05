<template>
    <app-button :type="type" @click="downloadApk">
        <slot></slot>
    </app-button>
</template>
  
<script>
import AppButton from "./AppButton.vue";

export default {
    components: {
        AppButton,
    },
    props: {
        type: {
            type: String,
            default: "primary",
        },
        apkUrl: {
            type: String,
            required: true,
        },
    },
    methods: {
        downloadApk() {
            window.open(this.apkUrl, "_blank");
        },
    },
};
</script>
  