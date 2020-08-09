<template>
    <div class="inputBox shadow">
      <input type="text" v-model="newTodoItem" placeholder="Type what you have to do" v-on:keyup.enter="addTodo">
      <span class="addContainer" v-on:click="addTodo">
        <i class="addBtn fas fa-plus" aria-hidden="true"></i>
      </span>
      <modal v-if="showModal" @close="showModal = false">
        <h3 slot="header">경고</h3>
        <span slot="body" @click="showModal = false">
          할 일을 입력하세요.
          <i class="closeModalBtn fas fa-times" aria-hidden="true"></i>
        </span>
        <span slot="footer"></span>
      </modal>
    </div>
</template>

<script>
import Modal from './common/Modal.vue'

export default {
  data() {
    return {
      newTodoItem:'',
      showModal: false
    }
  },
  methods: {
    addTodo() {
      // input 박스에 입력 값이 있을 때만 저장
      if(this.newTodoItem !== "") {
        // 입력된 텍스트의 앞뒤 공백 문자열 제거
        var value = this.newTodoItem && this.newTodoItem.trim();
        // console.log(this.newTodoItem);
        // 입력받은 텍스트를 로컬 스토리지의 setItem() API를 이용하여 저장(키, 값)
        // localStorage.setItem(value, value);
        this.$emit('addTodo',value);
        // input 박스 입력 값 초기화
        this.clearInput();
      } else {
        this.showModal = !this.showModal;
      }

    },
    clearInput() {
      this.newTodoItem = '';
    }
  },
  components: {
    Modal: Modal
  }
}
</script>

<style scoped>
  input:focus {
    outline:none;
  }
  .inputBox {
    background-color: white;
    height:50px;
    line-height: 50px;
    border-radius: 5px;
  }
  .inputBox input {
    border-style: none;
    font-size: 0.9rem;
  }
  .addContainer {
    float: right;
    background: linear-gradient(to right, #6478FB, #8763FB);
    display: block;
    width: 3rem;
    border-radius: 0 5px 5px 0;
  }
  .addBtn {
    cursor: pointer;
    color:white;
    vertical-align: middle;
  }
</style>
