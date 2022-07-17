<template>
  <el-menu
      router
      :default-active="$route.path"
      active-text-color="#ff9900"
  >
    <template v-for="(menu, menuId) in $router.options.routes">
      <!-- 菜单不隐藏,hidden=false。 -->
      <template v-if="!menu.meta.hidden">

        <!-- 判断是否只有一个节点 -->
        <el-menu-item v-if="menu.meta.leaf" :index="menu.redirect" :key="menuId">
          <i class="el-icon-setting"></i>
          <span slot="title">{{menu.meta.title}}</span>
        </el-menu-item>

        <!-- 多个节点 -->
        <el-submenu v-else :index="menu.path" :key="menuId">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span>{{menu.meta.title}}</span>
          </template>
          <el-menu-item-group>
            <el-menu-item v-for="(child,childId) in menu.children" :key="childId"
                          :index="menu.path +'/'+ child.path">
              {{child.meta.title}}
            </el-menu-item>
          </el-menu-item-group>
        </el-submenu>

      </template>
    </template>


  </el-menu>
</template>
