package com.line.backstage.config;

import com.line.backstage.service.UserInfoService;
import com.line.backstage.shiro.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * @author scxfsc
 */
@Component
public class SystemRealm extends AuthorizingRealm {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return null != token && token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userId = JwtUtil.getUserId(principals.toString());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //FIXME 待优化，用户授权信息缓存
        Map info = userInfoService.getUserInfo(userId);
        // 获取用户角色集
        simpleAuthorizationInfo.setRoles((Set<String>) info.get("roles"));
        // 获取用户权限字符集
        simpleAuthorizationInfo.setStringPermissions((Set<String>) info.get("perms"));
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 这里的 token是从 JWTFilter 的 executeLogin 方法传递过来的，已经经过了解密
        String token = (String) authenticationToken.getCredentials();

        String userId = JwtUtil.getUserId(token);
//        String username = JwtUtil.getUsername(token);

        if (userId == null) {
            throw new AuthenticationException("token失效");
        }
        //FIXME 通过用户ID查询用户信息，可加入缓存
//        UserInfo user = userInfoService.queryById(Integer.valueOf(userId));
//        if (user == null) {
//            throw new AuthenticationException("账号不存在");
//        }
//
//        if (user.getUserForbidFlag().equals((DataEnum.USER_FORBID_FLAG.getCode()))){
//            throw new AuthenticationException("账号已被禁用");
//        }
//
//        if (!JwtUtil.verifyToKen(token, userId, user.getUserPassword())){
//            throw new AuthenticationException("用户名或密码错误");
//        }
        return new SimpleAuthenticationInfo(token, token, getName());
    }
}
