package com.thatjamesemo.depend;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.Arrays;
import java.util.List;

public class PermissionChecker {

    public boolean hasPermissionMember(Long guildId, Member member, int level) {
        ConfigFile cf = new ConfigFile(guildId);
        int count = 0;

        List<Role> memberRoles = member.getRoles();

        List<Object[]> roleperms = (List<Object[]>) cf.getOptionAsList("roleperms");
        while(count <= level) {
            List<Object> rolesAtLevel = Arrays.asList(roleperms.get(count));
            for(Object s: rolesAtLevel) {
                if(memberRoles.contains(s)) {
                    return true;
                } else {
                    count += 1;
                }
            }
        }
        return false;
    }
}
