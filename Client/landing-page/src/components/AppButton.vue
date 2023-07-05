<template>
    <button class="app-button" :class="type" @click="$emit('click')">
        <slot></slot>
    </button>
</template>
  
<script>
export default {
    props: {
        type: {
            type: String,
            default: "primary",
        },
    },
};
</script>
  
<style scoped>
.app-button {
    padding: 12px 24px;
    font-size: 16px;
    border-radius: 4px;
    cursor: pointer;
}

.primary {
    background-color: #007bff;
    color: #fff;
    border: none;
}

.secondary {
    background-color: #6c757d;
    color: #fff;
    border: none;
}
</style>
  