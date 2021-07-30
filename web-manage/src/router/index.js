import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
    // ]
  },

  {
    path: '/newpass',
    name: 'newpass',
    noCache: false,
    component: () => import('@/views/newpass/index'),
    hidden: true
  },
  //
  // {
  //   path: '/404',
  //   component: () => import('@/views/404'),
  //   hidden: true
  // },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'component' }
    }]
  },

  {
    path: '/eerchant',
    component: Layout,
    redirect: 'noRedirect',
    meta: {
      title: '系统管理',
      icon: 'excel'
    },
    children: [
      {
        path: 'index',
        component: () => import('@/views/eerchant/index'),
        name: 'eerchant',
        meta: {
          title: '组织机构',
          noCache: true
        }
      },
      {
        path: 'recharge',
        name: 'Recharge',
        component: () => import('@/views/recharge/index'),
        meta: { title: '角色copy管理' }
      },
      {
        path: 'transaction',
        name: 'transaction',
        component: () => import('@/views/transaction/index'),
        meta: { title: '用户管理' }
      },
      {
        path: 'role',
        name: 'role',
        noCache: true,
        component: () => import('@/views/role/index'),
        meta: { title: '菜单管理' }
      }

    ]
  },
  {
    path: '/form',
    component: Layout,
    redirect: 'noRedirect',
    meta: {
      title: '用户管理',
      icon: 'excel'
    },
    children: [
      {
        path: 'index',
        name: 'index',
        component: () => import('@/views/form/index'),
        meta: { title: '用户列表' }
      },
      {
        path: 'team',
        name: 'team',
        component: () => import('@/views/form/team'),
        meta: { title: '我的团队' }
      },{
        path: 'cashout',
        name: 'cashout',
        component: () => import('@/views/form/cashout'),
        meta: { title: '提现列表' }
      },

      {
        path: 'cashin',
        name: 'cashin',
        component: () => import('@/views/form/cashin'),
        meta: { title: '充值列表' }
      },
      {
        path: 'bankcard',
        name: 'bankcard',
        component: () => import('@/views/form/bankcard'),
        meta: { title: '银行卡管理' }
      },
      {
        path: 'tool',
        name: 'tool',
        component: () => import('@/views/form/tool'),
        meta: { title: '辅助工具' }
      },
    ]
  },{
    path: '/table',
    component: Layout,
    redirect: 'noRedirect',
    meta: {
      title: '报表管理',
      icon: 'excel'
    },
    children: [
      {
        path: 'bonus',
        name: 'bonus',
        component: () => import('@/views/table/bonus'),
        meta: { title: '红利报表' }
      },
      {
        path: 'people',
        name: 'people',
        component: () => import('@/views/table/people'),
        meta: { title: '个人报表' }
      },{
        path: 'money',
        name: 'money',
        component: () => import('@/views/table/money'),
        meta: { title: '资金报表' }
      },

      {
        path: 'commission',
        name: 'commission',
        component: () => import('@/views/table/commission'),
        meta: { title: '佣金报表' }
      },
    ]
  },
  {
    path: '/orderMan',
    component: Layout,
    redirect: 'noRedirect',
    meta: {
      title: '订单管理',
      icon: 'list'
    },
    children: [
      {
        path: 'orderWater',
        name: 'orderWater',
        component: () => import('@/views/orderMan/orderWater'),
        meta: { title: '订单流水' }
      },
      {
        path: 'pingCang',
        name: 'pingCang',
        component: () => import('@/views/orderMan/pingCang'),
        meta: { title: '平仓日志' }
      },  {
        path: 'positionVo',
        name: 'posiVo',
        component: () => import('@/views/orderMan/positionVo'),
        meta: { title: '用户持仓' }
      },
    ]
  },
  {
    path: '/role',
    component: Layout,
    redirect: 'noRedirect',
    meta: {
      title: '系统设置',
      icon: 'clipboard'
    },
    children: [
      {
        path: 'system',
        name: 'system',
        noCache: true,
        component: () => import('@/views/system/index'),
        meta: { title: '系统账户设置' }
      },
      {
        path: 'safe',
        name: 'safe',
        noCache: true,
        component: () => import('@/views/safe/index'),
        meta: { title: '安全设置' }
      },
      {
        path: 'role',
        name: 'role',
        noCache: true,
        component: () => import('@/views/role/index'),
        meta: { title: '菜单管理' }
      },
      {
        path: 'menu',
        name: 'menus',
        component: () => import('@/views/role/menu'),
        meta: { title: '角色管理' }
      },
      {
        path: 'bank',
        name: 'bank',
        component: () => import('@/views/role/bank'),
        meta: { title: '收款银行卡' }
      },
      {
        path: 'setMoney',
        name: 'setMoney',
        component: () => import('@/views/role/setMoney'),
        meta: { title: '手续费设置' }
      },
    ]
  },
  {
    path: '/childpage',
    component: Layout,
    redirect: 'noRedirect',
    children: [
      {
        path: 'homes',
        name: 'homes',
        component: () => import('@/views/childpage/homes'),
        meta: { title: 'child首页', icon: 'component' }
      },
    ]
  },
  {
    path: '/customer-service',
    component: Layout,
    redirect: 'noRedirect',
    children: [
      {
        path: 'index',
        name: 'index',
        component: () => import('@/views/customer-service/index'),
        meta: { title: '客服系统', icon: 'people' }
      },
    ]
  },
  {
    path: '/wosen',
    component: Layout,
    redirect: 'noRedirect',
    meta: {
      title: '沃森管理',
      icon: 'excel'
    },
    children: [
      {
        path: 'index',
        name: 'index',
        component: () => import('@/views/wosen/index'),
        meta: { title: '沃森', icon: 'component' }
      },
    ]
  },


  // { path: '*', redirect: '/404', hidden: true }
]

//   [转发自用户 嘉哥]
// /
// * 动态路由配置：1、将后台的路由表进行格式化
// * @param {Object} asyncRouterMap 菜单
// */
export function dynamicRouter(asyncRouterMap) {
  // console.log('src/router/index.js中:动态路由数据格式化')
  // console.log(asyncRouterMap)
  const accessedRouters = []
  if (asyncRouterMap) {
    asyncRouterMap.forEach((item, index) => {
      console.log(item)
      const parent = generateRouter(item, true)
      const children = []
      if (item.children) {
        item.children.forEach((child, index) => {
          children.push(generateRouter(child, false))
        })
      }
      parent.children = children
      accessedRouters.push(parent)
    })
  }
  accessedRouters.push({
    path: '*',
    redirect: '/404',
    hidden: true
  })
  return accessedRouters
}

// /
// * 动态路由配置：2、递归处理数据结构
// * @param {Object} item
// * @param {Object} isParent
// */
function generateRouter(item, isParent) {
  const router = {
    path: item.path,
    name: item.name,
    meta: item.meta,
    // componentsMap数组中查找
    component: isParent ? Layout : componentsMap[item.name]
  }
  if (item.isParent) {
    // 如果设置为true，将始终显示根菜单
    router.alwaysShow = true
  }
  if (item.redirect) {
    // 如果设置为no redirect，则breadcrumb中不会重定向
    router.redirect = item.redirect
  }
  return router
}

/**
 * 本地组件映射关系
 */
export const componentsMap = {

  roleIndex: () => import('@/views/role/index'),
  OfficeIndex: () => import('@/views/orderMan/office'),
  AdminIndex: () => import('@/views/form/admin'),
  MenuIndex: () => import('@/views/role/menu'),
  RoleIndex: () => import('@/views/orderMan/role'),
  LogIndex: () => import('@/views/orderMan/orderWater')
}

const createRouter = () => new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
