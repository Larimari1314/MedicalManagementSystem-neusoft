<template>
  <div class="sidebar">
    <el-menu class="sidebar-el-menu"
             background-color="#324157"
             text-color="#bfcbd9"
             active-text-color="#20a0ff"
             unique-opened router>
      <template v-for="item in state.items">
        <template v-if="item.subs">
          <el-submenu :index="item.index" :key="item.index">
            <template #title>
              <i :class="item.icon"></i>
              <span>{{ item.title }}</span>
            </template>
            <template v-for="subItem in item.subs">
              <el-submenu v-if="subItem.subs" :index="subItem.index" :key="subItem.index">
                <template #title>{{ subItem.title }}</template>
                <el-menu-item v-for="(threeItem, i) in subItem.subs" :key="i" :index="threeItem.index">
                  {{ threeItem.title }}
                </el-menu-item>
              </el-submenu>
              <el-menu-item v-else :index="subItem.index" :key="subItem.index + 1">{{ subItem.title }}
              </el-menu-item>
            </template>
          </el-submenu>
        </template>
        <template v-else>
          <el-menu-item :index="item.index" :key="item.index">
            <i :class="item.icon"></i>
            <template #title>{{ item.title }}</template>
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>
<script setup>
import {reactive} from "vue";
import {useStore} from "vuex";

const Store = useStore();
const state = reactive({
  items: [
    {
      icon: "el-icon-date",
      index: "3",
      title: "学生模块",
      subs: [{
        index: "list",
        title: "学生管理",
      },
        {
          index: "upload",
          title: "文件上传",
        },
      ],
    }
  ]
});
</script>
<style scoped>
.sidebar {
  display: block;
  position: absolute;
  left: 0;
  top: 70px;
  bottom: 0;
  overflow-y: scroll;
  background: pink;
}

.sidebar::-webkit-scrollbar {
  width: 0;
}

.sidebar-el-menu:not(.el-menu--collapse) {
  width: 250px;
}

.sidebar > ul {
  height: 100%;
  text-align: left;
}
</style>
