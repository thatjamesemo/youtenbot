package com.thatjamesemo.depend;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;

public class PermissionChecker {

    public static boolean hasPermissionMember(Guild guild, Member member, int level) {
        ConfigFile cf = new ConfigFile(guild.getIdLong());

        if (guild.getOwner() == member) {
            return true;
        }

        List<String> memberRoles = member.getRoles().stream().map(role -> String.valueOf(role.getIdLong())).toList();
        List<Object> roleperms = cf.getOptionAsList("roleperms");

        for (int i = 0; i <= level; i++) {
            List<Object> rolesAtLevel = (List<Object>) roleperms.get(i);
            for (Object s : rolesAtLevel) {
                if (memberRoles.contains(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<String> getPermissionRoles(Long guildId, int level) {
        ConfigFile cf = new ConfigFile(guildId);

        List<Object> roleperms = cf.getOptionAsList("roleperms");

        return (List<String>) roleperms.get(level);
    }
}
