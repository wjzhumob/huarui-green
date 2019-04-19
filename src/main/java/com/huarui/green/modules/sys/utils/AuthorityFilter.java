package com.huarui.green.modules.sys.utils;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.huarui.common.config.Global;
import com.huarui.common.utils.ResultState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@Activate(group = Constants.PROVIDER)
public class AuthorityFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (Global.getConfig("dubbo.whitelist.enable").equals("false")) {
            LOGGER.debug("白名单已禁用");
            return invoker.invoke(invocation);
        }
        String clientIp = RpcContext.getContext().getRemoteHost();
        List<String> allowedIps = Arrays.asList(Global.getConfig("dubbo.whitelist.ip").split(","));
        if (allowedIps.contains(clientIp)) {
            return invoker.invoke(invocation);
        } else {
            return new RpcResult(ResultState.getTokenState("EERROR").output("此IP: "+clientIp+"禁止访问服务"));
        }
    }
}
