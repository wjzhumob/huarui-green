package com.huarui.green.impl;

import com.huarui.common.entity.sys.Role;
import com.huarui.common.entity.sys.User;
import com.huarui.common.utils.StringUtils;

import java.util.Iterator;

public class BaseServicImpl {
    
    public static String dataScopeFilter(User user, String o, String p) {
        StringBuilder sqlString = new StringBuilder();
        //List<String> dataScope = Lists.newArrayList();
        if (!user.isAdmin()) {
            Iterator roleList = user.getRoleList().iterator();

            while(roleList.hasNext()) {
                Role r = (Role) roleList.next();
                String[] offices = StringUtils.split(o, ",");
                int officeLength = offices.length;

                for (int i = 0; i < officeLength; ++i) {
                    String oa = offices[i];
                    if (/*!dataScope.contains(r.getDataScope()) &&*/ StringUtils.isNotBlank(oa)) {
                        if ("4".equals(r.getDataScope())) {
                            sqlString.append(" OR " + oa + ".id = '" + user.getOffice().getId() + "'");
                            sqlString.append(" OR " + oa + ".parent_ids LIKE '" + user.getOffice().getParentIds() + user.getOffice().getId() + ",%'");
                        } else if ("5".equals(r.getDataScope())) {
                            sqlString.append(" OR " + oa + ".id = '" + user.getOffice().getId() + "'");
                        } else if ("9".equals(r.getDataScope())) {
                            sqlString.append(" OR EXISTS (SELECT 1 FROM sys_role_office WHERE role_id = '" + r.getId() + "'");
                            sqlString.append(" AND office_id = " + oa + ".id)");
                        }
                        //dataScope.add(r.getDataScope());
                    }
                }
                if ("8".equals(r.getDataScope())) {
                    sqlString.append(" OR " + p + ".create_by = '" + user.getId() + "'");
                    sqlString.append(" OR o.id = '" + user.getOffice().getId() + "'");
                }
            }
        }
        return StringUtils.isNotBlank(sqlString.toString()) && !sqlString.toString().equals("null") ? " AND (" + sqlString.substring(4) + ")" : "";
    }
}
