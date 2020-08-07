<!-- 싱글 파일 컴포넌트 체계: HTML 파일에서 Vue 코드 작성 시 HTML코드 분석이 어렵고, 오탈자 찾기 어려움.
따라서, .vue 파일로 프로젝트 구조를 구성함. .vue 파일 1개는 뷰 애플리케이션을 구성하는 1개의 컴포넌트와 동일 -->
<template lang="html">
  <div>
    <span>
      <button>{{ message }}</button>
    </span>
  </div>
</template>

<script>
export default {
  data: {
    message: 'click this button'
  }
}
</script>

<style>
  span {
    font-size:1.2em;
  }
</style>
